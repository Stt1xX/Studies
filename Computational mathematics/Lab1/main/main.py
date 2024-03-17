from entity import Equations_system
from scanner import Scanner
from solver import Solver
from utils import bcolors

# файл делится на 3 блока
# первая строка содержит одно целое число n - количество уравнений в системе 
# cледующие n строк содержат n+1 чисел с плавающей точкой, разделенные пробелом (n + 1) со свободным членом после знака равно
# последняя строчка задает точность - число с плавающей точкой
# ***ПРИМЕР***
# x + 3y + 2z = 6
# 4x - 7z = 0
# 5x = 2
# с точностью 0.1
# ***ФАЙЛ***
#3
#1 3 2 6
#4 0 -7 0
#5 0 0 2
#0.1
#дирректория с файлами           main/example_files/test.txt

first_equations_system = Equations_system() 
solver = Solver()
scanner = Scanner()

print(f"{bcolors.OKGREEN}   _____ _____ ______        _     _____       _   _             \n  / ____|  __ \\___  /       | |   |  __ \\     | | (_)            \n | |  __| |  | | / /    ___ | |_  | |__) |   _| |_ _ _ __   __ _ \n | | |_ | |  | |/ /    / _ \\| __| |  ___/ | | | __| | '_ \\ / _` |\n | |__| | |__| / /__  | (_) | |_  | |   | |_| | |_| | | | | (_| |\n  \\_____|_____/_____|  \\___/ \\__| |_|    \\__,_|\\__|_|_| |_|\\__,_|\n                                                                 \n                                                                 {bcolors.ENDC}")

scanner.initialize_task(first_equations_system)

solver.simple_iterations(first_equations_system)

