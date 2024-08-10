from math import log
 

def solve_intergral(method, equation, left, right, accuracy):
    try:
        left = float(left.replace(',', '.'))
        right = float(right.replace(',', '.'))
        method = float(method.replace(',', '.'))
        equation = float(equation.replace(',', '.'))
        accuracy = float(accuracy.replace(',', '.'))
    except ValueError:
        return { "status" : 0, "error" : "Некорректный формат данных"}
    if equation == 3 and left <= 0 and right >= 0:
        return { "status" : 0, "error" : "Интеграл расходится, попробуйте другой промежуток"}
    if method == 1:
        return rectangle_left(equation, left, right, accuracy)
    if method == 2:
        return rectangle_center(equation, left, right, accuracy)
    if method == 3:
        return rectangle_right(equation, left, right, accuracy)
    if method == 4:
        return trapezoid(equation, left, right, accuracy)
    if method == 5:
        return simpson(equation, left, right, accuracy)
    else:
        return {"status" : 0, "error" : "Не понимаю, каким методом решать"}
    
def rectangle_left(equation, left, right, accuracy):
    n = 4
    h = (right - left) / n
    method = define_method(equation)

    current_value = 0
    current_pointer = left
    while current_pointer <= right:
        current_value += method(current_pointer) * h
        current_pointer += h

    prev_value = current_value + 10000
    counter = 1
    while abs(current_value - prev_value) > accuracy :
        counter += 1
        if counter == 25:
            return {"status" : 2, "value" : current_value, "number" : n, "warning" : "Количество итераций превисило допустимое значение"}
        prev_value = current_value
        n *= 2
        h = (right - left) / n

        current_value = 0
        current_pointer = left
        while current_pointer <= right:
            current_value += method(current_pointer) * h
            current_pointer += h

    return {"status" : 1, "value" : current_value, "number" : n}

def rectangle_center(equation, left, right, accuracy):
    n = 4
    h = (right - left) / n
    method = define_method(equation)

    current_value = 0
    current_pointer = left
    while current_pointer + h / 2 <= right:
        current_value += method(current_pointer + h / 2) * h
        current_pointer += h

    prev_value = current_value + 10000
    counter = 1
    while abs(current_value - prev_value) > accuracy :
        counter += 1
        if counter == 25:
            return {"status" : 2, "value" : current_value, "number" : n, "warning" : "Количество итераций превисило допустимое значение"}
        prev_value = current_value
        n *= 2
        h = (right - left) / n

        current_value = 0
        current_pointer = left
        while current_pointer + h / 2 <= right:
            current_value += method(current_pointer + h / 2) * h
            current_pointer += h

    return {"status" : 1, "value" : current_value, "number" : n}

def rectangle_right(equation, left, right, accuracy):
    n = 4
    h = (right - left) / n
    method = define_method(equation)

    current_value = 0
    current_pointer = left
    while current_pointer + h <= right:
        current_value += method(current_pointer + h) * h
        current_pointer += h

    prev_value = current_value + 10000
    counter = 1

    while abs(current_value - prev_value) > accuracy :
        counter += 1
        if counter == 25:
            return {"status" : 2, "value" : current_value, "number" : n, "warning" : "Количество итераций превисило допустимое значение"}
        prev_value = current_value
        n *= 2
        h = (right - left) / n

        current_value = 0
        current_pointer = left
        while current_pointer + h <= right:
            current_value += method(current_pointer + h) * h
            current_pointer += h

    return {"status" : 1, "value" : current_value, "number" : n}

def trapezoid(equation, left, right, accuracy):
    n = 4
    h = (right - left) / n
    method = define_method(equation)

    current_value = 0
    current_pointer = left
    y_values = []
    while current_pointer <= right:
        y_values.append(method(current_pointer))
        current_pointer += h

    for i in range(len(y_values)):
        if  i == 0 or i == len(y_values) - 1:
            current_value += y_values[i] / 2
        else:
            current_value += y_values[i]
    current_value *= h

    prev_value = current_value + 10000
    counter = 1

    while abs(current_value - prev_value) > accuracy :
        counter += 1
        if counter == 24:
            return {"status" : 2, "value" : current_value, "number" : n, "warning" : "Количество итераций превисило допустимое значение"}
        prev_value = current_value
        n *= 2
        h = (right - left) / n

        current_value = 0
        current_pointer = left
        y_values = []
        while current_pointer <= right:
            y_values.append(method(current_pointer))
            current_pointer += h

        for i in range(len(y_values)):
            if  i == 0 or i == len(y_values) - 1:
                current_value += y_values[i] / 2
            else:
                current_value += y_values[i]
        current_value *= h

    return {"status" : 1, "value" : current_value, "number" : n}

def simpson(equation, left, right, accuracy):
    n = 4
    h = (right - left) / n
    method = define_method(equation)

    current_value = 0
    current_pointer = left
    y_values = []
    while current_pointer <= right:
        y_values.append(method(current_pointer))
        current_pointer += h

    for i in range(len(y_values)):
        if  i == 0 or i == len(y_values) - 1:
            current_value += y_values[i]
        elif i % 2 == 1:
            current_value += y_values[i] * 4
        else:
            current_value += y_values[i] * 2
    current_value *= h / 3

    prev_value = current_value + 10000
    counter = 1

    while abs(current_value - prev_value) > accuracy :
        counter += 1
        if counter == 24:
            return {"status" : 2, "value" : current_value, "number" : n, "warning" : "Количество итераций превисило допустимое значение"}
        prev_value = current_value
        n *= 2
        h = (right - left) / n

        current_value = 0
        current_pointer = left
        y_values = []
        while current_pointer <= right:
            y_values.append(method(current_pointer))
            current_pointer += h

        for i in range(len(y_values)):
            if  i == 0 or i == len(y_values) - 1:
                current_value += y_values[i]
            elif i % 2 == 1:
                current_value += y_values[i] * 4
            else:
                current_value += y_values[i] * 2
        current_value *= h / 3

    return {"status" : 1, "value" : current_value, "number" : n}

def first_equation(x):
    return x
def second_equation(x):
    return x * x
def third_equation(x):
    return 1 / x

def define_method(equation):
    if equation == 1:
        return first_equation
    elif equation == 2:
        return second_equation
    elif equation == 3:
        return third_equation
    else:
        return None