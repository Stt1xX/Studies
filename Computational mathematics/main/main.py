from entity import Equations_system
from scanner import Scanner
from solver import Solver

first_equations_system = Equations_system()
solver = Solver()
scanner = Scanner()

scanner.initialize_task(first_equations_system)

solver.simple_iterations(first_equations_system)

# print(first_equations_system.get_size(), first_equations_system.get_d_matrix(), first_equations_system.get_main_matrix(), first_equations_system.get_accuracy())

