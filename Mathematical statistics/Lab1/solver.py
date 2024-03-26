from math import log2, ceil
import numpy as np

class Solver:

    numbers: list[float] = None

    __empirical_series = {}

    __heading: str = ""

    __statistical_series = {}   

    __normalized_statistical_series = {}

    __h = 0

    __normalized_h = 0

    def get_h(self):
        return self.__h
    
    def get_x_for_normal_dist(self):
        return self.__x_for_normal_dist 
    
    def get_y_for_normal_dist(self):
        return self.__y_for_normal_dist
    
    def get_normalized_h(self):
        return self.__normalized_h
    
    def get_heading(self):
        return self.__heading

    def get_statistical_series(self):
        return self.__statistical_series
    
    def get_normalized_statistical_series(self):
        return self.__normalized_statistical_series

    def get_empirical_series(self):
        return self.__empirical_series
    
    def find_median(self, selection): # медиана или квантиль 0.5
        selection_size = len(selection)
        if selection_size % 2 == 0:
            return (selection[selection_size // 2] + selection[selection_size // 2 - 1]) / 2
        return selection[selection_size // 2 - 1]
    
    def find_h(self, arr):
        h = (max(arr) - min(arr)) / (1 + log2(len(arr))) # Найндем длину промежутка по формуле Стрерджеса 
        h /= 2 # + делим интервал на 2 (выглядит круче)
        return h
    
    def find_interval_statistical_series(self, arr): # Интервальный статистический ряд  
        h = self.find_h(arr)
        current_start = min(arr)
        numbers = ceil((max(arr) - current_start) / h)
        current_end = current_start + h
        ret_arr = {}
        for i in range(numbers):
            frequency = 0
            for item in arr:
                if current_start <= item < current_end:
                    frequency += 1
            ret_arr[(current_start + current_end) / 2] = frequency / len(self.numbers) / h
            current_start = current_end
            current_end += h
        return ret_arr

    def solve(self, numbers, heading):
        self.__heading = heading
        self.__empirical_series = {}
        self.__statistical_series = {}
        self.__normalized_statistical_series = {}
        self.numbers = sorted(numbers)

        mean = np.mean(self.numbers)
        std = np.std(self.numbers)
        normalized_data = [(i - mean) / std for i in self.numbers]

        print(f"------ {heading} ------")
        print("Экстремальные значения:: Минимум:", min(self.numbers), "Максимум:", max(self.numbers))
        print("Размах:", round(max(self.numbers) - min(self.numbers), 3))
        print("Оценка математического ожидания:", round(mean, 5))
        print("Выборочная дисперсия:", round(std, 5))
        print("Медиана (квантиль 0.5):", round(self.find_median(self.numbers), 5))

        self.__normalized_statistical_series = self.find_interval_statistical_series(normalized_data)
        self.__normalized_h = self.find_h(normalized_data)
        self.__statistical_series = self.find_interval_statistical_series(self.numbers)
        self.__h = self.find_h(self.numbers)