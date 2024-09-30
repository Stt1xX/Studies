const X_VALUES = [-4, -3, -2, -1, 0, 1, 2, 3, 4];
const R_VALUES = [1, 2, 3, 4, 5];
export const Y_MIN = -3;
export const Y_MAX = 3;
export const X_MIN = -4;
export const X_MAX = 4;

export function xIsValid(input){
    if (input === "") return false;
    let val = Number(input);
    return !isNaN(val) && Number.isInteger(val) && X_VALUES.includes(val);
}

export function yIsValid(input){
    if (input === "") return false;
    let val = Number(input);
    return !isNaN(val) && val >= Y_MIN && val <= Y_MAX;
}

export function rIsValid(input){
    if (input === "") return false;
    let val = Number(input);
    return !isNaN(val) && R_VALUES.includes(val);
}
