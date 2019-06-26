const Task = require('../model/Task')
const GetPublicUser = require('./GetPublicUser')

var GetAllDD = async (userid) => {
  let task = new Task()
  task.condition = {
    'taskType': 1,
    'taskStatus': 1
  }
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
    if (result[i]['userid'] == userid) {
      continue
    }
    let info = await GetPublicUser(result[i]['userid'])
    let publishorInfo = Object.assign(info['data'], {
      'userid': result[i]['userid']
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
      "ddl": result[i]['ddl'],
      "allocation": result[i]['allocation'],
      "finishNum": result[i]['finishNum'],
      "totalNum": result[i]['totalNum'],
      "publishorInfo": publishorInfo
    })
  }
  return {
    'status': 200,
    'data': values
  }
}

module.exports = GetAllDD