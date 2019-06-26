const Task = require('../model/Task')
const GetPublicUser = require('./GetPublicUser')

var GetAllPP = async (userid) => {
  let task = new Task()
  task.condition = {
    'taskType': 0,
    'taskStatus': 1
  }
  let result = await task.queryTask(['*'])
  values = []
  for (let i = 0; i < result.length; i++) {
    // let info = await GetPublicUser(result[i]['acceptor'])
    // let acceptorInfo = Object.assign(info['data'], {
    //   "userid": result[i]['acceptor']
    // }) 
    if (result[i]['userid'] == userid) {
      continue
    }
    let info = await GetPublicUser(result[i]['userid'])
    let publishorInfo = Object.assign(info['data'], {
      "userid": result[i]['userid']
    })
    values.push({
      "origin": 0,
      "taskid": result[i]['id'],
      "taskStatus": result[i]['taskStatus'],
      "taskType": result[i]['taskType'],
      "statusCode": result[i]['statusCode'],
      "beginTime": result[i]['beginTime'],
      "value": result[i]['totalValue'],
      "title": result[i]['title'],
      "desc": result[i]['descr'],
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