function loadUser() {
 
  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
    var like = document.getElementById("like");
    var rec = document.getElementById("recommend");
    var profile = document.getElementById("profile");
    var quiz = document.getElementById("quiz");
      
    if(list.confirm === "Y") {
      login.innerHTML = "Sign-Out";
      like.style.visibility = "visible";
      rec.style.visibility = "visible";
      profile.style.visibility = "visible";
      quiz.style.visibility = "visible";
    } else {
      login.innerHTML = "Sign-In";
      like.style.visibility = "hidden";
      rec.style.visibility = "hidden";
      profile.style.visibility = "hidden";
      quiz.style.visibility = "hidden";
    }
  });
 
  getProds(1);
}
 
function getProds(sets) {
  console.log(sets);

  fetch('/product').then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    
    if (sets === 1) {
      productGrid.innerHTML = "";
    }
 
    products.forEach((offer) => {
 
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\" onclick=\"getDetails(" + offer.id + ")\">" +
                    "<img src=\""+ offer.imgUrl +"\" style=\"height:300px\" onerror=\"this.onerror=null; this.src='makeup.jpg'\" width=\"300\">" +
                    "<button type=\"button\" class=\"quickview\">View Details</button>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\">" +
                    offer.name + "</h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span id=\"l" + offer.id + "\" class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
    })
  });
}
 
function filter(sets) {
 
  document.getElementById('prodGrid').innerHTML = "";
 
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
 
    products.forEach((offer) => {
 
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\" onclick=\"getDetails(" + offer.id + ")\">" +
                    "<img src=\""+ offer.imgUrl +"\" style=\"height:300px\" onerror=\"this.onerror=null; this.src='makeup.jpg'\" width=\"300\">" +
                    "<button type=\"button\" class=\"quickview\">View Details</button>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\">" +
                    offer.name + "</h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span id=\"l" + offer.id + "\" class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
    })
  });
}
 
function getRecs() {
 
  document.getElementById('prodGrid').innerHTML = "";
  
 
  fetch("/recommend").then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
    var count = 0;
 
    products.forEach((offer) => {
 
      productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\" onclick=\"getDetails(" + offer.id + ")\">" +
                    "<img src=\""+ offer.imgUrl +"\" style=\"height:300px\" onerror=\"this.onerror=null; this.src='makeup.jpg'\" width=\"300\">" +
                    "<button type=\"button\" class=\"quickview\">View Details</button>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\">" +
                    offer.name + "</h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span id=\"l" + offer.id + "\" class=\"glyphicon glyphicon-heart\" onclick=\"putLiked("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
    })
  });
}
 
function getLiked(sets) {

  document.getElementById('prodGrid').innerHTML = "";
 
  fetch("/getLiked").then(response => response.json()).then((products) => {
    const productGrid = document.getElementById('prodGrid');
 
    products.forEach((offer) => {
 
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\" onclick=\"getDetails(" + offer.id + ")\">" +
                    "<img src=\""+ offer.imgUrl +"\" style=\"height:300px\" onerror=\"this.onerror=null; this.src='makeup.jpg'\" width=\"300\">" +
                    "<button type=\"button\" class=\"quickview\">View Details</button>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\">" +
                    offer.name + "</h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span id=\"l" + offer.id + "\" class=\"glyphicon glyphicon-remove\" onclick=\"removeLike("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
    })
  });
}
 
function putLiked(id) {
  fetch("/liked?id=" + id).then(response => response.json()).then((result) => {
    if (result.end === "Success") {
      document.getElementById("l" + id).style.color = "red";
    } else {
      alert("Please log in");
    }
  });
}
 
function removeLike(id) {
  fetch("/delete?id=" + id).then(response => response.json()).then((result) => {
    getLiked(1);
  });
}
 
function search() {
  var term = document.getElementById("search").value;
 
  if (term.length === 0) {
    getProds(1);
  } else {
    fetch("/search?term=" + term).then(response => response.json()).then((products) => {
      const productGrid = document.getElementById('prodGrid');
      productGrid.innerHTML = "";
 
      products.forEach((offer) => {
        
        productGrid.innerHTML += 
        "<div class=\"col-md-3 col-sm-6\">" +
            "<div class=\"product-grid\">" +
                "<div class=\"product-image\" onclick=\"getDetails(" + offer.id + ")\">" +
                    "<img src=\""+ offer.imgUrl +"\" style=\"height:300px\" onerror=\"this.onerror=null; this.src='makeup.jpg'\" width=\"300\">" +
                    "<button type=\"button\" class=\"quickview\">View Details</button>" +
                "</div>" +
                "<!-- Product Content -->" +
                "<div class=\"product-content\">" +
                    "<h4 class=\"title\">" +
                    offer.name + "</h4>" +
                    "<div class=\"price\">$" + offer.cost +
                    "<span id=\"l" + offer.id + "\" class=\"glyphicon glyphicon-remove\" onclick=\"removeLike("+ offer.id +")\"></span></div>" +
                "</div>" +
            "</div>" +
        "</div>";
      })      
    });
  }
}
 
function getDetails(id) {
 
  fetch("/details?id=" + id).then(response => response.json()).then((products) => {
 
    document.getElementById("main_div").innerHTML = 
      "<h3>Description</h3>" +
      "<p id=\"putDefault\">" +
        "DESCRIPTION GOES HERE" +
      "</p>";
 
    if (products.description === null) {
      document.getElementById("putDefault").innerText = "No Description Available";
    } else {
      document.getElementById("putDefault").innerText = products.description;
    }
 
    document.getElementById("putDesc").innerText = products.description;
    document.getElementById("putIngs").innerText = products.ings;
    document.getElementById("brandInfo").innerText = products.brand;
    document.getElementById("nameDesc").innerText = products.name;
    document.getElementById("nameDesc").href = products.productUrl;
    document.getElementById("price").innerText = "$" + products.cost;
    document.getElementById("product-pic-popup").onerror = function () {
        this.onerror=null; 
        this.src='makeup.jpg';
    };
    console.log(products.imgUrl);
 
    if (products.imgUrl !== null && products.imgUrl !== "") {
      document.getElementById("product-pic-popup").src = products.imgUrl;
    } else {
      document.getElementById("product-pic-popup").src = "makeup.jpg";
    }
    
    console.log(products.rating);
    getRatings(products.rating);
    showDetails();
  });
}