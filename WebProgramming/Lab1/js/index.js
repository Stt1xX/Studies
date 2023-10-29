import { Y_MAX, Y_MIN, yIsValid, R_MAX, R_MIN, rIsValid} from "./validator.js";

let xCoords = [-4, -3, -2, -1, 0, 1, 2, 3, 4, 5];
let chosenIndex = 4;
let xCoordsButton = document.getElementById("CoordinateX");
function changeValue(){
    chosenIndex = (chosenIndex + 1) % xCoords.length;
    xCoordsButton.innerHTML = xCoords[chosenIndex];
    let input = document.getElementById("CoordinateXInput");
    input.value = xCoords[chosenIndex];
}

xCoordsButton.addEventListener('click', changeValue);


var Ybox = document.getElementById("CoordinateY");
var Rbox = document.getElementById("Radius");
Ybox.addEventListener('input', function(event)  
    {
        if(yIsValid(Ybox.value))Ybox.setCustomValidity("");
        else Ybox.setCustomValidity(`Y coordinated must be a float number in range of: ${Y_MIN}..${Y_MAX}`);
        Ybox.reportValidity();
    });
Rbox.addEventListener('input', function(event)  
    {   
        if(rIsValid(Rbox.value))Rbox.setCustomValidity("");
        else Rbox.setCustomValidity(`Radius must be a float number in range of: ${R_MIN}..${R_MAX}`);
        Rbox.reportValidity();
    }); 