from utils import *
from myParser import parse_file, parse_console

dict_data_sources = {'a': data_sources.CONSOLE, 'b': data_sources.FILE}

def define_console_or_file():
    while True:
        print(f"Выберите, откуда мне брать данные:\na) Консоль\nb) Файл")
        data_source = input()
        if data_source in dict_data_sources:
            return dict_data_sources[data_source]
        print(f"{bcolors.FAIL}Введите либо (a), либо (b) другие буквы я не воспринимаю!{bcolors.ENDC}")

class Scanner:
    def initialize_task(self, entity):
        while True:
            if define_console_or_file() == data_sources.FILE:
                if parse_file(entity) != read_file_return_codes.OK:
                    continue # For trying again
                break
            else:
                parse_console(entity)
                break