<template>
    <Header/>
    <Main/>
    <Footer/>
</template>

<script setup>
  import Header from './components/Header.vue'
  import Main from './components/Main.vue'
  import Footer from './components/Footer.vue'

</script>
<style>
  body {
      margin: 0;
      background: #355C7D;  /* fallback for old browsers */
      background: -webkit-linear-gradient(to right, #C06C84, #6C5B7B, #355C7D);  /* Chrome 10-25, Safari 5.1-6 */
      background: linear-gradient(to right, #C06C84, #6C5B7B, #355C7D); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  }

  * {
    font-family: "Comfortaa", sans-serif;
    font-optical-sizing: auto;
    font-weight: Bold;
    font-style: normal;
    color: bisque;
  }
</style>