import random
from solver import Solver
from graph_builder import GraphBuilder
# Настройка программы
interval_begin = 0  
interval_end = 100 # конец должен быть больше начала 
selection_size = 10000
selection_count = 1000

solver = Solver()
graph_builder = GraphBuilder()
math_expectation_arr = list()
variance_arr = list()
median_arr = list()

def generate_selection():
    return [random.random() * (interval_end - interval_begin)  + interval_begin for i in range(selection_size)]

def find_mathematical_expectation(selection): # Мат ожидание
    ret_val: float = 0
    for i in selection:
        ret_val += i
    return ret_val / len(selection)

def find_variance(selection):  # Дисперсия
    ret_val: float = 0
    math_exp: float = find_mathematical_expectation(selection)
    for i in selection:
        ret_val += pow(i - math_exp, 2)
    return ret_val / len(selection)

def find_median(selection):
    if selection_size % 2 == 0:
        return (selection[selection_size // 2] + selection[selection_size // 2 - 1]) / 2
    return selection[selection_size // 2 - 1]


for i in range(selection_count):
    curr_selection = sorted(generate_selection()) # генерация
    math_expectation_arr.append(find_mathematical_expectation(curr_selection)) # мат ожидание
    variance_arr.append(find_variance(curr_selection)) # дисперсия
    median_arr.append(find_median(curr_selection)) # медиана

solver.solve(math_expectation_arr)
graph_builder.drow(solver.get_empirical_series(), solver.get_statistical_series(), len(solver.numbers))
solver.solve(variance_arr)
graph_builder.drow(solver.get_empirical_series(), solver.get_statistical_series(), len(solver.numbers))
solver.solve(median_arr)
graph_builder.drow(solver.get_empirical_series(), solver.get_statistical_series(), len(solver.numbers))