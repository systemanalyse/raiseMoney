# -*- coding: utf-8 -*-

import requests

url = 'http://118.89.20.188:3000/'

def testRegister():
  params = {
    "email": "test@qq.com",
    "password": "test",
    "name": "test",
    "gender": 1,
    "school": "test",
    "enrollment": 2016,
    "dormitory": "test",
    "studentCardURL": "",
    "avatarURL": "",
    "signature": "test",
    "phone": "test",
    "wechat": "test",
    "qq": "test"
  }
  s = requests.Session()
  r = s.post(url + 'regist', data=params)
  print(r.content)
  s.get(url + 'logout')

def testLogin():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  r = s.post(url + 'login', data=params)
  print(r.content)
  s.get(url + 'logout')

def testGetPrivaryInfo():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'user/1/Privary')
  print(r.content)

  s.get(url + 'logout')

def testGetSetting():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'user/1/Settings/Overview')
  print(r.content)

  s.get(url + 'logout')

def testUpdateUserInfo():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    "name": "test",
    "gender": 1,
    "school": "test",
    "enrollment": 2016,
    "dormitory": "",
    "studentCardURL": "test",
    "avatarURL": "test",
    "signature": "test",
    "phone": "test",
    "wechat": "test",
    "qq": "test"
  }
  r = s.put(url + 'user/1/Privary', data=params)
  print(r.content)

  s.get(url + 'logout')

def testGetPublicInfo():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'user/1/Public')
  print(r.content)

  s.get(url + 'logout')

def testGetWallet():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'user/1/Wallet')
  print(r.content)

  s.get(url + 'logout')

def testChangeWallet():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    "Jin": 100
  }
  r = s.put(url + 'user/12/Wallet', data=params)
  print(r.content)

  s.get(url + 'logout')

def testGetNotice():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'user/1/Notice')
  print(r.content)

  s.get(url + 'logout')

def testReadNotice():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    "noticeid": 1
  }
  r = s.put(url + 'user/1/ReadNotice', data=params)
  print(r.content)

  s.get(url + 'logout')

def testPublishTask():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    "taskType": 0,
    "beginTime": 100,
    "value": 10,
    "allocation": 0,
    "title": "test",
    "desc": "test",
    "startPosition": "test",
    "endPosition": "test",
    "ddl": 100,
    "totalNum": 1
  }
  r = s.post(url + 'task/Publish/12/', data=params)
  print(r.content)

  s.get(url + 'logout')

def testUserPublishTask():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'task/Publish/1/')
  print(r.content)

  s.get(url + 'logout')

def testUserReceivedTask():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'task/Received/2/')
  print(r.content)

  s.get(url + 'logout')

def testGetDD():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'task/DD')
  print(r.content)

  s.get(url + 'logout')

def testGetPP():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.get(url + 'task/PP')
  print(r.content)

  s.get(url + 'logout')

def testReceiveDD():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.put(url + 'task/ReceiveDD/9/2/')
  print(r.content)

  s.get(url + 'logout')

def testReceivePP():
  params = {
    "email": "test1@qq.com",
    "password": "test"
  }
  s = requests.Session()
  s.post(url + 'login', data=params)

  r = s.put(url + 'task/ReceivePP/6/2/')
  print(r.content)

  s.get(url + 'logout')

def testFinishPP():
  params = {
    "email": "test1@qq.com",
    "password": "test"
  }

  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    'photourl': ['test']
  }
  r = s.put(url + 'task/FinishPP/6/2/', data=params)
  print(r.content)

  s.get(url + 'logout')

def testFinishDD():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }

  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    'photourl': ['test']
  }
  r = s.put(url + 'task/FinishDD/9/2/', data=params)
  print(r.content)

  s.get(url + 'logout')

def testGetFeedback():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }

  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    'photourl': ['test']
  }
  r = s.get(url + 'task/Feedback/1/1/')
  print(r.content)

  s.get(url + 'logout')

def testConfirmFeedback():
  params = {
    "email": "test1@qq.com",
    "password": "test"
  }

  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    'photourl': ['test']
  }
  r = s.put(url + 'task/Confirm/9/1/2')
  print(r.content)

  s.get(url + 'logout')

def testGetDDValue():
  params = {
    "email": "test@qq.com",
    "password": "test"
  }

  s = requests.Session()
  s.post(url + 'login', data=params)

  params = {
    'photourl': ['test']
  }
  r = s.get(url + 'task/DD/1/12/2/')
  print(r.content)

  s.get(url + 'logout')

if __name__ == "__main__":
  testGetDDValue()
