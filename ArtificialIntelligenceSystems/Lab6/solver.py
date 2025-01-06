import numpy as np
from sklearn.metrics import accuracy_score, precision_score, recall_score, f1_score

WEIGHTS = list()

def separate_data(data, coef):
    border = int(len(data) * coef)
    return data[:border], data[border:]

def sigmoid_func(z):
    return 1 / (1 + np.exp(-z))

def get_depend_independ(data):
    return np.array(data["Outcome"]), np.array(data.drop(columns=["Outcome"]))

def gradient_search(m, n, depend, features, learning_rate, iterations):
    weights = np.zeros(n + 1)
    for _ in range(iterations):
        z = np.dot(features, weights)
        p = sigmoid_func(z)
        gradient = 1 / m * np.dot(features.T, (p - depend))
        weights -= learning_rate * gradient
    return weights

def hessian_search(m, n, depend, features, learning_rate, iterations):
    weights = np.zeros(n + 1)
    for _ in range(iterations):
        predictions = sigmoid_func(np.dot(features, weights))
        diag = predictions * (1 - predictions)
        H = (1/m) * np.dot(features.T * diag, features)
        H_inv = np.linalg.inv(H)  # инвертирование матрицы Гессиана
        gradient = 1 / m * np.dot(features.T, (predictions - depend)) # градиент
        weights -= np.dot(H_inv, gradient)
    return weights

def normalize_features(features):
    ret_arr = []
    for column in features.T:
        curr_col = [(x - np.mean(column)) / np.std(column) for x in column]
        ret_arr.append(curr_col)
    return np.array(ret_arr).T

def prepare_data(data):
    depend, features = get_depend_independ(data)
    m, n = np.shape(features) # m - количество объектов, n - количество признаков
    features = normalize_features(features)
    features = np.hstack((np.ones((m, 1)), features)) # добавляем #1 столбец
    return m, n, depend, features

def train_model(data, learning_rate=0.1, iterations=1000, method=gradient_search):
    global WEIGHTS
    m, n, depend, features = prepare_data(data)
    WEIGHTS = method(m, n, depend, features, learning_rate, iterations)

def test_model(data):
    _, _, depend, features = prepare_data(data)
    find_metrics(features, depend)

def find_metrics(features, depend):
    predicted_values = (sigmoid_func(np.dot(features, WEIGHTS)) > 0.5).astype(int)
    # Истинно положительные (True Positives)
    TP = np.sum((depend == 1) & (predicted_values == 1))
    # Истинно отрицательные (True Negatives)
    TN = np.sum((depend == 0) & (predicted_values == 0))
    # Ложноположительные (False Positives)
    FP = np.sum((depend == 0) & (predicted_values == 1))
    # Ложные отрицательные (False Negatives)
    FN = np.sum((depend == 1) & (predicted_values == 0))
    # Accuracy
    accuracy = (TP + TN) / (TP + TN + FP + FN)
    # Precision
    precision = TP / (TP + FP) if (TP + FP) != 0 else 0  # избегаем деления на ноль
    # Recall
    recall = TP / (TP + FN) if (TP + FN) != 0 else 0  # избегаем деления на ноль
    # F1-Score
    f1_score = 2 * (precision * recall) / (precision + recall) if (precision + recall) != 0 else 0  # избегаем деления на ноль
    # Выводим результаты
    print(f"Accuracy: {accuracy:.4f}")
    print(f"Precision: {precision:.4f}")
    print(f"Recall: {recall:.4f}")
    print(f"F1-Score: {f1_score:.4f}")
