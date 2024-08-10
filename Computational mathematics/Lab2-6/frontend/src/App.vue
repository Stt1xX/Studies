<template>
     <div class="content">
      <Header />
      <RouterView />
      <Footer />
     </div>

</template>

<script setup>
  import Header from './components/Header.vue'
  import Footer from './components/Footer.vue'

</script>
<style>

  input[type="number"]::-webkit-inner-spin-button, input[type="number"]::-webkit-outer-spin-button {
    -webkit-appearance: none;
    margin: 0;
  }

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
    color: #6e5b7b
  }

  .main-wrapper {
        margin-left: 3vw;
        margin-right: 3vw;
        font-size: 4vh;
        padding-bottom: 15vh;
        text-align: center;
    }

    .main-wrapper h1 {
        text-align: center;
        color: bisque
    }

    
    .rounded {
        background-color: bisque;
        border-radius: 15px;
    }

    button {
        border: 1.5px solid #6e5b7b;
        background-color: bisque;
        border-radius: 30px;
        cursor: pointer;
        transition: 0.7s;
    }

    button:hover {
        background-color: #6e5b7b;
        color: bisque;
    }

    .custom-select, .custom-input {
        width: 100%;
        margin-bottom: 30px;
        margin-top: 10px;
        display: flex;
        justify-content: space-around;
        align-items: center;
    }

    select, input {
        appearance: none;
        /* safari */
        -webkit-appearance: none;
        /* other styles for aesthetics */
        width: 100%;
        font-size: 2.4vh;
        padding: 0.675em 1em 0.675em 1em;
        background-color: bisque;
        border: 1px solid #6e5b7b;
        border-radius: 0.25rem;
        cursor: pointer;
    }


</style>