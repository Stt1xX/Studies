import pandas as pd

from solver import solve
from printer import get_first_look

data = pd.read_csv("WineDataset.csv").sample(frac=1).reset_index(drop=True)
get_first_look(data)
solve(data)