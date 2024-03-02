<template>
  <div class="mainWrapper">
    <div id="task">
      <Task />
    </div>
    <div id="form" class="scallop">
      <Form ref="form" />
    </div>
    <div id="graph" class="scallop">
      <Graph ref="graph" />
    </div>
    <div id="buttonOut">
      <Out />
    </div>
    <div id="tableWrap">
      <Table ref="table"/>
    </div>
  </div>
</template>

<script setup>
import {provide, ref} from 'vue'
import Graph from "@/components/Graph.vue";
import Out from "@/components/ButtonOut.vue"
import Table from "@/components/Table.vue"
import Task from "@/components/Task.vue"
import Form from "@/components/Form.vue";

let graph = ref(null)
let form = ref(null)
let table = ref(null)

provide('setCoords',  (newX, newY) => {
  form.value.setCoords(newX, newY)
  form.value.click()
})
provide('addNewAttempt', (newAttempt) => {
  table.value.addNewAttempt(newAttempt)
})
provide('setRadius', (newRadius) => {
  graph.value.setRadius(newRadius)
})

provide('drawPoint', (x, y, isHit) => {
  graph.value.drawPoint(x, y, isHit)
})

provide('drawPoints', (array) => {
  graph.value.drawPoints(array)
})

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