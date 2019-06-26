const Task = require('../model/Task')
const CreateNotice = require('./CreateNotice')
const GetPublicUser = require('./GetPublicUser')

var ReceiveDD = async (taskid, userid) => {
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
  if (result[0]['taskType'] == 0) {
    return {
      'status': 403,
      'data': 'This is DD'
    }
  }
  if (result[0]['statusCode'] > 1) {
    return {
      'status': 403,
      'data': 'Task has been finished'
    }
  }
  let acceptor = result[0]['acceptor'].split(',')
  let acceptNum = result[0]['acceptNum'] + 1
  acceptor.push(userid)
  let r = await task.updateTask({
    'acceptor': acceptor.join(','),
    'acceptNum': acceptNum
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
      'taskType': 1,
      'time': Date.parse(new Date()),
      'title': result[0]['title'],
      'descr': result[0]['descr'],
    })
    let info = await GetPublicUser(result[i]['userid'])
    let publishorInfo = Object.assign(info['data'], {
      'userid': result[i]['userid']
    })
    return {
      'status': 200,
      'data': {
        "taskid": result[i]['id'],
        "taskStatus": result[i]['taskStatus'],
        "taskType": result[i]['taskType'],
        "statusCode": result[i]['statusCode'],
        "beginTime": result[i]['beginTime'],
        "value": result[i]['totalValue'],
        "title": result[i]['title'],
        "desc": result[i]['descr'],
        "ddl": result[i]['ddl'],
        "allocation": result[i]['allocation'],
        "finishNum": result[i]['finishNum'],
        "totalNum": result[i]['totalNum'],
        "publishorInfo": publishorInfo
      }
    }
  }
}

module.exports = ReceiveDD