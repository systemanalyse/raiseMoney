const Task = require('../model/Task')
const GetPublicUser = require('./GetPublicUser')

var GetUserPublishTask = async (userid) => {
  let task = new Task()
  task.condition = {
    "userid": userid
  }
  let result = await task.queryTask(['*'])
  values = []
  for (let i = 0; i < result.length; i++) {
    let acceptor = result[i]['acceptor'].split(',')
    let acceptorInfo = []
    for (let j = 0; j < acceptor.length; j++) {
      if (acceptor[j] == "") {
        continue
      }
      let info = await GetPublicUser(acceptor[j])
      acceptorInfo.push(Object.assign(info['data'], {
        'userid': acceptor[j]
      }))
    }
    let finishor = result[i]['finishor'].split(',')
    values.push({
      "userid": userid,
      "taskid": result[i]['id'],
      "taskStatus": result[i]['taskStatus'],
      "taskType": result[i]['taskType'],
      "statusCode": result[i]['statusCode'],
      "beginTime": result[i]['beginTime'],
      "value": result[i]['totalValue'],
      "title": result[i]['title'],
      "descr": result[i]['desc'],
      "time": result[i]['time'],
      "startPosition": result[i]['startPosition'],
      "endPosition": result[i]['endPosition'],
      "ddl": result[i]['ddl'],
      "finishNum": result[i]['finishNum'],
      "totalNum": result[i]['totalNum'],
      "acceptor": acceptorInfo,
      "finishor": finishor
    })
  }
  return {
    'status': 200,
    'data': values
  }
}

module.exports = GetUserPublishTask