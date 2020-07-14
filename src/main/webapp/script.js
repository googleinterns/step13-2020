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

async function fetchDataAsync() {
    const response = await fetch('test.html');
    arrayOfProducts = await response.json();

}

//functions for filtering by product type
function filteringLipsticks(){
    let str = " ";
    var checkBox = document.getElementById("lipstick");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("lipstick").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
               str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("lipstick_").innerHTML = str;
    }else{
        document.getElementById("lipstick_").innerHTML = " ";
    }
} 

function filteringBlush(){
    let str = " ";
    var checkBox = document.getElementById("blush");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("blush").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
                 str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("blush_").innerHTML = str;
    }else{
        document.getElementById("blush_").innerHTML = " ";
    }
} 

function filteringBronzer(){
    let str = " ";
    var checkBox = document.getElementById("bronzer");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("bronzer").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
                 str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("bronzer_").innerHTML = str;
    }else{
        document.getElementById("bronzer_").innerHTML = " ";
    }
} 

function filteringEyebrow(){
    let str = " ";
    var checkBox = document.getElementById("eyebrow");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("eyebrow").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
                str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("brow_").innerHTML = str;
    }else{
        document.getElementById("brow_").innerHTML = " ";
    }
} 

function filteringEyeliner(){
    let str = " ";
    var checkBox = document.getElementById("eyeliner");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("eyeliner").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
                str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("eyeliner_").innerHTML = str;
    }else{
        document.getElementById("eyeliner_").innerHTML = " ";
    }
} 

function filteringEyeshadow(){
    let str = " ";
    var checkBox = document.getElementById("eyeshadow");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("eyeshadow").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
                 str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("eyeshadow_").innerHTML = str;
    }else{
        document.getElementById("eyeshadow_").innerHTML = " ";
    }
} 

function filteringLipliner(){
    let str = " ";
    var checkBox = document.getElementById("lipliner");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("lipliner").value;
           let prodName = arrayOfProducts[i].name;
           let prodType = arrayOfProducts[i].product_type;
           if(prodType == valName){
                str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("lipliner_").innerHTML = str;
    }else{
        document.getElementById("lipliner_").innerHTML = " ";
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
               str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("mascara_").innerHTML = str;
    }else{
        document.getElementById("mascara_").innerHTML = " ";
    }
} 

//filtering by tags
function gluten(){
    let str = " ";
    var checkBox = document.getElementById("gluten free");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodTags = arrayOfProducts[i].tag_list;
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("gluten free").value;
           let prodName = arrayOfProducts[i].name;
           if(prodTags.includes(valName)){
                 str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("gluten").innerHTML = str;
    }else{
        document.getElementById("gluten").innerHTML = " ";
    }
} 

function hypo(){
    let str = " ";
    var checkBox = document.getElementById("hypoallergenic");
    if(checkBox.checked == true){
        for(i = 0; i <  arrayOfProducts.length; i++){
           let prodTags = arrayOfProducts[i].tag_list;
           let prodBrand = arrayOfProducts[i].brand;
           let valName = document.getElementById("hypoallergenic").value;
           let prodName = arrayOfProducts[i].name;
           if(prodTags.includes(valName)){
                 str += prodBrand + ": " + prodName + "<br>";
           }
        }
        document.getElementById("hypo").innerHTML = str;
    }else{
        document.getElementById("hypo").innerHTML = " ";
    }
} 