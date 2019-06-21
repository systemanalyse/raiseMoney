const User = require('../model/User')

var GetSetting = async (userid) => {
  let user = new User(userid)
  let result = await user.queryUser(['*'])
  if (!!result) {
    let data = {
      "name": result['name'],
      "gender": result['gender'],
      "school": result['school'],
      "enrollment": result['enrollment'],
      "dormitory": result['dormitory']
    }
    let n = 0
    for (let i in data) {
      if (data[i] != '') {
        n++
      }
    }
    return {
      'status': 200,
      'data': {
        'profile': parseFloat(n) / 5,
        'certification': result['studentCardURL'] == ''? false : true
      }
    }
  } else {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
}

module.exports = GetSetting