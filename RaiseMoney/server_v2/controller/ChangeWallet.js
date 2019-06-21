const User = require('../model/User')

var ChangeWallet = async (userid, Jin) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  if (!result) {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
  values = {
    "Jin": Jin
  }
  result = await user.updateUser(values)
  if (!result) {
    return {
      'status': 400,
      'data': 'Wrong Format'
    }
  } else {
    return {
      'status': 200,
      'data': {
        "Jin": Jin
      }
    }
  }
}

module.exports = ChangeWallet