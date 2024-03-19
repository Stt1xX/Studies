from utils import read_file_return_codes, bcolors, read_equation_return_codes
from os import path

def is_float(number_string):
    try:
        number = float(number_string)
        return True
    except ValueError:
        return False

def validate_size(size):
    if size.isdigit() and int(size) <= 20 and int(size) >= 1:
            return int(size)
    return -1

def validate_accuracy(accuracy):
    if is_float(accuracy.replace(',', '.')) == True and 1 > float(accuracy.replace(',', '.')) > 0:
        return float(accuracy.replace(',', '.'))
    return -1

def parse_size(entity):
    while True:
        print("Введите размер матрицы 0 < n <= 20:")
        size = validate_size(input())
        if size == -1:
            print(f"{bcolors.FAIL}Некорректный размер матрицы, попробуйте еще раз{bcolors.ENDC}")
            continue # For trying again
        entity.set_size(size)
        return size
    
def parse_accuracy(entity):
    while True:
        print("Введите точность для алгоритма 0 < e < 1:")
        accuracy = validate_accuracy(input())
        if accuracy == -1:
            print(f"{bcolors.FAIL}Некорректная точность, попробуйте еще раз{bcolors.ENDC}")
            continue # For trying again
        entity.set_accuracy(accuracy)
        return accuracy

def parse_equation(equation, main_martix, d_matrix, size):
    current_string = equation.replace(',', '.').split()
    if len(current_string) != size + 1:
        return read_equation_return_codes.INVALID_NUMBER_OF_VARIABLES
    for var in current_string:
        if is_float(var) == False:
            return read_equation_return_codes.INVALID_COEFFICIENTS
    numeric_current_string = [float(x) for x in current_string]
    d_matrix.append(numeric_current_string[-1])
    main_martix.append(numeric_current_string[0: -1])
    return read_equation_return_codes.OK
    
def parse_console(entity):
    parse_size(entity)
    main_matrix = list()
    d_matrix = list()
    iterator = 1
    print(f"{bcolors.WARNING}Вводите коэффициенты, включая свободный коэффициент справа от знака '=', не меняя его знак{bcolors.ENDC}")
    while iterator < entity.get_size() + 1:
        print(f"Введите коэффициенты уравнения №{iterator}:")
        equation = input()
        parse_status = parse_equation(equation, main_matrix, d_matrix, entity.get_size())
        if parse_status == read_equation_return_codes.INVALID_COEFFICIENTS:
            print(f"{bcolors.FAIL}Строка содержит невалидные коэффициенты! Введите коэффициенты еще раз!{bcolors.ENDC}")
            iterator -= 1
        elif parse_status == read_equation_return_codes.INVALID_NUMBER_OF_VARIABLES:
            print(f"{bcolors.FAIL}Строка содержит неверное количество коэффициентов! Введите коэффициенты еще раз!{bcolors.ENDC}")
            iterator -= 1
        iterator += 1
    parse_accuracy(entity)
    entity.set_main_matrix(main_matrix)
    entity.set_d_matrix(d_matrix)


def parse_file(entity):
    print("Введите путь до файла:")
    file_path = input()
    size = 0
    accuracy = 0
    main_matrix = list()
    d_matrix = list()
    try:
        if path.isfile(file_path) == False:   
            print(f"{bcolors.FAIL}Файл не найден!{bcolors.ENDC}")
            return read_file_return_codes.INVALID_PATH
        file = open(file_path, 'r')
    except IOError:
        print(f"{bcolors.FAIL}У меня не хватает прав доступа для этого файла :({bcolors.ENDC}")
        return read_file_return_codes.PERMISSON_DENIED
    file_content = file.read().split('\n')

    size = validate_size(file_content[0])
    accuracy = validate_accuracy(file_content[len(file_content) - 1])
    if size == -1 or accuracy == -1:
        print(f"{bcolors.FAIL}Некорректное содержимое файла!{bcolors.ENDC}")
        return read_file_return_codes.INVALID_FILE
    size = int(file_content[0])
    for iterator in range(1, size + 1):
        parse_status = parse_equation(file_content[iterator], main_matrix, d_matrix, size)
        if parse_status != read_equation_return_codes.OK:
            print(f"{bcolors.FAIL}Некорректное содержимое файла!{bcolors.ENDC}")
            return read_file_return_codes.INVALID_FILE
    entity.set_accuracy(accuracy)
    entity.set_size(size)
    entity.set_main_matrix(main_matrix)
    entity.set_d_matrix(d_matrix)
    return read_file_return_codes.OK