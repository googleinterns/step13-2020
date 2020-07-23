function loadUser() {

  document.getElementById("Notification").style.visibility = "hidden";

  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
    var like = document.getElementById("like");
    var rec = document.getElementById("recommend");
      
    if(list.confirm === "Y") {
      login.innerHTML = "Sign-Out";
      like.style.visibility = "visible";
      rec.style.visibility = "visible";
    } else {
      login.innerHTML = "Sign-In";
      like.style.visibility = "hidden";
      rec.style.visibility = "hidden";
    }
  });

  getProds(1);
}

function getProds(sets) {
  fetch('/product').then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;

    if (sets === 1) {
      productGrid.innerHTML = "";
    }

    products.forEach((offer) => {
      if (count < (sets * 16) && count >= (sets * 16 - 16)) {
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\">" +
                    "<a href=\"\">" +
                        "<img src=\""+ offer.imgUrl +"\" alt=\"\">" +
                    "</a>" +
                    "<span class=\"product-brand-label\">" + offer.name + "</span>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\"><a target=\"_blank\" href=\""+ offer.productUrl +"\">" + offer.name +"</a></h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
      }

      count++;
    })

    document.getElementById("show").onclick = "getProds(" + (sets + 1) + ")";
  });
}

function filter(sets) {

  if (sets === 1) {
    document.getElementById('prodGrid').innerHTML = "";
  }

  var brand = "";
  var type = "";
  var tone = "";
  var vegan = "";
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
    if(document.getElementById("v" + i).checked) {
      vegan += document.getElementById("v" + i).value + "|";
    }
  }

  if (vegan.length === 0) {
    vegan = "None";
  }

  for (let i = 1; i <= 4; i++) {
    if(document.getElementById("p" + i).checked) {
      price += document.getElementById("p" + i).value + "|";
    }
  }

  if (price.length === 0) {
    price = "None";
  }

  var link = "/filter?brand=" + brand +
  "&type=" + type +
  "&tone=" + tone +
  "&vegan=" + vegan +
  "&price=" + price;

  fetch(link).then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;

    products.forEach((offer) => {
      if (count < (sets * 16) && count >= (sets * 16 - 16)) {
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\">" +
                    "<a href=\"\">" +
                        "<img src=\""+ offer.imgUrl +"\" alt=\"\">" +
                    "</a>" +
                    "<span class=\"product-brand-label\">" + offer.name + "</span>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\"><a target=\"_blank\" href=\""+ offer.productUrl +"\">" + offer.name +"</a></h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
      }

      count++;
    })

    document.getElementById("show").onclick = "filter(" + (sets + 1) + ")";
  });
}

function getRecs(sets) {

  if (sets === 1) {
    document.getElementById('prodGrid').innerHTML = "";
  }

  fetch("/recommend").then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;

    products.forEach((offer) => {
      if (count < (sets * 16) && count >= (sets * 16 - 16)) {
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\">" +
                    "<a href=\"\">" +
                        "<img src=\""+ offer.imgUrl +"\" alt=\"\">" +
                    "</a>" +
                    "<span class=\"product-brand-label\">" + offer.name + "</span>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\"><a target=\"_blank\" href=\""+ offer.productUrl +"\">" + offer.name +"</a></h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
      }

      count++;
    })

    document.getElementById("show").onclick = "getRecs(" + (sets + 1) + ")";
  });
}

function getLiked(sets) {

  if (sets === 1) {
    document.getElementById('prodGrid').innerHTML = "";
  }

  fetch("/getLiked").then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;

    products.forEach((offer) => {
      if (count < (sets * 16) && count >= (sets * 16 - 16)) {
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\">" +
                    "<a href=\"\">" +
                        "<img src=\""+ offer.imgUrl +"\" alt=\"\">" +
                    "</a>" +
                    "<span class=\"product-brand-label\">" + offer.name + "</span>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\"><a target=\"_blank\" href=\""+ offer.productUrl +"\">" + offer.name +"</a></h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></br>" +
                    "<span class=\"glyphicon glyphicon-remove\" onclick=\"removeLike("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
      }

      count++;
    })

    document.getElementById("show").onclick = "getLiked(" + (sets + 1) + ")";
  });
}

function putLiked(id) {
  fetch("/liked?id=" + id).then(response => response.json()).then((result) => {
    if (result.end === "Success") {
      document.getElementById("notice").innerText = "Added!";
      document.getElementById("Notification").style.visibility = "visible";
      setTimeout(function() {
        document.getElementById("Notification").style.visibility = "hidden";
      }, 3000);
    } else {
      document.getElementById("notice").innerText = "Must log in";
      document.getElementById("Notification").style.visibility = "visible";
      setTimeout(function() {
        document.getElementById("Notification").style.visibility = "hidden";
      }, 3000);
    }
  });
}

function removeLike(id) {
  fetch("/delete?id=" + id).then(response => response.json()).then((result) => {
    getLiked(1);
  });
}