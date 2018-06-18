#!/usr/bin/python
# -*- coding: UTF-8 -*- 

import requests
from bs4 import BeautifulSoup
import re

mobikeSite="https://zh.wikipedia.org/wiki/%E6%91%A9%E6%8B%9C%E5%8D%95%E8%BD%A6"
mobikeName="摩拜单车"
rfc3339BeiJingTime="T:00:00:000+0800"

ofoRequest = requests.get(mobikeSite)
if ofoRequest.status_code == 200:
    htmlText = ofoRequest.text
    soup = BeautifulSoup(htmlText,"html.parser")
    spanRegionTags = soup.find_all("span", {"id" : re.compile("运营城市")})
    spanTypeTags = soup.find_all("span", {"id" : re.compile("车种")})
    if len(spanRegionTags) > 0 and len(spanTypeTags) > 0:
        h2RegionTag = spanRegionTags[0].find_parent("h2")
        h2TypeTag = spanTypeTags[0].find_parent("h2")
        if spanRegionTags is not None and h2TypeTag is not None:
            contentStart = htmlText.find(str(h2RegionTag))
            contentStart += len(str(h2RegionTag))
            contentEnd = htmlText.find(str(h2TypeTag))
            #
            contentSoup = BeautifulSoup(htmlText[contentStart : contentEnd], "html.parser")
            liTags = contentSoup.find_all("ul")[0].find_all("li")
            result = []
            for i in range(len(liTags)):
                li = liTags[i]
                strLi = str(li)
                if i == 0:
                    timePosition = re.search(r"([0-9]{4,4})年：[0-1]?[0-9]月([0-3]?[0-9]日)?", strLi).span()
                    if timePosition is not None:
                        time = strLi[timePosition[0] : timePosition[1]]
                        time = time.replace("：", "")
                        year = time[0 : 4]
                        month = time[time.find("年") + 1 : time.find("月")]
                        if len(month) == 1:
                            month = "0" + month;
                        day = time[time.find("月") + 1 : len(time)]
                        if day == "":
                            day = "01"
                        elif len(day) == 1:
                            day = "0" + day
                        time = year + "-" + month + "-" + day + rfc3339BeiJingTime
                        city = li.a.string
                        result.append(mobikeName + "," + city + "," + time)
                else:
                    timePosition = re.search(r"([0-9]{4,4})年", strLi).span()
                    time = strLi[timePosition[0] : timePosition[1]]
                    year = time[0 : 4]
                    aTags = li.find_all("a")
                    for j in range(len(aTags)):
                        a = aTags[j]
                        if a.parent is li:
                            city = a.string
                            monthStart = strLi.find(city + "</a>") + len(city) + 4
                            monthWithDay = strLi[monthStart : monthStart + 10]
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
                                result.append(mobikeName + "," + city + "," + time)
            for k in range(len(result)):
                print(result[k])
        else:
            print("can not find expect content")
    else:
        print("can not find expect tag")
else:
    print("fail to surface mobike site")
