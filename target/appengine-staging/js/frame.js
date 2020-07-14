var imageUpload = document.getElementById('imageUpload');
imageUpload.addEventListener("change", handleFile, false);
//Canvas
var canvas1 = document.getElementById('lo-canvas');
var canvas2 = document.getElementById('hi-canvas');
var ctx1 = canvas1.getContext('2d');
var ctx2 = canvas2.getContext('2d');
//Variables
var canvasx = $(canvas2).offset().left;
var canvasy = $(canvas2).offset().top;
var last_mousex = last_mousey = 0;
var mousex = mousey = 0;
var mousedown = false;

function handleFile() {
  if (!this.files.length) {
    console.log("Woop");
  } else {
    for (let i = 0; i < this.files.length; i++) {
      
      var display = new Image();

      display.onload = sketch;
      display.src = URL.createObjectURL(this.files[i]);
    }
  }
}

function sketch() {
  //NOTE: the width and height (params 4 and 5) must be size proportionally to the params 1&2 (canvas size)
  ctx1.drawImage(this, 0, 0, this.width, this.height);
}

//Mousedown
$(canvas2).on('mousedown', function(e) {
    last_mousex = parseInt(e.clientX-canvasx);
	last_mousey = parseInt(e.clientY-canvasy);
    mousedown = true;
});

//Mouseup
$(canvas2).on('mouseup', function(e) {
    mousedown = false;
});

//Mousemove
$(canvas2).on('mousemove', function(e) {
    mousex = parseInt(e.clientX-canvasx);
	mousey = parseInt(e.clientY-canvasy);
    if(mousedown) {
        ctx2.clearRect(0,0,canvas2.width,canvas2.height); //clear canvas
        ctx2.beginPath();
        var width = mousex-last_mousex;
        var height = mousey-last_mousey;
        ctx2.rect(last_mousex,last_mousey,width,height);
        ctx2.strokeStyle = 'black';
        ctx2.lineWidth = 10;
        ctx2.stroke();
    }
    //Output
    $('#output').html('current: '+mousex+', '+mousey+'<br/>last: '+last_mousex+', '+last_mousey+'<br/>mousedown: '+mousedown);
});