import numpy as np
import matplotlib.pyplot as plt


def print_gen_information(dataset):
    print(f'------ {dataset[0]} ------')
    print(f'Математическое ожидание: {np.mean(dataset[1])}')
    print(f'Стандартное отклонение: {np.std(dataset[1])}')
    print(f'Минимальное значение: {min(dataset[1])}')
    print(f'Максимальное значение: {max(dataset[1])}')
    print(f'25-й перцентиль: {np.quantile(dataset[1], 0.25)}')
    print(f'50-й перцентиль: {np.quantile(dataset[1], 0.50)}')
    print(f'75-й перцентиль: {np.quantile(dataset[1], 0.75)}')
    print('----------------\n')

def drow_hist(dataset, i):
    plt.subplot(4, 4, i)
    plt.hist(dataset[1], bins=30, color="skyblue", edgecolor="black")
    plt.xlabel("Значение")
    plt.ylabel("Частота")
    plt.title(dataset[0])

#main method
def get_first_look(data):
    i = 1
    for col in data.items():
        print_gen_information(col)
        drow_hist(col, i)
        i += 1
    plt.tight_layout()
    plt.show()


def print_comput_result(error_matrix, k_value, model_number, accuracy):
    print(f'------ Модель №{model_number}, k = {k_value} ------')
    print(f'Матрица ошибок:')
    for row in error_matrix:
        print(' '.join(map(str, row)))
    print(f'Общая точность: {accuracy}%')

def print_features(model_number, list_of_features, max_size_of_list):
    print(f'============= Модель №{model_number} =============')
    length = len(list_of_features)
    print(f'Кол-во признаков - {length}')
    print(f'Признаки модели:')
    if length == max_size_of_list:
        print("Модель состоит из всех возможных признаков!")
    else:
        print('\n'.join(list_of_features))
    print('=====================================')