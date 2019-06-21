const Task = require('../model/Task')
const CreateNotice = require('./CreateNotice')

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
      'data': '刷单呢'
    }
  }
  if (result[0]['taskType'] == 0) {
    return {
      'status': 403,
      'data': '这是点点'
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
      'userType': true,
      'taskType': true,
      'time': Date.parse(new Date()),
      'title': result[0]['title'],
      'descr': result[0]['descr'],
    })
    return {
      'status': 200,
      'data': true
    }
  }
}

module.exports = ReceiveDD