const User = require('../model/User')

var GetWallet = async (userid) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  if (result) {
    return {
      'status': 200,
      'data': String(result['Jin'])
    }
  } else {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
}

module.exports = GetWallet