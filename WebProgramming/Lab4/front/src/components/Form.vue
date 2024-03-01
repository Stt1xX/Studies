<template>
  <form>
    <div id="innerForm">
      <div class="wrapperInput">
        <label for="coordinateX" class="itemLabel">Coordinate X: </label>
        <input id="coordinateX" v-model="x" v-on:input="paintX"
               type="text" placeholder="Enter the number from -2 to 2" maxlength="13"/>
        <p id="coordinateXMessage" class="errorMessage">Coordinate X can't be empty!</p>
      </div>
      <div class="wrapperInput">
        <label for="coordinateY" class="itemLabel">Coordinate Y: </label>
        <input id="coordinateY" v-model="y" v-on:input="paintY"
             type="text" placeholder="Enter the number from -5 to 3" maxlength="13"/>
        <p id="coordinateYMessage" class="errorMessage">Coordinate X can't be empty!</p>
      </div>
      <div class="wrapperInput">
        <label for="radius" class="itemLabel">Radius: </label>
        <input  id="radius"  v-model="radius" v-on:input="paintRadius();
                rIsValid(radius) && isPositive(radius) ? setRadius(radius) : setRadius(0);"
                type="text" placeholder="Enter the number from -2 to 2" maxlength="13"/>
        <p id="radiusMessage" class="errorMessage">Coordinate X can't be empty!</p>
      </div>
      <button type="button" v-on:click="click()">
        Try It!
      </button>
    </div>
  </form>
</template>

<script setup>

import {inject, onMounted, ref} from "vue";

// Validation

const isString = (str) => {
  return !isNaN(str) && // use type coercion to parse the _entirety_ of the string (`parseFloat` alone does not do this)...
      !isNaN(parseFloat(str)) // ...and ensure strings of whitespace fail
}

const rIsValid = (radius) => isString(radius) && radius <= 2

const isPositive = (radius) => radius >= 0

const xIsValid = x => {
  return isString(x) && x >= -2 && x <= 2;
}

const yIsValid = y => {
  return isString(y) && y >= -5 && y <= 3;
}

//Painting logic

let rInput,  yInput, xInput, coordinateXMessage, coordinateYMessage, radiusMessage

onMounted(() => {
  xInput = document.getElementById("coordinateX");
  yInput = document.getElementById("coordinateY");
  rInput = document.getElementById("radius");
  coordinateXMessage = document.getElementById("coordinateXMessage");
  coordinateYMessage = document.getElementById("coordinateYMessage");
  radiusMessage = document.getElementById("radiusMessage");
})


async function click(){
  if(paintX() & paintY() & paintRadius()){
    await sentForm()
  }
}

function paintX(){
  if (x.value === ''){
    xInput.classList.add("errorInput")
    coordinateXMessage.innerHTML = "Coordinate X can't be empty!"
    coordinateXMessage.style.visibility = "visible"
    return false;
  }
  else if (!xIsValid(x.value)) {
    xInput.classList.add("errorInput")
    coordinateXMessage.innerHTML = "Coordinate X is invalid!"
    coordinateXMessage.style.visibility = "visible"
    return false;
  }
  else {
    xInput.classList.remove("errorInput")
    coordinateXMessage.style.visibility = "hidden"
    return true;
  }
}
function paintY(){
  if (y.value === ''){
    yInput.classList.add("errorInput")
    coordinateYMessage.innerHTML = "Coordinate Y can't be empty!"
    coordinateYMessage.style.visibility = "visible"
    return false;
  }
  else if (!yIsValid(y.value)) {
    yInput.classList.add("errorInput")
    coordinateYMessage.innerHTML = "Coordinate Y is invalid!"
    coordinateYMessage.style.visibility = "visible"
    return false;
  }
  else {
    yInput.classList.remove("errorInput")
    coordinateYMessage.style.visibility = "hidden"
    return true;
  }
}

function paintRadius(){
  if (radius.value === ''){
    rInput.classList.add("errorInput")
    radiusMessage.innerHTML = "Radius can't be empty!"
    radiusMessage.style.visibility = "visible"
    return false;
  }
  else if (!rIsValid(radius.value)) {
    rInput.classList.add("errorInput")
    radiusMessage.innerHTML = "Radius is invalid!"
    radiusMessage.style.visibility = "visible"
    return false;
  }
  else if(!isPositive(radius.value)) {
    rInput.classList.add("errorInput")
    radiusMessage.innerHTML = "Radius should be positive!"
    radiusMessage.style.visibility = "visible"
    return false;
  }
  else {
    rInput.classList.remove("errorInput")
    radiusMessage.style.visibility = "hidden"
    return true;
  }
}

//ajax request
async function sentForm(){
  await fetch('http://localhost:8080/api-sentForm', {
    method: "POST",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      x: x.value,
      y: y.value,
      radius: radius.value
    })
  }).then(point => point.json())
      .then((point) => {
        addNewAttempt(point)
      })
}

//communication with other components

let x = ref('')
let y = ref('')
let radius = ref('')

function setCoords(newX, newY){
  x.value = newX;
  y.value = newY;
}

defineExpose({
  setCoords,
  click
})

const setRadius = inject('setRadius')
const addNewAttempt = inject('addNewAttempt')

</script>

<style scoped>

  .wrapperInput{
    margin-bottom: 30px;
  }

  .errorMessage {
    color: red;
    font-size: 13px;
    text-align: left;
    margin: 13px auto;
    visibility: hidden;
  }

  input, p{
    width: 80%;
  }

  #innerForm{
    padding: 30px;
    text-align: center;
  }


  .itemLabel{
    margin-bottom: 50px;
  }

  /* input style */

  input::placeholder{
    color: var(--fontColor);
  }

  input:focus {
    outline: 0;
  }

  input:-webkit-autofill,
  input:-webkit-autofill:hover,
  input:-webkit-autofill:focus,
  input:-webkit-autofill:active{
    box-shadow: inset 0 0 20px 20px var(--backgroundColor);
    transition: background-color 5000s ease-in-out 0s;
    -webkit-text-fill-color: var(--fontColor)
  }

  input{
    opacity: 1;
    margin-top: 5px;
    color: var(--fontColor);
    display: inline-block;
    height: calc(2.25rem + 2px);
    padding: 0.375rem 0.75rem;
    font-family: inherit;
    font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
    background: transparent;
    background-clip: padding-box;
    border: none;
    border-bottom: 2px solid var(--fontColor);
    border-radius: 0.25rem;
    transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
  }

  /* error input style */

  .errorInput{
    color : red;
    border-bottom: 2px solid red;
  }

  .errorInput::placeholder{
    color: red;
    font-size: 1rem;
  }

  input::placeholder{
    font-size: 1rem;
  }

  .errorInput:-webkit-autofill,
  .errorInput:-webkit-autofill:hover,
  .errorInput:-webkit-autofill:focus,
  .errorInput:-webkit-autofill:active {
    box-shadow: inset 0 0 20px 20px var(--backgroundColor);
    transition: background-color 5000s ease-in-out 0s;
    -webkit-text-fill-color: red;
    -webkit-text-stroke-color: red;
  }

  /* button */

  button{
    font-size: 30px;
    border-radius: 40px;
    width: 300px;
    height: 60px;
  }
</style>