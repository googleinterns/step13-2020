package com.google.sps.servlets;

import java.io.IOException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/veri")
public class VerificationServlet extends HttpServlet {

  class Confirmation {
    
    String confirm;

    private Confirmation(String affirm) {
      confirm = affirm;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    Confirmation confirmation;
    
    if (userService.isUserLoggedIn()) {
      confirmation = new Confirmation("Y");
    } else {
      confirmation = new Confirmation("N");
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(confirmation));
  }
}