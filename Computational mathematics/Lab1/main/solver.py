from utils import simple_iterations_codes, bcolors

class Solver:
    def simple_iterations(self, entity):
        print("Проверяю достаточные условия...")
        if self.changing_the_order(entity) != simple_iterations_codes.OK:
            print(f"{bcolors.FAIL}Достаточные условия для уравнения не выполняются! Я не могу его решить :({bcolors.ENDC}")
            return
        else:
            print(f"{bcolors.OKGREEN}Достаточные условия для уравнения выполняются! Решаю...{bcolors.ENDC}")
            self.expressing_coefficients(entity)
            
            if self.doing_iterations(entity) == simple_iterations_codes.OK:
                print(f"{bcolors.OKGREEN}Система успешно решена!{bcolors.ENDC}")

    def changing_the_order(self, entity):
        control_flag = False
        main_map = dict()
        d_map = dict()
        entity_main_matrix = entity.get_main_matrix()
        entity_d_matrix = entity.get_d_matrix()
        for i in entity_main_matrix:
            for j in range(len(i)):
                if i[j] < 0:
                    i[j] = abs(i[j])
        for i in range(0, len(entity_main_matrix)):
            max_item = entity_main_matrix[i].index(max(entity_main_matrix[i])) + 1
            main_map[max_item] = entity_main_matrix[i]
            d_map[max_item] = entity_d_matrix[i]
        if (len(main_map) < len(entity_main_matrix)):
            print(1)
            return simple_iterations_codes.INVALID_EQUATION
        for i in main_map:
            max_item = max(main_map[i])
            if sum(main_map[i]) - max_item > max_item:
                print(2)
                return simple_iterations_codes.INVALID_EQUATION
            elif max_item > sum(main_map[i]) - max_item:
                control_flag = True
        if control_flag == False:
            print(3)
            return simple_iterations_codes.INVALID_EQUATION
        entity.set_main_matrix([i[1] for i in sorted(main_map.items())])
        entity.set_d_matrix([i[1] for i in sorted(d_map.items())])
        return simple_iterations_codes.OK
    
    def expressing_coefficients(self, entity):
        new_main_arr = list()
        new_d_arr = list()
        old_main_arr = entity.get_main_matrix()
        old_d_arr = entity.get_d_matrix()
        for i in range (1, len(old_main_arr) + 1):
            current_arr = []
            for j in range(1, len(old_main_arr[i - 1]) + 1):
                if (i == j):
                    current_arr.append(0)
                else:
                    current_arr.append(old_main_arr[i - 1][j - 1] / -old_main_arr[i - 1][i - 1])                  
            new_main_arr.append(current_arr)
            new_d_arr.append(old_d_arr[i - 1] / old_main_arr[i - 1][i - 1])
        entity.set_d_matrix(new_d_arr)
        entity.set_main_matrix(new_main_arr)
    
    def doing_iterations(self, entity):
        current_vector = entity.get_d_matrix()
        counter = 1
        while True:
            if counter > 1000:
                print(f'{bcolors.FAIL}Слишком много итераций. Что-то с вашим уравнением не так...{bcolors.ENDC}')
                return simple_iterations_codes.TOO_LARGE_COUNT_OF_ITERATIONS
            counter += 1
            new_vector = []
            for i in range(len(entity.get_main_matrix())):
                current_coord = 0
                for j in range(len(entity.get_main_matrix()[i])):
                    current_coord += entity.get_main_matrix()[i][j] * current_vector[j]
                current_coord += entity.get_d_matrix()[i]
                new_vector.append(current_coord)
            # Определим максимальное отклонение
            variance = -100000
            for i in range(len(current_vector)):
                if variance < abs(current_vector[i] - new_vector[i]):
                    variance = abs(current_vector[i] - new_vector[i])
            if variance <= entity.get_accuracy():
                error_vector = []
                for i in range(len(current_vector)):
                    error_vector.append(abs(current_vector[i] - new_vector[i]))

                print(f'Количество итераций: {bcolors.OKCYAN}{counter}{bcolors.ENDC}')
                print(f'Вектор погрешностей: {bcolors.OKCYAN}({str(error_vector)[1:-1]}){bcolors.ENDC}')
                print(f'Решение: {bcolors.OKCYAN}({str(new_vector)[1:-1]}){bcolors.ENDC}')
                return simple_iterations_codes.OK
            current_vector = new_vector