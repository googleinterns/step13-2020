// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
var userSelecteddAnswers = [];
 
(window.onload = function(){
  function buildQuiz(){
    const output = [];
    myQuestions.forEach(
      (currentQuestion, questionNumber) => {
        const question = "";
        const values =[];
        const answers = [];
        for(choice in currentQuestion.answers){
          answers.push(
                `<li class="answer-choice" style="list-style: none;">
                    <input type="radio" id="${choice}" value="${currentQuestion.values[choice]}" name="q${questionNumber}" class="btn" style="visibility: hidden;">
                    <label for="${choice}">
                        <img class="choice-img" src="${currentQuestion.answers[choice]}">
                    </label>
                    <div class="description">
                        <h5>${currentQuestion.values[choice]}</h5>
                    </div>
                </li>`
          );
        }
        output.push(
            `<div class="slide">
                <div class="question">
                    <form class="questionForm" id="q${questionNumber}" data-question="${questionNumber}" method="POST">
                        <h3>${currentQuestion.question}</h3>
                        <p>Select one and press "Next"</p>
                        <ul class="list-question">
                            <div class="answers"> ${answers.join("")} </div>
                        </ul>
                    </form>
                </div>
            </div>`
        );
      }
    );
    quizContainer.innerHTML = output.join('');
  }
 
  function showResults(){
    const answerContainers = quizContainer.querySelectorAll('.answers');
    const answerValueContainer = quizContainer.querySelectorAll('.values');
    myQuestions.forEach( (currentQuestion, questionNumber) => {
      const answerContainer = answerContainers[questionNumber];
      const selector = `input[name=q${questionNumber}]:checked`;
      const userAnswer = (answerContainer.querySelector(selector) || {}).value;
      const userValue = (answerValueContainer.querySelector(selector) || {}).value;

      userSelectedAnswers.push(userValue);
      console.log(userValue)
    });
    resultsContainer.innerHTML = `Done! Click 'Results' to view your product recommendations!`;
  }
 
  function showSlide(n) {
    slides[currentSlide].classList.remove('active-slide');
    slides[n].classList.add('active-slide');
    currentSlide = n;
    if(currentSlide === 0){
      previousButton.style.display = 'none';
    }
    else{
      previousButton.style.display = 'inline-block';
    }
    if(currentSlide === slides.length-1){
      nextButton.style.display = 'none';
      submitButton.style.display = 'inline-block';
    }
    else{
      nextButton.style.display = 'inline-block';
      submitButton.style.display = 'none';
    }
  }
 
  function showNextSlide() {
    showSlide(currentSlide + 1);
  }
 
  function showPreviousSlide() {
    showSlide(currentSlide - 1);
  }

//   stores answer values for each question into array
  var arr = [];
  var position = 1;

  function collectValues() {
    var num = 4;
    if (position = 1) {
    	num = 8;
    }
    for (let i = 1; i <= num; i += 1) {
        if (document.getElementById("q" + position + "c" + i).checked) {
            arr[position - 1] = document.getElementById("q" + position + "c" + i).value;
            break;
        }
    }
    position++;
  }

  const quizContainer = document.getElementById('quiz');
  const resultsContainer = document.getElementById('results');
  const submitButton = document.getElementById('submit');  
  const myQuestions = [
  {
    question: "Which color do you wear the most?",
    answers: {
        q1c1: "images/quiz/reds.png",
        q1c2: "images/quiz/green.png",
        q1c3: "images/quiz/yellows.png",
        q1c4: "images/quiz/blacks.png",
        q1c5: "images/quiz/blues.png",
        q1c6: "images/quiz/purples.png",
        q1c7: "images/quiz/oranges.png",
        q1c8: "images/quiz/neutrals.png"
    },
    values: {
        q1c1: "red",
        q1c2: "green",
        q1c3: "yellow",
        q1c4: "black",
        q1c5: "blue",
        q1c6: "purple",
        q1c7: "orange",
        q1c8: "white"
    }
  },
  {
    question: "Which foundation range does your skin tone fall into?",
    answers: {
        q2c1: "images/quiz/skin-tones/fair.jpg",
        q2c2: "images/quiz/skin-tones/medium.png",
        q2c3: "images/quiz/skin-tones/tan.jpg",
        q2c4: "images/quiz/skin-tones/deep.jpg"
    },
    values: {
        q2c1: "fair",
        q2c2: "medium",
        q2c3: "tan",
        q2c4: "dark",
    }
  },
  {
    question: "Which shade of red lipstick would you wear?",
    answers: {
        q3c1: "images/quiz/bright-red.jpg",
        q3c2: "images/quiz/dark-red.gif",
        q3c3: "images/quiz/classic-red.jpg",
        q3c4: "images/quiz/none.png"
    },
    values: {
        q3c1: "bright red",
        q3c2: "dark red",
        q3c3: "classic red",
        q3c4: "none",
    }
  },
  {
    question: "What is your go-to makeup product?",
    answers: {
        q4c1: "images/quiz/lipstick.jpg",
        q4c2: "images/quiz/mascara.jpg",
        q4c3: "images/quiz/blushh.jpg",
        q4c4: "images/quiz/foundation-product.jpg"
    },
    values: {
        q4c1: "lipstick",
        q4c2: "mascara",
        q4c3: "blush",
        q4c4: "foundation"
    }
  },
  {
    question: "Which makeup product can't you live without?",
    answers: {
        q5c1: "images/quiz/lipstick.jpg",
        q5c2: "images/quiz/eyeliner.jpg",
        q5c3: "images/quiz/concealer.jpg",
        q5c4: "images/quiz/brow-wiz.jpg"
    },
    values: {
        q5c1: "lipstick",
        q5c2: "eyeliner",
        q5c3: "concealer",
        q5c4: "brow pencil"
    }
  },
  {
    question: "Which lipstick shade would you wear with a white outfit?",
    answers: {
        q6c1: "images/quiz/classic-red.jpg",
        q6c2: "images/quiz/lightpink.jpg",
        q6c3: "images/quiz/nude.jpg",
        q6c4: "images/quiz/coral.jpg"
    },
    values: {
        q6c1: "red",
        q6c2: "pink",
        q6c3: "nude",
        q6c4: "coral"
    }
  },
  {
    question: "Which bold color describes you?",
    answers: {
        q7c1: "images/quiz/hot_pink.jpg",
        q7c2: "images/quiz/bright-red.jpg",
        q7c3: "images/quiz/green_c.png",
        q7c4: "images/quiz/blue.png"
    },
    values: {
        q7c1: "pink",
        q7c2: "red",
        q7c3: "green",
        q7c4: "blue"
    }
  },
  {
    question: "Pick a warm color",
    answers: {
        q8c1: "images/quiz/classic-red.jpg",
        q8c2: "images/quiz/yellow.jpg",
        q8c3: "images/quiz/orange.png",
        q8c4: "images/quiz/brown.jpeg"
      },
    values: {
        q8c1: "red",
        q8c2: "yellow",
        q8c3: "orange",
        q8c4: "brown"
    }
  },
  {
    question: "Pick a cool color",
    answers: {
        q9c1: "images/quiz/purple.png",
        q9c2: "images/quiz/blue.png",
        q9c3: "images/quiz/green_c.png",
        q9c4: "images/quiz/fuchsia.jpg",
    },
    values: {
        q9c1: "purple",
        q9c2: "blue",
        q9c3: "green",
        q9c4: "pink"
    }
  }
];
  buildQuiz();
  const previousButton = document.getElementById("previous");
  const nextButton = document.getElementById("next");
  const slides = document.querySelectorAll(".slide");
  let currentSlide = 0;
  showSlide(currentSlide);
  submitButton.addEventListener("click", showResults);
  previousButton.addEventListener("click", showPreviousSlide);
  nextButton.addEventListener('click', showNextSlide);

  // Disable 'Next' Button until user selects an option **Not working
//   $('#choice').click(function() {
//       if ($("#choice").prop('checked', true)){
//           $('#next').prop('disabled', false);
//         } 
//         else {
//             $('#next').prop('disabled', true);
//         }
//     });

  // Popup Window
  document.getElementById("submit").addEventListener("click", function(){
      document.querySelector(".signin").style.display = "flex";
  })

  document.querySelector(".close-btn").addEventListener("click", function(){
      document.querySelector(".signin").style.display = "none";
  })

  function processAnswers(){
        var usersPick = userSelectedAnswers;
        var hiddenSelectedAnswers = document.getElementById("selectedAnswers");
        hiddenSelectedAnswers.value = usersPick.join(",");
        var form = document.getElementById("answers").action;
        form.submit();
        console.log("done");
  }
})();
