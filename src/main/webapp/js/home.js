function loadUser() {

  fetch('/veri').then(response => response.json()).then((list) => {
    var login = document.getElementById("sign");
    var quiz = document.getElementById("quiz");

    if(list.confirm === "Y") {
      login.innerHTML = "Sign-Out";
      quiz.style.visibility = "visible";

    } else {
      login.innerHTML = "Sign-In";
      quiz.style.visibility = "hidden";
    }
  });
}