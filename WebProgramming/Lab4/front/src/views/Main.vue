<template>
  <div class="mainWrapper">
    <div id="task">
      <Task />
    </div>
    <div id="form" class="scallop">
      <Form ref="form" :sentRadiusToMain="sentRadiusFromMain" />
    </div>
    <div id="graph" class="scallop">
      <Graph ref="graph"  :sentCoordsToMain="sentCoordsFromMain"/>
    </div>
    <div id="buttonOut">
      <Out />
    </div>
    <div id="tableWrap">
      <Table />
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import Graph from "@/components/Graph.vue";
import Out from "@/components/ButtonOut.vue"
import Table from "@/components/Table.vue"
import Task from "@/components/Task.vue"
import Form from "@/components/Form.vue";



// let x = ref('')
// let y = ref('')
// let radius = ref('');

let graph = ref(null)
let form = ref(null)

function sentRadiusFromMain(newRadius){
  // radius = newRadius
  graph.value.setRadius(newRadius)
}

function sentCoordsFromMain(newX, newY){
  form.value.setCoords(newX, newY)
  form.value.click()
}

</script>

<style scoped>
  .mainWrapper{
    flex-wrap: wrap;
    display: flex;
    justify-content: space-around;
    --graphHeight: 650px;
    font-family: "FutureStyle",serif;
    color: var(--fontColor);
    font-size: 35px;
    padding-right: 30px;
  }
  #graph{
    width: var(--graphHeight);
    height: var(--graphHeight);
  }
  #form{
    width: 700px;
    height: var(--graphHeight);
    margin-bottom: 50px; /* for small window Отступ перед графиком */
  }
  #buttonOut, #tableWrap, #graph, #task{
    display: flex;
    justify-content: center;
    align-items: center;
  }
  #task{
    margin: 100px 0;
    width: 100%;
  }
  #buttonOut, #tableWrap{
    margin: 70px 0;
    width: 100%;
  }
</style>