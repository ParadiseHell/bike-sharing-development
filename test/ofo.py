#!/usr/bin/python
# -*- coding: UTF-8 -*- 

import requests
from bs4 import BeautifulSoup
import re

ofoSite="https://zh.wikipedia.org/zh-hans/Ofo%E5%B0%8F%E9%BB%84%E8%BD%A6"
ofoName="ofo小黄车"
rfc3339BeiJingTime="T:00:00:000+0800"

ofoRequest = requests.get(ofoSite)
if ofoRequest.status_code == 200:
    htmlText = ofoRequest.text
    soup = BeautifulSoup(htmlText,"html.parser")
    spanRegionTags = soup.find_all("span", {"id" : re.compile("运营地区")})
    spanTypeTags = soup.find_all("span", {"id" : re.compile("车型")})
    if len(spanRegionTags) > 0 and len(spanTypeTags) > 0:
        h2RegionTag = spanRegionTags[0].find_parent("h2")
        h2TypeTag = spanTypeTags[0].find_parent("h2")
        if spanRegionTags is not None and h2TypeTag is not None:
            contentStart = htmlText.find(str(h2RegionTag))
            contentStart += len(str(h2RegionTag))
            contentEnd = htmlText.find(str(h2TypeTag))
            #
            contentSoup = BeautifulSoup(htmlText[contentStart : contentEnd], "html.parser")
            pTags = contentSoup.find_all("p")
            result = []
            for i in range(len(pTags)):
                p = pTags[i]
                strP = str(p)
                if i == 0:
                    timePosition = re.search(r"([0-9]{4,4})年[0-1]?[0-9]月([0-3]?[0-9]日)?", strP).span()
                    if timePosition is not None:
                        time = strP[timePosition[0] : timePosition[1]]
                        year = time[0 : 4]
                        month = time[time.find("年") + 1 : time.find("月")]
                        if len(month) == 1:
                            month = "0" + month;
                        day = time[time.find("月") + 1 : len(time)]
                        if day == "":
                            day = "01"
                        time = year + "-" + month + "-" + day + rfc3339BeiJingTime
                        city = p.a.string
                        result.append(ofoName + "," + city + "," + time)
                else:
                    timePosition = re.search(r"([0-9]{4,4})年", strP).span()
                    time = strP[timePosition[0] : timePosition[1]]
                    year = time[0 : 4]
                    aTags = p.find_all("a")
                    for j in range(len(aTags)):
                        a = aTags[j]
                        if a.parent is p:
                            city = a.string
                            monthStart = strP.find(city + "</a>") + len(city) + 4
                            monthWithDay = strP[monthStart : monthStart + 10]
                            monthWithDay = monthWithDay.replace(" ","")
                            if monthWithDay[0 : 1] == "（":
                                monthWithDay = monthWithDay[1 : monthWithDay.find("）")]
                                month = monthWithDay[0 : monthWithDay.find("月")]
                                if len(month) == 1:
                                    month = "0" + month
                                day = monthWithDay[monthWithDay.find("月") + 1 : len(monthWithDay)]
                                if day == "":
                                    day = "01"
                                time = year + "-" + month + "-" + day + rfc3339BeiJingTime
                                result.append(ofoName + "," + city + "," + time)
            for k in range(len(result)):
                print(result[k])
        else:
            print("can not find expect content")
    else:
        print("can not find expect tag")
else:
    print("fail to surface ofo site")
