package com.evoting;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import com.evoting.controller.AdminController;
import com.evoting.dao.StatesDao;
import com.evoting.dao.UserDao;
import com.evoting.model.Role;
import com.evoting.model.States;
import com.evoting.model.User;
import com.evoting.service.StatesService;
import com.evoting.service.UserService;
import com.evoting.util.Filters;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Application {

  public static void main(String[] args) {
/*    States states = new States();
    states.setName("selangor");
    new StatesService().add(states);
    User user = new User("asd","asd","qweqwe","asdasd"
            ,3,"male","as12431"
            ,"asd",Role.Admin,"email@"
            ,"12415",states);

    new UserService().add(user);
    States states1 = new StatesService().registerStates("Selangor");
    States states2 =new StatesService().registerStates("Penang");*/

   /* System.out.println(states1.toString());
    System.out.println(states2.toString());*/

    // Configure Spark
    port(4567);
   // staticFiles.location("/public");
   // staticFiles.expireTime(600L);
  //  enableDebugScreen();
/*//dsfasdfsdf
    // Set up before-filters (called before each get/post)
    before("*",                  Filters.addTrailingSlashes);
    before("*",                  Filters.handleLocaleChange);

    // Set up routes
    get(Path.Web.INDEX,          IndexController.serveIndexPage);
    get(Path.Web.BOOKS,          BookController.fetchAllBooks);
    get(Path.Web.ONE_BOOK,       BookController.fetchOneBook);
    get(Path.Web.LOGIN,          LoginController.serveLoginPage);
    post(Path.Web.LOGIN,         LoginController.handleLoginPost);
    post(Path.Web.LOGOUT,        LoginController.handleLogoutPost);*/

    get("/hello", (Request request, Response response) -> {
      Map<String,Object> model = new HashMap<>();
      model.put("users",new UserService().findAll());
      System.out.println(new UserService().findAll().toString());
      return new ModelAndView(model,"table-list.hbs");},new HandlebarsTemplateEngine());

   // post("/register/",);

   // get("*",                     ViewUtil.notFound);

    //Set up after-filters (called after each get/post)
  //  after("*",                   Filters.addGzipHeader);

  }
}
