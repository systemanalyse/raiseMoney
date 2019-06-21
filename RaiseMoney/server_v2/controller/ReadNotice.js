const Notice = require('../model/Notice')

var ReadNotice = async (userid) => {
  let notice = new Notice(userid)
  let result = await notice.updateNotice({
    'status': 0
  })
  if (!result) {
    return {
      'status': 400,
      'data': 'Wrong Format'
    }
  } else {
    return {
      'status': 200,
      'data': 'Successful'
    }
  }
}

module.exports = ReadNotice