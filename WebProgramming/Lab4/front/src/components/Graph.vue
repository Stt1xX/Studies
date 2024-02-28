<template>
  <canvas id="canvas" width="550px" height="550px"
          v-on:click="sentCoordsToMain
          (Math.round(($event.clientX - canvas.getBoundingClientRect().left - axis_number * cell_size) / cell_size * 10**5) / 10**5,
          -Math.round(($event.clientY - canvas.getBoundingClientRect().top - axis_number * cell_size) / cell_size * 10**5) / 10**5);">

  </canvas>
</template>

<script setup>
  import {onMounted} from "vue";

  let radius;

  function setRadius(newRadius){
    radius = newRadius
    fill_graph()
  }

  defineExpose({
    setRadius
  })

  defineProps({
    sentCoordsToMain:{
      type: Function,
      required: true
    }
  })

  //Utils
  let canvas;
  //It is assumed that we are building a "square" graph
  let size; // Radius of graph (size of axis / 2)
  let count_of_cell; // Diameter of graph (size of axis)
  let num_lines; // obviously number of lines
  let axis_number; // number of axis x and y among other lines
  let cell_size; // size of one cell
  //Colors
  const line_color = "#F792E320";
  const axis_color = "#F792E3";
  const figure_color = "#110E2890";
  const miss_point_color = "#eb7d34";
  const hit_point_color = "#53eb34";
  let ctx;
  let canvas_size;

  onMounted(() => {
    canvas = document.getElementById("canvas");
    canvas_size = canvas.height; // Can be canvas width
    size = 5; // !!!! Radius of graph (size of axis / 2) !!!!!!! - You can change
    count_of_cell = size * 2; // Diameter of graph (size of axis)
    num_lines = count_of_cell + 1; // obviously number of lines
    axis_number = count_of_cell / 2; // number of axis x and y among other lines
    cell_size = canvas_size / count_of_cell // size of one cell
    ctx = canvas.getContext("2d");
    create_empty_graph()
  })

  //functions
  function clear(){
    ctx.clearRect(0, 0, canvas_size, canvas_size);
  }
  function draw_grid(){
    for (let i = 0; i < num_lines; i++){
      ctx.beginPath();
      if(i === axis_number){
        ctx.lineWidth = 2;
        ctx.strokeStyle = axis_color;
      } else{
        ctx.lineWidth = 1;
        ctx.strokeStyle = line_color;
      }
      if (i === num_lines - 1 || i === axis_number){
        ctx.moveTo(cell_size * i, 0);
        ctx.lineTo(cell_size * i, canvas_size);
      } else{
        ctx.moveTo(cell_size * i + 0.5, 0);
        ctx.lineTo(cell_size * i + 0.5, canvas_size);
      }
      ctx.stroke();
    }
    for (let i = 0; i < num_lines; i++){
      ctx.beginPath();
      if(i === axis_number){
        ctx.lineWidth = 2;
        ctx.strokeStyle = axis_color;
      } else{
        ctx.lineWidth = 1;
        ctx.strokeStyle = line_color;
      }
      if (i === num_lines - 1 || i === axis_number){
        ctx.moveTo(0, cell_size * i);
        ctx.lineTo(canvas_size, cell_size * i);
      } else{
        ctx.moveTo(0, cell_size * i + 0.5);
        ctx.lineTo(canvas_size, cell_size * i + 0.5);
      }
      ctx.stroke();
    }
  }

  function offset_the_center(){
    ctx.translate(axis_number * cell_size, axis_number * cell_size);
  }

  function anti_offset_the_center(){
    ctx.translate(-axis_number * cell_size, -axis_number * cell_size);
  }

  function make_heads(){
    ctx.beginPath();
    ctx.lineWidth = 2;
    ctx.strokeStyle = axis_color;
    ctx.font = '13px Arial';
    ctx.textAling = 'start';

    ctx.moveTo(0, -cell_size * (num_lines - axis_number - 1));
    ctx.lineTo(5, -cell_size * (num_lines - axis_number - 1) + 9);
    ctx.moveTo(0, -cell_size * (num_lines - axis_number - 1));
    ctx.lineTo(-5, -cell_size * (num_lines - axis_number - 1) + 9);
    ctx.stroke();

    ctx.fill();
    ctx.fillText("y", -14.5, -cell_size * (num_lines - axis_number - 1) + 9.5);


    ctx.moveTo(cell_size * (num_lines - axis_number - 1), 0);
    ctx.lineTo(cell_size * (num_lines - axis_number - 1) - 9, 5);
    ctx.moveTo(cell_size * (num_lines - axis_number - 1), 0);
    ctx.lineTo(cell_size * (num_lines - axis_number - 1) - 9, -5);
    ctx.stroke();

    ctx.fill();
    ctx.fillText("x", cell_size * (num_lines - axis_number - 1) - 9.5, 14.5);
  }

  function make_ticks(){

    ctx.lineWidth = 1;
    ctx.strokeStyle = axis_color;
    ctx.fillStyle= axis_color;
    ctx.font = '11px Arial';
    ctx.textAling = 'start';
    ctx.beginPath();

    for(let i = 1; i < (num_lines - axis_number - 1); i++){

      //Draw a tick mark 6px long (-3 to 3)
      ctx.moveTo(cell_size*i + 0.5, -3);
      ctx.lineTo(cell_size*i + 0.5, 3);
      ctx.stroke();

      ctx.fillText(i.toString(), cell_size*i - 3, 15);
    }

    // Ticks marks along the negative X

    for(let i = -axis_number + 1; i < 0; i++){

      //Draw a tick mark 6px long (-3 to 3)
      ctx.moveTo(cell_size*i + 0.5, -3);
      ctx.lineTo(cell_size*i + 0.5, 3);
      ctx.stroke();

      ctx.fillText(i.toString().toString(), cell_size*i - 5, 15);
    }

    for(let i = -axis_number + 1; i < 0; i++){

      ctx.moveTo(-3, cell_size*i + 0.5);
      ctx.lineTo(+3, cell_size*i + 0.5);
      ctx.stroke();

      ctx.fillText((-i).toString(), -12, cell_size*i + 5);
    }

    for(let i = 1; i < (num_lines - axis_number - 1); i++){

      ctx.moveTo(-3, cell_size*i + 0.5);
      ctx.lineTo(+3, cell_size*i + 0.5);
      ctx.stroke();

      ctx.fillText((-i).toString(), + 8, cell_size*i + 3);
    }
  }

  function draw_figures(){
    ctx.fillStyle = figure_color;

    //second quarter of graph
    ctx.fillRect(-(cell_size * radius) / 2, -(cell_size * radius) + 1, (cell_size * radius - 1) / 2, cell_size * radius - 1);

    //third quarter of graph
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(-cell_size * radius, 0);
    ctx.lineTo(0, cell_size * radius / 2);
    ctx.fill();

    //fourth quarter of graph
    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(-radius * cell_size, 0);
    ctx.arc(0, 0, radius * cell_size, 0, Math.PI / 2);
    ctx.lineTo(0, radius * cell_size / 2);
    ctx.fill();
  }

  function create_empty_graph(){
    clear();
    draw_grid();
    offset_the_center();
    make_ticks();
    make_heads();
    anti_offset_the_center();
  }
  function fill_graph(){
    clear();
    draw_grid();
    offset_the_center();
    make_ticks();
    make_heads();
    draw_figures();
    anti_offset_the_center();
  }

</script>

<style scoped>
  canvas{
    cursor: pointer;
  }
</style>