import matplotlib.pyplot as plt

class GraphBuilder:
    x = [1, 2, 3, 4, 5]
    y = [25, 32, 34, 20, 25]

    def draw(self):
        plt.plot(self.x, self.y)
        plt.show()