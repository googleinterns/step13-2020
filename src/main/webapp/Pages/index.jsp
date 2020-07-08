<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<%
    BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>


<html>
    <head>
        <title>Upload Test</title>
    </head>
    <body>
        <form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
            <input type="text" name="foo">
            <input type="file" name="myFile">
            <input type="number" name="id_num" placeholder="0">
            <input type="submit" value="Submit">
        </form>

        <input type="number" id="pickID" placeholder="0" onchange="getNumber()">
        <a href="/" id="getRequestBtn">Click me</a>
    </body>

    <script>
        function getNumber() {
          document.getElementById("getRequestBtn").href = "/serve?getImgWithID=" + document.getElementById("pickID").value;
        }    
    </script>
</html>