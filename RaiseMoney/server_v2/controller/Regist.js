const User = require('../model/User')

var regist = async (params) => {
  let user = new User()
  user.condition = {
    'email': params['email']
  }
  let result = await user.queryUser(['*'])
  if (result) {
    return {
      'status': 406,
      'data': 'Email Repeat'
    }
  } else {
    values = {
      "email": params['email'],
      "password": params['password'],
      "name": params['name'],
      "gender": params['gender'],
      "school": params['school'],
      "enrollment": params['enrollment'],
      "dormitory": params['dormitory'],
      "studentCardURL": params['studentCardURL'],
      "avatarURL": params['avatarURL'],
      "signature": params['signature'],
      "phone": params['phone'],
      "wechat": params['wechat'],
      "qq": params['qq']
    }
    let result = await user.insertUser(values)
    if (!result) {
      return {
        'status': 400,
        'data': 'Wrong Format'
      }
    } else {
      return {
        'status': 200,
        'data': {
          "userid": result,
          "email": params['email'],
          "name": params['name'],
          "gender": params['gender'],
          "school": params['school'],
          "enrollment": params['enrollment'],
          "dormitory": params['dormitory'],
          "studentCardURL": params['studentCardURL'],
          "avatarURL": params['avatarURL'],
          "signature": params['signature'],
          "phone": params['phone'],
          "wechat": params['wechat'],
          "qq": params['qq'],
          "acceptNum": 0,
          "publishNum": 0,
          "Jin": 0
        }
      }
    }
  }
}

module.exports = regist