function loadUser() {

  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
    var like = document.getElementById("like");
    var rec = document.getElementById("recommend");
      
    if(list.confirm === "Y") {
      getRecs();
      getColors();
    } else {
      document.getElementById("photoLink").style.visibility = "hidden";

      getProds();

      var canvas = document.getElementById("Sample");
      var ctx = canvas.getContext('2d');
      var img = new Image();

      img.src = 'makeup.jpg';
      img.onload = function() {
        ctx.drawImage(this, 0, 0, this.width, this.height, 0, 0, 300, 300);
      }
    }
  });
}

function getRecs() {
  document.getElementById('prodGrid').innerHTML = "";

  fetch("/recommend").then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;
    products.forEach((offer) => {
      if (count < 16) {
        
        var str = offer.name;

        if (str.length > 7) {
          str = str.substring(0, 8);
          str += "..."
        }
        
        productGrid.innerHTML += 
          "<div class=\"col-md-auto product\">" +
            "<a href=\""+ offer.productUrl +"\">" +
            "<img src=\"" + offer.imgUrl + "\" onerror=\"this.onerror=null; this.src='makeup.jpg'\"></a>" +
            "<h5>" + str +"</h5>" +
        "</div>"
      }

      count++;
    })
  });
}

function getProds() {
  fetch('/product').then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;
    products.forEach((offer) => {
      if (count < 16) {
        productGrid.innerHTML += 
          "<div class=\"col-md-auto product\">" +
            "<a href=\""+ offer.productUrl +"\">" +
            "<img src=\"" + offer.imgUrl + "\"></a>" +
            "<h5>" + offer.name +"</h5>" +
        "</div>"
      }

      count++;
    })
  });
}

function getColors() {
  fetch("/pixel").then(response => response.json()).then((color) => {
    var canvas = document.getElementById("Sample");
    var ctx = canvas.getContext('2d');
    ctx.fillStyle = 'rgba(' + color.red + ', ' + color.green + ', ' + color.blue + ', 1)';
    ctx.fillRect(0, 0, 300, 300);

    document.getElementById("name").innerText = color.name;
  });
}