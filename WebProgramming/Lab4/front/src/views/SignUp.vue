<template>
  <div id="formWrapper">
    <div id="form" class="scallop">

      <div id="innerForm">
        <h1>Registration</h1>
        <span>
          Already have an account?
          <a href="http://localhost:5173/login" >Sign In</a>
        </span>
        <div class="wrapperInput">
          <input id="username" v-model="username" @input="checkUsername"
                 type="text" placeholder="Username" maxlength="20"/>
          <p id="usernameMessage" class="errorMessage">Field username can't be empty!</p>
        </div>
        <div class="wrapperInput">
          <input id="password" v-model="password" @input="checkPassword"
                 type="password" placeholder="Password" maxlength="20"/>
          <p id="passwordMessage" class="errorMessage">Field password can't be empty!</p>
        </div>
        <div class="wrapperInput">
          <input  id="confirmPassword"  v-model="secondPassword" @input="checkSecondPassword"
                  type="password" placeholder="Confirm password" maxlength="20"/>
          <p id="ConfirmPasswordMessage" class="errorMessage">Passwords don't match!</p>
        </div>
        <button @click="click">Sign Up</button>
      </div>
    </div>
  </div>
</template>

<script setup>

import {onMounted} from "vue";

let username, password, secondPassword;

let usernameMessage, passwordMessage, confirmPasswordMessage;

onMounted(() =>{
  usernameMessage = document.getElementById("usernameMessage")
  passwordMessage = document.getElementById("passwordMessage")
  confirmPasswordMessage = document.getElementById("ConfirmPasswordMessage")
})
function checkUsername(){
  console.log(username)
  if (username === '' || username === undefined)
    usernameMessage.style.visibility = "visible"
  else
    usernameMessage.style.visibility = "hidden"
}

function checkPassword(){
  if (password === '' || password === undefined)
    passwordMessage.style.visibility = "visible"
  else
    passwordMessage.style.visibility = "hidden"
}

function checkSecondPassword(){
  if (secondPassword !== password)
    confirmPasswordMessage.style.visibility = "visible"
  else
    confirmPasswordMessage.style.visibility = "hidden"
}

function click(){
  checkUsername();
  checkPassword();
  checkSecondPassword();
}

</script>

<style scoped>

#formWrapper{
  margin-top: 130px;
  margin-bottom: 130px;
  margin-right: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#form{
  height: 98%; /* For correctly display border */
  width: 600px;
}

#innerForm{
  height: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
  text-align: center;
  flex-wrap: wrap;
  padding: 20px;
}

.wrapperInput{
  width: 100%;
}

.errorMessage {
  color: red;
  font-size: 13px;
  text-align: left;
  margin: 13px auto 20px;
  visibility: hidden;
}

input, p{
  width: 80%;
}

/* button */

button{
  font-size: 26px;
  border-radius: 40px;
  width: 220px;
  height: 50px;
}

</style>