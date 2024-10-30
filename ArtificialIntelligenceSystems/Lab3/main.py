import pandas as pd
import numpy as np
import matplotlib.pyplot as plt

data = pd.read_csv("california_housing_train.csv")
FRACTION = 4 / 5


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
    plt.subplot(3, 3, i)
    plt.hist(dataset[1], bins=30, color="skyblue", edgecolor="black")
    plt.xlabel("Значение")
    plt.ylabel("Частота")
    plt.title(dataset[0])


def get_first_look():
    i = 1
    for col in data.items():
        print_gen_information(col)
        drow_hist(col, i)
        i += 1

    plt.tight_layout()
    plt.show()


def solve_lin_reg(x_values, y_values):
    B = np.array(x_values).T
    Y = np.array(y_values)
    if B.ndim == 1:
        return  [1 / B.T @ B] * B.T @ Y
    else:
        return np.linalg.inv(B.T @ B) @ B.T @ Y


def separate_data(data):
    data = np.array(data)
    if data.ndim == 2:
        quantile = FRACTION * len(data[0])
        return data[:, :int(quantile)], data[:, int(quantile):]
    else:
        quantile = FRACTION * len(data)
        return data[:int(quantile)], data[int(quantile):]


def find_determination_coef(x_values, y_true, weight_vector):
    y_pred = np.sum(np.array(x_values).T * weight_vector, axis=1)
    ss_res = np.sum((y_true - y_pred) ** 2)  # Сумма квадратов остатков
    ss_tot = np.sum((y_true - np.mean(y_true)) ** 2)  # Общая сумма квадратов
    return 1 - (ss_res / ss_tot)

def normalize_data(x_values):
    ret_arr = list()
    for column in x_values:
        curr_arr = list()
        x_min = column.min()
        x_max = column.max()
        for x in column:
            curr_arr.append((x - x_min) / (x_max - x_min))
        ret_arr.append(curr_arr)
    return ret_arr

def solve_model(x_values, y_values, number):
    x_values = normalize_data(list(x_values))
    y_values = list(y_values)
    x_values.append(list(np.ones(len(x_values[0]))))
    study_data_x, test_data_x = separate_data(x_values)
    study_data_y, test_data_y = separate_data(y_values)
    weight_vector = solve_lin_reg(study_data_x, study_data_y)
    coef = find_determination_coef(test_data_x, test_data_y, weight_vector)
    print(f"------ Модель №{number} ------")
    print(f"Коэффициент детерминации: {coef}")
    print(f"Вектор коэффициентов: {weight_vector}")
    print('----------------\n')



solve_model([data["total_rooms"]], data["total_bedrooms"], 1)
solve_model([data["longitude"], data["latitude"]], data["median_house_value"], 2)
solve_model([data["median_income"], data["housing_median_age"]], data["median_house_value"], 3)

get_first_look()

