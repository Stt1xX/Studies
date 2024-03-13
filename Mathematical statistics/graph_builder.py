import matplotlib.pyplot as plt

class GraphBuilder:
    empirical_series = {}
    statistical_series = {}
    
    def __init__(self, empirical_series, statistical_series) -> None:
        self.empirical_series = empirical_series
        self.statistical_series = statistical_series

    def drow_empirical_series(self):
        x_coord = []
        y_coord = []
        for item in self.empirical_series:
            x_coord.append(item[0])
            x_coord.append(item[1])
            y_coord.append(self.empirical_series[item])
            y_coord.append(self.empirical_series[item])
            plt.plot(x_coord, y_coord, color='c')

        plt.grid(True)
        plt.xlabel('x')
        plt.ylabel('F*(x)')
        plt.title('Эмпирический ряд')
        plt.show()

    def drow_histogram(self):
        centers = list()
        values = list()
        h = 0
        for item in self.statistical_series:
            centers.append((item[1] + item[0]) / 2)
            h = item[1] - item[0]
            values.append(self.statistical_series[item] / (item[1] - item[0]) / len(self.empirical_series))
        
        plt.bar(centers, values, edgecolor='k', width=h, fc=(0, 0, 1, 0.5))
        plt.plot(centers, values, color='red', alpha=0.75, marker='o', markersize=7)
        plt.grid(False)
        plt.xlabel('x')
        plt.ylabel('p/h')
        plt.title('Гистограмма и полигон частот')
        plt.show()