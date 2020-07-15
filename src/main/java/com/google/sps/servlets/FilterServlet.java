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

@WebServlet("/filter")
public class FilterServlet extends HttpServlet {
  
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
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> tones = new ArrayList<>();
    ArrayList<String> ingredients = new ArrayList<>();
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<Long> costs = new ArrayList<>();

    int count = 0;

    String typeFilter = request.getParameter("type");
    String toneFilter = request.getParameter("tone");
    String ingredientFilter = request.getParameter("ingredients");
    String brandFilter = request.getParameter("brand");
    String costFilter = request.getParameter("price");

    int curr = 0;

    for (int i = 0; i < typeFilter.length(); i++) {
      if (typeFilter.charAt(i) == '|') {
        types.add(typeFilter.substring(curr, i));
        curr = i + 1;
      }
    }

    curr = 0;
    
    for (int i = 0; i < toneFilter.length(); i++) {
      if (toneFilter.charAt(i) == '|') {
        tones.add(toneFilter.substring(curr, i));
        curr = i + 1;
      }
    }

    curr = 0;

    for (int i = 0; i < ingredientFilter.length(); i++) {
      if (ingredientFilter.charAt(i) == '|') {
        ingredients.add(ingredientFilter.substring(curr, i));
        curr = i + 1;
      }
    }

    curr = 0;

    for (int i = 0; i < brandFilter.length(); i++) {
      if (brandFilter.charAt(i) == '|') {
        brands.add(brandFilter.substring(curr, i));
        curr = i + 1;
      }
    }

    curr = 0;

    if (!costFilter.equals("None")) {
      
      String value;
      long number;
     
      for (int i = 0; i < costFilter.length(); i++) {
        if (costFilter.charAt(i) == '|') {
          value = costFilter.substring(curr, i);
          curr = i + 1;
          number = Long.parseLong(value);

          costs.add(number);
        }
      }
    }

    Query query = new Query("ProductsTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      
      long id = (long) entity.getProperty("id");
      long cost = (long) entity.getProperty("cost");
      String imgUrl = (String) entity.getProperty("imgUrl");
      String type = (String) entity.getProperty("type");
      String tone = (String) entity.getProperty("tone");
      String ings = (String) entity.getProperty("ingredients");
      String brand = (String) entity.getProperty("brand");
      String name = (String) entity.getProperty("name");

      count = 0;

      if (types.size() > 0) {
        for (int i = 0; i < types.size(); i++) {
          if (type.equals(types.get(i))) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (tones.size() > 0) {
        for (int i = 0; i < tones.size(); i++) {
          if (tone.equals(tones.get(i))) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (ingredients.size() > 0) {
        for (int i = 0; i < ingredients.size(); i++) {
          if (ings.equals(ingredients.get(i))) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (brands.size() > 0) {
        for (int i = 0; i < brands.size(); i++) {
          if (brand.equals(brands.get(i))) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (costs.size() > 0) {
        for (int i = 0; i < costs.size(); i++) {
          if (cost <= costs.get(i) || (costs.get(i) == 51 && cost > 50)) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (count == 5) {
        summaries.add(new Summary(id, name, imgUrl, type, tone, ings, 
                      brand, cost));
      }
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));
  }
}