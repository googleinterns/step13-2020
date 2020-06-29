const fileSelect = document.getElementById("fileSelect"),
      fileElem = document.getElementById("fileElem"),
      fileList = document.getElementById("fileList");

const submitForm = document.getElementById("imgForm");
const formArea = document.getElementById("num");

fileSelect.addEventListener("click", function (e) {
  if (fileElem) {
    fileElem.click();
  }
  e.preventDefault(); // prevent navigation to "#"
}, false);

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

      if (formArea.value == null) {
        formArea.value = "0";
      }

      formArea.value = parseInt(formArea.value) + 1;
    }

    for (let i = 1; i <= formArea.value; i++) {

      const file = document.createElement("input");
      var url = URL.createObjectURL(this.files[i]);
      file.type = "hidden";
      file.name = "file" + i.toString(10);
      file.value = url;
      formArea.appendChild(file);
    }

    const submitButton = document.createElement("input");
    submitButton.type = "submit";
    formArea.appendChild(submitButton);

    for (let i = 0; i < this.files.length; i++) {
      
      var display = new Image();

      display.onload = sketch;
      display.src = URL.createObjectURL(this.files[i]);

      img.onload = function() {
        URL.revokeObjectURL(this.src);

        if (width > height) {
          if (width > MAX_WIDTH) {
            height *= MAX_WIDTH / width;
            width = MAX_WIDTH;
          } 
          else {
            if (height > MAX_HEIGHT) {
              width *= MAX_HEIGHT / height;
              height = MAX_HEIGHT;
            }
          }
        }

        canvas.width = width;
        canvas.height = height;
        var ctx = document.getElementById('canvas').getContext('2d');
        ctx.drawImage(img, width, height);

      }
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

/*
function draw() {
  var ctx = document.getElementById('canvas').getContext('2d');
  var img = new Image();
  img.onload = function() {
    for (var i = 0; i < 4; i++) {
      for (var j = 0; j < 3; j++) {
        ctx.drawImage(img, j * 50, i * 38, 50, 38);
      }
    }
  };
  img.src = 'https://mdn.mozillademos.org/files/5397/rhino.jpg';
}
*/