import numpy as np
from scipy.stats import norm

alpha = 0.05
p = 0.7
N = 1000

def count(size):
    selection = np.random.geometric(p, (N, size))
    sample_average = np.mean(selection, axis=1)
    se = np.std(selection, axis=1, ddof=1) / np.sqrt(size)
    left = 1 / (sample_average + norm.ppf(1 - alpha / 2, 0, 1) * se)
    right = 1 / (sample_average - norm.ppf(1 - alpha / 2, 0, 1) * se)

    counter = 0

    for x in (left <= p) & (p <= right):
        if x == True:
            counter += 1

    return counter / N, np.mean(right - left)


first_exp = count(25)
second_exp = count(10000)

print(f"--- Первый эксперимент(N = 25) ---\nВероятность покрытия - { first_exp[0] }\nСредняя длина доверительных интервалов - { first_exp[1] }\n")
print(f"--- Второй эксперимент(N = 10000) ---\nВероятность покрытия - { second_exp[0] }\nСредняя длина доверительных интервалов - { second_exp[1] }\n")

