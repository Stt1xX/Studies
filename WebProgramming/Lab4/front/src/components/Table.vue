<template>
  <div class="scallop">
    <table>
      <tbody>
        <tr>
          <th class="isHit">
            Is Hit
          </th>
          <th class="xCoord">
            X
          </th>
          <th class="yCoord">
            Y
          </th>
          <th class="radius">
            Radius
          </th>
          <th class="time">
            Time
          </th>
        </tr>
        <tr v-for="attempt in attemptsArray">
          <td class="isHit">
            {{attempt.isHit}}
          </td>
          <td class="xCoord">
            {{attempt.x}}
          </td>
          <td class="yCoord">
            {{attempt.y}}
          </td>
          <td class="radius">
            {{attempt.radius}}
          </td>
          <td class="time">
            {{attempt.time}}
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script setup>

import {onMounted, ref} from "vue";

let attemptsArray = ref([]);
function addNewAttempt(newAttempt){
  attemptsArray.value.unshift(newAttempt)
}

defineExpose({
  addNewAttempt
})

onMounted(async function(){
  await fetch('http://localhost:8080/receiveAttempts')
      .then(function(res){
        return res.json()
      })
      .then(function(res){
        attemptsArray.value = res
      })
})

</script>

<style scoped>
  table{
    width: 90%;
    text-align: center;
    border-collapse: collapse;
  }

  div{
    padding: 20px 0;
    display: flex;
    justify-content: center;
    width: 83%;
  }

  td, th{
    border-bottom: 2px solid var(--fontColor);
  }

  .isHit{
    width: 15%;
  }

  .xCoord{
    width: 15%;
  }

  .yCoord{
    width: 15%;
  }

  .radius{
    width: 25%;
  }

  .time{
    width: 30%;
  }
</style>