import {radius} from "./main.js";


export const canvas = document.getElementById("graph");
const table = document.getElementById("attemptTable");
const ctx = canvas.getContext("2d");
//Variables
let size = 6; // Radius of graph (size of axis / 2)
let line_color = "#F792E320";
let axis_color = "#F792E3";
let figure_color = "#110E2890";
let miss_point_color = "#eb7d34";
let hit_point_color = "#53eb34";
export var arr_of_points = [];

//Utils
//It is assumed that we are building a "square" graph
let canvas_size = canvas.height; // Can be canvas width
let count_of_cell = size * 2; // Diameter of graph (size of axis)
let num_lines = count_of_cell + 1; // obviously number of lines
export let axis_number = count_of_cell / 2; // number of axis x and y among other lines
export let cell_size = canvas_size / count_of_cell // size of one cell
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
    ctx.moveTo(0, -cell_size * (num_lines - axis_number - 1));
    ctx.lineTo(5, -cell_size * (num_lines - axis_number - 1) + 9);
    ctx.moveTo(0, -cell_size * (num_lines - axis_number - 1));
    ctx.lineTo(-5, -cell_size * (num_lines - axis_number - 1) + 9);
    ctx.stroke();

    ctx.fillStyle= axis_color;
    ctx.fill();
    ctx.font = '13px Arial';
    ctx.textAling = 'start';
    ctx.fillText("y", -14.5, -cell_size * (num_lines - axis_number - 1) + 9.5);

    ctx.beginPath();
    ctx.lineWidth = 2;
    ctx.strokeStyle = axis_color;
    ctx.moveTo(cell_size * (num_lines - axis_number - 1), 0);
    ctx.lineTo(cell_size * (num_lines - axis_number - 1) - 9, 5);
    ctx.moveTo(cell_size * (num_lines - axis_number - 1), 0);
    ctx.lineTo(cell_size * (num_lines - axis_number - 1) - 9, -5);
    ctx.stroke();

    ctx.fillStyle= axis_color;
    ctx.fill();
    ctx.font = '13px Arial';
    ctx.textAling = 'start';
    ctx.fillText("x", cell_size * (num_lines - axis_number - 1) - 9.5, 14.5);
}

function make_ticks(){
    for(let i = 1; i < (num_lines - axis_number - 1); i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = axis_color;

        //Draw a tick mark 6px long (-3 to 3)
        ctx.moveTo(cell_size*i + 0.5, -3);
        ctx.lineTo(cell_size*i + 0.5, 3);
        ctx.stroke();

        ctx.font = '11px Arial';
        ctx.textAling = 'start';
        ctx.fillText(i, cell_size*i - 3, +15);
    }

    // Ticks marks along the negative X

    for(let i = -axis_number + 1; i < 0; i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = axis_color;

        //Draw a tick mark 6px long (-3 to 3)
        ctx.moveTo(cell_size*i + 0.5, -3);
        ctx.lineTo(cell_size*i + 0.5, 3);
        ctx.stroke();

        ctx.font = '11px Arial';
        ctx.textAling = 'end';
        ctx.fillText(i, cell_size*i - 5, 15);
    }

    for(let i = -axis_number + 1; i < 0; i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = axis_color;

        ctx.moveTo(-3, cell_size*i + 0.5);
        ctx.lineTo(+3, cell_size*i + 0.5);
        ctx.stroke();

        ctx.font = '11px Arial';
        ctx.textAlign = 'start';
        ctx.fillText(-i, -12, cell_size*i + 5);
    }

    for(let i = 1; i < (num_lines - axis_number - 1); i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = axis_color;

        ctx.moveTo(-3, cell_size*i + 0.5);
        ctx.lineTo(+3, cell_size*i + 0.5);
        ctx.stroke();

        ctx.font = '11px Arial';
        ctx.textAlign = 'start';
        ctx.fillText(-i, + 8, cell_size*i + 3);
    }
}

function draw_figures(){
    let _local_radius = radius.value;
    ctx.fillStyle = figure_color;
    ctx.fillRect(-(cell_size * _local_radius), 1, cell_size * _local_radius - 1, cell_size * _local_radius - 1);

    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(cell_size * _local_radius, 0);
    ctx.lineTo(0, cell_size * _local_radius);
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(-_local_radius * cell_size, 0);
    ctx.arc(0, 0, _local_radius * cell_size, Math.PI, -(Math.PI / 2));
    ctx.lineTo(0, _local_radius * cell_size / 2);
    ctx.fill();
}

function clear(){
    ctx.clearRect(0, 0, canvas_size, canvas_size);
}

export function get_points(){
    for (let i = 1, row; row = table.rows[i]; i++) {
        if (row.cells[1].innerText === "" || row.cells[2].innerText === ""){
            continue;
        }
        let color;
        if (row.cells[0].innerText === "Yes"){
            color = 1;
        } else{
            color = 0;
        }
        let localX = Number(row.cells[1].innerText);
        let localY = Number(row.cells[2].innerText);
        arr_of_points.push({x: localX, y: localY, color: color});
    }
}

function draw_point(x, y, color){
    ctx.beginPath();
    // ctx.fillStyle = axis_color;
    if (color === 1){
        ctx.fillStyle = hit_point_color;
    } else{
        ctx.fillStyle = miss_point_color;
    }
    ctx.arc(x * cell_size, -y * cell_size, 3, 0, 2 * Math.PI);
    ctx.fill();
}

function draw_points(arr){
    for (let point of arr){
        draw_point(point.x, point.y, point.color);
    }
}
export function makeEmptyGraph(){
    clear();
    draw_grid();
    offset_the_center();
    make_heads();
    make_ticks();
    draw_points(arr_of_points);
    anti_offset_the_center();
}

export function makeGraph(){
    clear();
    draw_grid();
    offset_the_center();
    make_heads();
    make_ticks();
    draw_figures();
    draw_points(arr_of_points);
    anti_offset_the_center();
}