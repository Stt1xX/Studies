<script setup>
  import{ref, onMounted} from "vue";

  const testResponse = ref('')

  onMounted(() => {
    fetch('http://localhost:8080/hello')
        .then(res => res.text())
        .then(res => testResponse.value = res)
  })
</script>

<template>
  <h1>{{testResponse}}</h1>
</template>

<style scoped>

</style>