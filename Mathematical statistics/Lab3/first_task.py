import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy import stats


data = pd.read_csv("exams_dataset.csv")
random_variable = data["math score"] + data["reading score"] + data["writing score"]
alpha = 0.05

average_sample = np.mean(random_variable)
sd_sample = np.std(random_variable, ddof=1)

n = int(1 + np.log2(len(random_variable))) # по стерджессу
intervals = np.linspace(random_variable.min(), random_variable.max(), n + 1)
cumul_distr_func = stats.norm.cdf(intervals, loc=average_sample, scale=sd_sample)
theoretical_frequencies = (cumul_distr_func[1:] - cumul_distr_func[:-1]) * len(random_variable)
real_frequencies, _ = np.histogram(random_variable, bins=intervals, density=False)
chi_value = sum((real_frequencies - theoretical_frequencies)**2 /  theoretical_frequencies)
p_value = 1 - stats.chi2.cdf(chi_value, df = n - 1)


print("--- Метод, реализованный самостоятельно ---")
if p_value > 0.05:
    print("Принимаем гипотезу H0")
else:
    print("Отклоняем гипотезу H0")
print(f'Статистика Хи-квадрат: {chi_value }\nР-значение: {p_value}')

# Готовая реализаяция

statistic, p_value1, _, _ = stats.chi2_contingency([real_frequencies, theoretical_frequencies])

print("--- Готовая реализация метода ---")
if p_value1 > 0.05:
    print("Принимаем гипотезу H0")
else:
    print("Отклоняем гипотезу H0")
print(f'Статистика Хи-квадрат: {statistic }\nР-значение: { p_value1 }')

# График частот


x_values = np.linspace(random_variable.min(), random_variable.max(), len(real_frequencies))

plt.plot(x_values, real_frequencies, label="Функция наблюдаемых значений", color="c")
plt.plot(x_values, theoretical_frequencies, label="Теоретическая функция", color="y")
plt.title("График частот")
plt.xlabel("Значение")
plt.ylabel("Частота")
plt.legend()
plt.show()

# График эмперических функций распределения

x_values = np.linspace(random_variable.min(), random_variable.max(), len(random_variable))
compute = lambda x : np.sum(random_variable <= x) / len(random_variable)

real_values = [compute(x) for x in x_values]
theoretical_values = stats.norm.cdf(x_values, loc=average_sample, scale=sd_sample)

plt.plot(x_values, real_values, label="Функция наблюдаемых значений", color="c")
plt.plot(x_values, theoretical_values, label="Теоретическая функция", color="y")
plt.title("Сравнение эмперических фукнций распределения")
plt.xlabel("Значение")
plt.ylabel("Вероятность")
plt.legend()
plt.show()