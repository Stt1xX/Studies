import pandas as pd
from printer import get_first_look
from solver import solve_model


data = pd.read_csv("california_housing_train.csv")

get_first_look(data)
solve_model(data, ["total_rooms"], "total_bedrooms", 1)
solve_model(data, ["longitude", "latitude"], "median_house_value", 2)
solve_model(data, ["median_income", "housing_median_age"], "median_house_value", 3)
