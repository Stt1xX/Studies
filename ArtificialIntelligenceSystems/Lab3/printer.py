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


def print_model_information(number, x_values_names, y_values_name, coef, weight_vector):
    print(f"------ Модель №{number} ------")
    print(f"Отражает зависимость {y_values_name} от {" ".join(x_values_names)}")
    print(f"Коэффициент детерминации: {coef}")
    print_weight_vector(weight_vector, x_values_names)
    print('----------------\n') 


def print_weight_vector(weight_vector, x_values_names):
    print("------ Коэффициенты ------")
    dictionary = dict(zip(["free_coefs"] + x_values_names, weight_vector))
    sorted_dict = dict(sorted(dictionary.items(), key=lambda item: abs(item[1]), reverse=True))
    print("Наименование признака : значение коэффициента для уравнения линейной регрессии")
    for key, value in sorted_dict.items():
        print(f"{key} : {value}")


def drow_hist(dataset, i):
    plt.subplot(3, 3, i)
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