import {yIsValid, rIsValid, xIsValid, yIsValidnoNan, rIsValidnoNan, xIsValidOnGraph} from "./validator.js";
import {canvas, makeEmptyGraph, makeGraph, grid_size, x_axis_grid_lines, y_axis_grid_lines, arr_of_points, draw_points} from "./graph.js";


var Y = document.getElementById("CoordinateY");
var R = document.getElementById("Radius");
var X = document.getElementsByClassName("CoordX");
var button = document.getElementById("Submit");
var form = document.getElementById("myForm");

getPoints();



form.onsubmit = function() {
    var flag = true;
    if(yIsValid(Y.value)){
        pinkY();
    }
    else {
        redY();
        flag = false;
    }
    if(rIsValid(R.value)){
        pinkR();
    }
    else {
        redR();
        flag = false;
    }
    if(!xIsValid()){
        redX();
        flag = false;
    }
    if (flag){
        Y.value = Math.round(Y.value * 10**4) / 10**4;
        var x_val = retX() * grid_size;
        var y_val = Y.value * grid_size;
        arr_of_points.push({y: y_val, x: x_val});
    }
    return flag;
};


Y.addEventListener('input', function(event)
    {   
        if(yIsValidnoNan(Y.value)){
            pinkY();
        }
        else {
            redY();
        }
    });
    
R.addEventListener('input', function(event)
    {   
        if(rIsValidnoNan(R.value)){
            pinkR();
            makeGraph();
        }
        else {
            redR();
            makeEmptyGraph();
        }
    });
    
for (var radio of X){
    radio.onclick = function(){
        pinkX();
    }
}

canvas.addEventListener('mousedown', function(event)
    {
        if(rIsValid(R.value)){
            pinkR();
            const rect = canvas.getBoundingClientRect();
            var xcor = Math.round((event.clientX - rect.left - grid_size * x_axis_grid_lines) / grid_size);
            Y.value = Math.round(-(event.clientY - rect.top - grid_size * y_axis_grid_lines) / grid_size * 10**11) / 10**11;            
            if (xIsValidOnGraph(xcor)){
                pinkX();
                checkX(xcor);
            } else{
                uncheckX();
            }
                button.click();
        } else{
            redR();
            pinkX();
            pinkY();
        }
});

function pinkX(){
    for (var element of X) {
        element.setAttribute("name", "CoordX");
    }
}

function redX(){
    for (var element of X) {
        element.setAttribute("name", "RadioError");
    }
}

function pinkR(){
    R.classList.remove("InputError");
    R.placeholder = "Enter the number from 1 to 4";
}

function redR(){
    makeEmptyGraph();
    R.classList.add("InputError");
    R.placeholder = "Invalid Radius";
}

function pinkY(){
    Y.classList.remove("InputError");
    Y.placeholder = "Enter the number from -3 to 3";
}

function redY(){
    Y.classList.add("InputError");
    Y.placeholder = "Invlalid Y Coordinate";
}

function uncheckX(){
    for (var rad of document.getElementsByClassName("custom-radio")){
        rad.checked = false;
    }
}

function checkX(xcor){
    var radio = document.getElementById(xcor);
    radio.checked = true;
}

function getPoints(){
    fetch("./?Points", {
        method: "GET"
    })
    .then( response => {
        if (response.status !== 200) {
            return Promise.reject();
        }
        return response.text()
        })
    .then(res => {
        const strings = res.split('\n');
        if (strings.length > 1) {
            for (let i = 0; i < strings.length - 1; i++) {
                const array = strings[i].split(' ');
                arr_of_points.push({x: array[0], y: array[1]});
            }
        }
    })
    .then(res => makeEmptyGraph())
    .catch(() => console.log('ошибка'));
}