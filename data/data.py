#!/usr/bin/python
# -*- coding: UTF-8 -*- 

import bikeUtils

#ofo
ofoSite="https://zh.wikipedia.org/zh-hans/Ofo%E5%B0%8F%E9%BB%84%E8%BD%A6"
ofoName="ofo小黄车"
ofoStartFlag = "运营地区"
ofoEndFlag = "车型"

#mobike
mobieSite="https://zh.wikipedia.org/wiki/%E6%91%A9%E6%8B%9C%E5%8D%95%E8%BD%A6"
mobikeName="摩拜单车"
mobikeStartFlag = "运营城市"
mobikeEndFlag = "车种"

#ofo
bikeUtils.obtainBikeData(ofoSite, ofoName, ofoStartFlag, ofoEndFlag, 0)

#mobike
bikeUtils.obtainBikeData(mobieSite, mobikeName, mobikeStartFlag, mobikeEndFlag, 1)
