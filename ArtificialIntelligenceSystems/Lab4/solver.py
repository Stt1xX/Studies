import random
import numpy as np
from math import sqrt

from printer import print_comput_result, print_features

TEST_TRAINING_BORDER = 0.7
K_VALUES = [3, 5, 7, 10]

# don't touch!
MODEL_NUMBERS = {1, 2}
CLASS_NAME = "Wine"
FEATURES = "features"
DEPENDENCE = "dependence"

def separate_data(data):
    border_index = int(len(data) * TEST_TRAINING_BORDER)
    return data[:border_index], data[border_index:]

def normalize_list(min_val, max_val, data):
    res_list = []
    for i in data:
        res_list.append((i - min_val) / (max_val - min_val))
    return res_list

def normalize_data(data, features):
    for col_name in features:
        train_data, _ = separate_data(data[col_name])
        curr_min, curr_max = (min(train_data), max(train_data))
        data[col_name] = normalize_list(curr_min, curr_max, data[col_name])


def get_sets_of_features(data, model_number):
    features = list(data.columns)
    features.remove(CLASS_NAME)
    if model_number == 1:
        return get_random_features_set(features)
    if model_number == 2:
        return features
    raise("Incorrect model's number")

def get_random_features_set(features):
    ret_list = []
    for i in range(random.randint(2, len(features) - 1)):
        val = features[random.randint(0, len(features) - 1)]
        ret_list.append(val)
        features.remove(val)
    return ret_list

def find_metric(coords1, coords2):
    ret_val = 0
    for keys in coords1:
        ret_val += (coords1[keys] - coords2[keys])**2
    return sqrt(ret_val)

def get_result_val(list_of_neighbours):
    res_dict = {}
    for point in list_of_neighbours:
        curr_val = point[0][DEPENDENCE]
        if curr_val in res_dict:
            res_dict[curr_val] += 1
        else:
            res_dict[curr_val] = 1
    max_count = -1
    val = 0
    for key in res_dict:
        if res_dict[key] > max_count:
            max_count = res_dict[key]
            val = key
    return val

def k_nn_method(points, point_coords, k_value):
    point_list = []
    for point in points:
        point_list.append((point, find_metric(point_coords[FEATURES], point[FEATURES])))
    sorter_list = sorted(point_list, key=lambda x: x[1])[:k_value]
    return get_result_val(sorter_list)

def get_list_of_values(data):
    ret_list = []
    for _, row in data.iterrows():  
        curr_row = dict(row)
        class_name = curr_row.pop(CLASS_NAME)
        ret_list.append({ FEATURES : curr_row, DEPENDENCE : class_name})
    return ret_list

def prepare_data(data, model_number):
    features = get_sets_of_features(data, model_number)
    normalize_data(data, features)
    main_list = get_list_of_values(data)
    train_values, test_values = separate_data(main_list)
    return train_values, test_values, features

def solve_data(test_values, train_values, k_value):
    true_values = []
    predicted_values = []
    for test_value in test_values:
        true_values.append(int(test_value[DEPENDENCE]))
        predicted_values.append(int(k_nn_method(train_values, test_value, k_value)))
    return true_values, predicted_values

def get_number_of_classes(data):
    my_set = set()
    my_set.update(list(data[CLASS_NAME]))
    return len(my_set)

def find_error_matrix(true_values, predicted_values, number_of_classes):
    ret_list = np.zeros((number_of_classes, number_of_classes), dtype=int)
    for i in range(len(true_values)):
        ret_list[true_values[i] - 1][predicted_values[i] - 1] += 1
    return ret_list

def find_accuracy(error_matrix, size):
    true_val = 0
    for i in range(len(error_matrix)):
        true_val += error_matrix[i][i]
    return int(true_val / size * 100)

# main method
def solve(data):
    n = get_number_of_classes(data)
    for model_number in MODEL_NUMBERS:
        train_values, test_values, feateures = prepare_data(data, model_number)
        print_features(model_number, feateures, data.shape[1] - 1)
        for k_value in K_VALUES:
            true_values, predicted_values = solve_data(test_values, train_values, k_value)
            error_matrix = find_error_matrix(true_values, predicted_values, n)
            accuracy = find_accuracy(error_matrix, len(true_values))
            print_comput_result(error_matrix, k_value, model_number, accuracy)