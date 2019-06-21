const Task = require('../model/Task')
const Feedback = require('../model/Feedback')
const CreateNotice = require('./CreateNotice')

var FinishDD = async (taskid, userid, photourl) => {
  let task = new Task(taskid)
  let result = await task.queryTask(['*'])
  if (result.length == 0) {
    return {
      'status': 404,
      'data': 'Task not found'
    }
  }
  if (result[0]['taskType'] == 0) {
    return {
      'status': 403,
      'data': 'This is DD'
    }
  }
  let acceptor = result[0]['acceptor'].split(',')
  if (!acceptor.includes(userid)) {
    return {
      'status': 403,
      'data': 'Don\'t hack me'
    }
  }
  if (result[0]['statusCode'] == 9) {
    return {
      'status': 406,
      'data': 'Task has been finished'
    }
  }
  photourl = Array(photourl)
  let finishor = result[0]['finishor'].split(',')
  let finishorNum = result[0]['finishNum'] + 1
  let statusCode = 2
  if (finishor == result[0]['totalNum']) {
    statusCode = 10
  }
  finishor.push(userid)
  let r = await task.updateTask({
    'finishor': finishor.join(','),
    'finishNum': finishorNum,
    'statusCode': statusCode
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
      'photourl': photourl.join(',')
    })
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

module.exports = FinishDD