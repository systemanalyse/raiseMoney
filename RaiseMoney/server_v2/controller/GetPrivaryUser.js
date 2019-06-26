const User = require('../model/User')
const CountTask = require('./CountTask')

var GetPrivaryUser = async (userid) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  let count = await CountTask(userid)
  if (!!result) {
    return {
      'status': 200,
      'data': {
        "userid": result['id'],
        "email": result['email'],
        "name": result['name'],
        "gender": result['gender'],
        "school": result['school'],
        "enrollment": result['enrollment'],
        "dormitory": result['dormitory'],
        "studentCardURL": result['studentCardURL'],
        "avatarURL": result['avatarURL'],
        "signature": result['signature'],
        "phone": result['phone'],
        "wechat": result['wechat'],
        "qq": result['qq'],
        "acceptNum": count['acceptNum'],
        "publishNum": count['publishNum'],
        "Jin": result['Jin']
      }
    }
  } else {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
}

module.exports = GetPrivaryUser