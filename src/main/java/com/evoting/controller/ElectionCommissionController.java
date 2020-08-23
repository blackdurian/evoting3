package com.evoting.controller;

import com.evoting.model.*;
import com.evoting.service.ElectionCommissionService;
import com.evoting.service.PartyService;
import com.evoting.service.StatesService;
import com.evoting.util.DateUtil;
import com.evoting.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.util.HashMap;
import java.util.Map;

public class ElectionCommissionController {

    private static ElectionCommissionService electionCommissionService = new ElectionCommissionService();
    private static PartyService partyService = new PartyService();
    private static StatesService statesService = new StatesService();


    public static Route getAllCandidates =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("candidates",electionCommissionService.findAllCandidate());
        return ViewUtil.render(model,"ElectionCommission/table-list.hbs");
    };

    public static Route getCandidateById =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        System.out.println(id);
        Candidate candidate = electionCommissionService.findCandidateById(id);
        Party party = electionCommissionService.findPartyById(id);
        model.put("party",party);
        model.put("candidate",candidate);
        return ViewUtil.render(model,"ElectionCommission/candidate-profile.hbs");
    };

    public static Route getAllParties =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("parties",electionCommissionService.findAllParty());
        return ViewUtil.render(model,"ElectionCommission/party-table-list.hbs");
    };

    public static Route getPartyById =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        System.out.println(id);
        Party party = electionCommissionService.findPartyById(id);
        model.put("party",party);
        return ViewUtil.render(model,"ElectionCommission/party-profile.hbs");
    };

    public static Route getAllPollingStaff =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("pollingStaffs",electionCommissionService.findAllPollingStaff());
        return ViewUtil.render(model,"ElectionCommission/pollingstaff-table-list.hbs");
    };

    public static Route getPollingStaffById =  (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        int id = Integer.parseInt(request.queryParams("id"));
        System.out.println(id);
        User user = electionCommissionService.findPollingStaffById(id);
        model.put("pollingStaff",user);
        return ViewUtil.render(model,"ElectionCommission/pollingstaff-profile.hbs");
    };

    public static Route serveRegisterParty = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        return ViewUtil.render(model,"ElectionCommission/party-register-form.hbs");
    };

    public static Route registerParty = (Request request, Response response) -> {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        Party party = new Party();
        party.setName(request.raw().getParameter("name"));
        party.setSlogan(request.raw().getParameter("slogan"));
        Part filePart = request.raw().getPart("logo");
        electionCommissionService.addParty(party,filePart.getInputStream());
        response.redirect("/view-user"); //TODO: redirect to Admin index

        return null;
    };

    public static Route serveRegisterCandidate = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("parties",partyService.findAllParty());
        model.put("states",statesService.findAllStates());
        return ViewUtil.render(model,"ElectionCommission/candidate-registration-form.hbs");
    };

    public static Route registerCandidate = (Request request, Response response) -> {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        Candidate candidate = new Candidate();
        Part filePart = request.raw().getPart("profileImg");
        candidate.setName(request.raw().getParameter("name"));
        candidate.setParty(partyService.findByName(request.raw().getParameter("party")));
        candidate.setStates(statesService.findByName(request.raw().getParameter("states")));
        electionCommissionService.addCandidate(candidate,filePart.getInputStream());
        response.redirect("/view-user"); //TODO: redirect to Admin index

        return null;
    };

    public static Route serveRegisterPollingStaff = (Request request, Response response) -> {
        Map<String,Object> model = new HashMap<>();
        model.put("states",statesService.findAllStates());
        return ViewUtil.render(model,"ElectionCommission/pollingstaff-registration-form.hbs");
    };

    public static Route registerPollingStaff = (Request request, Response response) -> {
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
        request.raw().setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
        User user = new User();
        Part filePart = request.raw().getPart("photo");
        user.setLastName(request.raw().getParameter("lastName"));
        System.out.println(request.raw().getParameter("lastName"));
        user.setFirstName(request.raw().getParameter("firstName"));
        System.out.println(request.raw().getParameter("firstName"));
        user.setUsername(request.raw().getParameter("username"));
        System.out.println(request.raw().getParameter("username"));
        user.setIc(request.raw().getParameter("ic"));
        System.out.println(request.raw().getParameter("ic"));
        user.setPassword(request.raw().getParameter("password"));
        System.out.println(request.raw().getParameter("password"));
        user.setEmail(request.raw().getParameter("email"));
        System.out.println(request.raw().getParameter("email"));
        user.setAddress(request.raw().getParameter("address"));
        System.out.println(request.raw().getParameter("address"));
        user.setPhone(request.raw().getParameter("phone"));
        System.out.println(request.raw().getParameter("phone"));
        user.setStates(statesService.findByName(request.raw().getParameter("states")));
        System.out.println(request.raw().getParameter("states"));
        user.setRole(Role.Polling_Staff);
        user.setBirthday(new DateUtil().textToDate(request.raw().getParameter("birthday")));
        System.out.println(new DateUtil().textToDate(request.raw().getParameter("birthday")));
        System.out.println(request.raw().getParameter("birthday"));
        user.setGender(request.raw().getParameter("gender"));
        System.out.println(request.raw().getParameter("gender"));
        electionCommissionService.addPollingStaff(user,filePart.getInputStream());
        response.redirect("/view-user"); //TODO: redirect to Admin index
        return null;
    };

    public static Route getImageByCandidateId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        Candidate candidate = electionCommissionService.findCandidateById(id);
        response.body();
        return candidate.getProfileImg();
    };

    public static Route getImageByPartyId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        Party party = electionCommissionService.findPartyById(id);
        response.body();
        return party.getLogo();
    };

    public static Route getImageByPollingStaffId =  (Request request, Response response) -> {
        int id = Integer.parseInt(request.params("id"));
        User user = electionCommissionService.findPollingStaffById(id);
        response.body();
        return user.getPhoto();
    };
}

     /*   Collection<Part> parts = null;
        try {
            parts = request.raw().getParts();
            System.out.println("Title: " + request.raw().getParameter("name"));
            Map<String, String[]> parameterMap = request.raw().getParameterMap();
            System.out.println(parameterMap.toString());
        } catch (IOException | ServletException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        for (Part part : parts) {
            System.out.println("Name:" + part.getName());
            System.out.println("Size: " + part.getSize());
            System.out.println("Filename:" + part.getSubmittedFileName());
        }

        String fName = null;
        Part file = null;
        try {
            file = request.raw().getPart("logo");
            fName = request.raw().getPart("logo").getSubmittedFileName();
        } catch (IOException | ServletException e1) {
            e1.printStackTrace();
        }
*/


        /*System.out.println("1" + (String) request.attribute("name"));
        System.out.println("2" +request.body());
        System.out.println("3" +request.params("name"));
        System.out.println("4" +request.contentType());
        System.out.println("5" +request.raw());
        System.out.println("6" +request.queryParams("name"));
        System.out.println("1"+request.attributes());            // the attributes list
        System.out.println("2"+request.attribute("name"));         // value of foo attribute
        //System.out.println("3"+request.attribute("A", "V"));      // sets value of attribute A to V
        System.out.println("4"+request.body());;                   // request body sent by the client
        System.out.println("5"+request.bodyAsBytes());;            // request body as bytes
        System.out.println("6"+request.contentLength());;          // length of request body
        System.out.println("7"+request.contentType());;            // content type of request.body
        System.out.println("8"+request.contextPath());;            // the context path, e.g. "/hello"
        System.out.println("9"+request.cookies());;                // request cookies sent by the client
        System.out.println("10"+request.headers());;                // the HTTP header list
        System.out.println("11"+request.headers("name"));;           // value of BAR header
        System.out.println("12"+request.host());;                   // the host, e.g. "example.com"
        System.out.println("13"+request.ip());;                     // client IP address
        System.out.println("14"+request.params("name"));;            // value of foo path parameter
        System.out.println("15"+request.params());;                 // map with all parameters
        request.queryParams().forEach((v) -> System.out.println(v));
        System.out.println("16"+request.pathInfo());;               // the path info
        System.out.println("17"+request.port());;                   // the server port
        System.out.println("18"+request.protocol());;               // the protocol, e.g. HTTP/1.1
        System.out.println("19"+request.queryMap());;               // the query map
        System.out.println("20"+request.queryMap("name"));;          // query map for a certain parameter
        System.out.println("21"+request.queryParams());;            // the query param list
        System.out.println("22"+request.queryParams("name"));;       // value of FOO query param
        System.out.println("23"+request.queryParamsValues("name"));  // all values of FOO query param
        System.out.println("24"+request.raw());;                    // raw request handed in by Jetty
        System.out.println("25"+request.requestMethod());;          // The HTTP method (GET, ..etc)
        System.out.println("26"+request.scheme());;                 // "http"
        System.out.println("27"+request.servletPath());;            // the servlet path, e.g. /result.jsp
        System.out.println("28"+request.session());;                // session management
        System.out.println("29"+request.splat());;                  // splat (*) parameters
        System.out.println("30"+request.uri());;                    // the uri, e.g. "http://example.com/foo"
        System.out.println("31"+request.url());;                    // the url. e.g. "http://example.com/foo"
        System.out.println("32"+request.userAgent());;*/


