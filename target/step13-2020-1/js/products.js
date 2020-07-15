function loadUser() {

  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
      
    if(list.confirm === "Y") {
      login.innerHTML = "Sign-Out";
    } else {
      login.innerHTML = "Sign-In";
    }
  });

  filter();
}

function filter() {
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