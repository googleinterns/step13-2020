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

@WebServlet("/sendRating")
public class SendRating extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
    
    long id = Long.parseLong(request.getParameter("id"));
    double rating = Double.parseDouble(request.getParameter("rating"));

    //Final Entity will be UserData. This section begins to populate the database
    Entity ProductRating = new Entity("ProductRating");
    ProductRating.setProperty("id", id);
    ProductRating.setProperty("rating", rating);

    //Sends new data to the database
    datastore.put(ProductRating);
  }
}