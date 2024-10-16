from pyswip import Prolog

def start_dialog():
    while True:
        print("Привет! Меня зовут БОБ! Я - база знаний по игре League of Legends. Выбери одну из следующих опций:")
        print("a) Подобрать персонажа")
        print("b) Узнать информацию о персонаже")
        print("c) Посмотреть друзей и врагов выбранной территории")
        print("d) Выход")
        option = input().strip()
        if option == "a":
            find_character_manager()
        elif option == "b":
            find_info_character_manager()
        elif option == "c":
            find_info_territory_manager()
        elif option == "d":
            break
        else:
            print("Похоже я вас не понимаю, пожалуйста попробуйте еще раз")


def find_info_territory_manager():
    print("Вы выбрали опцию \"Посмотреть друзей и врагов выбранной территории\". Отлично! Введите наименование территории")
    name_of_territory = input()
    wars = [x['X'] for x in list(prolog.query(f'wars(X, {name_of_territory})'))]
    friends = [x['X'] for x in list(prolog.query(f'friends(X, {name_of_territory})'))]
    valid_name = list(prolog.query(f'homeland({name_of_territory}, X)'))
    if len(valid_name) > 0:
        print("Итак, я нашел некоторую информацию об этой территории:")
        print(f"Наименование территории: {name_of_territory}")
        print(f"Враждебные территории: {wars}")
        print(f"Союзные территории: {friends}")
    else:
        print("Извините я не нашел такой территории, вот, на всякий случай, список всех территорий, о которых я что-то знаю")
        all_lands =list(set(x['X'] for x in list(prolog.query("homeland(X, Y)"))))
        for i in range(1, len(all_lands) + 1):
            print(i, " : ", all_lands[i - 1])

def find_info_character_manager():
    print("Вы выбрали опцию \"Узнать информацию о песонаже\". Отлично! Введите имя персонажа, о котором хотите узнать")
    name_of_character = input()
    homeland = list(prolog.query(f'homeland(X, {name_of_character})'))
    role = list(prolog.query(f'role(X, {name_of_character})'))
    position = list(prolog.query(f'position(X, {name_of_character})'))
    enemies = list(prolog.query(f'enemies(X, {name_of_character}); enemies({name_of_character}, X)'))
    countrymans = list(prolog.query(f'countrymans(X, {name_of_character})'))
    if len(homeland) == 1:
        print("Итак, я нашел некоторую информацию об этом парнише:")
        print(f"Имя персонажа: {name_of_character}")
        print(f"Родина персонажа: {homeland[0]['X']}")
        print(f"Роль персонажа: {role[0]['X']}")
        print(f"Позиция персонажа: {position[0]['X']}")
        if len(enemies) > 0:
            print("Враги персонажа: ", [x['X'] for x in enemies])
        else:
            print("У персонажа отсутствуют враги")
        if len(countrymans) > 0:
            print("Соотечественники персонажа: ", [x['X'] for x in countrymans])
        else:
            print("У персонажа отсутствуют соотечественники")
    else:
        print("Извините, я не нашел такого персонажа, вот, на всякий случай, список всех песонажаей, про которых я что-то знаю:")
        all_chars = [x['X'] for x in list(prolog.query("character(X)"))]
        for i in range(1, len(all_chars) + 1):
            print(i, " : ", all_chars[i - 1])


def find_character_manager():
    while True:
        try:
            print("Вы выбрали опцию \"Подобрать персонажа\". Отлично! Сейчас я задам Вам ряд вопросов, на основе которых, приму решение о том, какой персонаж вам подходит больше всего")
            result_list = homeland_choise() + role_choise() + position_choise()
            result_dict = {1 : set(), 2 : set(), 3 : set()}
            for item in result_list:
                result_dict[result_list.count(item)].add(item)
            
            print("Итак, вот персонажи, которых я для Вас нашел:")
            print("Наилучшая синергия:", result_dict[3])
            print("Вы неплохо поладите:", result_dict[2])
            print("Будет тяжеловато освоиться:", result_dict[1])
            print("Остальных персонажей крайне не рекомендую. Удачи, боец!")
            break
        except Exception as unrecognized_input:
            print("Похоже я вас не понимаю, пожалуйста попробуйте еще раз")
            continue
    
    
def homeland_choise():
    print("Какое время года Вам нравится больше всего?")
    print("a) Зима")
    print("b) Весна")
    print("c) Лето")
    print("d) Осень")
    season = input().strip()
    if season == "a":
        season = "winter"
    elif season == "b":
        season = "spring"
    elif season == "c":
        season = "summer"
    elif season == "d":
        season = "autumn"
    else:
        raise Exception
    return [x['Y'] for x in list(prolog.query(f'homeland(X, Y), season({season}, X)'))]

def role_choise():
    print("Какая роль вам нравится больше всего?")
    print("a) Убийца")
    print("b) Танк")
    print("c) Маг")
    print("d) Стрелок")
    print("e) Воин")
    role = input().strip()
    if role == "a":
        role = "killer"
    elif role == "b":
        role = "tank"
    elif role == "c":
        role = "wizard"
    elif role == "d":
        role = "shooter"
    elif role == "e":
        role = "warrior"
    else:
        raise Exception
    return [x['Y'] for x in list(prolog.query(f'role({role}, Y)'))]

def position_choise():
    print("На какой позиции на карте вы бы хотили отыгрывать?")
    print("a) Топ")
    print("b) Керри")
    print("c) Саппорт")
    print("d) Лес")
    print("e) Мид")
    position = input().strip()
    if position == "a":
        position = "top"
    elif position == "b":
        position = "carry"
    elif position == "c":
        position = "support"
    elif position == "d":
        position = "jungle"
    elif position == "e":
        position = "mid"
    else:
        raise Exception
    return [x['Y'] for x in list(prolog.query(f'position({position}, Y)'))]

prolog = Prolog()
prolog.consult("Lab1/knowledgeBase.pl")
start_dialog()