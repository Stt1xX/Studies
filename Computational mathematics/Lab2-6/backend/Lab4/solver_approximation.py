from math import sqrt, exp, log, pow
import numpy as np 

def find_approximation(x_values, y_values):
    local_x_values = []
    local_y_values = []
    for i in range(len(x_values)):
        if x_values[i] != "" and y_values[i] != "":
            local_x_values.append(float(x_values[i].replace(',', '.')))
            local_y_values.append(float(y_values[i].replace(',', '.')))
    return { "status" : 1, "x_values" : [round(x, 3) for x in local_x_values], "y_values" : [round(y, 3) for y in local_y_values], 
            "linear_result" : find_linear_function(local_x_values, local_y_values), 
            "quadratic_result" : find_quadratic_function(local_x_values, local_y_values),
            "cubic_result" : find_cubic_function(local_x_values, local_y_values),
            "degree_result" : find_degree_function(local_x_values, local_y_values),
            "exp_result" : find_exp_function(local_x_values, local_y_values),
            "log_result" : find_log_function(local_x_values, local_y_values)
            }

def find_polinomial_function(x_values, y_values, n):
    current_degree = n
    left_matrix = []
    right_matrix = []
    for i in range(n + 1):
        current_degree -= n
        current_column = []
        for j in range(n + 1):
            current_sum = 0
            for x in x_values:
                current_sum += x**current_degree
            current_degree += 1
            current_column.append(current_sum)
        left_matrix.append(current_column)
    current_degree = 0
    for i in range(n + 1):
        current_sum = 0
        for i in range(len(y_values)):
            current_sum += x_values[i]**current_degree * y_values[i]
        current_degree += 1
        right_matrix.append(current_sum)
    main_det = np.linalg.det(np.array(left_matrix))
    ratio_arr = []
    for i in range(n + 1):
        curr_det = left_matrix.copy()
        curr_det[i] = right_matrix
        curr_high_det = np.linalg.det(np.array(curr_det))
        ratio_arr.append(round(curr_high_det / main_det, 3))
    ret_arr = []
    for x in x_values:
        current_sum = 0
        current_degree = -1
        for i in range(n + 1):
            current_degree += 1
            current_sum += x**current_degree * ratio_arr[i]
        ret_arr.append(round(current_sum, 3))
    return [ret_arr, ratio_arr]

def find_linear_function(x_values, y_values):
    result_arr = find_polinomial_function(x_values, y_values, 1)
    linear_values = result_arr[0]
    reliability = round(find_reliability(y_values, linear_values), 3)
    return {"a" : result_arr[1][1], "b" : result_arr[1][0], "values" : linear_values, "differences" : find_differences(y_values, linear_values), 
            "deviation" : find_diviation(y_values, linear_values), "pirson" : round(find_pirson(x_values, y_values), 3), 
            "reliability" : reliability, "status" : define_status(reliability) }

def find_quadratic_function(x_values, y_values):
    result_arr = find_polinomial_function(x_values, y_values, 2)
    quadratic_values = result_arr[0]
    reliability = round(find_reliability(y_values, quadratic_values), 3)
    return {"a" : result_arr[1][2], "b" : result_arr[1][1], "c" : result_arr[1][0], "values" : quadratic_values, "differences" : find_differences(y_values, quadratic_values), 
            "deviation" : find_diviation(y_values, quadratic_values), 
            "reliability" : reliability, "status" : define_status(reliability) }

def find_cubic_function(x_values, y_values):
    result_arr = find_polinomial_function(x_values, y_values, 3)
    cubic_values = result_arr[0]
    reliability = round(find_reliability(y_values, cubic_values), 3)
    return {"a" : result_arr[1][3], "b" : result_arr[1][2], "c" : result_arr[1][1], "d" : result_arr[1][0], "values" : cubic_values, "differences" : find_differences(y_values, cubic_values), 
            "deviation" : find_diviation(y_values, cubic_values), 
            "reliability" : reliability, "status" : define_status(reliability) }

def find_degree_function(x_values, y_values):
    try:
        ln_x_values = [log(x) for x in x_values]
        ln_y_values = [log(y) for y in y_values]
        result_arr = find_polinomial_function(ln_x_values, ln_y_values, 1)
        a = exp(result_arr[1][0])
        b = result_arr[1][1]
        degree_values = []
        for x in x_values:
            degree_values.append(round(a * x**b, 3))
        reliability = round(find_reliability(y_values, degree_values), 3)
        return {"a" : round(a, 3), "b" : round(b, 3), "values" : degree_values, "differences" : find_differences(y_values, degree_values), 
            "deviation" : find_diviation(y_values, degree_values), 
            "reliability" : reliability, "status" : define_status(reliability) }
    except Exception:  
        return {"error" : "Для аппроксимации спомощью степенной функции используется логарифмы по x и y, поэтому координаты точек должны быть положительными" }
    

def find_exp_function(x_values, y_values):
    try:
        ln_y_values = [log(y) for y in y_values]
        result_arr = find_polinomial_function(x_values, ln_y_values, 1)
        a = exp(result_arr[1][0])
        b = result_arr[1][1]
        degree_values = []
        for x in x_values:
            degree_values.append(round(a * pow(x, b), 3))
        reliability = round(find_reliability(y_values, degree_values), 3)
        print({"a" : round(a, 3), "b" : round(b, 3), "values" : degree_values, "differences" : find_differences(y_values, degree_values), 
            "deviation" : find_diviation(y_values, degree_values), 
            "reliability" : reliability, "status" : define_status(reliability) })
        return {"a" : round(a, 3), "b" : round(b, 3), "values" : degree_values, "differences" : find_differences(y_values, degree_values), 
            "deviation" : find_diviation(y_values, degree_values), 
            "reliability" : reliability, "status" : define_status(reliability) }
    except Exception:
        return {"error" : "Для аппроксимации спомощью экспоненциальной функции используется логарифм по y, поэтому координатa точек y должна быть положительной" }
    
def find_log_function(x_values, y_values):
    try:
        ln_x_values = [log(x) for x in x_values]
        result_arr = find_polinomial_function(ln_x_values, y_values, 1)
        a = result_arr[1][1]
        b = result_arr[1][0]
        log_values = []
        for x in x_values:
            log_values.append(round(a * log(x) + b, 3))
        reliability = round(find_reliability(y_values, log_values), 3)
        return {"a" : round(a, 3), "b" : round(b, 3), "values" : log_values, "differences" : find_differences(y_values, log_values), 
            "deviation" : find_diviation(y_values, log_values), 
            "reliability" : reliability, "status" : define_status(reliability) }
    except Exception:
        return {"error" : "Для аппроксимации спомощью логарифмической функции используется логарифм по x, поэтому координатa точек x должна быть положительной" }


def find_differences(y_values, phi_values):
    differences = []
    for i in range(len(y_values)):
        differences.append(round(phi_values[i] - y_values[i], 3))
    return differences

def find_diviation(y_values, phi_values):
    deviation = 0
    for i in range(len(y_values)):
        deviation += (phi_values[i] - y_values[i])**2
    deviation /= len(y_values)
    deviation = round(sqrt(deviation), 3)
    return deviation
        
def find_pirson(x_values, y_values):
    n = len(x_values)
    x_average = 0
    y_average = 0
    for i in range(n):
        x_average += x_values[i]
        y_average += y_values[i]
    y_average /= n
    x_average /= n
    pirson_top = 0
    bottom_1 = 0
    bottom_2 = 0
    for i in range(n):
        pirson_top += ((x_values[i] - x_average) * (y_values[i] - y_average))
        bottom_1 += (x_values[i] - x_average)**2
        bottom_2 += (y_values[i] - y_average)**2
    pirson_bottom = sqrt(bottom_1 * bottom_2)
    try:
        return pirson_top / pirson_bottom
    except Exception:
        return 1


def find_reliability(y_values, phi_values):
    n = len(y_values)
    reliability_top = 0
    reliability_bottom = 0
    average_phi = 0
    for i in range(n):
        average_phi += phi_values[i]
    average_phi /= n
    for i in range(n):
        reliability_top += (y_values[i] - phi_values[i])**2
        reliability_bottom += (y_values[i] - average_phi)**2
    if round(reliability_top, 3) == 0:
        return 1
    return 1 - reliability_top / reliability_bottom

def define_status(reliability):
    if (reliability >= 0.95):
        return "Высокая точность аппрокимации"
    elif (reliability >= 0.75):
        return "Средняя точность аппроксимации"
    elif (reliability >= 0.5):
        return "Низкая точность аппроксимации"
    else:
        return "Неудовлетворительная точность аппроксимации"
    