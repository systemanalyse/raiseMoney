const Task = require('../model/Task')

var CountTask = async (userid) => {
  let task = new Task()
  task.condition = {
    "userid": userid
  }
  let result = await task.queryTask(['*'])
  let publishNum = result.length
  task.condition = {}
  result = await task.queryTask(['*'])
  let acceptNum = 0
  for (let i = 0; i < result.length; i++) {
    let acceptor = result[i]['acceptor'].split(',')
    for (let j = 0; j < acceptor.length; j++) {
      if (acceptor[j] == userid) {
        acceptNum++
        break
      }
    }
  }
  return {
    'publishNum': publishNum,
    'acceptNum': acceptNum
  }
}

module.exports = CountTask