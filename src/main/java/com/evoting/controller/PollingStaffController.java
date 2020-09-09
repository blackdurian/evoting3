package com.evoting.controller;

import com.evoting.model.Candidate;
import com.evoting.model.Party;
import com.evoting.model.User;
import com.evoting.service.PollingStaffService;
import com.evoting.service.UserService;
import com.evoting.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.List;
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
        model.put("voter",user);// TODO: Validation To Cast Once Only approve buttom
        return ViewUtil.render(model,"PollingStaff/voter-profile.hbs");
    };

    public static Route getImageByCandidateId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        Candidate candidate = pollingStaffService.findCandidateById(id);
        response.body();
        return candidate.getProfileImg();
    };

    public static Route getImageByPartyId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        Party party = pollingStaffService.findPartyById(id);
        response.body();
        return party.getLogo();
    };

    public static Route getImageByVoterId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        User user = pollingStaffService.findVoterById(id);
        response.body();
        return user.getPhoto();
    };

    public static Route prepareVote = (Request request, Response response) -> {
        Integer id = Integer.parseInt(request.queryParams("id"));
        User user = pollingStaffService.findVoterById(id);
        pollingStaffService.prepareVote(user);// TODO validation prepare onece only
        response.redirect("/pollingStaff/candidate-campaign?id=" +id.toString());

        return null;
    };

    public static Route getCandidateOptionByVoterId =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        User user = pollingStaffService.findVoterById(id);
        model.put("candidates", pollingStaffService.getCandidateOptionsByUser(user));
        //model.put("parties", )TODO:parties
        return ViewUtil.render(model, "PollingStaff/candidate-competition-page.hbs");
    };
}
