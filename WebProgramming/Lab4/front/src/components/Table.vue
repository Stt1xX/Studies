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
      </tbody>
    </table>
  </div>
</template>

<script setup>

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