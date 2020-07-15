function loadUser() {

  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
      
    if(list.confirm === "Y") {
      login.innerHTML = "Sign-Out";
    } else {
      login.innerHTML = "Sign-In";
    }
  });

  getProds();
}

function getProds() {
  fetch('/product').then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    products.forEach((offer) => {
      productGrid.innerHTML = 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\">" +
                    "<a href=\"\">" +
                        "<img src=\"images/foundation.jpg\" alt=\"\">" +
                    "</a>" +
                    "<span class=\"product-brand-label\">NARS Cosmetics</span>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\"><a target=\"_blank\" href=\"https://www.narscosmetics.com/USA/gobi-sheer-glow-foundation/0607845060567.html?gclsrc=aw.ds&&gclid=Cj0KCQjwoub3BRC6ARIsABGhnybJPY4B6yUqqkB5E-UeGnO8jX0UTKF5ls1TVDn2DUd3rF6tpeDhnhoaAi_VEALw_wcB\">NARS Sheer Glow Foundation</a></h4>" +
                    "<div class=\"price\">$21.00 <a target=\"\" href=\"\">" +
                    "<span class=\"glyphicon glyphicon-heart\"></span></a></div>" +
                "</div>" +
            "</div>" +
        "</div>";
    })
  });
}

function filter() {

  var brand = "";
  var type = "";
  var tone = "";
  var ingredients = "";
  var price = "";

  for (let i = 1; i <= 6; i++) {
    if(document.getElementById("b" + i).checked) {
      brand += document.getElementById("b" + i).value + "|";
    }
  }

  if (brand.length === 0) {
    brand = "None";
  }

  for (let i = 1; i <= 9; i++) {
    if(document.getElementById("t" + i).checked) {
      type += document.getElementById("t" + i).value + "|";
    }
  }

  if (type.length === 0) {
    type = "None";
  }

  for (let i = 1; i <= 8; i++) {
    if(document.getElementById("s" + i).checked) {
      tone += document.getElementById("s" + i).value + "|";
    }
  }

  if (tone.length === 0) {
    tone = "None";
  }

  for (let i = 1; i <= 2; i++) {
    if(document.getElementById("i" + i).checked) {
      ingredients += document.getElementById("i" + i).value + "|";
    }
  }

  if (ingredients.length === 0) {
    ingredients = "None";
  }

  for (let i = 1; i <= 4; i++) {
    if(document.getElementById("p" + i).checked) {
      price += document.getElementById("p" + i).value + "|";
    }
  }

  if (price.length === 0) {
    price = "None";
  }

  var link = "/link?brand=" + brand +
  "&type=" + type +
  "&tone=" + tone +
  "&ingredients=" + ingredients +
  "&price" + price;

  fetch(link).then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    products.forEach((offer) => {
      productGrid.innerHTML = 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\">" +
                    "<a href=\"\">" +
                        "<img src=\"images/foundation.jpg\" alt=\"\">" +
                    "</a>" +
                    "<span class=\"product-brand-label\">NARS Cosmetics</span>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\"><a target=\"_blank\" href=\"https://www.narscosmetics.com/USA/gobi-sheer-glow-foundation/0607845060567.html?gclsrc=aw.ds&&gclid=Cj0KCQjwoub3BRC6ARIsABGhnybJPY4B6yUqqkB5E-UeGnO8jX0UTKF5ls1TVDn2DUd3rF6tpeDhnhoaAi_VEALw_wcB\">NARS Sheer Glow Foundation</a></h4>" +
                    "<div class=\"price\">$21.00 <a target=\"\" href=\"\">" +
                    "<span class=\"glyphicon glyphicon-heart\"></span></a></div>" +
                "</div>" +
            "</div>" +
        "</div>";
    })
  });
}