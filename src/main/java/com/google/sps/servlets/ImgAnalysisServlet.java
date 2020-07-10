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

import java.io.IOException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.sps.data.DataHold;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.servlet.annotation.WebServlet;

@WebServlet("/analyze")
public class ImgAnalysisServlet extends HttpServlet {

   private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    //Note: All things ID related will be replaced with the authenticated user name
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res)
      throws IOException {
    
      String key = null;
      boolean found = false;
      long id = 0;
      long searchForID = Long.parseLong(req.getParameter("getImgWithID"));
      ArrayList<DataHold> imgData = new ArrayList<>();

      Query query = new Query("ImageTest3");

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      PreparedQuery results = datastore.prepare(query);

      for (Entity entity : results.asIterable()) {

        key = (String) entity.getProperty("blobkey");
        id = (long) entity.getProperty("id");

        if (id == searchForID) {
          found = true;
          int size = (int) entity.getProperty("count");

          for (int i = 0; i < size; i++) {
            imgData.add(new DataHold((String) entity.getProperty("desc" + i), (String) entity.getProperty("score" + i)));
          }

          break;
        }
      }

      if (found) {
        Gson gson = new Gson();

        res.setContentType("application/json;");
        res.getWriter().println(gson.toJson(imgData));
      }


      /*
      BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
      blobstoreService.serve(blobKey, res);
      */
    }
}