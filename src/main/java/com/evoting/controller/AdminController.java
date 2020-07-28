package com.evoting.controller;

import com.evoting.dao.UserDao;
import com.evoting.service.UserService;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class AdminController {
  private static UserService userService = new UserService();
  /*
    post("/sign-in", (req, res) -> {
        Map<String, String> model = new HashMap<>();
        String username = req.queryParams("username");
        res.cookie("username", username);
        model.put("username", username);
        return new ModelAndView(model, "sign-in.hbs");
    }, new HandlebarsTemplateEngine());*/


    public static Route allUser = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("users",new UserService().findAll());
        System.out.println(new UserService().findAll().toString());
        return new ModelAndView(model,"table-list.hbs");};

    public static Route registerUser = (Request request, Response response) -> {

        response.redirect("/admin");
        return null;
    };

}
