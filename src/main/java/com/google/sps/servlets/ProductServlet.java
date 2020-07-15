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
    private final String ingredients;
    private final String brand;
    private final long cost;

    Summary(long id, String name, String imgUrl, String type, String tone, 
            String ingredients, String brand, long cost) {
      this.id = id;
      this.name = name;
      this.imgUrl = imgUrl;
      this.type = type;
      this.tone = tone;
      this.ingredients = ingredients;
      this.brand = brand;
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
    String ingredients;
    String brand;
    String name;
    
    /*
    long id = Long.parseLong(request.getParameter("id"));
    long cost = Long.parseLong(request.getParameter("cost"));
    String imgUrl = request.getParameter("imgUrl");
    String type = request.getParameter("type");
    String tone = request.getParameter("tone");
    String ingredients = request.getParameter("ingredients");
    String brand = request.getParameter("brand");
    String name = request.getParameter("name");

    //Final Entity will be UserData. This section begins to populate the database
    Entity ProductsTest1 = new Entity("ProductsTest1");
    ProductsTest1.setProperty("id", id);
    ProductsTest1.setProperty("name", name);
    ProductsTest1.setProperty("imgUrl", imgUrl);
    ProductsTest1.setProperty("type", type);
    ProductsTest1.setProperty("tone", tone);
    ProductsTest1.setProperty("ingredients", ingredients);
    ProductsTest1.setProperty("brand", brand);
    ProductsTest1.setProperty("cost", cost);

    //Sends new data to the database
    datastore.put(ProductsTest1);*/
    
    
    Query query = new Query("ProductsTest");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      id = (long) entity.getProperty("id");
      imgUrl = (String) entity.getProperty("imgUrl");
      type = (String) entity.getProperty("type");
      tone = (String) entity.getProperty("tone");
      ingredients = (String) entity.getProperty("ingredients");
      brand = (String) entity.getProperty("brand");
      name = (String) entity.getProperty("name");
      cost = (long) entity.getProperty("cost");

      summaries.add(new Summary(id, name, imgUrl, type, tone, ingredients, brand, cost));
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws IOException {
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    long id = Long.parseLong(request.getParameter("id"));
    String imgUrl = request.getParameter("imgUrl");
    String type = request.getParameter("type");
    String tone = request.getParameter("tone");
    String ingredients = request.getParameter("ingredients");
    String brand = request.getParameter("brand");
    String name = request.getParameter("name");

    //Final Entity will be UserData. This section begins to populate the database
    Entity ProductsTest = new Entity("ProductsTest");
    ProductsTest.setProperty("id", id);
    ProductsTest.setProperty("name", name);
    ProductsTest.setProperty("imgUrl", imgUrl);
    ProductsTest.setProperty("type", type);
    ProductsTest.setProperty("tone", tone);
    ProductsTest.setProperty("ingredients", ingredients);
    ProductsTest.setProperty("brand", brand);

    //Sends new data to the database
    datastore.put(ProductsTest);
  }
}