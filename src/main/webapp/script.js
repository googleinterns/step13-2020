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

var arrayOfProducts = Array();
var array = Array();

async function fetchDataAsync() {
    const response = await fetch('test.html');
    arrayOfProducts = await response.json();

}

//functions for filtering by product type
function filteringLipsticks(){
    var checkBox = document.getElementById("lipstick");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("lipstick").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 

function filteringBlush(){
    var checkBox = document.getElementById("blush");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("blush").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 


function filteringBronzer(){
    var checkBox = document.getElementById("bronzer");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("bronzer").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 

function filteringEyebrow(){
    var checkBox = document.getElementById("eyebrow");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("eyebrow").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 

function filteringEyeliner(){
    var checkBox = document.getElementById("eyeliner");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("eyeliner").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 


function filteringEyeshadow(){
    var checkBox = document.getElementById("eyeshadow");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("eyeshadow").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("brands").innerHTML = array;
    }
} 


function filteringLipliner(){
    var checkBox = document.getElementById("lipliner");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("lipliner").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 

function filteringMascara(){
    var checkBox = document.getElementById("mascara");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("mascara").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               let product = (" " + prodBrand + ": " + prodName + "> $" + arrayOfProducts[i].price);
               array.push(product);
           }
        }
        document.getElementById("productType").innerHTML = array;
    }
} 






















function filteringTag(){
    var checkBox = document.getElementById("result");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodTags = arrayOfProducts[i].tag_list
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("result").value;
           let prodName = arrayOfProducts[i].name;
           if(tag_list.includes(valName)){
               let product = (" " + prodBrand +  + ": " + prodName 
               + "> $" + arrayOfProducts[i].price + "\n");
               array.push(product);
           }
        }
        document.getElementById("tags").innerHTML = array;
    }
} 
