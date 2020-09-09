package com.evoting.util;

import com.evoting.model.Candidate;
import com.evoting.model.Role;
import com.evoting.model.User;
import com.evoting.model.VoteStatus;
import com.evoting.service.ElectionCommissionService;
import com.evoting.service.VoterService;
import spark.*;
import static com.evoting.util.RequestUtil.*;
import static spark.Spark.halt;
import static spark.Spark.redirect;

public class Filters {
  private static ElectionCommissionService electionCommissionService = new ElectionCommissionService();
  private static VoterService voterService = new VoterService();
  // If a user manually manipulates paths and forgets to add
  // a trailing slash, redirect the user to the correct path
  public static Filter addTrailingSlashes = (Request request, Response response) -> {
    if (!request.pathInfo().endsWith("/")) {
      response.redirect(request.pathInfo() + "/");
    }
  };

  // Locale change can be initiated from any page
  // The locale is extracted from the request and saved to the user's session
  public static Filter handleLocaleChange = (Request request, Response response) -> {
    if (getQueryLocale(request) != null) {
      request.session().attribute("locale", getQueryLocale(request));
      response.redirect(request.pathInfo());
    }
  };

  // Enable GZIP for all responses
  public static Filter addGzipHeader = (Request request, Response response) -> {
    response.header("Content-Encoding", "gzip");
  };

  public static Filter handleAdminRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    if (user == null) {
      response.redirect("/login");
      halt();
    }else if(user.getRole() != Role.Admin){
      halt(404, "You have no permission");
      //todo logout and redirect to login
      //todo error page
    }
  };

  public static Filter handleElectionCommissionRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    if (user == null) {
      response.redirect("/login");
      halt();
    }else if(user.getRole() != Role.Election_Commission){
      halt(404, "You have no permission");
      //todo logout and redirect to login
      //todo error page
    }
  };

  public static Filter handlePollingStaffRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    if (user == null) {
      response.redirect("/login");
      halt();
    }else if(user.getRole() != Role.Polling_Staff){
      halt(404, "You have no permission");
      //todo logout and redirect to login
      //todo error page
    }
  };

  public static Filter handleVoterRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    String candidateId = request.queryParams("id");
    VoteStatus voteStatus = null;
  boolean isLogin;
    if (user != null) {
      isLogin = true;
      voteStatus = voterService.checkStatus(user);
    } else {
      isLogin = false;
    }
    boolean isVoting = candidateId!=null;

    if (isVoting){
      Candidate candidate = electionCommissionService.findCandidateById(Integer.parseInt(candidateId));
      if (candidate!=null && candidate.getStates()!=null){
        if (isLogin && !user.getStates().equals(candidate.getStates())){
          request.session().removeAttribute("dialogUrl");
          response.redirect("/voter/view-status");
          halt();
        }else if(isLogin && voteStatus!=VoteStatus.NOT_VOTED){
          request.session().removeAttribute("dialogUrl");
          response.redirect("/voter/view-status");
          halt();
        } else {
          String dialogUrl = request.url()+"?id="+candidateId;
          System.out.println(dialogUrl);
          request.session().attribute("dialogUrl", dialogUrl);
        }
      }
    }else {
      request.session().removeAttribute("dialogUrl");
    }

  if(isLogin && user.getRole() != Role.Voter){
    request.session().removeAttribute("currentUser");
    request.session().attribute("loggedOut", true);
    response.redirect("/login");
      halt(404, "You have no permission");
      //todo logout and redirect to login
      //todo error page
    }
    if (!isLogin) {
      response.redirect("/login");
      halt();
    }
  };
}
