<template>
  <div class="wrapper">
    <div class="content">
    <Header />
    <RouterView />
    <Footer />
    </div>
  </div>
</template>

<script>
  import Footer from './components/Footer.vue';
  import Header from './components/Header.vue';

  export default {
    components: {
      Footer,
      Header
    }
  }
</script>


<style scoped>

</style>
