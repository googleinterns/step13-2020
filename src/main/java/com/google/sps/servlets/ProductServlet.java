package com.google.sps.servlets;

import java.util.ArrayList;

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
import com.google.gson.Gson;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
  
  class Summary {
    private final long id;
    private final String name;
    private final String imgUrl;
    private final String type;
    private final String tone;
    private final String vegan;
    private final String brand;
    private final String productUrl;
    private final long cost;
    private final String description;

    Summary(long id, String name, String imgUrl, String type, String tone, 
            String vegan, String brand, String productUrl, long cost, String description) {
      this.id = id;
      this.name = name;
      this.imgUrl = imgUrl;
      this.type = type;
      this.tone = tone;
      this.vegan = vegan;
      this.brand = brand;
      this.productUrl = productUrl;
      this.cost = cost;
      this.description = description;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ArrayList<Summary> summaries = new ArrayList<>();       
    
    long id;
    long cost;
    String imgUrl;
    String type;
    String tone;
    String vegan;
    String brand;
    String name;
    String productUrl;
    String description;

    Query query = new Query("ProductsTest3");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      id = (long) entity.getProperty("id");
      imgUrl = (String) entity.getProperty("imgUrl");
      type = (String) entity.getProperty("type");
      tone = (String) entity.getProperty("tone");
      vegan = (String) entity.getProperty("vegan");
      brand = (String) entity.getProperty("brand");
      name = (String) entity.getProperty("name");
      productUrl = (String) entity.getProperty("productUrl");
      cost = (long) entity.getProperty("cost");
      description = (String) entity.getProperty("description");

      summaries.add(new Summary(id, name, imgUrl, type, tone, vegan, brand, productUrl, cost, description));
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));
    
  }
}