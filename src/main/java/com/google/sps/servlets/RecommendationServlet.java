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
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
  
    String username;
    String skinColor;
    long productID;
    String type;
    String tone;
    String ingredients;
    String brand;

    UserService userService = UserServiceFactory.getUserService();
      if (!userService.isUserLoggedIn()) {
        //May want to add in an error message later like "ERROR: User is not logged in"
        return;
      }
    
    username = userService.getCurrentUser().getEmail();

    Query query = new Query("UserData");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    //List<UserInfo> userInfo = new ArrayList<>();

    for (Entity entity : results.asIterable()) {

      productID = (long) entity.getProperty("date");
      skinColor = (String) entity.getProperty("skinColor");
      type = (String) entity.getProperty("type");
      tone = (String) entity.getProperty("tone");
      ingredients = (String) entity.getProperty("ingredients");
      brand = (String) entity.getProperty("brand");

      if (username.equals((String) entity.getProperty("email"))) {
        //UserInfo info = new UserInfo(skinColor, productID);    
        //userInfo.add(info);
      }
    }

  }
}