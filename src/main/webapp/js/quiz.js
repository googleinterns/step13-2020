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
let userSelectedAnswers = [];d49f
(window.onload = function(){
  function buildQuiz(){
    const output = [];
    myQuestions.forEach(
      (currentQuestion, questionNumber) => {
        const answers = [];
        for(letter in currentQuestion.answers){
          answers.push(
            `<label>
              <input type="radio" name="question${questionNumber}" value="${letter}">
              ${letter} :
              ${currentQuestion.answers[letter]}
            </label>`
          );
        }
        output.push(
          `<div class="slide">
            <div class="question"> ${currentQuestion.question} </div>
            <div class="answers"> ${answers.join("")} </div>
          </div>`
        );
      }
    );
    quizContainer.innerHTML = output.join('');
  }

  function showResults(){
    const answerContainers = quizContainer.querySelectorAll('.answers');
    myQuestions.forEach( (currentQuestion, questionNumber) => {
      const answerContainer = answerContainers[questionNumber];
      const selector = `input[name=question${questionNumber}]:checked`;
      const userAnswer = (answerContainer.querySelector(selector) || {}).value;
      userSelectedAnswers.push(userAnswer);
      answerContainers[questionNumber].style.color = 'lightgreen';
    });
    resultsContainer.innerHTML = `Done! Click next for your product recommendations!`;
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
  const quizContainer = document.getElementById('quiz');
  const resultsContainer = document.getElementById('results');
  const submitButton = document.getElementById('submit');  
  const myQuestions = [
  {
    question: "What is your perferred color family?",
    answers: {
      a: "Warm colors: reds, oranges, yellows",
      b: "Cool colors: blues, greens, purples",
      c: "Neutrals: browns, black/grey, white",
    }
  },
  {
    question: "What is your eye color?",
    answers: {
      a: "Brown",
      b: "Green",
      c: "Blue",
      d: "Hazel"
    }
  },
  {
    question: "Which time of day do you enjoy the most?",
    answers: {
      a: "Morning",
      b: "Afternoon",
      c: "Evening",
      d: "Night"
    }
  },
  {
    question: "What is your clothing style?",
    answers: {
        a: "Casual",
        b: "Dressy",
        c: "Business",
    }
  },
  {
    question: "If you selected warm as your perferred color family, what is your favorite warm?",
    answers: {
        a: "Red",
        b: "Yellow",
        c: "Orange",
        d: "I did not select warm."
    }
  },
  {
    question: "If you selected cool as your perferred color family, what is your favorite cool?",
    answers: {
        a: "Blue",
        b: "Green",
        c: "Purple",
        d: "I did not select cool."
    }
  },
  {
    question: "If you selected neutral as your perferred color family, what is your favorite neutral?",
    answers: {
        a: "Brown",
        b: "Black/Grey",
        c: "White",
        d: "I did not select neutral."
    }
  }
];
  buildQuiz();
  const previousButton = document.getElementById("previous");
  const nextButton = document.getElementById("next");
  const slides = document.querySelectorAll(".slide");
  let currentSlide = 0;
  showSlide(currentSlide);
  submitButton.addEventListener('click', showResults);
  previousButton.addEventListener("click", showPreviousSlide);
  nextButton.addEventListener("click", showNextSlide);
})();
