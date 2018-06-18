#!/usr/bin/python
# -*- coding: UTF-8 -*- 

cityFile = open("city.json")
lines = cityFile.readlines()
result = ""
for i in range(len(lines)):
    line = lines[i].decode("utf8").strip()
    line = line.replace("\n", "")
    line = line.replace("\r", "")
    line = line.replace("\"", "")
    line = line.replace(":", "")
    line = line.replace(" ", "")
    line = line.replace("name", "")
    line = line.replace("code", "")
    line = line.replace("district", "")
    line = line.replace("[", "")
    line = line.replace("]", "")
    line = line.replace("{", "")
    line = line.replace("}", "")
    line = line.replace(",", "")
    try : 
        int(line)
    except ValueError:
        index = line.find(u"市")
        if index != -1 and index == (len(line) - 1):
            result += (line + ",")
print(result)
