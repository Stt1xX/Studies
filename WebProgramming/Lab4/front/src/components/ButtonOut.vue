<template>
  <button @click="click($router)">
    Bye-Bye
  </button>
</template>

<script setup>
  function click(router){
    setCookie("Login", "", 0);
    setCookie("Password", "", 0);
    router.push('login')
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
  button{
    font-size: 30px;
    border-radius: 40px;
    width: 400px;
    height: 80px;
  }
</style>