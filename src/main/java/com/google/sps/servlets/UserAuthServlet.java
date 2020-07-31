package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
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

@WebServlet("/auth")
public class UserAuthServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    response.setContentType("text/html");

    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      String userEmail = userService.getCurrentUser().getEmail();
      //Put redirection here
      String urlToRedirectToAfterUserLogsOut = "/auth";
      String logoutUrl = userService.createLogoutURL(
        urlToRedirectToAfterUserLogsOut);

      response.getWriter().println("<head><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" +
      "integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"+ 
      "crossorigin=\"anonymous\"> + \"<link href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css\" rel=\"stylesheet\">" +
      "<style>*{overflow: auto;}" + "body { text-align: center; font-family: Arial, Helvetica, sans-serif;}" + 
      "nav{box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); padding: 20px; height: 70px; width: inherit;} h1 {color:rebeccapurple;}" +
      "input[type=file]{background-color: rgba(10, 10, 10, 0.884);border: none; font-size: 15px;font-family: Arial, Helvetica, sans-serif;color: white;text-align: center;border: none;border-radius: 5px;transition: 0.3s;opacity: 1;height: 35px;}" +
      "button {background-color: rgba(10, 10, 10, 0.884);border: none; font-size: 14px;font-family: Arial, Helvetica, sans-serif;color: white;text-align: center;border: none;border-radius: 5px;transition: 0.3s;opacity: 1;height: 35px;}" +
      "#return_button{background-color: rgba(10, 10, 10, 0.884); border: none; font-size: 14px;font-family: Arial, Helvetica, sans-serif;color: white;text-align: center;border: none;border-radius: 5px;transition: 0.3s;opacity: 1;height: 35px;}" +
      ".container {height: 200px;position: relative;border: 3px solid green;}" +
      ".center {margin: 0;position: absolute;top: 50%;left: 50%;-ms-transform: translate(-50%, -50%)transform: translate(-50%, -50%);}" +
      "</style></head><body><nav class=\"navbar navbar-light white\" style=\"position: fixed-top;\">" +
               "<div class=\"container-fluid\">" +
                    "<h1>Style Advisor</h1>" + 
                    "<a href=\"../index.html\"><button type=\"button\" id=\"return_button\">Home</button></a>" +
               "</div>" +
       "</nav>" +
            "<div class=\"center\">" +
                "<a href=\"" + logoutUrl + "\"><button type=\"button\">LogIn</button>" +
               " </div>" +
        "</body>"
      );
    } 
    else {
      //Get redirection page
      String urlToRedirectToAfterUserLogsIn = "/auth";
      String loginUrl = userService.createLoginURL(
        urlToRedirectToAfterUserLogsIn);
      
      response.getWriter().println("<head><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" +
      "integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"+ 
      "crossorigin=\"anonymous\"> + \"<link href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css\" rel=\"stylesheet\">" +
      "<style>*{overflow: auto;}" + "body { text-align: center; font-family: Arial, Helvetica, sans-serif;}" + 
      "nav{box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); padding: 20px; height: 70px; width: inherit;} h1 {color:rebeccapurple;}" +
      "input[type=file]{background-color: rgba(10, 10, 10, 0.884);border: none; font-size: 15px;font-family: Arial, Helvetica, sans-serif;color: white;text-align: center;border: none;border-radius: 5px;transition: 0.3s;opacity: 1;height: 35px;}" +
      "button {background-color: rgba(10, 10, 10, 0.884);border: none; font-size: 14px;font-family: Arial, Helvetica, sans-serif;color: white;text-align: center;border: none;border-radius: 5px;transition: 0.3s;opacity: 1;height: 35px;}" +
      "#return_button{background-color: rgba(10, 10, 10, 0.884); border: none; font-size: 14px;font-family: Arial, Helvetica, sans-serif;color: white;text-align: center;border: none;border-radius: 5px;transition: 0.3s;opacity: 1;height: 35px;}" +
      ".container {height: 200px;position: relative;border: 3px solid green;}" +
      ".center {margin: 0;position: absolute;top: 50%;left: 50%;-ms-transform: translate(-50%, -50%)transform: translate(-50%, -50%);}" +
      "</style></head><body><nav class=\"navbar navbar-light white\" style=\"position: fixed-top;\">" +
               "<div class=\"container-fluid\">" +
                    "<h1>Style Advisor</h1>" + 
                    "<a href=\"../index.html\"><button type=\"button\" id=\"return_button\">Home</button></a>" +
               "</div>" +
       "</nav>" +
            "<div class=\"center\">" +
                "<a href=\"" + loginUrl + "\"><button type=\"button\">LogIn</button>" +
               " </div>" +
        "</body>"
      );
    }
  }
}