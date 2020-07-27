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
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

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

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ColorInfo;
import com.google.cloud.vision.v1.DominantColorsAnnotation;

import com.google.protobuf.ByteString;

/**
 * This class has two primary functions. The first function is to convert
 * image files into a binary string that can be stored in a database for
 * retrieval. The second function is use the Cloud Vision API to analyze
 * the qualities of the image.
 */
@WebServlet("/upload")
public class UploadImgServlet extends HttpServlet {

  //Creates a class to access the Blobstore API
  private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    // This function conducts a POST request where data from a form is used
    // as input for database entries.
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

      // Creates a map containing the uploaded files. The key is the name, and
      // the value is a list of the images in binary format. 
      Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
      List<BlobKey> blobKeys = blobs.get("myFile");

      //Holds the id of the uploaded image. Will be replaced by the username
      long id = Long.parseLong(req.getParameter("id_num"));
      
      //Used to track the number of labels per image. Will not exist in final code
      int count = 0;
      boolean found = false;

      //Checks if a binary string could be made. Must add something to check for only img files
      if (blobKeys == null || blobKeys.isEmpty()) {
        res.sendRedirect("/");
      }
      else {
        //NOTE: This is for testing only. The real database will use the username as the identifier
        Query query = new Query("ImageTest3");

        // Get API's to make a query to the database
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery results = datastore.prepare(query);

        // This loop searches for database entries with the same id. In the final version,
        // it would be by username
        for (Entity entity : results.asIterable()) {

          if (id == (long) entity.getProperty("id")) {
            found = true;
            break;
          }
        }

        if (found) {
          //The final version will go back to the product page with an invalid username error
          res.sendRedirect("/");
        } 
        
        //Final Entity will be UserData. This section begins to populate the database
        Entity imgTest = new Entity("ImageTest3");
        imgTest.setProperty("blobkey", blobKeys.get(0).getKeyString());
        imgTest.setProperty("id", id);

        // Yields an array of bytes representing an image so that operation can be performed.
        byte[] blobBytes = getBlobBytes(blobKeys.get(0));
        
        // Yields labels for object identification. Final version will need face detection
        List<EntityAnnotation> imageLabels = getImageLabels(blobBytes);

        // This is used for getting image properties. Will have to make it like the labels tutorial
        // detectProperties(blobBytes);
        
        // This entire section is used for storing the labels in the database.
        /*
        for (EntityAnnotation label : imageLabels) {
          imgTest.setProperty("desc" + count, label.getDescription());
          imgTest.setProperty("score" + count, label.getScore());
          count++;
        }

        imgTest.setProperty("count", count);
        */

        //Sends new data to the database
        datastore.put(imgTest);
      }
      
      res.sendRedirect("/Pages/index.jsp");
    }

    /**
   * Blobstore stores files as binary data. This function retrieves the binary data stored at the
   * BlobKey parameter.
   */
  private byte[] getBlobBytes(BlobKey blobKey) throws IOException {
    ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();

    int fetchSize = BlobstoreService.MAX_BLOB_FETCH_SIZE;
    long currentByteIndex = 0;
    boolean continueReading = true;
    while (continueReading) {
      // end index is inclusive, so we have to subtract 1 to get fetchSize bytes
      byte[] b =
          blobstoreService.fetchData(blobKey, currentByteIndex, currentByteIndex + fetchSize - 1);
      outputBytes.write(b);

      // if we read fewer bytes than we requested, then we reached the end
      if (b.length < fetchSize) {
        continueReading = false;
      }

      currentByteIndex += fetchSize;
    }

    return outputBytes.toByteArray();
  }

  /**
   * Uses the Google Cloud Vision API to generate a list of labels that apply to the image
   * represented by the binary data stored in imgBytes.
   */
  private List<EntityAnnotation> getImageLabels(byte[] imgBytes) throws IOException {
    
    //Constructs an image from a byte string
    ByteString byteString = ByteString.copyFrom(imgBytes);
    Image image = Image.newBuilder().setContent(byteString).build();

    //Loads classes to identify image features
    Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
    AnnotateImageRequest request =
        AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
    List<AnnotateImageRequest> requests = new ArrayList<>();
    requests.add(request);

    //Creates an API request to identify the features in the image
    ImageAnnotatorClient client = ImageAnnotatorClient.create();
    BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
    client.close();
    List<AnnotateImageResponse> imageResponses = batchResponse.getResponsesList();
    AnnotateImageResponse imageResponse = imageResponses.get(0);

    if (imageResponse.hasError()) {
      System.err.println("Error getting image labels: " + imageResponse.getError().getMessage());
      return null;
    }

    return imageResponse.getLabelAnnotationsList();
  }

  /* From the detect color guide*/
  // Detects image properties such as color frequency from the specified local image.
  public static void detectProperties(byte[] byteStr) throws IOException {
    List<AnnotateImageRequest> requests = new ArrayList<>();

    ByteString imgBytes = ByteString.copyFrom(byteStr);

    //Gets classes needed to identify image features
    Image img = Image.newBuilder().setContent(imgBytes).build();
    Feature feat = Feature.newBuilder().setType(Feature.Type.IMAGE_PROPERTIES).build();
    AnnotateImageRequest request =
        AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
    requests.add(request);

    // Initialize client that will be used to send requests. This client only needs to be created
    // once, and can be reused for multiple requests. After completing all of your requests, call
    // the "close" method on the client to safely clean up any remaining background resources.
    try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
      BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.format("Error: %s%n", res.getError().getMessage());
          return;
        }

        // For full list of available annotations, see http://g.co/cloud/vision/docs
        DominantColorsAnnotation colors = res.getImagePropertiesAnnotation().getDominantColors();
        for (ColorInfo color : colors.getColorsList()) {
          System.out.format(
              "fraction: %f%nr: %f, g: %f, b: %f%n",
              color.getPixelFraction(),
              color.getColor().getRed(),
              color.getColor().getGreen(),
              color.getColor().getBlue());
        }
      }
    }
  }
}