const Task = require('../model/Task')
const Feedback = require('../model/Feedback')
const CreateNotice = require('./CreateNotice')
const GetPublicUser = require('./GetPublicUser')

var FinishDD = async (taskid, userid, photourl, desc) => {
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
  if (result[0]['statusCode'] > 1) {
    return {
      'status': 406,
      'data': 'Task has been finished'
    }
  }
  photourl = Array(photourl)
  let finishor = result[0]['finishor'].split(',')
  let finishorNum = result[0]['finishNum'] + 1
  let statusCode = 1
  let taskStatus = 1
  if (finishor == result[0]['totalNum']) {
    statusCode = 2
    taskStatus = 0
  }
  finishor.push(userid)
  let r = await task.updateTask({
    'finishor': finishor.join(','),
    'finishNum': finishorNum,
    'statusCode': statusCode,
    'taskStatus': taskStatus
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

module.exports = FinishDD