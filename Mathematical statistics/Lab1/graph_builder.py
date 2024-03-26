import matplotlib.pyplot as plt
import numpy as np
import scipy.stats as stats

class GraphBuilder:

    def drow_histogram(self, heading, arr, h):
        centers = list()
        values = list()
        
        for item in arr:
            centers.append(item)
            values.append(arr[item]) # для вероятностей
        plt.bar(centers, values, edgecolor='k', width=h, fc=(0, 0, 1, 0.5))
        plt.grid(False)
        plt.xlabel('x')
        plt.ylabel('p')
        plt.title(heading)
        plt.show()

    def drow_empirical_series(self, heading, empirical_series):
        x_coord = []
        y_coord = []
        for item in empirical_series:
            x_coord.append(item[0])
            x_coord.append(item[1])
            y_coord.append(empirical_series[item])
            y_coord.append(empirical_series[item])
            plt.plot(x_coord, y_coord, color='c')

        plt.grid(True)
        plt.xlabel('x')
        plt.ylabel('F*(x)')
        plt.title(heading)
        plt.show()

    def drow_boxplot(self, heading, arr):
        plt.boxplot(arr)
        plt.title(heading)
        plt.show()

    def drow_normail_distribution(self):
        x = np.arange(-5, 5, 0.01)
        plt.plot(x, stats.norm.pdf(x), color="red")
        
    def drow_gamma_distribution(self, shape):
        x = np.arange(0.01, 10, 0.01)
        plt.plot(x, stats.gamma.pdf(x, shape), color="red")
        