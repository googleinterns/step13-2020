function loadUser() {

  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
      
    if(list.confirm === "Y") {
      login.innerHTML = "Sign-Out";
    } else {
      login.innerHTML = "Sign-In";
    }
  });
}