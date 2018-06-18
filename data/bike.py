# -*- coding: UTF-8 -*-

import requests

#api
createBikeAPI="http://localhost:9999/bikes";

#ofo小黄车
ofoPlayload = {
        'name': 'ofo小黄车',
        'founded_at': '2015-03-26T00:00:00+0800',
        'introduction': 'ofo 小黄车是全球第一个无桩共享单车出行平台，首创“无桩单车共享”模式。用户只需在ofo官方App、支付宝小程序、微信服务号和微信小程序输入车牌号或扫码，即可解锁用车，随取随用，随时随地，也可以共享自己的单车到 ofo 共享平台，获得所有 ofo 小黄车的终身免费使用权，以1换N。'
}
#ofoCreateReequest = requests.post(createBikeAPI,ofoPlayload);
#print(ofoCreateReequest.json());
try:
    bikes = open('./bike.txt')
    bikeList = []
    try:
        for bike in bikes:
            bike = bike.strip()
            if bike != "":
                bikeList.append(bike)
    finally:
        bikes.close()
    bikePlayload = {}
    for i in range(len(bikeList)):
        detail = bikeList[i].split("#")
        if len(detail) == 3:
            bikePlayload.clear()
            bikePlayload['name'] = detail[0]
            bikePlayload['founded_at'] = detail[1]
            bikePlayload['introduction'] = detail[2]
            #请求
            createBikeRequest = requests.post(createBikeAPI,bikePlayload)
            print(createBikeRequest.json())
except IOError:
    print("failed");

