package com.evoting.util;



import com.evoting.model.User;
import org.eclipse.jetty.http.*;
import spark.*;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.*;

import static com.evoting.util.RequestUtil.getSessionCurrentUser;
import static com.evoting.util.RequestUtil.getSessionLocale;


public class ViewUtil {

    // Renders a template given a model and a request
    // The request is needed to check the user session for language settings
    // and to see if the user is logged in


    public static String render(Map<String, Object> model, String templatePath) {

        return new HandlebarsTemplateEngine().render(new ModelAndView(model, templatePath));
    }



    /*public static Route notFound = (Request request, Response response) -> {
        response.status(HttpStatus.NOT_FOUND_404);
        return render(request, new HashMap<>(), Path.Template.NOT_FOUND);
    };*/
}
