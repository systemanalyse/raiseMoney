const User = require('../model/User')

var GetPublicUser = async (userid) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  if (!!result) {
    return {
      'status': 200,
      'data': {
        "name": result['name'],
        "dormitory": result['dormitory'],
        "avatarURL": result['avatarURL'],
        "signature": result['signature'],
        "phone": result['phone'],
        "wechat": result['wechat'],
        "qq": result['qq'],
      }
    }
  } else {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
}

module.exports = GetPublicUser