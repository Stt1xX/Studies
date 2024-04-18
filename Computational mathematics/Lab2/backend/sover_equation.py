from math import sin, cos
from backend.utils import availability_status
import numpy as np

def solve_equation(intervalA, intervalB, method, equation, accuracy, approach):
    try:
        intervalA = float(intervalA.replace(',', '.'))
        intervalB = float(intervalB.replace(',', '.'))
        method = float(method.replace(',', '.'))
        equation = float(equation.replace(',', '.'))
        accuracy = float(accuracy.replace(',', '.'))
        if approach != "": 
            approach = float(approach.replace(',', '.'))
    except ValueError:
        return { "status" : 0, "error" : "Некорректный формат данных"}
    check_status = check_availability_equation(intervalA, intervalB, equation)
    if check_status == availability_status.MORE_THEN_ONE_ROOTS:
        return {"status" : 0, "error" : "Похоже, на указанном промежутке более одного корня"}
    elif check_status == availability_status.UNKNOWN_EQUATION:
        return {"status" : 0, "error" : "Похоже, я не знаю как решать такое уравнение"}
    elif check_status == availability_status.ZERO_ROOTS:
        return {"status" : 0, "error" : "Похоже, на заданном промежутке нет корней"}
    else:
        if method == 1:
            return half_division(intervalA, intervalB, equation, accuracy)
        elif method == 2:
            return secant(intervalA, intervalB, approach, equation, accuracy)
        elif method == 3:
            return simple_iterations_equation(intervalA, intervalB, approach, equation, accuracy)
        else:
            return {"status" : 0, "error" : "Не понимаю, каким методом решать"}

def secant(left, right, approach, equation, accuracy):
    counter = 0
    search_value = define_method(equation)
    search_value_second_derivative = define_method_for_second_derivative(equation)
    preprevious = None
    previous = None
    if search_value(left) * search_value_second_derivative(left) > 0:
        preprevious = left
    else:
        preprevious = right

    if approach != "" and abs(preprevious - approach) > accuracy:
        previous = approach
    elif preprevious == left:
        previous = preprevious + 0.2
    else:
        previous = preprevious - 0.2
            
    while abs(preprevious - previous) > accuracy:
        x = previous - (previous - preprevious) * search_value(previous) / (search_value(previous) - search_value(preprevious))
        counter += 1
        preprevious = previous
        previous = x

    return {"status" : 1, "root" : previous, "value" : search_value(preprevious), "number_of_iterations" : counter}

def half_division(left, right, equation, accuracy):
    counter = 0
    search_value = define_method(equation)
    while right - left > accuracy:
        x = (right + left) / 2
        if search_value(x) * search_value(left) > 0:
            left = x
        else:
            right = x
        counter += 1
    return {"status" : 1, "root" : x, "value" : search_value(x), "number_of_iterations" : counter}
    
def simple_iterations_equation(left, right, approach, equation, accuracy):
    search_value = define_method(equation)
    search_first_derivative = define_method_for_first_derivative(equation)
    counter = 1
    x = None
    previous = None
    if approach != "":
        previous = approach
        if search_first_derivative(previous) > 0:
            gamma = -1 / search_max_derivative(left, right, equation).get('max_value')
        else:
            gamma = 1 / search_max_derivative(left, right, equation).get('max_value')
    else:
        if abs(search_first_derivative(left)) >= abs(search_first_derivative(right)):
            previous = left
        else:
            previous = right
        if search_first_derivative(previous) > 0:
            gamma = -1 / search_max_derivative(left, right, equation).get('max_value')
        else:
            gamma = 1 / search_max_derivative(left, right, equation).get('max_value')
    x = gamma * search_value(previous) + previous
    if abs(gamma * search_first_derivative(left) + 1) > 1 or  abs(gamma * search_first_derivative(right) + 1) > 1:
        return {"status" : 0, "error" : "К сожалению этот метод не сходится, попробуйте уменьшить промежуток поиска корня"}
    while abs(x - previous) > accuracy:
        previous = x
        x = gamma * search_value(previous) + previous
        counter += 1
    return {"status" : 1, "root" : x, "value" : search_value(x), "number_of_iterations" : counter}


def first_equation(x):
    return 2.74 * pow(x, 3) - 1.93 * pow(x, 2) - 15.28 * x - 3.72

def second_equation(x):
    return -1.38 * pow(x, 3) - 5.42 * pow(x, 2) + 2.57 * x + 10.95 

def third_equation(x):
    return pow(x, 3) + 2.84 * pow(x, 2) - 5.606 * x - 14.766

def fourth_equation(x):
    return sin(x + 1) - 0.2

def first_equation_first_derivative(x):
    return 8.22 * pow(x, 2) - 3.86 * x - 15.28

def second_equation_first_derivative(x):
    return -4.14 * pow(x, 2) - 10.84 * x + 2.57

def third_equation_first_derivative(x):
    return 3 * pow(x, 2) + 5.68 * x - 5.606

def fourth_equation_first_derivative(x):
    return cos(x + 1)

def first_equation_second_derivative(x):
    return 16.44 * x - 3.86

def second_equation_second_derivative(x):
    return -8.28 * x  - 10.84

def third_equation_second_derivative(x):
    return 6 * x + 5.68

def fourth_equation_second_derivative(x):
    return -sin(x + 1)

def search_max_derivative(left, right, equation):
    search_value = define_method_for_first_derivative(equation)
    N = 1000
    maximum = -1
    maximumX = None    
    for x in np.linspace(left, right, N):
        if abs(search_value(x)) > maximum:
            maximum = abs(search_value(x))
            maximumX = x
    return {"max_x" : maximumX, "max_value" : maximum}

def check_availability_equation(left, right, equation):
    N = 10000
    step = (right - left) / N
    counter = 0
    previous = left
    method = define_method(equation)
    if method == None:
        return availability_status.UNKNOWN_EQUATION
    
    for x in np.linspace(left + step, right, N):
        if method(x) * method(previous) <= 0:
            counter += 1
        previous = x

    if counter == 1:
        return availability_status.ONE_ROOT
    elif counter == 0:
        return availability_status.ZERO_ROOTS
    else:
        return availability_status.MORE_THEN_ONE_ROOTS

def define_method(equation):
    if equation == 1:
        return first_equation
    elif equation == 2:
        return second_equation
    elif equation == 3:
        return third_equation
    elif equation == 4:
        return fourth_equation
    else:
        return None
    
def define_method_for_second_derivative(equation):
    if equation == 1:
        return first_equation_second_derivative
    elif equation == 2:
        return second_equation_second_derivative
    elif equation == 3:
        return third_equation_second_derivative
    elif equation == 4:
        return fourth_equation_second_derivative
    else:
        return None
    
def define_method_for_first_derivative(equation):
    if equation == 1:
        return first_equation_first_derivative
    elif equation == 2:
        return second_equation_first_derivative
    elif equation == 3:
        return third_equation_first_derivative
    elif equation == 4:
        return fourth_equation_first_derivative
    else:
        return None
        