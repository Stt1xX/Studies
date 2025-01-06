import pandas as pd
from printer import get_first_look
from solver import separate_data, train_model, test_model,hessian_search, gradient_search

SEP_COEF = 0.8
LEARNING_RATE = 0.1
ITERATIONS = 1000
data = pd.read_csv("data/diabetes.csv")
get_first_look(data)
train_data, test_data = separate_data(data, SEP_COEF)
train_model(train_data, learning_rate=LEARNING_RATE, iterations=ITERATIONS, method=gradient_search)
test_model(test_data)