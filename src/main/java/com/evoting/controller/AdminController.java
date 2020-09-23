package com.evoting.controller;

import com.evoting.dto.BarChart;
import com.evoting.dto.DoughNutChart;
import com.evoting.model.*;
import com.evoting.service.*;
import com.evoting.util.DateUtil;
import com.evoting.util.ViewUtil;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class AdminController {


    private static UserService userService = new UserService();
    private static AdminService adminService = new AdminService();
    private static StatesService statesService = new StatesService();
    private static DashBoardService dashBoardService = new DashBoardService();
    private static ElectionCommissionService electionCommissionService = new ElectionCommissionService();

    private static Gson gson = new Gson();

    public static Route serveVoteCount = (Request request, Response response) -> {
        Map<String, BarChart> model = new HashMap<>();
        //States Bar
        BarChart br1 = new BarChart();
        List<String> states = new ArrayList<>();
        List<Long> sVote = new ArrayList<>();
        dashBoardService.countVotedByState().forEach((k, v) -> {
            states.add(k.getName());
            sVote.add(v);
        });
        br1.setLabel(states);
        br1.setData(sVote);
        model.put("State", br1);

        //Party Bar
        BarChart br2 = new BarChart();
        List<String> party = new ArrayList<>();
        List<Long> pVote = new ArrayList<>();
        dashBoardService.countVotedByParty().forEach((k, v) -> {
            party.add(k.getName());
            pVote.add(v);
        });
        br2.setLabel(party);
        br2.setData(pVote);
        model.put("Party", br2);

        //Candidate Bar
        BarChart br3 = new BarChart();
        List<String> candidate = new ArrayList<>();
        List<Long> cVote = new ArrayList<>();
        electionCommissionService.countVoted().forEach((k, v) -> {
            candidate.add(k.getName());
            cVote.add(v);
        });
        br3.setLabel(candidate);
        br3.setData(cVote);
        model.put("Candidate", br3);

        return gson.toJson(model);
    };

    public static Route serveCountTotalDashBoard = (Request request, Response response) -> {
        Map<String, Integer> model = new HashMap<>();
        model.put("totalParty", dashBoardService.totalParty());
        model.put("totalCandidate", dashBoardService.totalCandidate());
        model.put("totalVoter", dashBoardService.totalVoter());
        return gson.toJson(model);
    };

    public static Route serveVoteResult = (Request request, Response response) -> {
        List<DoughNutChart> result = new ArrayList<>();
        Map<States, List<VotingResult>> nestedMap = dashBoardService.findAllVotingResult().stream()
                .collect(
                        Collectors.groupingBy(
                                VotingResult::getStates,
                                Collectors.mapping(
                                        VotingResult::getObject,
                                        toList()
                                )
                        )

                );

        nestedMap.forEach((s, v) -> {
            String name = s.getName();
            List<String> label = new ArrayList<>();
            List<Integer> data = new ArrayList<>();
            List<String> party = new ArrayList<>();
            for (VotingResult votingResult : v) {
                label.add(votingResult.getCandidate().getName());
                data.add(votingResult.getVoteCount());
                party.add(votingResult.getCandidate().getParty().getName());
            }
            DoughNutChart doughNutChart = new DoughNutChart(name, label, data, party);
            result.add(doughNutChart);
        });

        return gson.toJson(result);
    };

    public static Route getAllUser = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("users", userService.findAllVoter());

        User profile = request.session().attribute("currentUser");
        model.put("profile", profile);
        return ViewUtil.render(model, "Admin/table-list.hbs");
    };

    public static Route getUserById = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        System.out.println(id);
        User user = userService.findById(id);
        model.put("user", user);

        User profile = request.session().attribute("currentUser");
        model.put("profile", profile);
        return ViewUtil.render(model, "Admin/voter-profile.hbs");
    };

    public static Route getImageByUserId = (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        User user = userService.findById(id);
        response.body();
        return user.getPhoto();
    };

    public static Route updateVoterById = (Request request, Response response) -> {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        User user = userService.findById(Integer.parseInt(request.queryParams("id")));
        System.out.println(Integer.parseInt(request.queryParams("id")));
        Part filePart = request.raw().getPart("photo");
        user.setLastName(request.raw().getParameter("lastName"));
        System.out.println(request.raw().getParameter("lastName"));
        user.setFirstName(request.raw().getParameter("firstName"));
        user.setUsername(request.raw().getParameter("username"));
        user.setEmail(request.raw().getParameter("email"));
        user.setAddress(request.raw().getParameter("address"));
        user.setPhone(request.raw().getParameter("phone"));
        adminService.updateUserById(user, filePart.getInputStream());
        response.redirect("/admin/states-dashboard"); //TODO: redirect to Admin index
        return null;
    };

    public static Route serveRegisterVoter = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("states", statesService.findAllStates());

        return ViewUtil.render(model, "Admin/voter-register-form.hbs");
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
        user.setBirthday(DateUtil.textToDate(request.raw().getParameter("birthday")));
        user.setGender(request.raw().getParameter("gender"));
        adminService.addVoter(user, filePart.getInputStream());
        response.redirect("/admin/states-dashboard"); //TODO: redirect to Admin index

        return null;
    };

    public static Route serveDoughNutDashBoard = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        //model.put("states",statesService.findAllStates());

        User profile = request.session().attribute("currentUser");
        model.put("profile", profile);
        return ViewUtil.render(model, "Admin/states-dashboard.hbs");
    };

    public static Route serveBarChartDashBoard = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        //model.put("states",statesService.findAllStates());
        User profile = request.session().attribute("currentUser");
        model.put("profile", profile);
        return ViewUtil.render(model, "Admin/barchart-dashboard.hbs");
    };
}
