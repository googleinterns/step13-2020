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
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
  
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
    UserService userService = UserServiceFactory.getUserService();

    String tone1 = request.getParameter("tone1");
    String tone2 = request.getParameter("tone2");
    String prod1 = request.getParameter("prod1");
    String prod2 = request.getParameter("prod2");

    if (!userService.isUserLoggedIn()) {
      //May want to add in an error message later like "ERROR: User is not logged in"
      Gson gson = new Gson();

      Status status = new Status("Failure"); 

      response.setContentType("application/json;");
      response.getWriter().println(gson.toJson(status));

      return;
    }

    String username = userService.getCurrentUser().getEmail();

    if (tone2.equals("bright red")) {
      tone2 = "light";
    } else if (tone2.equals("dark red")) {
      tone2 = "Dark-Tan";
    } else if (tone2.equals("classic red")) {
      tone2 = "Light-Tan";
    } else {
      tone2 = "Brown";
    }

    if (prod2.equals("concealer")) {
      prod2 = "Foundation";
    } else if (prod2.equals("brow pencil")) {
      prod2 = "Eyeliner";
    }

    long entityId = 0;
    boolean found = false;
    Query query = new Query("Quiz");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      if (username.equals((String) entity.getProperty("email"))) {
        entityId = entity.getKey().getId();
        found = true;
      }
    }

    if (found) {
      Key entityKey = KeyFactory.createKey("Quiz", entityId);
      datastore.delete(entityKey);
    }

    Entity Quiz = new Entity("Quiz");
    Quiz.setProperty("email", username);
    Quiz.setProperty("tone1", tone1);
    Quiz.setProperty("tone2", tone2);
    Quiz.setProperty("prod1", prod1);
    Quiz.setProperty("prod2", prod2);
    datastore.put(Quiz);

    Gson gson = new Gson();

    Status status = new Status("Success");

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(status));
  }


  /*
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {

    String answers = request.getParameter("selectedAnswers");
    String [] answersArray = answers.split(",");
    UserService userService = UserServiceFactory.getUserService();

    if (userService.isUserLoggedIn()) {
        String userEmail = userService.getCurrentUser().getEmail();

        Entity userEntity = new Entity("User");
        userEntity.setProperty("email", userEmail);
        userEntity.setProperty("answer1", answersArray[0]);
        userEntity.setProperty("answer2", answersArray[1]);
        userEntity.setProperty("answer3", answersArray[2]);
        userEntity.setProperty("answer4", answersArray[3]);
        userEntity.setProperty("answer5", answersArray[4]);
        userEntity.setProperty("answer6", answersArray[5]);
        userEntity.setProperty("answer7", answersArray[6]);
        userEntity.setProperty("answer8", answersArray[7]);
        userEntity.setProperty("answer9", answersArray[8]);

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(userEntity);
    }
  }
  */
}