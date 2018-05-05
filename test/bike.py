# -*- coding: UTF-8 -*-

import requests

#api
createBikeAPI="http://localhost:9999/bikes"

#ofo小黄车
ofoPlayload = {'name':'ofo小黄车','founded_at':'2015-03-25T16:00:00Z'}
ofoCreateReequest = requests.post(createBikeAPI,ofoPlayload);
print(ofoCreateReequest.json())

#摩拜单车
mobikePlayload = {'name':'摩拜单车','founded_at':'2015-01-26T16:00:00Z'}
mobikeCreateReequest = requests.post(createBikeAPI,mobikePlayload);
print(mobikeCreateReequest.json())
