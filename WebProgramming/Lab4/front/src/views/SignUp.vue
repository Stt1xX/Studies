<template>
  <div id="formWrapper">
    <div id="form" class="scallop">

      <div id="innerForm">
        <h1>Registration</h1>
        <span>
          Already have an account?
          <router-link to="/login">Sign In</router-link>
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
        <button @click="click($router)">Sign Up</button>
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
  if (username === '' || username === undefined){
    usernameMessage.innerHTML = "Field username can't be empty!";
    usernameMessage.style.visibility = "visible"
    return false;
  } else{
    usernameMessage.style.visibility = "hidden"
    return true;
  }
}

function checkPassword(){
  if (password === '' || password === undefined){
    passwordMessage.style.visibility = "visible"
    return false;
  } else{
    passwordMessage.style.visibility = "hidden"
    return true;
  }
}

function checkSecondPassword(){
  if (secondPassword !== password){
    confirmPasswordMessage.style.visibility = "visible"
    return false;
  } else{
    confirmPasswordMessage.style.visibility = "hidden"
    return true;
  }
}

function click(router){
  if (checkUsername() & checkPassword() & checkSecondPassword()) {
    fetch('http://localhost:8080/api-signUp', {
      method: "POST",
      headers: {
        'Content-Type': 'application/json',
        credentials: 'include'

      },
      body: JSON.stringify({
        username: username,
        password: password,
      })
    }).then(res => {
      if (res.ok) {
        setCookie("Login", username, 2);
        setCookie("Password", password, 2);
        router.push('/main')
      } else {
        usernameMessage.innerHTML = "This user with same name has already been registered!";
        usernameMessage.style.visibility = "visible"
      }
    })
  }
}

function setCookie(name,value,days) {
    let expires = "";
    if (days) {
      let date = new Date();
      date.setTime(date.getTime() + (days*24*60*60*1000));
      expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
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