class Equations_system:
    
    __d_matrix = None  # cвободные коэффициенты
    __main_matrix = None # коэффициенты при переменных
    __size = None # количество уравнений
    __accuracy = None # необходимая точность


    def __init__(self):
        pass
    
    def set_accuracy(self, accurecy):
        self.__accuracy = accurecy

    def set_d_matrix(self, d_matrix):
        self.__d_matrix = d_matrix

    def set_main_matrix(self, main_matrix):
        self.__main_matrix = main_matrix

    def set_size(self, size):
        self.__size = size

    def get_d_matrix(self):
        return self.__d_matrix
    
    def get_main_matrix(self):
        return self.__main_matrix
    
    def get_size(self):
        return self.__size
    
    def get_accuracy(self):
        return self.__accuracy