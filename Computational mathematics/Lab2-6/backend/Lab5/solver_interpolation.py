from math import sin, cos
from backend.Lab5.utils import StatusInterpol, StatusMethod
import numpy as np
import json


def find_interpolation_type1(argument, x_values, y_values):
    # Некая валидация на сервере
    n = len(x_values)
    try:
        local_x_values = []
        local_y_values = []
        for i in range(len(x_values)):
            if x_values[i] == "" and y_values[i] == "":
                n -= 1
            if x_values[i] != "" and y_values[i] != "":
                local_x_values.append(float(x_values[i].replace(',', '.')))
                local_y_values.append(float(y_values[i].replace(',', '.')))
        argument = float(argument.replace(',', '.'))
    except ValueError:
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Некорректный формат данных" }
    except (IndexError, TypeError):
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Количество X координат и Y координат должно быть одинаковым"}
    
    if len(local_x_values) < 2:
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Количество точек для интерполяции должно быть больше 2"}
    
    if len(local_x_values) != len(local_y_values) or len(local_x_values) != n:
        print(x_values, y_values)
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Количество X координат и Y координат должно быть одинаковым"}
    
    if argument in local_x_values:
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Подскажи, пожалуйста, деловая колбаса, а зачем мне считать значение в узле интерполяции???"}
    
    # Сортировка точек
    dict_points = dict(sorted(dict(zip(local_x_values, local_y_values)).items()))

    if len(dict_points) != len(local_x_values):
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Координата X должна быть уникальной, братишка"}

    points = (np.array(list(dict_points.keys())), list(dict_points.values()))
    return {
        "status" : json.dumps(StatusInterpol.FIRST_TYPE_OK),
        "lagrange_result" : lagrange_method(points, argument),
        "newton_separated_result" : newton_separated_differences_method(points, argument),
        "newton_finite_result" : newton_finite_differences_method(points, argument),
        "interpol_points" : {"x_values" : points[0].tolist(), "y_values" : points[1]}
    }

def find_interpolation_type2(argument, number_of_equation, left_border, right_border, number_of_points):
    # Некая валидация на сервере
    try:
        left_border = float(left_border.replace(',', '.'))
        right_border = float(right_border.replace(',', '.'))
        argument = float(argument.replace(',', '.'))
        number_of_points = int(number_of_points)
        number_of_equation = int(number_of_equation)
    except ValueError:
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Некорректный формат данных" }
    
    if number_of_points < 2:
        return { "status" : json.dumps(StatusInterpol.ERROR), "error" : "Количество точек для интерполяции должно быть больше 2"}
    
    points = get_points(number_of_equation, left_border, right_border, number_of_points)
    return {
        "status" : json.dumps(StatusInterpol.SECOND_TYPE_OK),
        "lagrange_result" : lagrange_method(points, argument),
        "newton_separated_result" : newton_separated_differences_method(points, argument),
        "newton_finite_result" : newton_finite_differences_method(points, argument),
        "true_value" : find_true_value(argument, number_of_equation, points),
        "interpol_points" : {"x_values" : points[0].tolist(), "y_values" : points[1]}
    }
    

def lagrange_method(points, argument):
    x_values, y_values = points

    # Получаем коэффициенты c_i
    c_values = list()
    for i in range(len(x_values)):
        current_c = y_values[i]
        for j in range(len(x_values)):
            if j != i:
                current_c /= x_values[i] - x_values[j]
        c_values.append(current_c)
    
    # Получаем конкретные значения аппроксимируещей функции для графика
    graph_x_values = find_graph_points(x_values)
    graph_y_values = list()
    for i in range(len(graph_x_values)):
        current_y = 0
        for j in range(len(c_values)):
            current_summand = c_values[j]
            for k in range(len(x_values)):
                if j != k:
                    current_summand *= (graph_x_values[i] - x_values[k])
            current_y += current_summand
        graph_y_values.append(current_y)

    # Поиск значения аппроксимирующей функции в заданной точке
    argument_value = 0
    for j in range(len(c_values)):
        current_summand = c_values[j]
        for k in range(len(x_values)):
            if j != k:
                current_summand *= (argument - x_values[k])
        argument_value += current_summand

    return {"status" : json.dumps(StatusMethod.OK), "x_values" : graph_x_values, "y_values" : graph_y_values, "argument_value" : argument_value }


def newton_separated_differences_method(points, argument):
    x_values, _ = points

    f_values = find_separate_differences(points)
    # Получаем конкретные значения аппроксимируещей функции для графика
    graph_x_values = find_graph_points(x_values)
    graph_y_values = list()
    for i in range(len(graph_x_values)):
        current_y_value = f_values[0]
        for k in range(1, len(f_values)):
            multiplier = 1
            for j in range(k):
                multiplier *= graph_x_values[i] - x_values[j]
            current_y_value += f_values[k] * multiplier
        graph_y_values.append(current_y_value)

    # Поиск значения аппроксимирующей функции в заданной точке
    argument_value = f_values[0]
    for k in range(1, len(f_values)):
        multiplier = 1
        for j in range(k):
            multiplier *= argument - x_values[j]
        argument_value += f_values[k] * multiplier

    return {"status" : json.dumps(StatusMethod.OK), "x_values" : graph_x_values, "y_values" : graph_y_values, "argument_value" : argument_value }
    


def newton_finite_differences_method(points, argument):
    x_values, _ = points

    # Проверка возможности применить метод
    h = x_values[1] - x_values[0]
    for i in range(1, len(x_values)):
        if x_values[i] - x_values[i - 1] - h > 0.000001:
            return {"status" : json.dumps(StatusMethod.ERROR), "error" : "Метод применим только для функций заданных на равномерной сетке"}
        
    diffs, diffs_with_x = find_finite_differences(points)
    # Получаем конкретные значения аппроксимируещей функции для графика
    graph_x_values = find_graph_points(x_values)
    graph_y_values = list()
    middle = (x_values.max() + x_values.min()) / 2
    for x in graph_x_values:
        graph_y_values.append(find_newton_func_value(x, x_values, middle, diffs))

    # Поиск значения аппроксимирующей функции в заданной точке
    argument_value = find_newton_func_value(argument, x_values, middle, diffs)

    return {"status" : json.dumps(StatusMethod.OK), "x_values" : graph_x_values, "y_values" : graph_y_values, "argument_value" : argument_value, "diffs_table" : [[round(x, 2) for x in rows] for rows in diffs_with_x]}


# Поиск разделенных разностей порядка len(points)
def find_separate_differences(points):
    x_values, y_values = points
    n = len(y_values)
    diffs = np.zeros((n, n))
    diffs[0] = y_values
    for i in range(1, n + 1):
        for j in range(n - i):
            diffs[i][j] = (diffs[i - 1][j + 1] - diffs[i - 1][j]) / (x_values[j + i] - x_values[j])
    f_values = np.ravel(np.compress([1], diffs, axis=1))
    return f_values


# Поиск конечных разностей порядка len(points)
def find_finite_differences(points):
    x_values, y_values = points
    n = len(y_values)
    diffs = np.zeros((n, n))
    diffs[0] = y_values
    for i in range(1, n):
        for j in range(n - i):
            diffs[i][j] = diffs[i - 1][j + 1] - diffs[i - 1][j]
    diffs_with_x = np.append([x_values], diffs, axis=0)
    return np.transpose(diffs), np.transpose(diffs_with_x)

# Находит значение формулы Ньютона, принимая на вход таблицу конечных разностей
def find_newton_func_value(x, interpol_values, middle_point, diffs):
    x_start = 0
    diffs_i = list()
    h = interpol_values[1] - interpol_values[0]
    if x < middle_point:
        # Первая формула Ньютона
        for i in range(1, len(interpol_values)):
            if x < interpol_values[i]:
                x_start = interpol_values[i - 1]
                diffs_i = diffs[i - 1]
                break
        t = (x - x_start) / h
        t_multipliesrs = np.append(1, find_t_multipliers(t, len(diffs_i), type_=1))
    else:
        # Вторая формула Ньютона
        for i in range(len(interpol_values) - 2, -1, -1):
            if x > interpol_values[i]:
                x_start = interpol_values[i + 1]
                diffs_i = find_diffs_i_back_newton(i + 1, diffs)
                break
        t = (x - x_start) / h
        t_multipliesrs = np.append(1, find_t_multipliers(t, len(diffs_i), type_=2))
    return np.sum(t_multipliesrs * diffs_i)

# Поиск разностей в таблице разностей для интерполирования назад (Метод Ньютона для равноудаленных узлов)
def find_diffs_i_back_newton(slice_number, diffs):
    ret_arr = np.ndarray(0)
    for i in range(slice_number, -1, -1):
        ret_arr = np.append(ret_arr, diffs[i][slice_number - i])
    return ret_arr

# Высчитывает дробь - t-множитель, принимая на вход само значение t (Метод Ньютона для равноудаленных узлов)
def find_t_multipliers(t, len_, type_):
    ret_arr = np.ndarray(0)
    for i in range(1, len_):
        top = t
        bottom = 1
        for j in range(1, i):
            bottom *= (j + 1)
            if type_ == 1:
                # Первая формула Ньютона
                top *= (t - j)
            else:
                # Вторая формула Ньютона
                top *= (t + j)
        ret_arr = np.append(ret_arr, top / bottom)
    return ret_arr

def find_true_value(argument, number_of_equation, points):
    x_values, _ = points
    method = define_method(number_of_equation)
    argument_value = method(argument)
    graph_x_values = find_graph_points(x_values)
    graph_y_values = list()
    for x in graph_x_values:
        graph_y_values.append(method(x))
    return {"status" : json.dumps(StatusMethod.OK), "x_values" : graph_x_values, "y_values" : graph_y_values, "argument_value" : argument_value }


# Возвращает начальный набор точек для интерполирования функций
def get_points(number_of_equation, left_border, right_border, number_of_points):
    find_y = define_method(number_of_equation)
    x_values = np.linspace(left_border, right_border, number_of_points)
    y_values = [find_y(x) for x in x_values]
    return x_values, y_values

# Возвращает X_points для отправки на фронт для графика
def find_graph_points(x_values):
    left_border = min(x_values)
    right_border = max(x_values)
    graph_left_border = left_border - (left_border + right_border) / 4
    graph_right_border = right_border + (left_border + right_border) / 4
    return np.linspace(graph_left_border, graph_right_border, 1000).tolist()

def first_equation(x):
    return 2 * x - 3

def second_equation(x):
    return x**3 + 4.81 * x**2 - 17.37 * x + 5.38

def third_equation(x):
    return 5 * sin(x) - 2 * cos(x)

def define_method(number_of_equation):
    if  number_of_equation == 1:
        return first_equation
    elif  number_of_equation == 2:
        return second_equation
    elif  number_of_equation == 3:
        return third_equation
    else:
        return None