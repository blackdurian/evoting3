package com.evoting.controller;

import com.evoting.model.Candidate;
import com.evoting.model.CastingVote;
import com.evoting.model.Party;
import com.evoting.model.User;
import com.evoting.service.CastingVoteService;
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
import java.util.stream.Collectors;

public class PollingStaffController {
    private static PollingStaffService pollingStaffService = new PollingStaffService();
    private static CastingVoteService castingVoteService = new CastingVoteService();

    public static Route getAllVoter =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("voters",pollingStaffService.findAllVoters());
        return ViewUtil.render(model,"PollingStaff/table-list.hbs");
    };

    public static Route getVoterById =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        boolean hasApproved = false;
        int id = Integer.parseInt(request.queryParams("id"));
        User user = pollingStaffService.findVoterById(id);
        List<CastingVote> castingVotes = castingVoteService.findAll()
                .stream()
                .filter(e->e.getUser().equals(user))
                .collect(Collectors.toList());
        if (castingVotes.size()>0){
            hasApproved = true;
        }
        System.out.println(castingVotes.size());
        model.put("hasApproved", hasApproved);
        model.put("voter",user);
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
        List<CastingVote> castingVotes = castingVoteService.findAll()
                .stream()
                .filter(e->e.getUser().equals(user))
                .collect(Collectors.toList());
        if (castingVotes.size()==0){
            pollingStaffService.prepareVote(user);
            response.redirect("/pollingStaff/candidate-campaign?id=" +id.toString());
        }else {
            throw new RuntimeException("prepare once only");
        }
        return null;
    };

    public static Route getCandidateOptionByVoterId =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        User user = pollingStaffService.findVoterById(id);
        model.put("candidates", pollingStaffService.getCandidateOptionsByUser(user));
        return ViewUtil.render(model, "PollingStaff/candidate-competition-page.hbs");
    };

    public static Route serveDoughNutDashBoard = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        //model.put("states",statesService.findAllStates());
        return ViewUtil.render(model,"PollingStaff/states-dashboard.hbs");
    };

    public static Route serveBarChartDashBoard = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        //model.put("states",statesService.findAllStates());
        return ViewUtil.render(model,"PollingStaff/barchart-dashboard.hbs");
    };
}
