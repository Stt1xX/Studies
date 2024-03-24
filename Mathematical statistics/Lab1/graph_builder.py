import matplotlib.pyplot as plt

class GraphBuilder:
    empirical_series = {}
    statistical_series = {}
    length = 0

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
            values.append(self.statistical_series[item] / (item[1] - item[0]) * self.length) # для абсолютных частот
            # values.append(self.statistical_series[item] / (item[1] - item[0])) # для относительных частот
        plt.bar(centers, values, edgecolor='k', width=h, fc=(0, 0, 1, 0.5))
        plt.grid(False)
        plt.xlabel('x')
        plt.ylabel('p/h')
        plt.title('Гистограмма относительных частот')
        plt.show()

    def drow_frequency_range(self):
        centers = list()
        values = list()
        for item in self.statistical_series:
            centers.append((item[1] + item[0]) / 2)
            values.append(self.statistical_series[item] * self.length / (item[1] - item[0]))   # для абсолютных частот
            # values.append(self.statistical_series[item] / (item[1] - item[0])) # для относительных частот
        plt.plot(centers, values, color='red', alpha=0.75, marker='o', markersize=7)
        plt.grid(False)
        plt.xlabel('x')
        plt.ylabel('p/h')
        plt.title('Полигон относительных частот')
        plt.show()

    def drow(self, empirical_series, statistical_series, length):
        self.empirical_series = empirical_series
        self.statistical_series = statistical_series
        self.length = length
        self.drow_empirical_series()
        self.drow_histogram()
        self.drow_frequency_range()