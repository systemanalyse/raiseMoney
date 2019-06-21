const User = require('../model/User')

var GetPrivaryUser = async (userid) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
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
        "wechart": result['wechat'],
        "qq": result['qq'],
        "acceptNum": result['acceptNum'],
        "publishNum": result['publishNum'],
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