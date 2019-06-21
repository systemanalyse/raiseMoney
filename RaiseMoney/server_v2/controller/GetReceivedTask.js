const Task = require('../model/Task')
const GetPublicUser = require('./GetPublicUser')

var GetReceivedTask = async (userid) => {
  let task = new Task()
  task.condition = {}
  let result = await task.queryTask(['*'])
  values = []
  for (let i = 0; i < result.length; i++) {
    // let acceptor = result[i]['acceptor'].split(',')
    // let acceptorInfo = []
    // for (let j = 0; j < acceptor.length; j++) {
    //   if (acceptor[j] == "") {
    //     continue
    //   }
    //   let info = await GetPublicUser(acceptor[j])
    //   acceptorInfo.push(Object.assign(info['data'], {
    //     'userid': acceptor[j]
    //   }))
    // }
    let acceptor = result[i]['acceptor'].split(',')
    if (!acceptor.includes(String(userid))) {
      continue
    }
    let info = await GetPublicUser(result[i]['userid'])
    let publishorInfo = Object.assign(info['data'], {
      "userid": result[i]['userid']
    })
    values.push({
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
      "publishInfo": publishorInfo
    })
  }
  return {
    'status': 200,
    'data': values
  }
}

module.exports = GetReceivedTask