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
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle
comments data */
@WebServlet("/img")
public class UserImgServlet extends HttpServlet {

  /*
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) 
    throws IOException { 

    String limit = request.getParameter("limit");
    int max;
    int count = 1, showForm = 1;

    if (limit == null) {
      max = 0;
    } else {
      max = Integer.parseInt(limit);
    }

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      showForm = 0;
    }

    Query query = new Query("Task").addSort("time", 
        SortDirection.DESCENDING);

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<Task> tasks = new ArrayList<>();

    for (Entity entity : results.asIterable()) {
      
      if (count > max) {
        break;
      }

      long id = entity.getKey().getId();
      String username = (String) entity.getProperty("name");
      String textDate = (String) entity.getProperty("date");
      String words = (String) entity.getProperty("text");
      String email = (String) entity.getProperty("email");
      String imgURL = (String) entity.getProperty("imgURL");

      Task task = new Task(id, textDate, words, email, showForm, imgURL);
      
      tasks.add(task);

      count++;
    }

    Gson gson = new Gson();

    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(tasks));
  }
  */

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) 
    throws IOException {
    
    ArrayList<String> images = new ArrayList<>();
    String fileCount = request.getParameter("num");
    int size = 0;

    if (fileCount == null) {
      return;
    }

    int fileNum = Integer.parseInt(fileCount);

    if (fileNum > 0){
      for (int i = 1; i <= fileNum; i++) {
        String fileName = "file" + i;
        images.add(request.getParameter(fileName));
      }
    } else {
        return;
    }

    Entity testIdentity = new Entity("test");
    size = images.size();

    for (int i = 0; i < size; i++) {
      testIdentity.setProperty("imgUrl", images.get(i));
    }

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    datastore.put(testIdentity);
    
    //response.sendRedirect("");
  }
}