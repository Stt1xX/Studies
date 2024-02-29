<template>
  <div id="formWrapper">
    <div id="form" class="scallop">

      <div id="innerForm">
        <h1>Authorization</h1>
        <span>
          New?
          <router-link to="/signUp">Sign Un</router-link>
        </span>
        <p id="invalidAuthorizationMessage" class="errorMessage">Invalid username or password! Try again!</p>
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
        <button @click="click() ? $router.push('/') : {}">Sign In</button>
      </div>
    </div>
  </div>
</template>

<script setup>

import {onMounted} from "vue";

let username, password;

let usernameMessage, passwordMessage;

onMounted(() =>{
  usernameMessage = document.getElementById("usernameMessage")
  passwordMessage = document.getElementById("passwordMessage")
})
function checkUsername(){
  if (username === '' || username === undefined) {
    usernameMessage.style.visibility = "visible"
    return false;
  } else{
    usernameMessage.style.visibility = "hidden"
    return true;
  }
}

function checkPassword(){
  if (password === '' || password === undefined) {
    passwordMessage.style.visibility = "visible"
    return false;
  } else{
    passwordMessage.style.visibility = "hidden"
    return true;
  }

}

function click(){
  return checkUsername() & checkPassword();
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

  h1{
    margin-left: -30px;
  }

  span{
    margin-right: -30px;
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

  #invalidAuthorizationMessage{
    margin-bottom: 0;
    margin-top: 0;
    font-size: 17px;
    text-align: center;
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
    margin-top: 15px;
  }
</style>