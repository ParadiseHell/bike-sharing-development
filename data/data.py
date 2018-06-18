#!/usr/bin/python
# -*- coding: UTF-8 -*- 

import bikeUtils

#ofo
ofoSite="https://zh.wikipedia.org/zh-hans/Ofo%E5%B0%8F%E9%BB%84%E8%BD%A6"
ofoName="ofo小黄车"
ofoStartFlag = "运营地区"
ofoEndFlag = "车型"
ofoStrategy = 0

#mobike
mobikeSite="https://zh.wikipedia.org/wiki/%E6%91%A9%E6%8B%9C%E5%8D%95%E8%BD%A6"
mobikeName="摩拜单车"
mobikeStartFlag = "运营城市"
mobikeEndFlag = "车种"
mobikeStrategy = 1

developmentFilePath = "test.txt"

def main():
    try:
        developmentFile = open(developmentFilePath, "w") 
        print("obtaining ofo data......")
        ofoData = bikeUtils.obtainBikeData(ofoSite, ofoName, ofoStartFlag, ofoEndFlag, ofoStrategy)
        if ofoData is None or len(ofoData) == 0:
            return
            print("cannot obtain ofo data")
        else:
            for data in ofoData:
                developmentFile.write(data + "\n")
            developmentFile.write("\n")
        print("obtaining mobike data......")
        mobikeData = bikeUtils.obtainBikeData(mobikeSite, mobikeName, mobikeStartFlag, mobikeEndFlag, mobikeStrategy)
        if mobikeData is None or len(mobikeData) == 0:
            print("cannot obtain mobike data")
            return
        else:
            for data in mobikeData:
                developmentFile.write(data + "\n")
        print("obtain data success")
    except IOError:
        print("cannot open file")

if __name__ == "__main__":
    main()
