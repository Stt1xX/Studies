import re


def mainRegular():
    with open('timetable.xml', encoding="utf-8") as f1:
        s = f1.read()
    tags = re.findall(r'(?<=<).*?(?=>)', s)
    text = re.findall(r'(?<=>).*(?=<)', s)

    match = re.search(r'(?<=<).*?(?=>)', s)
    if tags[0] == match[0]:
        tags.remove(tags[0])
    tags.append('/')  # Добавляем элемент для нормальной работы цикла
    flag = 1
    j = 0

    # Экранирование
    for c in text:
        c.replace('&quot;', '"')
        c.replace("&apos;", '"')
        c.replace("&lt;", "<")
        c.replace("&gt;", ">")
        c.replace("&amp;", "&")
    # Экранирование

    with open('timetableRegular.json', 'w') as f1_2:

        f1_2.write('{\n')
        for i in range(len(tags) - 1):
            if tags[i][0] != '/':
                if '/' + tags[i] == tags[i + 1]:
                    f1_2.write(flag * '\t' + '"' + tags[i] + '": "' + text[j] + '"')
                    j += 1
                else:
                    f1_2.write(flag * '\t' + '"' + tags[i] + '": {\n')
                    flag += 1
            else:
                if '/' + tags[i - 1] == tags[i]:
                    if tags[i + 1][0] != '/':
                        f1_2.write(',')
                    f1_2.write('\n')
                else:
                    flag -= 1
                    f1_2.write(flag * '\t' + '}')
                    if tags[i + 1][0] != '/':
                        f1_2.write(',')
                    f1_2.write('\n')
        f1_2.write('}')


mainRegular()