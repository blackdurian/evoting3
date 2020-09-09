package com.evoting.controller;

import com.evoting.model.Candidate;
import com.evoting.model.States;
import com.evoting.model.User;
import com.evoting.model.VoteStatus;
import com.evoting.service.ElectionCommissionService;
import com.evoting.service.VoterService;
import com.evoting.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

public class VoterController {

    private static ElectionCommissionService electionCommissionService = new ElectionCommissionService();
    private static VoterService voterService = new VoterService();

    public static Route getConfirmation =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        System.out.println(id);
        Candidate candidate = electionCommissionService.findCandidateById(id);
        model.put("candidate",candidate);
        return ViewUtil.render(model,"Voter/confirm-dialog.hbs");
    };

    public static Route getStatus =  (Request request, Response response) -> {
        User user = (User) request.session().attribute("currentUser");
        VoteStatus voteStatus = voterService.checkStatus(user);
        Map<String,Object> model = new HashMap<>();
        model.put("message",voteStatus.getMessage());
        model.put("status",voteStatus);
        model.put("user",user);
        return ViewUtil.render(model,"Voter/view-status.hbs");
    };

    public static Route handleVotingProcess = (Request request, Response response) -> {
        Integer candidateId = Integer.parseInt(request.queryParams("id"));
        System.out.println(candidateId.toString());

        boolean isConfirm = Boolean.parseBoolean(request.queryParams("vote"));
        Candidate candidate = electionCommissionService.findCandidateById(candidateId);
        System.out.println(candidate.getName());
        User user = (User) request.session().attribute("currentUser");
        if (isConfirm){
            voterService.voterChoice(user, candidate);
        }
        request.session().removeAttribute("dialogUrl");
        response.redirect("/voter/view-status"); //TODO: redirect to Admin index
        return null;
    };
}
