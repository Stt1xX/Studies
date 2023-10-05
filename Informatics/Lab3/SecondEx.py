# С помощью регулярного выражения найти в тексте все слова, в которых две гласные стоят подряд,
# а после этого слова идёт слово, в котором не больше 3 согласных.(Сделал для русского языка.)
import re


def F(n):
    for i in range(1, len(n)):
        if len(re.findall('[бвгджзклмйнрпстфхцчшщ]', n[i], flags=re.IGNORECASE)) < 4 and re.findall(
                r"\w*[аеёиоуыэюя]{2}\w*", n[i - 1], flags=re.IGNORECASE):
            print(n[i - 1])


print('2Часть Вариант №', 367511 % 6, sep='')
second_test1 = re.sub(r'\W', ' ', 'ааа . ,пвавывафыбю ю,,,,,юю...аыв').split()
second_test2 = re.sub(r'\W', ' ', 'Кривошеее существо гуляет по парку').split()
second_test3 = re.sub(r'\W', ' ', 'Петрида Йохтар').split()
second_test4 = re.sub(r'\W[-]', ' ', 'Здесь вспыыш ии-чудо машини').split()
second_test5 = re.sub(r'[.?,!]', ' ', 'Фамиилия,,,,,, м-моя Йохтур тр руубляяя').split()

print("Первый тест:")
F(second_test1)
print("\nВторой тест: ")
F(second_test2)
print("\nТретий тест: ")
F(second_test3)
print("\nЧетвертый тест: ")
F(second_test4)
print("\nПятый тест: ")
F(second_test5)

input()
print("Теперь, если вы хотите проверить программу своими примерами, сейчас самое время это сделать:")
while True:
    str = re.sub(r'\W', ' ', input()).split()
    F(str)
    print('Следующий тест:')
    if not str:
        break
