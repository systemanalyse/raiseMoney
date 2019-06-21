const User = require('../model/User')
const CountTask = require('./CountTask')

var Login = async (email, password) => {
  if (!!!email || !!!password) {
    return {
      'status': 400,
      'data': 'Wrong Format'
    }
  } else {
    let user = new User()
    user.condition = {
      'email': email
    }
    
    let result = await user.queryUser(['*'])
    let user_password = result['password']
    if (password == user_password) {
      let count = await CountTask(result['id'])
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
          "publishNum": count['publishNum'],
          "acceptNum": count['acceptNum'],
          "Jin": result['Jin']
        }
      }
    } else {
      return {
        'status': 401,
        'data': 'Wrong Email or Password'
      }
    }
  }
}

module.exports = Login