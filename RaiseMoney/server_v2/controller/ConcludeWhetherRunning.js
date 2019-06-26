const Task = require('../model/Task')

var ConcludeWhetherRunning = async () => {
  let task = new Task()
  task.condition = {}
  let now = (new Date()).valueOf()
  let result = await task.queryTask(['id', 'statusCode', 'ddl'])
  for (let i = 0; i < result.length; i++) {
    let statusCode = 4
    if (result[i]['ddl'] < now && result[i]['statusCode'] < 2) {
      await task.updateTask({
        'statusCode' : statusCode,
        'taskStatus': 0
      })
    } else {
      continue
    }
  }
  return true
}

module.exports = ConcludeWhetherRunning