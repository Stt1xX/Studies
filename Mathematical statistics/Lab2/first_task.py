import numpy as np
from scipy.stats import f

alpha = 0.05
N = 1000
mean1 = mean2 = 0
var1 = 2
var2 = 1
sd1 = np.sqrt(var1)
sd2 = np.sqrt(var2)
tau = var1 / var2

def count(size):
    n1 = n2 = size

    selection1 = np.random.normal(mean1, sd1, (N, n1))
    selection2 = np.random.normal(mean2, sd2, (N, n2))

    numerator = n2 * np.sum(((selection1 - mean1)**2), axis=1)
    denominator = n1 * np.sum((selection2 - mean2)**2, axis=1)
    fraction = numerator / denominator

    left = fraction / f.ppf(1 - alpha / 2, n1, n2)
    right = fraction / f.ppf(alpha / 2, n1, n2)

    counter = 0

    for x in (left <= tau) & (right >= tau):
        if x == True:
            counter += 1
    
    return counter / N, (right - left).mean()

first_exp = count(25)
second_exp = count(10000)

print(f"--- Первый эксперимент(N = 25) ---\nВероятность покрытия - { first_exp[0] }\nСредняя длина доверительных интервалов - { first_exp[1] }\n")
print(f"--- Второй эксперимент(N = 10000) ---\nВероятность покрытия - { second_exp[0] }\nСредняя длина доверительных интервалов - { second_exp[1] }\n")
    

