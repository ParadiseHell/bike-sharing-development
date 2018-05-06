# -*- coding: UTF-8 -*-

import requests

#api
createBikeAPI="http://localhost:9999/bikes"

#ofo小黄车
ofoPlayload = {'name':'ofo小黄车','founded_at':'2015-03-26T00:00:00+0800'}
ofoCreateReequest = requests.post(createBikeAPI,ofoPlayload);
print(ofoCreateReequest.json())

#摩拜单车
mobikePlayload = {'name':'摩拜单车','founded_at':'2015-01-27T00:00:00+0800'}
mobikeCreateReequest = requests.post(createBikeAPI,mobikePlayload);
print(mobikeCreateReequest.json())
