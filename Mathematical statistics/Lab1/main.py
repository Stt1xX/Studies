import random
import numpy as np
import scipy.stats as stats
import pandas as pd
from solver import Solver
from graph_builder import GraphBuilder


def task1():
    print("-------- Задание 1 --------")
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
    solver.solve(math_expectation_arr, "Выборочное среднее")
    graph_builder.drow_normail_distribution()
    graph_builder.drow_histogram(solver.get_heading(), solver.get_normalized_statistical_series(), solver.get_normalized_h())
    solver.solve(variance_arr, "Выборочная дисперсия")
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

def task2():
    print("-------- Задание 2 --------")
    csv_dict = pd.read_csv('mobile_phones.csv', sep=',').to_dict()
    print(f'В {sum(csv_dict['dual_sim'].values())} можно вставить 2 сим карты')
    print(f'{sum(csv_dict['three_g'].values())} телефонов поддерживают 3G')
    print(f'{max(csv_dict['n_cores'].values())} - наибольшее количество ядер среди всех телефонов')
    
    all_battery_power = csv_dict['battery_power'].values()
    battery_power_with_wifi = []
    battery_power_without_wifi = []
    # getting values
    for i in range(len(csv_dict['battery_power'])):
        if csv_dict['wifi'][i] == 1:
            battery_power_with_wifi.append(csv_dict['battery_power'][i])
        else:
            battery_power_without_wifi.append(csv_dict['battery_power'][i])

    solver = Solver()
    graph_builder = GraphBuilder()
    solver.solve(all_battery_power, "Общая выборка")
    solver.print_cvantil(2 / 5)
    graph_builder.drow_histogram(solver.get_heading(), solver.get_statistical_series(), solver.get_h())
    graph_builder.drow_empirical_series(solver.get_heading(), solver.get_empirical_series())
    graph_builder.drow_boxplot(solver.get_heading(), solver.numbers)
    solver.solve(battery_power_with_wifi, "Выборка с наличием Wi-Fi")
    solver.print_cvantil(2 / 5)
    graph_builder.drow_histogram(solver.get_heading(), solver.get_statistical_series(), solver.get_h())
    graph_builder.drow_empirical_series(solver.get_heading(), solver.get_empirical_series())
    graph_builder.drow_boxplot(solver.get_heading(), solver.numbers)
    solver.solve(battery_power_without_wifi, "Выборка с отсутствием Wi-Fi")
    solver.print_cvantil(2 / 5)
    graph_builder.drow_histogram(solver.get_heading(), solver.get_statistical_series(), solver.get_h())
    graph_builder.drow_empirical_series(solver.get_heading(), solver.get_empirical_series())
    graph_builder.drow_boxplot(solver.get_heading(), solver.numbers)

# task2()
task1()