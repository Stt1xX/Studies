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

    def drow_normail_distribution(self):
        x = np.arange(-5, 5, 0.01)
        plt.plot(x, stats.norm.pdf(x), color="red")
        
    def drow_gamma_distribution(self, shape):
        x = np.arange(0.01, 10, 0.01)
        plt.plot(x, stats.gamma.pdf(x, shape), color="red")

    # def drow(self, empirical_series, normolized_statistical_series, statistical_series, heading, h, normalized_h):
    #     self.drow_histogram(heading, statistical_series, h)
    #     # self.drow_histogram(heading, normolized_statistical_series, normalized_h)
    #     self.drow_normail_distribution()
    #     # self.drow_gamma_distribution(2)
    #     self.drow_gamma_distribution(1)
    #     plt.show()
        