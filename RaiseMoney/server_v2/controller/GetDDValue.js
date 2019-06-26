const Task = require('../model/Task')

var GetDDValue = async (taskid, userid, number) => {
  let task = new Task(userid)
  task.condition = {
    'id': taskid
  }
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

  let value = result[0]['value'].split(',')
  let confirmNum = parseInt(result[0]['confirmNum'])
  let allMoney = 0
  for (let i = 0; i < parseInt(number); i++) {
    allMoney += parseFloat(value[confirmNum + i])
  }

  return {
    'status': 200,
    'data': String(allMoney)
  }

}

module.exports = GetDDValue