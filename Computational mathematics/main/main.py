from entity import Equations_system
from scanner import Scanner
from solver import Solver

first_equations_system = Equations_system()
solver = Solver()
scanner = Scanner()

print("   _____ _____ ______        _     _____       _   _             \n  / ____|  __ \\___  /       | |   |  __ \\     | | (_)            \n | |  __| |  | | / /    ___ | |_  | |__) |   _| |_ _ _ __   __ _ \n | | |_ | |  | |/ /    / _ \\| __| |  ___/ | | | __| | '_ \\ / _` |\n | |__| | |__| / /__  | (_) | |_  | |   | |_| | |_| | | | | (_| |\n  \\_____|_____/_____|  \\___/ \\__| |_|    \\__,_|\\__|_|_| |_|\\__,_|\n                                                                 \n                                                                 ")

scanner.initialize_task(first_equations_system)

print("Начинаю считать...")

solver.simple_iterations(first_equations_system)

# print(first_equations_system.get_size(), first_equations_system.get_d_matrix(), first_equations_system.get_main_matrix(), first_equations_system.get_accuracy())

