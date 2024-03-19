from utils import *
from myParser import parse_file, parse_console
from randGenerator import generate_task

dict_data_sources = {'a': data_sources.CONSOLE, 'b': data_sources.FILE, 'c': data_sources.RAND}

def define_console_or_file():
    while True:
        print(f"Выберите режим работы:\na) Консоль\nb) Файл\nc) Рандомная генерация")
        data_source = input()
        if data_source in dict_data_sources:
            return dict_data_sources[data_source]
        print(f"{bcolors.FAIL}Введите либо (a), (b) или (c) другие буквы я не воспринимаю!{bcolors.ENDC}")

class Scanner:
    def initialize_task(self, entity):
        while True:
            define = define_console_or_file()
            if  define == data_sources.FILE:
                if parse_file(entity) != read_file_return_codes.OK:
                    continue # For trying again
                break
            elif define == data_sources.RAND:
                generate_task(entity)
                break
            else:
                parse_console(entity)
                break