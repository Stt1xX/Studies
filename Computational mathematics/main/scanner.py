from utils import *
from myParser import *
from os import path

dict_data_sources = {'a': data_sources.CONSOLE, 'b': data_sources.FILE}

def validate_size(size):
    if size.isdigit():
        if int(size) <= 20:
            return int(size)
    return -1

def define_console_or_file():
    while True:
        print(f"Выберите, откуда мне брать данные:\na) Консоль\nb) Файл")
        data_source = input()
        if data_source in dict_data_sources:
            return dict_data_sources[data_source]
        print(f"{bcolors.FAIL}Введите либо (a), либо (b) другие буквы я не воспринимаю{bcolors.ENDC}")

def read_task_from_file(entity):
    print("Введите путь до файла:")
    file_path = input()
    if path.exists(file_path):        
        return parse_file(file_path, entity)
    else:
        print(f"{bcolors.FAIL}Файл не найден{bcolors.ENDC}")
        return read_file_return_codes.INVALID_PATH
    
def read_task_from_console(entity):
    while True:
        print("Введите размер матрицы n <= 20:")
        size = validate_size(input())
        if size == -1:
            print(f"{bcolors.FAIL}Некорректный размер матрицы, попробуйте еще раз{bcolors.ENDC}")
            continue # For trying again
        for i in range(1, size):
            print(f"Введите уравнение №{i}:")
            parse_equation()
        break
        
def initialize_task(entity):
    while True:
        if define_console_or_file() == data_sources.FILE:
            if read_task_from_file(entity) != read_file_return_codes.OK:
                continue # For trying again
            break
        else:
            read_task_from_console(entity)
            break