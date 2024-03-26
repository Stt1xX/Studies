import random
from solver import Solver
from graph_builder import GraphBuilder
import numpy as np
import scipy.stats as stats

# Настройка программы
interval_begin = 0  
interval_end = 10 # конец должен быть больше начала 
selection_size = 100 # размер выборки
selection_count = 10000 # количество выборок

solver = Solver()
graph_builder = GraphBuilder()
math_expectation_arr = list()
variance_arr = list()
median_arr = list()
low_values_arr = list()
max_values_arr = list()
uniform = stats.uniform(interval_begin, interval_end) # функция с равномерным распределением


def generate_selection():
    return [random.random() * (interval_end - interval_begin)  + interval_begin for i in range(selection_size)]

for i in range(selection_count):
    curr_selection = sorted(generate_selection()) # генерация
    low_values_arr.append(uniform.cdf(curr_selection[1]) * selection_size)
    max_values_arr.append((1 - uniform.cdf(curr_selection[selection_size - 1])) * selection_size)
    math_expectation_arr.append(np.mean(curr_selection)) # мат ожидание
    variance_arr.append(np.std(curr_selection)) # дисперсия
    median_arr.append(solver.find_median(curr_selection)) # медиана
solver.solve(math_expectation_arr, "Математическое ожидание")
graph_builder.drow_normail_distribution()
graph_builder.drow_histogram(solver.get_heading(), solver.get_normalized_statistical_series(), solver.get_normalized_h())
solver.solve(variance_arr, "Дисперсия")
graph_builder.drow_normail_distribution()
graph_builder.drow_histogram(solver.get_heading(), solver.get_normalized_statistical_series(), solver.get_normalized_h())
solver.solve(median_arr, "Медиана")
graph_builder.drow_normail_distribution()
graph_builder.drow_histogram(solver.get_heading(), solver.get_normalized_statistical_series(), solver.get_normalized_h())
solver.solve(low_values_arr, "Low values")
graph_builder.drow_gamma_distribution(2)
graph_builder.drow_histogram(solver.get_heading(), solver.get_statistical_series(), solver.get_h())
solver.solve(max_values_arr, "Max values")
graph_builder.drow_gamma_distribution(1)
graph_builder.drow_histogram(solver.get_heading(), solver.get_statistical_series(), solver.get_h())