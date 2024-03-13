from math import log2, ceil

class Solver:

    numbers: list[float] = None

    dict_numbers: dict[str, float] = {}

    __empirical_series = {}

    __statistical_series = {}

    def get_statistical_series(self):
        return self.__statistical_series

    def get_empirical_series(self):
        return self.__empirical_series

    def __init__(self, numbers: list[float]):
        self.numbers = sorted(numbers)
        for i in self.numbers:
            if i in self.dict_numbers:
                self.dict_numbers[i] += 1
            else:
                self.dict_numbers[i] = 1

    def find_variation_series(self):  # Вариационный ряд
        return self.numbers
    
    def find_min(self):
        return min(self.numbers)
    
    def find_max(self):
        return max(self.numbers)
    
    def find_mathematical_expectation(self): # Мат ожидание
        ret_val: float = 0
        for i in self.dict_numbers:
            ret_val += i * self.dict_numbers[i]
        return ret_val / len(self.numbers)
    
    def find_variance(self):  # Выборочная дисперсия
        ret_val: float = 0
        math_exp: float = self.find_mathematical_expectation()
        for i in self.dict_numbers:
            ret_val += (pow(i - math_exp, 2) * self.dict_numbers[i])
        return ret_val / len(self.numbers)

    def find_variance_corrected(self): # Среднеквадратичное
        return self.find_variance() * len(self.numbers) / (len(self.numbers) - 1)
    
    def find_empirical_series(self): # Эмпиричейчкий ряд
        current_probability : float = 0
        previous =  float("-inf")
        for i in self.dict_numbers:
            if previous == float("-inf"):
                self.__empirical_series[(i - 3, i)] = current_probability
            else:
                self.__empirical_series[(previous, i)] = current_probability
            print(f"{previous} < x <= {i}: {round(current_probability, 2)}")
            current_probability += self.dict_numbers[i] / len(self.numbers)
            previous = i
        print(f"{previous} < x <= {float("inf")}: {round(current_probability, 2)}")
        self.__empirical_series[(previous, previous + 3)] = current_probability

    def find_interval_statistical_series(self): # Интервальный статистический ряд  
        h = (self.find_max() - self.find_min()) / (1 + log2(len(self.numbers))) # Найндем длину промежутка по формуле Стрерджеса 
        begin = self.find_min() - h / 2
        numbers = ceil((self.find_max() - self.find_min()) / h)
        current_start = begin
        current_end = current_start + h
        for i in range(numbers):
            frequency = 0
            for item in self.dict_numbers:
                if current_start <= item < current_end:
                    frequency += self.dict_numbers[item]
            print(f"[{round(current_end - h, 2)}  {round(current_end, 2)}) : {frequency / len(self.numbers)}")
            self.__statistical_series[(current_end - h, current_end)] = frequency / len(self.numbers)
            current_start = current_end
            current_end += h

    def get_plug(self):
        return "------------------------------------------------------"

    def sovle(self):
        print(self.get_plug())
        print("Вариационный ряд: ", self.find_variation_series())
        print("Экстремальные значения:: Минимум:", self.find_min(), "Максимум:", self.find_max())
        print("Размах:", self.find_max() - self.find_min())
        print(self.get_plug())
        print("Оценка математического ожидания:", self.find_mathematical_expectation())
        print("Выборочная дисперсия:", self.find_variance())
        print("Выборочное среднеквадратичное отлонение:", pow(self.find_variance(), 1/2))
        print("Исправленная выборочная дисперсия:", self.find_variance_corrected()) # Оценка для дисперсии
        print("Исправленное выборочное среднеквадратичное отлонение:", pow(self.find_variance_corrected(), 1/2)) # Оценка для дисперсии
        print(self.get_plug())
        print("Эмпирическая фнкция:")
        self.find_empirical_series()
        print(self.get_plug())
        print("Интервальный статистический ряд:")
        self.find_interval_statistical_series()
        print(self.get_plug())