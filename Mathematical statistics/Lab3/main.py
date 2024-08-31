import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from scipy.stats import chi2, ttest_ind

data = pd.read_csv("exams_dataset.csv")
first_variable = data["math score"]
second_variable = data["writing score"]
alpha = 0.05

n = int(1 + np.log2(len(first_variable))) # по стерджессу

left = min(first_variable.min(),second_variable.min())
right = max(first_variable.max(),second_variable.max())

first_grouped, _ = np.histogram(first_variable, bins=n, range=(left, right))
second_grouped, _ = np.histogram(second_variable, bins=n, range=(left, right))

chi_value = len(first_variable) * len(second_variable) * np.sum(1 / (first_grouped + second_grouped) * (first_grouped / len(first_variable) - second_grouped / len(second_variable))**2)
p_value = 1 - chi2.cdf(chi_value, df = n - 1)

print("--- Метод, реализованный самостоятельно ---")
if p_value > 0.05:
    print("Принимаем гипотезу H0")
else:
    print("Отклоняем гипотезу H0")
print(f'Статистика Хи-квадрат: {chi_value }\nР-значение: {p_value}')


# Готовая реализация критерия Стьюдента
 
statistic, p_value1 = ttest_ind(a=first_variable, b=second_variable)

print("--- Готовая реализация метода ---")
if p_value1 > 0.05:
    print("Принимаем гипотезу H0")
else:
    print("Отклоняем гипотезу H0")
print(f'Статистика Стьюдента: {statistic }\nР-значение: { p_value1 }')

plt.plot(np.linspace(left, right, len(first_grouped)), first_grouped, label="Математика", color='c')
plt.plot(np.linspace(left, right, len(first_grouped)), second_grouped, label="Письменная часть", color='y')
plt.xlabel("Баллы")
plt.ylabel("Кол-во набравших")
plt.title("Распределение случайных величин")
plt.legend()
plt.show()

compute = lambda x, array : np.sum(x >= array) / len(array)
x_values = np.linspace(left, right, len(first_variable))
y_first = [compute(x, first_variable) for x in x_values]
y_second = [compute(x, second_variable) for x in x_values]

plt.plot(x_values, y_first, label="Математика", color='c')
plt.plot(x_values, y_second, label="Письменная часть", color='y')
plt.title("Сравнение эмперических фукнций распределения")
plt.xlabel("Значение")
plt.ylabel("Вероятность")
plt.legend()
plt.show()
