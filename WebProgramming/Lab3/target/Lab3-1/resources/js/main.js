import {xIsValid, yIsValid, rIsValid, Y_MAX, Y_MIN, X_MIN, X_MAX} from "./validator.js"
import {canvas, makeEmptyGraph, makeGraph, cell_size, axis_number, arr_of_points, get_points} from "./graph.js"

let coordinateX = document.getElementById("myForm:coordinateX");
let coordinateY = document.getElementById("myForm:coordinateY");
export let radius = document.getElementById("myForm:radius");
let button = document.getElementById("myForm:submitAjax");

function pinkY(){
    coordinateY.classList.remove("InputError");
    coordinateY.placeholder = `Enter the number from ${Y_MIN} to ${Y_MAX}`;
}

function redY(){
    coordinateY.classList.add("InputError");
    coordinateY.placeholder = "Invalid Y Coordinate";
}

function pinkX(){
    coordinateX.classList.remove("InputError");
    coordinateX.placeholder = `Enter the number from ${X_MIN} to ${X_MAX}`;
}

function redX() {
    coordinateX.classList.add("InputError");
    coordinateX.placeholder = "Invalid X Coordinate";
}

function pinkR(){
    radius.classList.remove("SelectError");
}

function redR() {
    radius.classList.add("SelectError");
}

export function checkValidation(){
    let flag = true;
    if (!xIsValid(coordinateX.value)){
        redX();
        flag = false;
    } else{
        pinkX();
    }
    if (!yIsValid(coordinateY.value)){
        redY();
        flag = false;
    } else{
        pinkY();
    }
    if (!rIsValid(radius.value)){
        redR();
        flag = false;
    } else{
        pinkR();
    }
    return flag;
}

coordinateX.addEventListener('input', function(event)
{
    if(xIsValid(coordinateX.value)){
        pinkX();
    }
    else {
        redX();
    }
});

coordinateY.addEventListener('input', function(event)
{
    if(yIsValid(coordinateY.value)){
        pinkY();
    }
    else {
        redY();
    }
});

radius.addEventListener('input', function(event)
{
    if(rIsValid(radius.value)){
        pinkR();
        makeGraph(); // When Radius is specified we display our figures
    }
    else {
        redR();
        makeEmptyGraph(); // When Radius isn't specified we don't display figures obviously
    }
});

//using on attribute onSlideEnd in p:slider component main.xhtml
export function eventListenerForSlider(event, ui){
    if(xIsValid(coordinateX.value)){
        pinkX();
    }
    else {
        redX();
    }
}

canvas.addEventListener('mousedown', function(event)
{
    if(rIsValid(radius.value)){
        pinkR();
        const rect = canvas.getBoundingClientRect();
        coordinateX.value = Math.round((event.clientX - rect.left - cell_size * axis_number) / cell_size);
        coordinateY.value = Math.round(-(event.clientY - rect.top - cell_size * axis_number) / cell_size * 10**11) / 10**11;
        button.click();
    } else{
        redR();
        pinkX();
        pinkY();
    }
});



//block with execution of functions
get_points();
makeEmptyGraph();

export function add_current_attempt(){
    let table = document.getElementById("attemptTable");
    let color;
    console.log(table.rows[1].cells[0].innerText);
    if (table.rows[1].cells[0].innerText === "Yes"){
        color = 1;
    } else{
        color = 0;
    }
    let x_val = coordinateX.value;
    let y_val = coordinateY.value;
    arr_of_points.push({x: x_val, y: y_val, color: color});
}