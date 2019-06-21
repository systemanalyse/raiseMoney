const Notice = require('../model/Notice')
const GetPublicUser = require('./GetPublicUser')

var GetNotice = async (userid) => {
  let notice = new Notice(userid)
  let result = await notice.queryNotice(['*'])
  if (!!result) {
    r = []
    for (let i = 0; i < result.length; i++) {
      let info = await GetPublicUser(result[i].cuserid)
      r.push({
        'noticeid': result[i].id,
        "userType": result[i].userType,
        "taskType": result[i].taskType,
        "title": result[i].title,
        "desc": result[i].descr,
        "time": result[i].time,
        "status": result[i].status,
        "cinfo": Object.assign({
          'cuserid': result[i].cuserid
        }, info['data'])
      })
    }
    return {
      'status': 200,
      'data': r
    }
  } else {
    return {
      'status': 404,
      'data': 'User not found'
    }
  }
}

module.exports = GetNotice