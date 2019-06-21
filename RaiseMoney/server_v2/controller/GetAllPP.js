const Task = require('../model/Task')
const GetPublicUser = require('./GetPublicUser')

var GetAllPP = async () => {
  let task = new Task()
  task.condition = {
    'taskType': 0
  }
  let result = await task.queryTask(['*'])
  values = []
  for (let i = 0; i < result.length; i++) {
    // let info = await GetPublicUser(result[i]['acceptor'])
    // let acceptorInfo = Object.assign(info['data'], {
    //   "userid": result[i]['acceptor']
    // }) 
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
      "value": result[i]['value'],
      "title": result[i]['totalValue'],
      "desc": result[i]['descr'],
      "time": result[i]['time'],
      "startPosition": result[i]['startPosition'],
      "endPosition": result[i]['endPosition'],
      "ddl": result[i]['ddl'],
      "publishorInfo": publishorInfo
    })
  }
  return {
    'status': 200,
    'data': values
  }
}

module.exports = GetAllPP