<template>
  <form>
    <div id="innerForm">
      <label for="coordinateX" class="itemLabel">Coordinate X: </label>
      <input id="coordinateX"  type="text" placeholder="Enter the number from -2 to 2" /><br>
      <label for="coordinateY" class="itemLabel">Coordinate Y: </label>
      <input id="coordinateY"  type="text" placeholder="Enter the number from -5 to 3"/><br>
      <label for="radius" class="itemLabel">Radius: </label>
      <input  id="radius"  v-on:input="changeRadius($event.target.value)" type="text" placeholder="Enter the number from -2 to 2"/><br>
      <Try />
    </div>
  </form>
</template>

<script setup>

import Try from "@/components/ButtonTry.vue";

defineProps({
  changeRadius: {
    type: Function,
    required: true
  }
})

</script>

<style scoped>
  p{
    font-size: 10px;
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
    color: var(--fontColor);
    display: inline-block;
    width: 80%;
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
    margin-bottom: 50px;
  }

  /* input style */

</style>