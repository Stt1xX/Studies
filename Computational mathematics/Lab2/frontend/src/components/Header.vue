<template>
  <div id="cap">
    <router-link to="/Lab2Part1" class="mapper" tag="button">Lab2.1</router-link>
    <router-link to="/Lab2Part2" class="mapper" tag="button">Lab2.2</router-link>
    <router-link to="/Lab3" class="mapper" tag="button">Lab3</router-link>
    <router-link to="/Lab4" class="mapper" tag="button">Lab4</router-link>
    <router-link to="/Lab5" class="mapper" tag="button">Lab5</router-link>
    <router-link to="/Lab6" class="mapper" tag="button">Lab6</router-link>
  </div>
  <div id="pixel-art">
    <form>
        <button type="submit" id="reload-button">
          SttIxX <br>
          Production
        </button>
    </form>
  </div>
</template>

<script setup>
  $(document).ready(() => {
    const onScrollCap = () => {

      const cap = $('#cap')

      let prevScroll = $(window).scrollTop()
      let currentScroll

      $(window).scroll(() => {

        currentScroll = $(window).scrollTop()

        const capHidden = () => cap.hasClass('cap_hidden')

        if (currentScroll > prevScroll && !capHidden()) { // если прокручиваем страницу вниз и header не скрыт
          cap.addClass('cap_hidden') // то скрываем header
        }
        if (currentScroll < prevScroll && capHidden()) { // если прокручиваем страницу вверх и header скрыт
          cap.removeClass('cap_hidden') // то отображаем header
        }
        prevScroll = currentScroll // записываем на сколько прокручена страница на данный момент

      })
    }

    onScrollCap()

  })


</script>

<style scoped>

  @import url('https://fonts.googleapis.com/css2?family=Comfortaa:wght@300..700&display=swap');
  @import url('https://fonts.googleapis.com/css2?family=Pixelify+Sans:wght@400..700&display=swap');

  a {
    text-decoration:none;
    text-align: center;
  }


  #cap {
    top: 0;
    position: sticky;
    width: 100%;
    transition: 0.8s;

    display: flex;
    justify-content: space-around;
    align-items: center;
  }

  .cap_hidden{
    transform: translateY(-100%);
  }

  #pixel-art {
    display: flex;
    justify-content: space-around;
    align-items: center;
    height: 92vh;    
  }
  
  #reload-button {
      text-align: center;
      font-size: 10vw;
      cursor: pointer;
      background: transparent;
      border-style: none;
  }

  .mapper {
    background: bisque;
    border-style: none;
    cursor: pointer;
    height: 100%;
    width: 100%;

    color: #6e5b7b;
    font-size: 3vh;
    padding: 1% 0;
  }

  .mapper:hover {
    background-color: transparent;
    color: bisque;
    transition: 0.8s;
  }

  #reload-button {
    color: bisque;
  }
</style>