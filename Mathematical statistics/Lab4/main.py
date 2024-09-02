import numpy as np
import pandas as pd
from scipy.stats import t, chi2
from sklearn.linear_model import LinearRegression
import matplotlib.pyplot as plt


data = pd.read_csv("cars93.csv")
dependent_var = np.array(data["Price"])
independent_vars = np.array([data["MPG.city"], data["Horsepower"], data["Fuel.tank.capacity"], np.ones(len(dependent_var))]).T

# Находим коэффициенты методом наименьших квадратов для линейной регрессии
coeffs = np.linalg.inv(independent_vars.T @ independent_vars) @ independent_vars.T @ dependent_var
print("Вектор параметров вычисленный самостоятельно:", coeffs)


# Готовая реализация
model = LinearRegression().fit(independent_vars, dependent_var)
print("Вектор параметров вычисленный при помощи библиотеки:", np.concatenate((model.coef_[:-1],  np.array([model.intercept_]))))
# print(model.score(independent_vars, dependent_var))
        

m = 3
n = len(data)
ss_tot = np.sum((dependent_var - np.mean(dependent_var))**2) # Общая сумма квадратов
ss_reg = np.sum((np.dot(coeffs, independent_vars.T) - np.mean(dependent_var))**2) # Объясненная сумма квадратов
ss_res = ss_tot - ss_reg # Сумма квадратов остатков регрессии
varince = ss_res / (n - m)
print("Коэффициент детерминации:", ss_reg / ss_tot)
print("Оценка дисперсии:", varince)

# Вычисление ковариантной матирцы 
cov_matrix = np.linalg.inv(independent_vars.T @ independent_vars)
standart_errors = np.sqrt(varince * np.diag(cov_matrix))

# Вычисление критических точек
alpha = 0.05
t_value = abs(t.ppf(alpha / 2, n - m))
print("Доверительные итнервалы для параметров модели:")

for i in range(len(coeffs)):
    print([coeffs[i] - standart_errors[i] * t_value, coeffs[i] + standart_errors[i] * t_value])

print("Доверительный интервал для остаточной дисперсии:")
print(varince * (n - m) / chi2.ppf(1 - alpha / 2, n - m), varince * (n - m) / chi2.ppf(alpha / 2, n - m))
