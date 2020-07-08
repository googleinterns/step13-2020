const fileElem = document.getElementById("fileElem"),
      fileList = document.getElementById("fileList");

fileElem.addEventListener("change", handleFiles, false); 

function handleFiles() {
  if (!this.files.length) {
    fileList.innerHTML = "<p>No files selected!</p>";
  } else {
    fileList.innerHTML = "";
    const list = document.createElement("ul");
    fileList.appendChild(list);

    for (let i = 0; i < this.files.length; i++) {
      const li = document.createElement("li");
      list.appendChild(li);
      
      const img = document.createElement("img");
      img.src = URL.createObjectURL(this.files[i]);
      img.height = 60;

      // When display feature in the 2nd loop is removed, comment revoke out
      img.onload = function() {
        URL.revokeObjectURL(this.src);
      }
      li.appendChild(img);
      const info = document.createElement("span");
      info.innerHTML = this.files[i].name + ": " + this.files[i].size + " bytes";
      li.appendChild(info);
    }

    for (let i = 0; i < this.files.length; i++) {
      
      var display = new Image();

      display.onload = sketch;
      display.src = URL.createObjectURL(this.files[i]);
    }
  }
}

function sketch() {
  var canvas = document.getElementById('canvas');
  canvas.width = 800;
  canvas.height = 600;
  var ctx = canvas.getContext('2d');

  //NOTE: the width and height (params 4 and 5) must be size proportionally to the params 1&2 (canvas size)
  ctx.drawImage(this, 0, 0, this.width * 0.5, this.height * 0.5);
}

function fetchBlobstoreUrlAndShowForm() {
  fetch('/serve')
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('imgForm');
        messageForm.action = imageUploadUrl;
      });
}

function getImage() {

  fetch('/serve').then(response).then((texts) => {
    const imgElement = document.getElementById('test');
    imgElement.getAttribute("src") = texts;
  });

}