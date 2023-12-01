export const Y_MAX = 3;
export const Y_MIN = -3;
export const R_MAX = 4;
export const R_MIN = 1;
export const X_VALUES = [-5, -4, -3, -2, -1, 0, 1, 2, 3];

export function yIsValid(input){
    if(input == "") return false;
    let val = Number(input);
    if (val !== NaN && val >= Y_MIN && val <= Y_MAX) return true;
    return false;
}

export function rIsValid(input){
    let val = Number(input);
    if (val !== NaN && val >= R_MIN && val <= R_MAX) return true;
    return false;
}

export function yIsValidnoNan(input){
    let val = Number(input);
    if (val >= Y_MIN && val <= Y_MAX) return true;
    return false;
}

export function rIsValidnoNan(input){
    if(input == "") return true;
    let val = Number(input);
    if (val >= R_MIN && val <= R_MAX) return true;
    return false;
}

export function xIsValidOnGraph(input){
    let val = Number(input);
    return X_VALUES.includes(val);
}

export function xIsValid(){
    var inputs = document.getElementsByClassName("custom-radio");
    for (var input of inputs){
        if (input.checked){
            return true;
        }
    }
    return false;
}