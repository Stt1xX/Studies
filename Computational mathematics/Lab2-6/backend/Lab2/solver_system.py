from math import sin, cos

def sovle_system(approachX, approachY, method, equation, accuracy):
    try:
        approachX = float(approachX.replace(',', '.'))
        approachY = float(approachY.replace(',', '.'))
        method = float(method.replace(',', '.'))
        equation = float(equation.replace(',', '.'))
        accuracy = float(accuracy.replace(',', '.'))
    except ValueError:
        return { "status" : 0, "error" : "Некорректный формат данных"}
    if method == 1:
        return newton(equation, approachX, approachY, accuracy)
    else:
        return {"status" : 0, "error" : "Не понимаю, каким методом решать"}
    

def newton(equation, approachX, approachY, accuracy):
    search_value = define_method(equation)
    counter = 1
    previous = {"x" : approachX, "y" : approachY}
    try:
        current = {"x" : previous.get('x') + search_value(approachX, approachY).get('delta_x'), "y" : previous.get('y') + search_value(approachX, approachY).get('delta_y')}
    except ZeroDivisionError:
        return {"status" : 0, "error": "Недопустимое начальное приближение, выберите, пожалуйста, другое"}
    while max(abs(previous.get('x') - current.get('x')), abs(previous.get('y') - current.get('y'))) > accuracy:
        counter += 1
        previous = current
        current = {"x" : previous.get('x') + search_value(previous.get('x'), previous.get('y')).get('delta_x'), "y" : previous.get('y') + search_value(previous.get('x'), previous.get('y')).get('delta_y')}
    return {"status" : 1, "roots" : f'x: {current.get('x')} y: {current.get('y')}' ,"number_of_iterations" : counter, "vector_of_inaccuracy" : f'x: {abs(previous.get('x') - current.get('x'))} y: {abs(previous.get('y') - current.get('y'))}'}
        


def first_system(x, y):
    delta_x = (-2 * sin(x + 1) + 0.4 + cos(x)) / (2 * cos(x + 1) + sin(x))
    return {'delta_x': delta_x, 'delta_y' : sin(x + 1) - y - 1.2 + cos(x + 1) * delta_x}

def second_system(x, y):
    delta_x = (2 - pow(x, 2) - pow(y, 2) - 2 * y * (0.5 - y - cos(x - 1))) / (2 * x + 2 * y * sin (x - 1)) 
    return {'delta_x': delta_x, 'delta_y' : 0.5 - y - cos(x - 1) + sin(x - 1) * delta_x}

def define_method(equation):
    if equation == 1:
        return first_system
    elif equation == 2:
        return second_system
    else:
        return None
