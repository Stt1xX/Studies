export const canvas = document.getElementById("graph");
export const ctx = canvas.getContext("2d");

//VARIABLES
var count_of_cell = 12;
var indent = 0;
var canvas_height = canvas.height - indent;
var canvas_width = canvas.width - indent;
var num_lines_x = count_of_cell + 1;
var num_lines_y = count_of_cell + 1;

export var arr_of_points = [];
export var grid_size = (canvas_height - indent) / count_of_cell;
export var x_axis_grid_lines = count_of_cell / 2;
export var y_axis_grid_lines = count_of_cell / 2;
var x_starting_point = 1;
var y_starting_point = 1;

// Draw grid lines along y-axis
function draw_grid(){
    for (var i = 0; i < num_lines_x; i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        if (i == x_axis_grid_lines){
            ctx.lineWidth = 2;
            ctx.strokeStyle = "#F792E3";
        } else{
            ctx.strokeStyle = "#F792E320";
        }
        if (i == num_lines_x - 1 || i == x_axis_grid_lines){
            ctx.moveTo(grid_size*i, 0);
            ctx.lineTo(grid_size*i, canvas_height);
        } else{
            ctx.moveTo(grid_size*i + 0.5, 0);
            ctx.lineTo(grid_size*i + 0.5, canvas_height);
        }
        ctx.stroke();
    }
    
    for (var i = 0; i < num_lines_y; i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        if (i == y_axis_grid_lines){
            ctx.lineWidth = 2;
            ctx.strokeStyle = "#F792E3";
        } else {
            ctx.strokeStyle = "#F792E320";
        }
        if (i == num_lines_y - 1 || i == y_axis_grid_lines){
            ctx.moveTo(indent, grid_size*i + indent);
            ctx.lineTo(canvas_width, grid_size*i + indent);
        } else {
            ctx.moveTo(indent, grid_size*i + 0.5 + indent);
            ctx.lineTo(canvas_width, grid_size*i + 0.5 + indent);
        }
        ctx.stroke();
    }
}

draw_grid();
// Draw grid lines along x-axis


function offset_the_center(){
    ctx.translate(y_axis_grid_lines * grid_size + indent, x_axis_grid_lines * grid_size + indent);
}

function anti_offset_the_center(){
    ctx.translate(-y_axis_grid_lines * grid_size - indent, -x_axis_grid_lines * grid_size - indent);
}

// Make head of Y-axis X-axis
function make_heads(){
    ctx.beginPath();
    ctx.lineWidth = 2;
    ctx.strokeStyle = "#F792E3";
    ctx.moveTo(0, -grid_size * (num_lines_y - y_axis_grid_lines - 1));
    ctx.lineTo(5, -grid_size * (num_lines_y - y_axis_grid_lines - 1) + 9);
    ctx.moveTo(0, -grid_size * (num_lines_y - y_axis_grid_lines - 1));
    ctx.lineTo(-5, -grid_size * (num_lines_y - y_axis_grid_lines - 1) + 9);
    ctx.stroke();

    ctx.fillStyle= '#F792E3';
    ctx.fill();
    ctx.font = '13px Arial';
    ctx.textAling = 'start';
    ctx.fillText("y", -14.5, -grid_size * (num_lines_y - y_axis_grid_lines - 1) + 9.5);
    
    ctx.beginPath();
    ctx.lineWidth = 2;
    ctx.strokeStyle = "#F792E3";
    ctx.moveTo(grid_size * (num_lines_x - x_axis_grid_lines - 1), 0);
    ctx.lineTo(grid_size * (num_lines_x - x_axis_grid_lines - 1) - 9, 5);
    ctx.moveTo(grid_size * (num_lines_x - x_axis_grid_lines - 1), 0);
    ctx.lineTo(grid_size * (num_lines_x - x_axis_grid_lines - 1) - 9, -5);
    ctx.stroke();
    
    ctx.fillStyle= '#F792E3';
    ctx.fill();
    ctx.font = '13px Arial';
    ctx.textAling = 'start';
    ctx.fillText("x", grid_size * (num_lines_x - x_axis_grid_lines - 1) - 9.5, 14.5);
}



// Ticks marks along the positive X-axis
function make_ticks(){
    for(var i = 1; i < (num_lines_x - x_axis_grid_lines - 1); i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = "#F792E3";
        
        //Draw a tick mark 6px long (-3 to 3)
        ctx.moveTo(grid_size*i + 0.5, -3);
        ctx.lineTo(grid_size*i + 0.5, 3);
        ctx.stroke();
        
        ctx.font = '11px Arial';
        ctx.textAling = 'start';
        ctx.fillText(x_starting_point*i, grid_size*i - 3, +15);
    }
    
    // Ticks marks along the negative X

    for(var i = -x_axis_grid_lines + 1; i < 0; i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = "#F792E3";
        
        //Draw a tick mark 6px long (-3 to 3)
        ctx.moveTo(grid_size*i + 0.5, -3);
        ctx.lineTo(grid_size*i + 0.5, 3);
        ctx.stroke();
        
        ctx.font = '11px Arial';
        ctx.textAling = 'end';
        ctx.fillText(x_starting_point*i, grid_size*i - 5, 15);
    }
    
    for(var i = -y_axis_grid_lines + 1; i < 0; i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = "#F792E3";
        
        ctx.moveTo(-3, grid_size*i + 0.5);
        ctx.lineTo(+3, grid_size*i + 0.5);
        ctx.stroke();
        
        ctx.font = '11px Arial';
        ctx.textAlign = 'start';
        ctx.fillText(-y_starting_point*i, -12, grid_size*i + 5);
    }
    
    for(var i = 1; i < (num_lines_y - y_axis_grid_lines - 1); i++){
        ctx.beginPath();
        ctx.lineWidth = 1;
        ctx.strokeStyle = "#F792E3";
        
        ctx.moveTo(-3, grid_size*i + 0.5);
        ctx.lineTo(+3, grid_size*i + 0.5);
        ctx.stroke();
        
        ctx.font = '11px Arial';
        ctx.textAlign = 'start';
        ctx.fillText(-y_starting_point*i, + 8, grid_size*i + 3);
    }
}

function draw_figures(){
    var radius = document.getElementById("Radius").value;
    ctx.fillStyle = "#110E2890";
    ctx.fillRect(-(grid_size * radius), 1, grid_size * radius - 1, grid_size * radius / 2);

    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(grid_size * radius, 0);
    ctx.lineTo(0, -(grid_size * radius) / 2);
    ctx.fill();

    ctx.beginPath();
    ctx.moveTo(0, 0);
    ctx.lineTo(radius * grid_size / 2, 0);
    ctx.arc(0, 0, radius * grid_size / 2,0, (Math.PI / 2));
    ctx.lineTo(0, radius * grid_size / 2);
    ctx.fill();
}

function clear(){
    ctx.clearRect(0, 0, canvas.width, canvas.height);
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

function draw_point(x, y){
    ctx.beginPath();
    ctx.fillStyle = "#F792E3";
    ctx.arc(x * grid_size, -y * grid_size, 3, 0, 2 * Math.PI);
    ctx.fill();
}

export function draw_points(arr){
    for (var point of arr){
        draw_point(point.x, point.y);
    }
}