#!/usr/bin/python
# -*- coding: UTF-8 -*- 

import requests #网络请求
from bs4 import BeautifulSoup #html代码处理
import re #正则表达式

RFC3339BeiJingTime = "T:00:00:000+0800"
chineseCities = None

def obtainBikeData(bikeSite, bikeName ,startFlag, endFlag, strategy):
    dataTags = obatinDataTags(bikeSite, startFlag, endFlag, strategy)
    if dataTags is not None:
        result = []
        time = ""
        city = ""
        for i in range(len(dataTags)):
            dataTag = dataTags[i]
            data = str(dataTags)
            if i == 0:
                if strategy == 0 : #ofo
                    timePosition = re.search(r"([0-9]{4,4})年[0-1]?[0-9]月([0-3]?[0-9]日)?", data).span()
                    if timePosition is not None:
                        time = data[timePosition[0] : timePosition[1]]
                else : #mobike
                    timePosition = re.search(r"([0-9]{4,4})年：[0-1]?[0-9]月([0-3]?[0-9]日)?", data).span()
                    if timePosition is not None:
                        time = data[timePosition[0] : timePosition[1]]
                        time = time.replace("：", "")
                #计算年月日并转换成格式化时间
                year = time[0 : 4]
                month = time[time.find("年") + 1 : time.find("月")]
                day = time[time.find("月") + 1 : len(time)]
                time = convertToFormatDate(year, month, day)
                #获取城市
                city = dataTag.a.string
                #确保存储的数据正确性
                if time is not None and isChineseCity(city):
                    result.append(bikeName + "," + city + "," + time)
            else:
                timePosition = re.search(r"([0-9]{4,4})年", data).span()
                time = data[timePosition[0] : timePosition[1]]
                year = time[0 : 4]
                aTags = dataTag.find_all("a")
                for j in range(len(aTags)):
                    a = aTags[j]
                    if a.parent is dataTag:
                        city = a.string
                        monthStart = data.find(city + "</a>") + len(city) + 4
                        monthWithDay = data[monthStart : monthStart + 10]
                        monthWithDay = monthWithDay.replace(" ","")
                        if monthWithDay[0 : 1] == "（":
                            monthWithDay = monthWithDay[1 : monthWithDay.find("）")]
                            month = monthWithDay[0 : monthWithDay.find("月")]
                            day = monthWithDay[monthWithDay.find("月") + 1 : monthWithDay.find("日")]
                            time = convertToFormatDate(year, month, day)
                            if time is not None and isChineseCity(city):
                                result.append(bikeName + "," + city + "," + time)
        for k in range(len(result)):
            print(result[k])
        return result
    else : 
        return None

#获取数据soup
def obatinDataTags(bikeSite ,startFlag, endFlag, strategy):
    request = requests.get(bikeSite)
    if request.status_code == 200:
        htmlText = request.text
        #创建BeautifulSoup,对hmtl进行处理
        soup = BeautifulSoup(htmlText, "html.parser")
        #查找数据相关的html代码
        startTags = soup.find_all("span", {"id" : re.compile(startFlag)})
        endTags = soup.find_all("span", {"id" : re.compile(endFlag)})
        if len(startTags) > 0 and len(endTags) > 0:
            startH2Tag = startTags[0].find_parent("h2")
            endH2Tag = endTags[0].find_parent("h2")
            if startH2Tag is not None and endH2Tag is not None:
                #获取数据代码的位置
                dataStart = htmlText.find(str(startH2Tag))
                dataStart += len(str(startH2Tag))
                dataEnd = htmlText.find(str(endH2Tag))
                dataSoup = BeautifulSoup(htmlText[dataStart : dataEnd], "html.parser")
                if strategy == 0 : #ofo
                    dataTags = dataSoup.find_all("p")
                    if len(dataTags) > 0 :
                        return dataTags
                    else :
                        print("empty data")
                        return None
                elif strategy == 1 : #mobike
                    dataTags = dataSoup.find_all("ul")
                    if len(dataTags) > 0:
                        dataTags = dataTags[0].find_all("li")
                        if len(dataTags) > 0:
                            return dataTags
                        else:
                            print("empty data")
                            return None
                    else :
                        print("empty data")
                        return None
                else :
                    print("wrong stragey")
                    return None
        print("process data failed")
        return None
    else:
        print("request failed")
        return None

# 转换时间(RFC3339)
def convertToFormatDate(year, month, day):
    if year is None or len(year) != 4:
        return None
    elif month is None :
        return None
    else :
        if len(month) == 1 :
            month = "0" + month
        if day is None or day == "" :
            day = "01"
        elif len(day) == 1 :
            day = "0" + day
        return year + "-" + month + "-" + day + RFC3339BeiJingTime

def isChineseCity(city):
    global chineseCities
    if chineseCities is None : 
        try :
            cityFile = open("city.json")
            lines = cityFile.readlines()
            result = ""
            for i in range(len(lines)):
                line = str(lines[i]).strip()
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
                except ValueError :
                    index = line.find(u"市")
                    if index != -1 and index == (len(line) - 1):
                        result += (line + ",")
            chineseCities = result
        except IOError :
            return False
    if chineseCities is None :
        return False
    else :
        return chineseCities.find(city) != -1
