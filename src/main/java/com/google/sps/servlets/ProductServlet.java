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
    String imgUrl;
    String type;
    String tone;
    String vegan;
    String brand;
    String name;
    String productUrl;
    
    /*
    long id = Long.parseLong(request.getParameter("id"));
    long cost = Long.parseLong(request.getParameter("cost"));
    long rating = Long.parseLong(request.getParameter("rating"));
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
    ProductsTest3.setProperty("cost", cost);

    //Sends new data to the database
    datastore.put(ProductsTest3);
    */


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

      summaries.add(new Summary(id, name, imgUrl, type, tone, vegan, brand, productUrl, cost));
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));
  }
}