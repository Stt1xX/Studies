<template>
  <div id="cap">
    <button id="Lab2" class="mapper">Lab2</button>
    <button id="Lab3" class="mapper">Lab3</button>
    <button id="Lab4" class="mapper">Lab4</button>
    <button id="Lab5" class="mapper">Lab5</button>
    <button id="Lab6" class="mapper">Lab6</button>
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
</style>