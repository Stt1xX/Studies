import xmltodict, json


def mainLib():
    with open('timetable.xml', encoding="utf-8") as f1:
        s = xmltodict.parse(f1.read())

    with open('timetableLib.json', 'w') as f2:
        f2.write(json.dumps(s, ensure_ascii=False))


mainLib()
