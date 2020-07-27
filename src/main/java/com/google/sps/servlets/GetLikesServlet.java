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

@WebServlet("/getLiked")
public class GetLikesServlet extends HttpServlet {
  
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

    Summary(long id, String name, String imgUrl, String type, String tone, 
            String vegan, String brand, String productUrl, long cost) {
      this.id = id;
      this.name = name;
      this.imgUrl = imgUrl;
      this.type = type;
      this.tone = tone;
      this.vegan = vegan;
      this.brand = brand;
      this.productUrl = productUrl;
      this.cost = cost;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    ArrayList<Summary> summaries = new ArrayList<>();

    long id;
    long cost;
    long productId;
    String imgUrl;
    String type;
    String tone;
    String vegan;
    String brand;
    String name;
    String productUrl;

    UserService userService = UserServiceFactory.getUserService();
    
    if (!userService.isUserLoggedIn()) {
      //May want to add in an error message later like "ERROR: User is not logged in"
      return;
    }
    
    String username = userService.getCurrentUser().getEmail();

    Query query = new Query("UserDataTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      Query productQuery = new Query("ProductsTest3");
      PreparedQuery queryRes = datastore.prepare(productQuery);

      if (username.equals((String) entity.getProperty("email"))) {
        productId = (long) entity.getProperty("id");

        for (Entity product : queryRes.asIterable()) {
          id = (long) product.getProperty("id");
          imgUrl = (String) product.getProperty("imgUrl");
          type = (String) product.getProperty("type");
          tone = (String) product.getProperty("tone");
          vegan = (String) product.getProperty("vegan");
          brand = (String) product.getProperty("brand");
          name = (String) product.getProperty("name");
          productUrl = (String) product.getProperty("productUrl");
          cost = (long) product.getProperty("cost");

          if (id == productId) {
            summaries.add(new Summary(id, name, imgUrl, type, tone, vegan, brand, productUrl, cost));
          }
        }
      }
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));
  }
}