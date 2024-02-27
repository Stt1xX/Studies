<template>
    <div class="content">
    <Header />
    <RouterView />
    <Footer />
    </div>
</template>

<script setup>
  import Footer from './components/Footer.vue';
  import Header from './components/Header.vue';

</script>


<style scoped>

</style>
