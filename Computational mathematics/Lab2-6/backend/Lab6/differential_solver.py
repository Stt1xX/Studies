import numpy as np
import json
from backend.Lab6.utils import Method, StatusMethod, FindAccYValueRetCode
# from utils import Method, StatusMethod, FindAccYValueRetCode
from math import sin, e, pow, cos, isnan


ONE_STEP_CONSTRAINT = 20
MILN_CONSTRAINT = 30
MAX_POINTS = 200


CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE = "Предупреждение: Остальные точки посчитать не представляется возможным!"
FAILED_TO_MEET_ACCURACY_MESSAGE = "Предупреждение: Я не могу достигнуть нужной точности!"
OK_MESSAGE = "Метод отработал корректно!"

def solve_differential_equation(accuracy, y_value, h_value, left_border, right_border, equation_number):
    # Некая валидация на сервере
    try:
        left_border = float(left_border.replace(',', '.'))
        right_border = float(right_border.replace(',', '.'))
        accuracy = float(accuracy.replace(',', '.'))
        h_value = float(h_value.replace(',', '.'))
        y_value = float(y_value.replace(',', '.'))
        equation_number = int(equation_number)
    except ValueError:
        return { "status" : json.dumps(StatusMethod.ERROR), "error" : "Некорректный формат данных" }
    
    if left_border >= right_border:
        return { "status" : json.dumps(StatusMethod.ERROR), "error" : "Левая граница интервала должна быть меньше правой"}
    
    if (right_border - left_border) / h_value > MAX_POINTS:
        return { "status" : json.dumps(StatusMethod.ERROR), "error" : "Слишком маленький шаг для такого промежутка, пожалуйста, поменять входные данные"}
    if accuracy < 0 or accuracy > 0.1:
        return { "status" : json.dumps(StatusMethod.ERROR), "error" : "Точность не соотвествует заданному условию, пожалуйста, убедитесь, что она больше 0 и меньше 0.1" }
    
    if not equation_number in [1, 2, 3]:
        return { "status" : json.dumps(StatusMethod.ERROR), "error" : "Некорректный номер уравнения, проверьте еще раз"}
    
    if right_border - left_border < h_value:
        return { "status" : json.dumps(StatusMethod.ERROR), "error" : "Шаг не должен быть больше интервала"}
    return {
        "status" : json.dumps(StatusMethod.OK),
        "euler_result" : one_step_method(accuracy, y_value, h_value, left_border, right_border, equation_number, Method.EULER),
        "euler_improved_result" : one_step_method(accuracy, y_value, h_value, left_border, right_border, equation_number, Method.EULER_IMPROVED),
        "miln_result" : many_step_method(accuracy, y_value, h_value, left_border, right_border, equation_number, Method.MILN),
        "true_result" : find_true_values(left_border, right_border, y_value, h_value, equation_number)
    }

# Основонй метод
def one_step_method(accuracy, y_value, h_value, left, right, equation_number, method_number):
    message = OK_MESSAGE
    x_values = [left]
    y_values = [y_value]
    current_h_value = h_value
    find_f_value = define_equation(equation_number)
    counter = 1
    if method_number == Method.EULER:
        method = euler_method
        p_value = 1
    elif method_number == Method.EULER_IMPROVED:
        method = euler_improved_method
        p_value = 2
    else:
        return None
    while x_values[-1] + accuracy * 0.01 < right:
        current_y_value, current_h_value, current_counter, ret_code = find_accurate_y_value(find_f_value, left, right, x_values[-1] + current_h_value, y_value, current_h_value, accuracy, counter, method, p_value)
        if ret_code == FindAccYValueRetCode.TOO_LOW_H_VALUE or ret_code == FindAccYValueRetCode.TOO_MUCH_ITERATIONS:
            message =  FAILED_TO_MEET_ACCURACY_MESSAGE
        if isnan(current_y_value) or current_y_value == float("inf") or current_y_value == float("-inf"):
            message = CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE
            break
        y_values.append(current_y_value)
        x_values.append(x_values[-1] + current_h_value)
        counter = current_counter
    x_values_noda, y_values_noda = find_noda_y(x_values, y_values, h_value)
    return {
        "x_values" : [] if message == CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE else x_values,
        "y_values" : [] if message == CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE else y_values,
        "h_value" : current_h_value,
        "message" : message,
        "x_values_noda" : x_values_noda,
        "y_values_noda" : y_values_noda
    }

def many_step_method(accuracy, y_value, h_value, left, right, equation_number, method_number):
    message = OK_MESSAGE
    find_f_value = define_equation(equation_number)
    if method_number == Method.MILN:
        method = miln_method
    else:
        return None
    current_step = h_value
    initial_values = init_y_values_via_one_step_method(find_f_value, left, y_value, current_step, 4, Method.EULER_IMPROVED)
    method_x_values, method_y_values = method(find_f_value, accuracy, current_step, left, right, initial_values)
    true_y_values = find_true_values(left, right, y_value, current_step, equation_number)["y_values_noda"]
    if len(method_y_values) == len(true_y_values):
        while not(check_accuracy_many_step_methods(method_y_values, true_y_values, accuracy)) and len(method_y_values) < MAX_POINTS:
            current_step /= 2
            initial_values = init_y_values_via_one_step_method(find_f_value, left, y_value, current_step, 4, Method.EULER_IMPROVED)
            method_x_values, method_y_values = method(find_f_value, accuracy, current_step, left, right, initial_values)
            true_y_values = find_true_values(left, right, y_value, current_step, equation_number)["y_values_noda"]
            if len(method_y_values) != len(true_y_values):
                message = CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE
                break
    else:
        message = CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE
    x_values_noda, y_values_noda = find_noda_y(method_x_values, method_y_values, h_value)
    return {
        "x_values" : [] if message == CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE else method_x_values,
        "y_values" : [] if message == CAN_NOT_COMPUTE_OTHER_POINTS_MESSAGE else method_y_values,
        "x_values_noda" : x_values_noda,
        "y_values_noda" : y_values_noda,
        "h_value" : current_step,
        "message" : message
    }

def init_y_values_via_one_step_method(find_f_value, left, y_value, h_value, size_of_init_arrray, type_of_method):
    if type_of_method == Method.EULER:
        method = euler_method
    elif type_of_method == Method.EULER_IMPROVED:
        method = euler_improved_method
    else:
        return None
    ret_arr = [y_value]
    for i in range(size_of_init_arrray - 1):
        ret_arr.append(method(find_f_value, left, left + h_value * (i + 1), y_value, h_value))
    return ret_arr


def check_accuracy_many_step_methods(point_set_1, point_set_2, accuracy):
    for i in range(len(point_set_1)):
        if abs(point_set_1[i] - point_set_2[i]) > accuracy:
            return False
    return True

def euler_method(find_f_value, left, right, y_value, h_value):
    x_values = np.arange(left, right + h_value * 0.01, h_value)
    previous_y_value = y_value
    for x_value in x_values[:-1]:
        previous_y_value = previous_y_value + h_value * find_f_value(x_value, previous_y_value)
    return previous_y_value

def euler_improved_method(find_f_value, left, right, y_value, h_value):
    x_values = np.arange(left, right + h_value * 0.01, h_value)
    previous_y_value = y_value
    for i in range(len(x_values) - 1):
        previous_y_value = previous_y_value + h_value / 2 * (find_f_value(x_values[i], previous_y_value) + find_f_value(x_values[i + 1], previous_y_value + h_value * find_f_value(x_values[i], previous_y_value)))
    return previous_y_value

def find_accurate_y_value(equation, left, right, current_right, y_value, h_value, accuracy, counter, find_y_i, p_value):
    y_first = find_y_i(equation, left, current_right, y_value, h_value)
    if (right - left) / h_value > MAX_POINTS:
        return y_first, h_value, counter, FindAccYValueRetCode.TOO_LOW_H_VALUE
    if counter >= ONE_STEP_CONSTRAINT:
        return y_first, h_value, counter, FindAccYValueRetCode.TOO_MUCH_ITERATIONS
    y_second = find_y_i(equation, left, current_right, y_value, h_value / 2)
    if check_accuracy_one_step_methods(y_first, y_second, p_value, accuracy):
        return find_accurate_y_value(equation, left, right, current_right - h_value / 2, y_value, h_value / 2, accuracy, counter + 1, find_y_i, p_value)
    else:
        return y_second, h_value, counter, FindAccYValueRetCode.OK

def check_accuracy_one_step_methods(y_first, y_second, p_value, accuracy):
    return abs(y_first - y_second) / (2**p_value - 1) > accuracy

def miln_method(find_f_value, accuracy, h_value, left, right, initial_values):
    f_values = []
    x_values = np.arange(left, right + h_value * 0.01, h_value)
    y_values = initial_values
    if len(y_values) < 3:
        return x_values.tolist()[:len(y_values)], y_values
    for i in range(1, 3):
        f_values.append(find_f_value(x_values[i], y_values[i]))
    for i in range(4, len(x_values)):
        f_values.append(find_f_value(x_values[i - 1], y_values[i - 1]))
        current_y_predicted = y_values[i - 4] + 4 * h_value / 3 * (2 * f_values[i - 4] - f_values[i - 3] + 2 * f_values[i - 2])
        current_y_adjustable = y_values[i - 2] + h_value / 3 * (f_values[i - 3] + 4 * f_values[i - 2] + find_f_value(x_values[i], current_y_predicted))
        counter = 0

        while counter < MILN_CONSTRAINT and abs(current_y_adjustable - current_y_predicted) > accuracy:
            current_y_predicted = current_y_adjustable
            current_y_adjustable = y_values[i - 2] + h_value / 3 * (f_values[i - 3] + 4 * f_values[i - 2] + find_f_value(x_values[i], current_y_predicted))
            counter += 1

        if isnan(current_y_adjustable) or current_y_adjustable == float("inf") or current_y_adjustable == float("-inf"):
            return x_values.tolist()[:len(y_values)], y_values
        
        y_values.append(current_y_adjustable)

    return x_values.tolist(), y_values
        

# Поиск свободного коэффициента
def find_c1(x, y):
    return -pow(e, x) / y - x * pow(e, x)

def find_c2(x, y):
    return y - pow(x, 3) / 3 - pow(x, 2) / 2

def find_c3(x, y):
    return (y + x * sin(x) / 2 + x * cos(x) / 2 + cos(x) / 2) / pow(e, x)

def find_true_value(left_x_value, left_y_value, x, equation_number):
    if(equation_number == 1):
        c = find_c1(left_x_value, left_y_value)
        return -pow(e, x) / (x * pow(e, x) + c)
    elif(equation_number == 2):
        c = find_c2(left_x_value, left_y_value)
        return pow(x, 3) / 3 + pow(x, 2) / 2 + c
    elif(equation_number == 3):
        c = find_c3(left_x_value, left_y_value)
        return - x * sin(x) / 2 - x * cos(x) / 2 - cos(x) / 2 + c * pow(e, x) 
    else:
        return None

# Основонй метод
def find_true_values(left, right, y_value, h_value, equation_number):
    x_values = np.arange(left, right + h_value * 0.01, h_value)
    y_values = list()
    x_values_for_graph = np.linspace(left, right, 1000)
    y_values_for_graph = list()
    for x in x_values:
        y_values.append(find_true_value(left, y_value, x, equation_number))
    for x in x_values_for_graph:
        y_values_for_graph.append(find_true_value(left, y_value, x, equation_number))
    return {
        "x_values_noda" : x_values.tolist(),
        "y_values_noda" : y_values,
        "x_values" : x_values_for_graph.tolist(),
        "y_values" : y_values_for_graph,
    }
    

def define_equation(equation_number):
    if (equation_number == 1):
        return first_equation_solve
    elif(equation_number == 2):
        return second_equation_solve
    elif(equation_number == 3):
        return third_equation_solve
    else:
        return None

def first_equation_solve(x, y):
    return y + (1 + x) * y**2

def second_equation_solve(x, y):
    return x**2 + x

def third_equation_solve(x, y):
    return y + x * sin(x)


def find_noda_y(x_values, y_values, h_value):
    left = x_values[0]
    right = x_values[-1]
    x_values_noda = np.arange(left, right + h_value * 0.01, h_value)
    y_values_noda = list()
    for i in range(len(x_values_noda)):
        min_delta = 10000000
        current_j = 0
        for j in range(len(x_values)):
            current_delta =  abs(x_values[j] - x_values_noda[i])
            if current_delta < min_delta:
                min_delta = current_delta
                current_j = j
        y_values_noda.append(y_values[current_j])
    return x_values_noda.tolist(), y_values_noda
