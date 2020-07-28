package com.evoting.util;



import org.eclipse.jetty.http.*;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;


public class ViewUtil {

    // Renders a template given a model and a request
    // The request is needed to check the user session for language settings
    // and to see if the user is logged in
    public static String render(Request request, Map<String, Object> model, String templatePath) {
/*        model.put("msg", new MessageBundle(getSessionLocale(request)));
        model.put("currentUser", getSessionCurrentUser(request));
        model.put("WebPath", Path.Web.class); // Access application URLs from templates*/
        return strictVelocityEngine().render(new ModelAndView(model, templatePath));
    }





    private static HandlebarsTemplateEngine strictVelocityEngine() {
        return new HandlebarsTemplateEngine();
    }
}
