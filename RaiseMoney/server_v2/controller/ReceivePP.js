const Task = require('../model/Task')
const CreateNotice = require('./CreateNotice')
const GetPublicUser = require('./GetPublicUser')

var ReceivePP = async (taskid, userid) => {
  let task = new Task(taskid)
  let result = await task.queryTask(['*'])
  if (result.length == 0) {
    return {
      'status': 404,
      'data': 'Task not found'
    }
  }
  if (result[0]['userid'] == userid) {
    return {
      'status': 403,
      'data': '?'
    }
  }
  if (result[0]['taskType'] == 1) {
    return {
      'status': 403,
      'data': 'This is PP'
    }
  }
  if (result[0]['statusCode'] != 0) {
    return {
      'status': 403,
      'data': 'This PP has been received'
    }
  }
  let r = await task.updateTask({
    'acceptor': userid,
    'acceptNum': 1,
    'statusCode': 1,
    'taskStatus': 0
  })
  if (!r) {
    return {
      'status': 400,
      'data': 'Data Form Wrong'
    }
  } else {
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

module.exports = ReceivePP