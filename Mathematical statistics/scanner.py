from utils import bcolors
from os import path

class FileManager:

    file_path = None

    def __init__(self, file_path):
        self.file_path = file_path

    def read(self):
        if path.isfile(self.file_path) == False:   
            print(f"{bcolors.FAIL}Файл не найден!{bcolors.ENDC}")
            return 1
        file = open(self.file_path, 'r')
        file_content = file.read().split('\n')
        print(f"{bcolors.OKGREEN}Файл найден!{bcolors.ENDC}")
        print(file_content)
        return 0