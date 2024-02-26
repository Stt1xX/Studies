<script setup>
  import{ref, onMounted} from "vue";
  import{canvas} from "../components/graph.js";

  const testResponse = ref('')

  onMounted(() => {
    fetch('http://localhost:8080/hello')
        .then(res => res.text())
        .then(res => testResponse.value = res)
  })
</script>

<template>

    <div id="task" class="scallop">
      <p>
        Set the parameters for the graph and the coordinates of the point:
      </p>
    </div>
  <div class="wrapper">
    <div id="form" class="itemWrapper scallop">
      <form>
          <div id="innerForm">
                  <label for="coordinateX" class="itemLabel">Coordinate X: </label>
                  <input id="coordinateX" type="text" placeholder="Enter the number from -2 to 2" /><br>
                  <label for="coordinateY" class="itemLabel">Coordinate Y: </label>
                  <input id="coordinateY" type="text" placeholder="Enter the number from -5 to 3"/><br>
                  <label for="radius" class="itemLabel">Radius: </label>
                  <input  id="radius" type="text" placeholder="Enter the number from -2 to 2"/>
          </div>
      </form>
    </div>
    <div id="graph" class="itemWrapper scallop">
        <canvas id="canvas" width="560px" height="560px">The browser does not support canvas</canvas>
    </div>
  </div>
</template>

<style scoped>
  #form{
    width: 700px;
    height: var(--graphHeight);
  }

  #innerForm{
    padding: 30px;
    text-align: center;
  }

  .wrapper{
    display: flex;
    justify-content: space-around;
    --graphHeight: 600px;
    font-family: "FutureStyle",serif;
    color: var(--fontColor);
    font-size: 35px;
  }

  #task {
    text-align: center;
    margin-top: 100px;
    width: 600px;
    height: 100px;
    font-size:  30px;
    font-family: "FutureStyle",serif;
    color: var(--fontColor);
  }

  #button {
  }

  #task p{
    margin: 0;
    position: relative;
    top: 50%;
    transform: translateY(-50%)
  }

  #task{
    margin: 100px auto;
  }

  .itemWrapper{
    margin-right: 30px;
  }

  .itemLabel{
    margin-bottom: 50px;
  }

  #graph{
    width: var(--graphHeight);
    height: var(--graphHeight);
  }

  td {
    padding-bottom: 2em;
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