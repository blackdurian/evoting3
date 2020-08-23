package com.evoting.controller;

import com.evoting.dao.UserDao;
import com.evoting.model.Party;
import com.evoting.model.Role;
import com.evoting.model.States;
import com.evoting.model.User;
import com.evoting.service.AdminService;
import com.evoting.service.StatesService;
import com.evoting.service.UserService;
import com.evoting.util.DateUtil;
import com.evoting.util.ViewUtil;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

public class AdminController {

    private static UserService userService = new UserService();
    private static AdminService adminService = new AdminService();
    private static StatesService statesService = new StatesService();
  /*
    post("/sign-in", (req, res) -> {
        Map<String, String> model = new HashMap<>();
        String username = req.queryParams("username");
        res.cookie("username", username);
        model.put("username", username);
        return new ModelAndView(model, "sign-in.hbs");
    }, new HandlebarsTemplateEngine());*/


    public static Route getAllUser =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("users",userService.findAll());
        return ViewUtil.render(model,"Admin/table-list.hbs");
    };

    public static Route getUserById =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
       int id = Integer.parseInt(request.queryParams("id"));
       System.out.println(id);
         User user = userService.findById(id);
        model.put("user",user);
        return ViewUtil.render(model,"Admin/voter-profile.hbs");
    };

    public static Route getImageByUserId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        User user = userService.findById(id);
        response.body();
        return user.getPhoto();
    };

    public static Route updateVoterById = (Request request, Response response) -> {//TODO UPDATE ADMIN
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        User user = userService.findById(Integer.parseInt(request.queryParams("id")));
        Part filePart = request.raw().getPart("photo");
        user.setLastName(request.raw().getParameter("lastName"));
        user.setFirstName(request.raw().getParameter("firstName"));
        System.out.println(request.raw().getParameter("firstName"));
        user.setUsername(request.raw().getParameter("username"));
        user.setEmail(request.raw().getParameter("email"));
        user.setAddress(request.raw().getParameter("address"));
        user.setPhone(request.raw().getParameter("phone"));
        adminService.updateUserById(user, filePart.getInputStream());
        response.redirect("/view-user"); //TODO: redirect to Admin index
        return null;
    };

    public static Route serveRegisterVoter = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("states",statesService.findAllStates());
        return ViewUtil.render(model,"Admin/voter-register-form.hbs");
    };

    public static Route registerVoter = (Request request, Response response) -> {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        User user = new User();
        Part filePart = request.raw().getPart("photo");
        user.setLastName(request.raw().getParameter("lastName"));
        user.setFirstName(request.raw().getParameter("firstName"));
        user.setUsername(request.raw().getParameter("username"));
        user.setIc(request.raw().getParameter("ic"));
        user.setPassword(request.raw().getParameter("password"));
        user.setEmail(request.raw().getParameter("email"));
        user.setAddress(request.raw().getParameter("address"));
        user.setPhone(request.raw().getParameter("phone"));
        user.setStates(statesService.findByName(request.raw().getParameter("states")));
        user.setRole(Role.Voter);
        user.setBirthday(new DateUtil().textToDate(request.raw().getParameter("birthday")));
        user.setGender(request.raw().getParameter("gender"));
        adminService.addVoter(user,filePart.getInputStream());
        response.redirect("/view-user"); //TODO: redirect to Admin index

        return null;
    };
}
