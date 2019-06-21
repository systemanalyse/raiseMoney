const Task = require('../model/Task')
const Feedback = require('../model/Feedback')
const CreateNotice = require('./CreateNotice')

var FinishPP = async (taskid, userid, photourl) => {
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
  let r = await task.updateTask({
    'finishor': userid,
    'finishNum': 1,
    'statusCode': 9
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
      'taskType': false,
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

module.exports = FinishPP