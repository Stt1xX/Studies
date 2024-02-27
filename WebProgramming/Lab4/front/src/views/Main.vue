<template>
  <div class="mainWrapper">
    <div id="task">
      <Task />
    </div>
    <div id="form" class="scallop">
      <Form :changeRadius="test"/>
    </div>
    <div id="graph" class="scallop">
      <Graph ref="graph" />

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

let graph = ref(null)
function test(newRadius){
  radius =newRadius
  graph.value.changeRadius(newRadius)
}

let radius = ref('');
let x = ref('')
let y = ref('')

</script>

<style scoped>
  .mainWrapper{
    flex-wrap: wrap;
    display: flex;
    justify-content: space-around;
    --graphHeight: 600px;
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
    margin-bottom: 100px; /* for small window Отступ перед графиком */
  }
  #buttonOut, #tableWrap, #graph, #task{
    display: flex;
    justify-content: center;
    align-items: center;
  }
  #task, #tableWrap{
    margin: 100px 0;
    width: 100%;
  }
</style>