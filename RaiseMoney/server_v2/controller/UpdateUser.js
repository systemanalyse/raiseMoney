const User = require('../model/User')

var update = async (userid, params) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  if (!result) {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
  values = {
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
  result =  await user.updateUser(values)
  if (!result) {
    return {
      'status': 400,
      'data': 'Wrong Format'
    }
  } else {
    return {
      'status': 200,
      'data': {
        "userid": userid,
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
        "qq": params['qq']
      }
    }
  }
}

module.exports = update