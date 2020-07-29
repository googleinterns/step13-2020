document.getElementById("send").style.visibility = "hidden";

var imageUpload = document.getElementById('imageUpload');
imageUpload.addEventListener("change", handleFile, false);

//Canvas
var canvas1 = document.getElementById('lo-canvas');
var canvas2 = document.getElementById('hi-canvas');
var sampleCanvas = document.getElementById('Sample');
var ctx1 = canvas1.getContext('2d');
var ctx2 = canvas2.getContext('2d');
var sampleCtx = sampleCanvas.getContext('2d');

//Variables
var canvasx = $(canvas2).offset().left;
var canvasy = $(canvas2).offset().top;
var last_mousex = last_mousey = 0;
var mousex = mousey = 0;
var mousedown = false;
var map = new Map();
var pixel = [255, 255, 255];
var uploaded = false;

function handleFile() {
  if (!this.files.length) {
    console.log("Woop");
  } else {
    for (let i = 0; i < this.files.length; i++) {
      
      var display = new Image();

      display.onload = sketch;
      display.src = URL.createObjectURL(this.files[i]);
    }

    uploaded = true;
  }
}

function sketch() {
  ctx1.clearRect(0,0, canvas1.width, canvas1.height);

  if (this.height > 500 || this.width > 500) {

    var height = this.height;
    var width = this.width;

    while (height > 500 && width > 500) {
      height = 0.8 * height;
      width = 0.8 * width;
    }

    height = Math.round(height);
    width = Math.round(width);

    ctx1.drawImage(this, 0, 0, width, height);
  } else {
    ctx1.drawImage(this, 0, 0, this.width, this.height);
  }
}

//Mousedown
$(canvas2).on('mousedown', function(e) {
    ctx2.clearRect(0,0,canvas2.width,canvas2.height);
    last_mousex = parseInt(e.clientX-canvasx);
	last_mousey = parseInt(e.clientY-canvasy);
    mousedown = true;
});

//Mouseup
$(canvas2).on('mouseup', function(e) {
    mousedown = false;

    if (mousex - last_mousex !== 0 && uploaded) {
      //getMode();
      getMean();
      drawSample();
      document.getElementById("send").style.visibility = "visible";
    }
});

//Mousemove
$(canvas2).on('mousemove', function(e) {
    mousex = parseInt(e.clientX - canvasx);
	mousey = parseInt(e.clientY - canvasy);
    if(mousedown) {
        ctx2.clearRect(0,0,canvas2.width,canvas2.height); //clear canvas
        ctx2.beginPath();
        var width = mousex - last_mousex;
        var height = mousey - last_mousey;
        ctx2.rect(last_mousex,last_mousey,width,height);
        ctx2.strokeStyle = 'black';
        ctx2.lineWidth = 5;
        ctx2.stroke();
    }
    //Output
    $('#output').html('current: '+mousex+', '+mousey+'<br/>last: '+last_mousex+', '+last_mousey+'<br/>mousedown: '+mousedown);
});

/*
function getMode() {
  var data = ctx1.getImageData(last_mousex,last_mousey, mousex-last_mousex, mousey-last_mousey).data;
  var found = false;

  document.getElementById("text").style.visibility = "visible";

  for (let i = 0; i < data.length; i += 4) {
    found = false;
    var colors = [data[i], data[i + 1], data[i + 2]];
    
    for (let [key, value] of map) {
      if (key[0] === colors[0] && key[1] === colors[1] && key[2] === colors[2]) {
        found = true;
        value++;
        break;
      }
    }

    if (!found) {
      map.set(colors, 1);
    }
  }

  var mode = 0;

  for (let [key, value] of map) {
    if (value >= mode) {
      mode = value;
      pixel = key;
    }
  }

  document.getElementById("text").style.visibility = "hidden";

  console.log("Red is: " + pixel[0]);
  console.log("Blue is: " + pixel[1]);
  console.log("Green is: " + pixel[2]);
}
*/

function getMean() {
  var data = ctx1.getImageData(last_mousex,last_mousey, mousex-last_mousex, mousey-last_mousey).data;
  var found = false;
  var colors = [0, 0, 0];
  var count = 0

  for (let i = 0; i < data.length; i += 4) {
    found = false;
    colors[0] += data[i];
    colors[1] += data[i + 1];
    colors[2] += data[i + 2];

    count++;
  }

  for (let i = 0; i < colors.length; i++) {
    pixel[i] = colors[i] / count;
  }

  console.log("Red is: " + pixel[0]);
  console.log("Blue is: " + pixel[1]);
  console.log("Green is: " + pixel[2]);
}

function drawSample() {
  sampleCtx.fillStyle = 'rgba(' + pixel[0] + ', ' + pixel[1] + ', ' + pixel[2] + ', 1)';
  sampleCtx.fillRect(0, 0, 20, 20);
}

function sendColorData() {
  
  var link = "/color?r=" + pixel[0] +
  "&g=" + pixel[1] +
  "&b=" + pixel[2];

  fetch(link).then(response => response.json()).then((result) => {
    console.log(result.end);
    location.replace("../profile.html");
  });
}
