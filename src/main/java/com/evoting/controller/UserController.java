package com.evoting.controller;

import com.evoting.model.User;
import com.evoting.service.LogInService;
import com.evoting.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {

    private static UserService userService = new UserService();

    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = userService.findByUsername(username);
        if (user == null) {
            return false;
        }
        return true;
    }
}
