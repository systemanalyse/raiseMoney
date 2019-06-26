const User = require('../model/User')
const CountTask = require('./CountTask')

var GetPublicUser = async (userid) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  let count = await CountTask(userid)
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
        "acceptNum": count['acceptNum'],
        "publishNum": count['publishNum']
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