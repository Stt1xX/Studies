import main

tags = main.parser()[0]
text = main.parser()[1]
strarr = []
columnset = set()
columnarr = []

def tablemaker():
    counter = 0
    for i in range(len(tags)):
        if tags[i][0] == '/':
            counter -= 1
        else:
            counter += 1
        if (counter == 1 or counter == 2) and tags[i][0] != '/' and tags[i + 1][0] != '/':
            strarr.append(tags[i])
        if counter == 3:
            columnset.add(tags[i])
    for i in strarr:
        if strarr.count(i) > 1:
            number = strarr.count(i)
            for j in range(strarr.count(i)):
                strarr[strarr.index(i)] += '(' + str(number - strarr.count(i) + 1) + ')'
    for c in columnset:
        columnarr.append(c)
        columnarr.sort()


def tablefiller():
    with open('timetableCSV.csv', 'w') as f1:
        f1.write(strarr[0])
        for i in columnarr:
            f1.write(', ' + i)
        for i in range(len(strarr) - 1):
            f1.write('\n' + strarr[i + 1] + ', ')
            for j in range(len(columnarr) - 1):
                f1.write('"' + main.dictmaker()[columnarr[j] + '(' + str(i + 1) + ')'] + '", ')
            f1.write('"' + main.dictmaker()[columnarr[len(columnarr) - 1] + '(' + str(i + 1) + ')'] + '"')



tablemaker()
tablefiller()