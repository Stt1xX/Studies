// export const X_ALLOWED_ARR = [-4, -3, -2, -1, 0, 1, 2, 3, 4];
export const Y_MAX = 5;
export const Y_MIN = -5;
export const R_MAX = 4;
export const R_MIN = 1;

// export function xIsValid(input){
//     let val = Number(input);
//     if(val !== NaN && X_ALLOWED_ARR.includes(val)) return val;
//     return false;
// }

export function yIsValid(input){
    let val = Number(input);
    if (val !== NaN && val >= Y_MIN && val <= Y_MAX) return true;
    return false;
}

export function rIsValid(input){
    let val = Number(input);
    if (val !== NaN && val >= R_MIN && val <= R_MAX) return true;
    return false;
}