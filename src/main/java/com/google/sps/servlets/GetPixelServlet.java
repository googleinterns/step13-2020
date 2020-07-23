package com.google.sps.servlets;

import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import java.lang.Math;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/pixel")
public class GetPixelServlet extends HttpServlet {
  
  private final double WHITE = 255;

  class Pixels {
    private final double red;
    private final double green;
    private final double blue;
    private final String name;

    Pixels(double red, double green, double blue, String name) {
      this.red = red;
      this.green = green;
      this.blue = blue;
      this.name = name;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    UserService userService = UserServiceFactory.getUserService();

    String username = userService.getCurrentUser().getEmail();
    double red = WHITE;
    double green = WHITE;
    double blue = WHITE;

    Query query = new Query("PixelTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      if (username.equals((String) entity.getProperty("email"))) {
        red = (double) entity.getProperty("red");
        blue = (double) entity.getProperty("blue");
        green = (double) entity.getProperty("green");
      }
    }

    Gson gson = new Gson();

    Pixels data = new Pixels(red, green, blue, username);

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(data));
  }
}