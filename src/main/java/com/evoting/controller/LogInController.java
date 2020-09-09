package com.evoting.controller;

import com.evoting.model.User;
import com.evoting.service.LogInService;
import com.evoting.service.UserService;
import com.evoting.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static com.evoting.util.RequestUtil.*;

public class LogInController {
    private static LogInService logInService = new LogInService();
    private static UserService userService = new UserService();
    public static Route serveLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(model,"log-in.hbs");
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        User user = userService.authenticate(request.queryParams("username"), request.queryParams("password"));
        if (user ==null) {
            model.put("authenticationFailed", true);
            response.redirect("login");
        }else{
            model.put("authenticationSucceeded", true);
            request.session().attribute("currentUser", user);
            switch (user.getRole()){
                case Admin:
                    request.session().removeAttribute("dialogUrl");
                    response.redirect("/admin/view-user");//TODO: Admin dashboard
                    break;
                case Voter:
                    String dialogUrl = request.session().attribute("dialogUrl");
                    //TODO: Voter dashboard
                    response.redirect(dialogUrl != null ? dialogUrl : "/voter/view-status");//TODO: Voter dashboard
                    break;
                case Polling_Staff:
                    request.session().removeAttribute("dialogUrl");
                    response.redirect("/pollingStaff/view-voters");//TODO: polling staff dashboard
                    break;
                case Election_Commission:
                    request.session().removeAttribute("dialogUrl");
                    response.redirect("/electionCommission/view-candidates");//TODO: EC dashboard
                    break;
                default:
                    request.session().removeAttribute("dialogUrl");
                    response.redirect("login");
            }
        }
        return null;
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("currentUser");
        request.session().removeAttribute("dialogUrl");
        request.session().attribute("loggedOut", true);
        response.redirect("login");
        return null;
    };

    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect("login");
        }
    };
}
