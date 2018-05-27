#!/usr/bin/python
# -*- coding: UTF-8 -*- 

import requests
import json

#api
getBikeListAPI="http://localhost:9999/bikes"
createDevlopmentAPI="http://localhost:9999/developments"

#获取所有单车
getBikeListRequest = requests.get(getBikeListAPI)
success = 0
bikeDictionary = {}
if getBikeListRequest.status_code == 200:
    success = 1 
    json = getBikeListRequest.json()
    for i in range(len(json)):
        bike = json[i]
        bikeDictionary[bike['name']] = bike['id']
else:
    success = 0
    print("failed")

if success == 1:
    #读取文件
    try:
        developments = open("./development.txt","r")
        developmentList = []
        try:
            for development in developments:
                development = development.strip()
                if development != "":
                    developmentList.append(development) 
        finally:
            developments.close()
        developmentPlaylod = {}
        for i in range(len(developmentList)):
            detailList = developmentList[i].split(",")
            if len(detailList) == 4:
                developmentPlaylod.clear()
                #
                bikeName = detailList[0]
                if bikeName in bikeDictionary:
                    bikeId = bikeDictionary.get(bikeName)
                    city = detailList[1]
                    deliveryAt = detailList[2]
                    deliveryCount = detailList[3]
                    #
                    developmentPlaylod['bike_id'] = bikeId
                    developmentPlaylod['city'] = city
                    developmentPlaylod['delivery_at'] = deliveryAt
                    developmentPlaylod['delivery_count'] = deliveryCount
                    #请求
                    createDevlopmentRequest = requests.post(createDevlopmentAPI,developmentPlaylod)
                    print(createDevlopmentRequest.json())
    except IOError:
        print("failed")
