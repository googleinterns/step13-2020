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
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
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
@WebServlet("/delete")
public class DeleteLikedServlet extends HttpServlet {
  
  class Status {
    private final String end;

    Status(String end) {
      this.end = end;
    }
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    String username;
    long id = Long.parseLong(request.getParameter("id"));
    boolean found = false;

    UserService userService = UserServiceFactory.getUserService();
    
    if (!userService.isUserLoggedIn()) {
      //May want to add in an error message later like "ERROR: User is not logged in"
      Gson gson = new Gson();

      Status status = new Status("Failure"); 

      response.setContentType("application/json;");
      response.getWriter().println(gson.toJson(status));

      return;
    }
    

    username = userService.getCurrentUser().getEmail();

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Query query = new Query("UserDataTest1");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      if (id == (long) entity.getProperty("id") && 
          username.equals((String) entity.getProperty("email"))) {
          id = entity.getKey().getId();
          found = true;
          break;
      }
    }

    if (found) {
      Key entityKey = KeyFactory.createKey("UserDataTest1", id);
      datastore.delete(entityKey);
    }

    Gson gson = new Gson();

    Status status = new Status("Success");

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(status));
  }
}