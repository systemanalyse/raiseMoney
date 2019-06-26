const Task = require('../model/Task')
const Feedback = require('../model/Feedback')
const CreateNotice = require('./CreateNotice')
const GetPublicUser = require('./GetPublicUser')

var FinishPP = async (taskid, userid, photourl, desc) => {
  let task = new Task(taskid)
  let result = await task.queryTask(['*'])
  if (result.length == 0) {
    return {
      'status': 404,
      'data': 'Task not found'
    }
  }
  photourl = Array(photourl)
  if (result[0]['taskType'] == 1) {
    return {
      'status': 403,
      'data': 'This is PP'
    }
  }
  if (userid != result[0]['acceptor']) {
    return {
      'status': 403,
      'data': 'Don\'t hack me'
    }
  }
  if (result[0]['statusCode'] != 1) {
    return {
      'status': 403,
      'data': '?'
    }
  }
  let r = await task.updateTask({
    'finishor': userid,
    'finishNum': 1,
    'statusCode': 2
  })
  if (!r) {
    return {
      'status': 400,
      'data': 'Data Form Wrong'
    }
  } else {
    let feedback = new Feedback()
    feedback.insertFeedback({
      'userid': userid,
      'taskid': taskid,
      'puserid': result[0]['userid'],
      'photourl': photourl.join(','),
      'finishtime': Date.parse(new Date()),
      'descr': desc
    })
    CreateNotice({
      'userid': result[0]['userid'],
      'cuserid': userid,
      'taskid': taskid,
      'userType': 1,
      'taskType': 0,
      'time': Date.parse(new Date()),
      'title': result[0]['title'],
      'descr': result[0]['descr'],
    })
    let info = await GetPublicUser(result[0]['userid'])
    let publishorInfo = Object.assign(info['data'], {
      'userid': result[0]['userid']
    })
    return {
      'status': 200,
      'data': {
        "taskid": result[0]['id'],
        "taskStatus": result[0]['taskStatus'],
        "taskType": result[0]['taskType'],
        "statusCode": result[0]['statusCode'],
        "beginTime": result[0]['beginTime'],
        "value": result[0]['totalValue'],
        "title": result[0]['title'],
        "desc": result[0]['descr'],
        "startPosition": result[0]['startPosition'],
        "endPosition": result[0]['endPosition'],
        "ddl": result[0]['ddl'],
        "publishorInfo": publishorInfo
      }
    }
  }
}

module.exports = FinishPP