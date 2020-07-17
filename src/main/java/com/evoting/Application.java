package com.evoting;

import static spark.Spark.port;
import static spark.Spark.staticFiles;

import com.evoting.model.States;
import com.evoting.service.StatesService;
import com.evoting.util.Filters;
import com.evoting.util.Path;
import com.evoting.util.ViewUtil;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
public class Application {

  public static void main(String[] args) {
    States states1 = new StatesService().registerStates("Selangor");
    States states2 =new StatesService().registerStates("Penang");

    System.out.println(states1.toString());
    System.out.println(states2.toString());

    // Configure Spark
    port(4567);
    staticFiles.location("/public");
    staticFiles.expireTime(600L);
    enableDebugScreen();

    // Set up before-filters (called before each get/post)
    before("*",                  Filters.addTrailingSlashes);
    before("*",                  Filters.handleLocaleChange);

    // Set up routes
    get(Path.Web.INDEX,          IndexController.serveIndexPage);
    get(Path.Web.BOOKS,          BookController.fetchAllBooks);
    get(Path.Web.ONE_BOOK,       BookController.fetchOneBook);
    get(Path.Web.LOGIN,          LoginController.serveLoginPage);
    post(Path.Web.LOGIN,         LoginController.handleLoginPost);
    post(Path.Web.LOGOUT,        LoginController.handleLogoutPost);
    get("*",                     ViewUtil.notFound);

    //Set up after-filters (called after each get/post)
    after("*",                   Filters.addGzipHeader);

  }
}
