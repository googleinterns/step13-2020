function loadUser() {

  fetch('/veri').then(response => response.json()).then((list) => {
    var form = document.getElementById("infoForm");
    var login = document.getElementById("sign-in");
    var quiz = document.getElementById("quiz");

    if(list.confirm === "Y") {
      login.value = "Sign-Out";
      quiz.style.visibility = "visible";

    } else {
      login.value = "Sign-In";
      quiz.style.visibility = "hidden";
    }

    form.addEventListener('submit', handleForm);
  });
}

function handleForm(event) {
    event.preventDefault();
}