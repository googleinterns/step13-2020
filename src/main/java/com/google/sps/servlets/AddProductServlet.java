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

@WebServlet("/add")
public class AddProductServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService(); 
    
    long id = Long.parseLong(request.getParameter("id"));
    long cost = Long.parseLong(request.getParameter("cost"));
    
    //Save the rating for its own servlet
    //long rating = Long.parseLong(request.getParameter("rating"));

    String description = request.getParameter("description");
    String imgUrl = request.getParameter("imgUrl");
    String type = request.getParameter("type");
    String tone = request.getParameter("tone");
    String vegan = request.getParameter("vegan");
    String ingredients = request.getParameter("ingredients");
    String brand = request.getParameter("brand");
    String name = request.getParameter("name");
    String productUrl = request.getParameter("productUrl");

    //Final Entity will be UserData. This section begins to populate the database
    Entity ProductsTest3 = new Entity("ProductsTest3");
    ProductsTest3.setProperty("id", id);
    ProductsTest3.setProperty("name", name);
    ProductsTest3.setProperty("description", description);
    ProductsTest3.setProperty("productUrl", productUrl);
    ProductsTest3.setProperty("imgUrl", imgUrl);
    ProductsTest3.setProperty("type", type);
    ProductsTest3.setProperty("tone", tone);
    ProductsTest3.setProperty("vegan", vegan);
    ProductsTest3.setProperty("ingredients", ingredients);
    ProductsTest3.setProperty("brand", brand);
    //ProductsTest3.setProperty("rating", rating);
    ProductsTest3.setProperty("cost", cost);

    //Sends new data to the database
    datastore.put(ProductsTest3);
  }
}