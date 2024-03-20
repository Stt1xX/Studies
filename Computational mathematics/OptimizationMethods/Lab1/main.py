import math

# Уравнение 1 / x + e^x промежуток - [0.5 - 1.5]
a = 0.5
b = 1.5
eps = 10**-10

def function_value(x):
    return 1 / x + math.e**x

def first_derivative_value(x):
    return -1 / x**2 + math.e**x

def second_derivative_value(x):
    return 1 / x**3 + math.e**x

def print_result(x, y):
    print(f"Вычисленные значения:\nx={x}\nf(x)={y}")

def dividing_segment_in_half(rounding):
    print("Метод половинного деления:")
    current_a = a
    current_b = b
    counter = 0
    while current_b - current_a > 2 * eps and counter < 25:
        counter += 1
        x1 = (current_a + current_b - eps) / 2
        x2 = (current_a + current_b + eps) / 2
        y1 = function_value(x1)
        y2 = function_value(x2)
        if y1 > y2:
            current_a = x1
        else:
            current_b = x2
            curr_val = (current_a + current_b) / 2
        print(f'{counter}: x={round(curr_val, rounding)}\tf(x)={round(function_value(curr_val), rounding)}')
    ret_val = (current_a + current_b) / 2
    print_result(ret_val, function_value(ret_val))

def golden_ratio(rounding):
    print("Метод золотого сечения:")
    golden = (1 + 5 ** 0.5) / 2
    current_a = a
    current_b = b
    y1 = function_value(a + (b - a) * (1 - 1 / golden))
    y2 = function_value(a + (b - a) * (1 / golden))
    counter = 0
    while  current_b - current_a > 2 * eps and counter < 25:
        counter += 1
        if y1 < y2:
            current_b = current_a + (current_b - current_a) * (1 / golden)
            y2 = y1
            y1 = function_value(current_a + (current_b - current_a) * (1 - 1 / golden))
        else:
            current_a = current_a + (current_b - current_a) * (1 - 1 / golden)
            y1 = y2
            y2 = function_value(current_a + (current_b - current_a) * (1 / golden))
        curr_val = (current_a + current_b) / 2
        print(f'{counter}: x={round(curr_val, rounding)}\tf(x)={round(function_value(curr_val), rounding)}')
    ret_val = (current_a + current_b) / 2
    print_result(ret_val, function_value(ret_val))

def newton_method(rounding):
    print("Метод Ньютона:")
    current_approximation = (a + b) / 2
    counter = 0
    while abs(first_derivative_value(current_approximation)) > eps and counter < 25:
        counter += 1
        current_approximation = current_approximation - first_derivative_value(current_approximation) / second_derivative_value(current_approximation)
        print(f'{counter}: x={round(current_approximation, rounding)}\tf(x)={round(function_value(current_approximation), rounding)}')
    print_result(current_approximation, function_value(current_approximation))

dividing_segment_in_half(5)
golden_ratio(5)
newton_method(5)