# # Вариант №13
# # Основное задание
import re


def parser():
    tags = []
    text = []
    with open('timetable.xml', encoding="utf-8") as f1:
        s = f1.read().replace('<?xml version="1.0" encoding="UTF-8" ?>', "")
    for i in range(len(s)):
        if s[i] == '<':
            str = ""
            j = i + 1
            while s[j] != '>':
                str += s[j]
                j += 1
            tags.append(str)

    tags.append('/')  # Добавляем элемент для нормальной работы цикла
    for i in range(1, len(tags)):
        if tags[i] == '/' + tags[i - 1]:
            text.append(s[s.find(tags[i - 1]) + len(tags[i - 1]) + 1:s.find(tags[i]) - 1])
        s = s.replace(tags[i - 1], '', 1)
    # Экранирование
    for c in text:
        c.replace('&quot;', '"')
        c.replace("&apos;", '"')
        c.replace("&lt;", "<")
        c.replace("&gt;", ">")
        c.replace("&amp;", "&")
    # Экранирование
    return [tags, text]


def printer():
    j = 0
    flag = 1
    with open('timetableMain.json', 'w') as f1_2:
        f1_2.write('{\n')
        for i in range(len(parser()[0]) - 1):
            if parser()[0][i][0] != '/':
                if '/' + parser()[0][i] == parser()[0][i + 1]:
                    f1_2.write(flag * '\t' + '"' + parser()[0][i] + '": "' + parser()[1][j] + '"')
                    j += 1
                else:
                    f1_2.write(flag * '\t' + '"' + parser()[0][i] + '": {\n')
                    flag += 1
            else:
                if '/' + parser()[0][i - 1] == parser()[0][i]:
                    if parser()[0][i + 1][0] != '/':
                        f1_2.write(',')
                    f1_2.write('\n')
                else:
                    flag -= 1
                    f1_2.write(flag * '\t' + '}')
                    if parser()[0][i + 1][0] != '/':
                        f1_2.write(',')
                    f1_2.write('\n')
        f1_2.write('}')


def dictmaker():
    dictm = dict()
    tags = parser()[0]
    text = parser()[1]
    j = 0
    for i in range(len(tags) - 1):
        if '/' + tags[i] == tags[i + 1]:
            if tags.count(tags[i]) == 1:
                dictm[tags[i]] = text[j]
            else:
                indexes = [k for k in range(0, len(tags)) if tags[k]==tags[i]]
                dictm[tags[i] + '(' + str(indexes.index(i) + 1) + ')'] = text[j]
            j += 1
    return dictm

#
# print(parser()[0])
# print(parser()[1])
# print(dictmaker())


# s = re.split('<|>', s)
# ex = []
# for i in range(len(s)):
#     x = 0
#     while x < 100:
#         if x * " " == s[i]:
#             ex.append(s[i])
#         x += 1
# for i in s:
#     for j in ex:
#         if i == j:
#             s.remove(j)
# print(s)
#
# for i in range(len(s) - 1):
#     dict[s[i]] = s[i:s.index('/' + s[i])]

