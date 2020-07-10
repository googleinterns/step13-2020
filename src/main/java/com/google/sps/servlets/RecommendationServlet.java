// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

//Have to make a UserInfo file

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle
comments data */
@WebServlet("/recommend")
public class RecommendationServlet extends HttpServlet {
  
  private class Node {
    private int weight;
    private Summary summary;
    private Node next = null;

    Node(int weight, Summary summary) {
      this.weight = weight;
      this.summary = summary;
    }
  }

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

    HashMap<String, Integer> toneMap = new HashMap<>();
    HashMap<String, Integer> typeMap = new HashMap<>();
    HashMap<String, Integer> brandMap = new HashMap<>();
    HashMap<String, Integer> veganMap = new HashMap<>();
    HashMap<String, Integer> costMap = new HashMap<>();

    List<Summary> summaries = new ArrayList<>();
    Node head = null;

    String username;
    long productId;
    long cost;
    String type;
    String tone;
    String vegan;
    String brand;
    String imgUrl;
    String productUrl;
    String name;
    int count = 0;
    int weight = 0;
    
    UserService userService = UserServiceFactory.getUserService();
    
    if (!userService.isUserLoggedIn()) {
      //May want to add in an error message later like "ERROR: User is not logged in"
      return;
    }
    
    username = userService.getCurrentUser().getEmail();

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    Query query = new Query("ColorTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      if (username.equals((String) entity.getProperty("email"))) {
        processMap(toneMap, (String) entity.getProperty("tone"));
      }
    }

    query = new Query("UserDataTest1");
    results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      Query productQuery = new Query("ProductsTest3");
      PreparedQuery queryRes = datastore.prepare(productQuery);

      if (username.equals((String) entity.getProperty("email"))) {
        productId = (long) entity.getProperty("id");

        for (Entity product : queryRes.asIterable()) {
          type = (String) product.getProperty("type");
          tone = (String) product.getProperty("tone");
          vegan = (String) product.getProperty("vegan");
          brand = (String) product.getProperty("brand");
          cost = (long) product.getProperty("cost");

          if (productId == (long) product.getProperty("id")) {
            processMap(toneMap, tone);
            processMap(typeMap, type);
            processMap(brandMap, brand);
            processMap(veganMap, vegan);

            if (cost < 10) {
              processMap(costMap, "Under $10");
            } else if (cost >= 10 && cost < 35) {
              processMap(costMap, "$10-$35");
            } else if (cost >= 35 && cost < 50) {
              processMap(costMap, "$35-$50");
            } else {
              processMap(costMap, "$50+");
            }
          }
        }
      }
    }

    if (typeMap.isEmpty() || toneMap.isEmpty() || brandMap.isEmpty() || veganMap.isEmpty() || costMap.isEmpty()) {
      query = new Query("ProductsTest3");
      results = datastore.prepare(query);

      for (Entity entity : results.asIterable()) {
        productId = (long) entity.getProperty("id");
        imgUrl = (String) entity.getProperty("imgUrl");
        type = (String) entity.getProperty("type");
        tone = (String) entity.getProperty("tone");
        vegan = (String) entity.getProperty("vegan");
        brand = (String) entity.getProperty("brand");
        name = (String) entity.getProperty("name");
        productUrl = (String) entity.getProperty("productUrl");
        cost = (long) entity.getProperty("cost");

        summaries.add(new Summary(productId, name, imgUrl, type, tone, vegan, brand, productUrl, cost));
      }

      Gson gson = new Gson();

      response.setContentType("application/json;");
      response.getWriter().println(gson.toJson(summaries));

      return;
    }

    query = new Query("ProductsTest3");
    results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      Node curr = head;

      productId = (long) entity.getProperty("id");
      imgUrl = (String) entity.getProperty("imgUrl");
      type = (String) entity.getProperty("type");
      tone = (String) entity.getProperty("tone");
      vegan = (String) entity.getProperty("vegan");
      brand = (String) entity.getProperty("brand");
      name = (String) entity.getProperty("name");
      productUrl = (String) entity.getProperty("productUrl");
      cost = (long) entity.getProperty("cost");

      if (typeMap.get(type) != null) {
        weight += typeMap.get(type);
      }

      if (brandMap.get(brand) != null) {
        weight += brandMap.get(brand);
      }

      if (veganMap.get(vegan) != null) {
        weight += veganMap.get(vegan);
      }

      if (cost < 10) {
        if (costMap.get("Under $10") != null) {
          weight += costMap.get("Under $10");
        }
      } else if (cost >= 10 && cost < 35) {
        if (costMap.get("$10-$35") != null) {
          weight += costMap.get("$10-$35");
        }
      } else if (cost >= 35 && cost < 50) {
        if (costMap.get("$35-$50") != null) {
          weight += costMap.get("$35-$50");
        }
      } else {
        if (costMap.get("$50+") != null) {
          weight += costMap.get("$50+");
        }
      }

      if (head == null) {
        Node node = new Node(weight, new Summary(productId, name, imgUrl, type, tone, vegan, brand, productUrl, cost));
        head = node;
      } else {
        while (curr != null) {
          if (weight >= curr.weight) {
            Node node = new Node(weight, new Summary(productId, name, imgUrl, type, tone, vegan, brand, productUrl, cost));
            node.next = curr.next;
            curr.next = node;
            break;
          }

          curr = curr.next;
        }
      }

      if (count < 16) {
        count++;
      } else {
        head = head.next;
      }
    }

    count = 0;
    Node curr = head;

    while (curr != null && count < 16) {
      summaries.add(curr.summary);
      curr = curr.next;
      count++;
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(summaries));

    /*
    query = new Query("QuizTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      
    }
    */
  }

  private void processMap(HashMap<String, Integer> map, String key) {
    if (map.containsKey(key)) {
      map.put(key, map.get(key) + 1);
    } else {
      map.put(key, 1);
    }
  }
}