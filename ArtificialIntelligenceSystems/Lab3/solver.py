import numpy as np
from printer import print_model_information


FRACTION = 4 / 5

def solve_lin_reg(x_values, y_values):
    B = np.array(x_values).T
    Y = np.array(y_values)
    return np.linalg.inv(B.T @ B) @ B.T @ Y


def separate_data_inner(data):
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


def normalize_data_inner(study_data, test_data):
    ret_arr = list()
    for column in test_data:
        curr_arr = list()
        x_min = study_data.min()
        x_max = study_data.max()
        for x in column:
            curr_arr.append((x - x_min) / (x_max - x_min))
        ret_arr.append(curr_arr)
    return ret_arr


def normalize_data(data : tuple):
    return normalize_data_inner(data[0], data[0]), normalize_data_inner(data[0], data[1])


def separate_data(data, x_values_names, y_values_name):
    x_values = list([data[name] for name in x_values_names])
    y_values = list(data[y_values_name])
    study_data_x, test_data_x = normalize_data(separate_data_inner(x_values))
    study_data_x.insert(0, list(np.ones(len(study_data_x[0]))))
    test_data_x.insert(0, list(np.ones(len(test_data_x[0]))))
    study_data_y, test_data_y = separate_data_inner(y_values)
    return study_data_x, test_data_x, study_data_y, test_data_y

#main method
def solve_model(data, x_values_names, y_values_name, number):
    study_data_x, test_data_x, study_data_y, test_data_y = separate_data(data, x_values_names, y_values_name)
    weight_vector = solve_lin_reg(study_data_x, study_data_y)
    coef = find_determination_coef(test_data_x, test_data_y, weight_vector)
    print_model_information(number, x_values_names, y_values_name, coef, weight_vector)