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

@WebServlet("/color")
public class ColorServlet extends HttpServlet {
  
  class Status {
    private final String end;

    Status(String end) {
      this.end = end;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    String username;
    String tone;
    int location = 0;
    double red = 0;
    double green = 0;
    double blue = 0;

    double[][] colorStats = {
        {118.67, 11.93, 74.00, 11.79, 57.67, 13.80},
        {159.67, 39.80, 107.00, 26.46, 78.33, 26.27},
        {175.67, 8.08, 125.67, 4.93, 95.33, 1.53},
        {192.67, 9.45, 136.33, 1.15, 101.67, 4.73},
        {205.00, 16.09, 149.00, 8.19, 119.00, 7.94},
        {200.00, 21.17, 151.67, 18.04, 126.00, 20.52},
        {204.33, 7.23, 177.33, 9.07, 169.67, 9.87},
        {212.67, 12.01, 180.00, 11.53, 159.67, 12.10}
    };


    UserService userService = UserServiceFactory.getUserService();

    if (!userService.isUserLoggedIn()) {
      //May want to add in an error message later like "ERROR: User is not logged in"
      Gson gson = new Gson();

      Status status = new Status("Failure"); 

      response.setContentType("application/json;");
      response.getWriter().println(gson.toJson(status));

      return;
    }

    username = userService.getCurrentUser().getEmail();

    red = Double.parseDouble(request.getParameter("r"));
    green = Double.parseDouble(request.getParameter("g"));
    blue = Double.parseDouble(request.getParameter("b"));

    double zScoreSums = Math.abs((red - colorStats[0][0]) / (colorStats[0][1])) + 
    Math.abs((green - colorStats[0][2]) / (colorStats[0][3])) + 
    Math.abs((blue - colorStats[0][4]) / (colorStats[0][5]));

    double scoreSum;

    for (int i = 1; i < colorStats.length; i++) {
      scoreSum = Math.abs((red - colorStats[i][0]) / (colorStats[i][1])) + 
      Math.abs((green - colorStats[i][2]) / (colorStats[i][3])) + 
      Math.abs((blue - colorStats[i][4]) / (colorStats[i][5]));

      if (scoreSum < zScoreSums) {
        zScoreSums = scoreSum;
        location = i;
      }
    }

    switch (location) {
      case 1:
        tone = "Brown";
        break;
      case 2:
        tone = "Dark-Tan";
        break;
      case 3:
        tone = "Tan";
        break;
      case 4:
        tone = "Light-Tan";
        break;
      case 5:
        tone = "Medium";
        break;
      case 6:
        tone = "Light";
        break;
      case 7:
        tone = "Fair";
        break;
      default:
        tone = "Dark";
    }

    long entityId = 0;
    boolean found = false;
    Query query = new Query("ColorTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      if (username.equals((String) entity.getProperty("email"))) {
        entityId = entity.getKey().getId();
        found = true;
      }
    }

    if (found) {
      Key entityKey = KeyFactory.createKey("ColorTest1", entityId);
      datastore.delete(entityKey);
    }

    found = false;

    query = new Query("PixelTest1");
    results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      if (username.equals((String) entity.getProperty("email"))) {
        entityId = entity.getKey().getId();
        found = true;
      }
    }

    if (found) {
      Key key = KeyFactory.createKey("PixelTest1", entityId);
      datastore.delete(key);
    }

    //Final Entity will be UserData. This section begins to populate the database
    Entity colorTest1 = new Entity("ColorTest1");
    colorTest1.setProperty("email", username);
    colorTest1.setProperty("tone", tone);

    //Sends new data to the database
    datastore.put(colorTest1);

    Entity pixelTest1 = new Entity("PixelTest1");
    pixelTest1.setProperty("email", username);
    pixelTest1.setProperty("red", red);
    pixelTest1.setProperty("green", green);
    pixelTest1.setProperty("blue", blue);

    //Sends new data to the database
    datastore.put(pixelTest1);

    Gson gson = new Gson();

    Status status = new Status("Success");

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(status));
  }
}