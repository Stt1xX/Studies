import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import chi2, chi2_contingency

data = pd.read_csv("exams_dataset.csv")
score = data["math score"] + data["reading score"] + data["writing score"]
test_preparation_course = [False if x == "none" else True for x in data["test preparation course"]]
alpha = 0.05

left = score.min()
right = score.max()

n = int(1 + np.log2(len(score))) # по стерджессу

with_course = np.array(0)
no_course = np.array(0)

for i in range(len(score)):
    if test_preparation_course[i]:
        with_course = np.append(with_course, score[i])
    else:
        no_course = np.append(no_course, score[i])
matrix = np.array([np.histogram(with_course, bins=n, range=(left, right))[0], np.histogram(no_course, bins=n, range=(left, right))[0]])

intermediate_sum = 0
for i in range(n):
    for j in range(2):
        intermediate_sum += matrix[j][i]**2 / (np.sum(matrix, axis=1)[j]) / (np.sum(matrix, axis=0)[i])

chi_value = len(score) * (intermediate_sum - 1)
p_value = 1 - chi2.cdf(chi_value, df = n - 1)

print("--- Метод, реализованный самостоятельно ---")
if p_value > 0.05:
    print("Принимаем гипотезу H0")
else:
    print("Отклоняем гипотезу H0")
print(f'Статистика Хи-квадрат: {chi_value }\nР-значение: {p_value}')

# Готовая реализаяция

statistic, p_value1, _, _ = chi2_contingency(matrix)

print("--- Готовая реализация метода ---")
if p_value1 > 0.05:
    print("Принимаем гипотезу H0")
else:
    print("Отклоняем гипотезу H0")
print(f'Статистика Хи-квадрат: {statistic }\nР-значение: { p_value1 }')

x_values = np.linspace(left, right, len(matrix[0]))
plt.plot(x_values, matrix[0], label="С дополнительными курсами", color="c")
plt.plot(x_values, matrix[1], label="Без дополнительных курсов", color="y")
plt.xlabel("Баллы")
plt.ylabel("Кол-во набравших")
plt.title("Распределение случайных величин")
plt.legend()
plt.show()



