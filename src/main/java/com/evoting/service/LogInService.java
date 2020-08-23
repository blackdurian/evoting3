package com.evoting.service;

import com.evoting.dao.UserDao;
import spark.Request;

public class LogInService {
    private UserDao userDao;

    public LogInService() {
        userDao = new UserDao();
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }
}
