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
  
  private static final int COMPARISON_LIMIT = 51;
  private static final int NEEDED_MATCHES = 5;

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
    ArrayList<String> types = new ArrayList<>();
    ArrayList<String> tones = new ArrayList<>();
    ArrayList<String> vegan = new ArrayList<>();
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<Long> costs = new ArrayList<>();

    int count = 0;

    String typeFilter = request.getParameter("type");
    String toneFilter = request.getParameter("tone");
    String veganFilter = request.getParameter("vegan");
    String brandFilter = request.getParameter("brand");
    String costFilter = request.getParameter("price");

    types = parseFilters(typeFilter);

    tones = parseFilters(toneFilter);

    vegan = parseFilters(veganFilter);

    brands = parseFilters(brandFilter);

    int curr = 0;

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

    Query query = new Query("ProductsTest3");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      
      long id = (long) entity.getProperty("id");
      long cost = (long) entity.getProperty("cost");
      String imgUrl = (String) entity.getProperty("imgUrl");
      String type = (String) entity.getProperty("type");
      String tone = (String) entity.getProperty("tone");
      String vegans = (String) entity.getProperty("vegan");
      String brand = (String) entity.getProperty("brand");
      String name = (String) entity.getProperty("name");
      String productUrl = (String) entity.getProperty("productUrl");

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
        for (String str: tones) {
          if (tone.equals(str)) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (vegan.size() > 0) {
        for (String str: vegan) {
          if (vegans.equals(str)) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (brands.size() > 0) {
        for (String str: brands) {
          if (brand.equals(str)) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (costs.size() > 0) {
        for (Long num: costs) {
          if ((cost <= num && num != COMPARISON_LIMIT) || (num == COMPARISON_LIMIT && cost > 50)) {
            count++;
            break;
          }
        }
      } else {
        count++;
      }

      if (count == NEEDED_MATCHES) {
        summaries.add(new Summary(id, name, imgUrl, type, tone, vegans, 
                      brand, productUrl, cost));
      }
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));
  }

  private ArrayList<String> parseFilters(String params) {
    int curr = 0;
    ArrayList<String> list = new ArrayList<>();

    for (int i = 0; i < params.length(); i++) {
      if (params.charAt(i) == '|') {
        list.add(params.substring(curr, i));
        curr = i + 1;
      }
    }

    return list;
  }
}