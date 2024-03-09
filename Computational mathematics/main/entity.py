class Equations_system:
    
    def __init__(self):
        pass

    def set_b_matrix(self, b_matrix):
        self.__b_matrix = b_matrix

    def set_main_matrix(self, main_matrix):
        self.__main_matrix = main_matrix

    def set_size(self, size):
        self.__size = size

    def get_b_matrix(self):
        return self.__b_matrix
    
    def get_main_matrix(self):
        return self.__main_matrix
    
    def get_size(self):
        return self.size