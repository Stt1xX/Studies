from utils import bcolors, codes
from os import path

class FileManager:

    file_path: str = None

    def __init__(self, file_path: str):
        self.file_path = file_path

    def read(self):
        if path.isfile(self.file_path) == False:   
            print(f"{bcolors.FAIL}Файл не найден!{bcolors.ENDC}")
            return codes.ERROR_CODE
        
        print(f"{bcolors.OKGREEN}Файл найден!{bcolors.ENDC}")
        try:
            file = open(self.file_path, 'r')
            numbers = list(map(float, file.read().replace(',', '.').replace('\n', ' ').split())) 
        except ValueError:
            print(f"{bcolors.FAIL}Файл поврежден!{bcolors.ENDC}")
        return numbers
