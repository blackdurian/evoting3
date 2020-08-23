package com.evoting.controller;

import com.evoting.model.User;
import com.evoting.service.PollingStaffService;
import com.evoting.service.UserService;
import com.evoting.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class PollingStaffController {
    private static PollingStaffService pollingStaffService = new PollingStaffService();

    public static Route getAllVoter =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("voters",pollingStaffService.findAllVoters());
        return ViewUtil.render(model,"PollingStaff/table-list.hbs");
    };

    public static Route getVoterById =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        User user = pollingStaffService.findVoterById(id);
        model.put("voter",user);
        return ViewUtil.render(model,"PollingStaff/voter-profile.hbs");
    };

    public static Route getImageByVoterId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        User user = pollingStaffService.findVoterById(id);
        response.body();
        return user.getPhoto();
    };
}
