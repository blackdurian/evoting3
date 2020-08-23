package com.evoting.util;

import com.evoting.model.Role;
import com.evoting.model.User;
import spark.*;
import static com.evoting.util.RequestUtil.*;
import static spark.Spark.halt;

public class Filters {

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
      response.redirect("/login.hbs");
      halt();
    }else if(user.getRole() != Role.Admin){
      halt(404, "You have no permission");
    }
  };

  public static Filter handleElectionCommissionRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    if (user == null) {
      response.redirect("/login.hbs");
      halt();
    }else if(user.getRole() != Role.Election_Commission){
      halt(404, "You have no permission");
    }
  };

  public static Filter handlePollingStaffRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    if (user == null) {
      response.redirect("/login.hbs");
      halt();
    }else if(user.getRole() != Role.Polling_Staff){
      halt(404, "You have no permission");
    }
  };

  public static Filter handleVoterRole = (Request request, Response response) -> {
    User user = request.session().attribute("currentUser");
    if (user == null) {
      response.redirect("/login.hbs");
      halt();
    }else if(user.getRole() != Role.Voter){
      halt(404, "You have no permission");
    }
  };
}
