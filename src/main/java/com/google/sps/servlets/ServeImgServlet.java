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

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.servlet.annotation.WebServlet;

/**
 * The objective of this class is to serve images that are specified by an ID number.
 * This class will not exist in the final version. It is only for testing.
 */
@WebServlet("/serve")
public class ServeImgServlet extends HttpServlet {

  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

  //Note: All things ID related will be replaced with the authenticated user name
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res)
    throws IOException {
    
    String key = null;
    boolean found = false;
    long id = 0;
    long searchForID = Long.parseLong(req.getParameter("getImgWithID"));

    //Prepares for a database query
    Query query = new Query("ImageTest3");

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    // Iterates through the loop to retrieve the string representation of the binary data
    // that has an ID number that matches the ID specified on the form.
    for (Entity entity : results.asIterable()) {

      key = (String) entity.getProperty("blobkey");
      id = (long) entity.getProperty("id");

      if (id == searchForID) {
        found = true;
        break;
      }
    }

    // Serves the image of the ID is found
    if (found) {
      BlobKey blobKey = new BlobKey(key);
      blobstoreService.serve(blobKey, res);
    }
  }
}