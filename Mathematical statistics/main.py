from scanner import FileManager
from solver import Solver
from graph_builder import GraphBuilder
from utils import *
import sys

# Файл содрежит выборкy из n элементов. Числа в выборке отделены друг от друга пробелами.
# Для отображения вещественных чисел использовать либо точку, либо запятую


my_manager = FileManager("test.txt")

task = my_manager.read()
if task == codes.ERROR_CODE:
    sys.exit()

solver = Solver(task)
solver.sovle()

graph_builder = GraphBuilder(solver.get_empirical_series(), solver.get_statistical_series())
graph_builder.drow_empirical_series()
graph_builder.drow_histogram()
