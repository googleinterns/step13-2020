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
import java.io.IOException;
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

      response.getWriter().println("<head><style>" +
      "div.topper { background-color: lightblue; width: 300px;"+
      "border: 15px solid purple;" +
      "padding: 50px; margin: auto; text-align: center;}" +
      "ul { list-style-type: none; margin: 0;" +
      "padding: 0; overflow: hidden; background-color: #333; }" +
      "li { float: left; } li a { display: block; color: white;" +
      "text-align: center; padding: 14px 16px; text-decoration: none; }" +
      "li a:hover { background-color: #111 }" +
      "header.masthead { padding-top: 10.5rem; padding-bottom: 50rem;" +
      "text-align: center; color: #fff;" +
      "background-color: lightblue;" +
      "background-repeat: no-repeat; background-attachment: scroll;" +
      "background-position: center center; background-size: cover; }" +
      "header.masthead .masthead-heading { font-size: 3.25rem;" +
      "font-weight: 700; line-height: 3.25rem;" +
      "margin-bottom: 2rem; font-family: \"Montserrat\", -apple-system, " +
      "BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", " +
      "Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", " +
      "\"Segoe UI Symbol\", \"Noto Color Emoji\"; }" +
      "@media (min-width: 768px) { header.masthead { " +
      "padding-top: 17rem; padding-bottom: 12.5rem; }" +
      "header.masthead .masthead-heading { font-size: 4.5rem;" +
      "font-weight: 700; line-height: 4.5rem;" +
      "margin-bottom: 8rem; } }" +
      "</style></head><body>" +
      "<ul><li><a class=\"active\" href=" +
      "\"https://www.youtube.com/watch?v=dQw4w9WgXcQ\">Home</a></li></ul>" +
      "<header class=\"masthead\"><div class \"topper\">" +
      "<div class=\"masthead-heading\"><a href=\"" + logoutUrl + "\">LOG-OUT HERE" +
      "</a></div></div></header></body>"
      );
    } 
    else {
      //Get redirection page
      String urlToRedirectToAfterUserLogsIn = "/auth";
      String loginUrl = userService.createLoginURL(
        urlToRedirectToAfterUserLogsIn);
      
      response.getWriter().println("<head><style>" +
      "div.topper { background-color: lightblue; width: 300px;"+
      "border: 15px solid purple;" +
      "padding: 50px; margin: auto; text-align: center;}" +
      "ul { list-style-type: none; margin: 0;" +
      "padding: 0; overflow: hidden; background-color: #333; }" +
      "li { float: left; } li a { display: block; color: white;" +
      "text-align: center; padding: 14px 16px; text-decoration: none; }" +
      "li a:hover { background-color: #111 }" +
      "header.masthead { padding-top: 10.5rem; padding-bottom: 50rem;" +
      "text-align: center; color: #fff;" +
      "background-color: lightblue;" +
      "background-repeat: no-repeat; background-attachment: scroll;" +
      "background-position: center center; background-size: cover; }" +
      "header.masthead .masthead-heading { font-size: 3.25rem;" +
      "font-weight: 700; line-height: 3.25rem;" +
      "margin-bottom: 2rem; font-family: \"Montserrat\", -apple-system, " +
      "BlinkMacSystemFont, \"Segoe UI\", Roboto, \"Helvetica Neue\", " +
      "Arial, sans-serif, \"Apple Color Emoji\", \"Segoe UI Emoji\", " +
      "\"Segoe UI Symbol\", \"Noto Color Emoji\"; }" +
      "@media (min-width: 768px) { header.masthead { " +
      "padding-top: 17rem; padding-bottom: 12.5rem; }" +
      "header.masthead .masthead-heading { font-size: 4.5rem;" +
      "font-weight: 700; line-height: 4.5rem;" +
      "margin-bottom: 8rem; } }" +
      "</style></head><body>" +
      "<ul><li><a class=\"active\" href=" +
      "\"https://www.youtube.com/watch?v=dQw4w9WgXcQ\">Home</a></li></ul>" +
      "<header class=\"masthead\"><div class \"topper\">" +
      "<div class=\"masthead-heading\"><a href=\"" + loginUrl + "\">LOGIN HERE" +
      "</a></div></div></header></body>"
      );
    }
  }
}