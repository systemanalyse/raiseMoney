const Notice = require('../model/Notice')

var CreateNotice = async (params) => {
  let notice = new Notice()
  let result = await notice.insertNotice({
    'userid': params['userid'],
    'cuserid': params['cuserid'],
    'taskid': params['taskid'],
    'userType': params['userType'],
    'taskType': params['taskType'],
    'title': params['title'],
    'descr': params['descr'],
    'time': params['time'],
    'status': true
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

module.exports = CreateNotice