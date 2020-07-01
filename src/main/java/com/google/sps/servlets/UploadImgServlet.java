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
import java.io.PrintWriter;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;

@WebServlet("/upload")
public class UploadImgServlet extends HttpServlet {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
        List<BlobKey> blobKeys = blobs.get("myFile");
        long id = Long.parseLong(req.getParameter("id_num"));
        boolean found = false;

        if (blobKeys == null || blobKeys.isEmpty()) {
            res.sendRedirect("/");
        }
        else {
          //NOTE: This is for testing only. The real database will use the username as the identifier

          Query query = new Query("ImageTest3");

          DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
          PreparedQuery results = datastore.prepare(query);

          for (Entity entity : results.asIterable()) {

            if (id == (long) entity.getProperty("id")) {
              found = true;
              break;
            }
          }

          if (found) {
            //This condition will not exist in the final version. The final will need an error for not logged in
            res.sendRedirect("/");
          } else {
             Entity imgTest = new Entity("ImageTest3");
             imgTest.setProperty("blobkey", blobKeys.get(0).getKeyString());
             imgTest.setProperty("id", id);

             datastore.put(imgTest);
          }
        }        
        
        res.sendRedirect("/Pages/index.jsp");

        /*
        if (blobKeys == null || blobKeys.isEmpty()) {
            res.sendRedirect("/");
        } else {
            System.out.println(blobKeys.get(0));
            res.sendRedirect("/serve?blob-key=" + blobKeys.get(0).getKeyString());
        }
        */
    }
}