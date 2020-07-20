package com.google.sps.servlets;

import com.google.sps.javaclasses.User;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.io.IOException;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
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
}