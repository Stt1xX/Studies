from myParser import parse_size, parse_accuracy
from random import randrange, random
from utils import bcolors
import sys


def generate_task(entity):
    size = parse_size(entity)
    print("Прочитал размер, размер -", entity.get_size())
    accuracy = parse_accuracy(entity)
    print("Прочитал точность, точность -", entity.get_accuracy())
    main_matrix = list()
    d_matrix = list()
    for i in range(size):
        current_arr = []
        for j in range(size):
            if i == j:
                current_arr.append(round(random() * 20 + 10, 2))
            else:
                current_arr.append(round(random(), 2))
        main_matrix.append(current_arr)
    for i in range(size):
        d_matrix.append(round(random() * 100, 2))
    entity.set_d_matrix(d_matrix)
    entity.set_main_matrix(main_matrix)
    print_system(main_matrix, d_matrix, size)
    
def print_system(main_matrix, d_matrix, size):
    print("Ваша система уравнений:")
    for i in range(size):
        for j in range(size):
            if main_matrix[i][j] > 0 and j != 0:
                print("+", end='')
            print(f'{main_matrix[i][j]}{bcolors.OKCYAN}X{j + 1}{bcolors.ENDC}', end='')
        print(f'={d_matrix[i]}')
