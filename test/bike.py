# -*- coding: UTF-8 -*-

import requests

#api
createBikeAPI="http://localhost:9999/bikes"

#ofo小黄车
ofoPlayload = {
        'name': 'ofo小黄车',
        'founded_at': '2015-03-26T00:00:00+0800',
        'introduction': 'ofo 小黄车是全球第一个无桩共享单车出行平台，首创“无桩单车共享”模式。用户只需在ofo官方App、支付宝小程序、微信服务号和微信小程序输入车牌号或扫码，即可解锁用车，随取随用，随时随地，也可以共享自己的单车到 ofo 共享平台，获得所有 ofo 小黄车的终身免费使用权，以1换N。'
}
ofoCreateReequest = requests.post(createBikeAPI,ofoPlayload);
print(ofoCreateReequest.json())

#摩拜单车
mobikePlayload = {
        'name': '摩拜单车',
        'founded_at': '2015-01-27T00:00:00+0800',
        'introduction': '摩拜单车创建了全球首个智能共享单车模式，自主研发的专利智能锁集成了 GPS 和通讯模块，使用了新一代物联网技术，通过智能手机 app 让用户随时随地可以定位并使用最近的摩拜单车，骑行到达目的地后，就近停放在路边合适的区域，关锁即实现电子付费结算。'
}
mobikeCreateReequest = requests.post(createBikeAPI,mobikePlayload);
print(mobikeCreateReequest.json())
