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

      response.getWriter().println(
       " <title>LogOut | Style Advisor</title>" +
     "<link rel =\"stylesheet\" href=\"css/home.css\">" +
    "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\" integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">" +
    "<link href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css\" rel=\"stylesheet\">" +
    "<script src=\"https://unpkg.com/boxicons@latest/dist/boxicons.js\"></script>" +
    "<link href=\"https://cdn.jsdelivr.net/npm/boxicons@2.0.5/css/boxicons.min.css\" rel=\"stylesheet\">" +
"</head>" +
"<body onload=\"loadUser()\">" +
    "<nav class=\"navbar navbar-expand-sm container-fluid stick-top navbar-light bg-lignt pt-3\" style=\"position: fixed-top;\"> " +
      "  <h3 href=\"products.html\" class=\"brand-name\">Style Advisor</h3>" +
       " <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbar-tog\" data-toggle=\"#navbarResponsive\">" +
           "<span class=\"navbar-toggler-icon\" data-toggle=\"collapse\"></span> " +
        "</button>" +
        "<div class=\"collapse navbar-collapse\" id=\"navbar-tog\">" +
            "<ul class=\"navbar-nav ml-auto\">" +
                "<li class=\"nav-item\">" +
                  "  <a id=\"quiz\" href=\"index.html\" class=\"nav-link\">Home</a>" +
                "</li>" +
            "</ul>" +
        "</div>" +
    "</nav>" +
    "<img class=\"wave\" src=\"images/home/wave.png\">" +
    "<div class=\"container\">" +
        "<div class=\"design-container\">" +
            "<div class=\"img-design\">" +
                "<img class=\"img-overlay\" src=\"images/home/makeup.svg\">" +
            "</div>" +
        "</div>" +
        "<div class=\"login-container\">" +
            "<a href=\"" + logoutUrl + "\"><button type=\"button\">LogOut</button>" +
        "</div>" +
    "</div>" +
    "<script src=\"/js/home.js\"></script>" +
    "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>" +
    "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script>" +
    "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\" integrity=\"sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI\" crossorigin=\"anonymous\"></script>" +
"</body>" +
"</html>"
      );
    } 
    else {
      //Get redirection page
      String urlToRedirectToAfterUserLogsIn = "/auth";
      String loginUrl = userService.createLoginURL(
        urlToRedirectToAfterUserLogsIn);
      
      response.getWriter().println(
       " <title>LogIn | Style Advisor</title>" +
     "<link rel =\"stylesheet\" href=\"css/home.css\">" +
    "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\" integrity=\"sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk\" crossorigin=\"anonymous\">" +
    "<link href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css\" rel=\"stylesheet\">" +
    "<script src=\"https://unpkg.com/boxicons@latest/dist/boxicons.js\"></script>" +
    "<link href=\"https://cdn.jsdelivr.net/npm/boxicons@2.0.5/css/boxicons.min.css\" rel=\"stylesheet\">" +
"</head>" +
"<body onload=\"loadUser()\">" +
    "<nav class=\"navbar navbar-expand-sm container-fluid stick-top navbar-light bg-lignt pt-3\" style=\"position: fixed-top;\"> " +
      "  <h3 href=\"products.html\" class=\"brand-name\">Style Advisor</h3>" +
       " <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbar-tog\" data-toggle=\"#navbarResponsive\">" +
           "<span class=\"navbar-toggler-icon\" data-toggle=\"collapse\"></span> " +
        "</button>" +
        "<div class=\"collapse navbar-collapse\" id=\"navbar-tog\">" +
            "<ul class=\"navbar-nav ml-auto\">" +
                "<li class=\"nav-item\">" +
                  "  <a id=\"quiz\" href=\"index.html\" class=\"nav-link\">Home</a>" +
                "</li>" +
            "</ul>" +
        "</div>" +
    "</nav>" +
    "<img class=\"wave\" src=\"images/home/wave.png\">" +
    "<div class=\"container\">" +
        "<div class=\"design-container\">" +
            "<div class=\"img-design\">" +
                "<img class=\"img-overlay\" src=\"images/home/makeup.svg\">" +
            "</div>" +
        "</div>" +
        "<div class=\"login-container\">" +
            "<a href=\"" + loginUrl + "\"><button type=\"button\">LogIn</button>" +
        "</div>" +
    "</div>" +
    "<script src=\"/js/home.js\"></script>" +
    "<script src=\"https://code.jquery.com/jquery-3.5.1.slim.min.js\" integrity=\"sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj\" crossorigin=\"anonymous\"></script>" +
    "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script>" +
    "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\" integrity=\"sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI\" crossorigin=\"anonymous\"></script>" +
"</body>" +
"</html>"
      );
    }
  }
}