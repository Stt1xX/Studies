# С помощью регулярного выражения найти в тексте слова, в которых встречается строго одна гласная буква
# (встречаться она может несколько раз). Пример таких слов: окно, трава, молоко, etc.
# После чего данные слова требуется отсортировать по увеличению длины слова.
import re


def F(n):
    str = []
    for i in range(len(n)):
        match = re.search('[аеёиоуыэюя]', n[i], flags=re.IGNORECASE)
        flag = 0
        if match:
            for j in re.findall('[аеёиоуыэюя]', n[i], flags=re.IGNORECASE):
                if j != match[0]:
                    flag = 1
                    break
            if flag == 0:
                str.append(n[i])
    str_new = sorted(str, key=len)
    for c in str_new:
        print(c)


print('2Часть Вариант №', 367511 % 5, sep='')
third_test1 = re.sub('\W', ' ',
                     'Классное слово – обороноспособность, которое должно идти после слов: трава ии-иии молоко.').split()
third_test2 = re.sub('\W', ' ', 'Повар спрашивает повара').split()
third_test3 = re.sub('\W', ' ', 'А Какаа у тебЕ профессия, ПоППо').split()
third_test4 = re.sub('\W', ' ', 'Мы летим на АаАаАаААААааааАА О').split()
third_test5 = re.sub('\W', ' ', 'sfsАААааоО ывоаыва аывадфвыоа пририди').split()
print('\nРезультаты первого теста:')
F(third_test1)
print('\nРезультаты второго теста:')
F(third_test2)
print('\nРезультаты третьего теста:')
F(third_test3)
print('\nРезультаты четвертого теста:')
F(third_test4)
print('\nРезультаты пятого теста:')
F(third_test5)

input()
print('Сейчас вы можете протестировать меня, введя свои предложения для проверки:')
while 1:
    str_A = re.sub('\W', ' ', input()).split()
    print("Результаты теста таковы:")
    F(str_A)
    if str_A == []:
        break
