from scipy.stats import expon
import random

A1_value = 42.348805	
A2_value = 1.341817457
q_border = 0.6 # вероятность формирования сл. величины  в первой фазе
VOLUME = 300


def generate_values():
    result_arr = []
    for i in range(VOLUME + 1):
        if random.random() >= q_border:
            result_arr.append(expon.rvs(scale=A2_value, loc=0))
        else:
            result_arr.append(expon.rvs(scale=A1_value, loc=0))
    return result_arr

