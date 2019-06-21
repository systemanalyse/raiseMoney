const Task = require('../model/Task')
const GetWallet = require('./GetWallet')
const ChangeWallet = require('./ChangeWallet')
const CreateNotice = require('./CreateNotice')

var ConfirmTask = async (taskid, userid, fuserid) => {
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
  // if (result[0]['statusCode'] == 10) {
  //   return {
  //     'status': 403,
  //     'data': 'don\'t hack me'
  //   }
  // }
  let taskType = result[0]['taskType']
  let finishor = result[0]['finishor'].split(',')
  if (!finishor.includes(fuserid)) {
    return {
      'status': 403,
      'data': 'don\'t hack me'
    }
  }
  let money = await GetWallet(fuserid)
  let confirmNum = result[0]['confirmNum'] + 1
  let statusCode = 8
  if (confirmNum == result[0]['finishNum']) {
    statusCode = 10
  }
  let r = await task.updateTask({
    'statusCode': statusCode,
    'confirmNum' : confirmNum
  })
  let bonus = 0
  if (taskType == 0) {
    bonus = parseFloat(result[0]['value'])
  } else {
    let value = result[0]['value'].split(',')
    bonus = value[confirmNum - 1]
  }

  if (!r) {
    return {
      'status': 400,
      'data': 'data form wrong'
    }
  } else {
    ChangeWallet(fuserid, bonus + parseFloat(money['data']))
    CreateNotice({
      'userid': fuserid,
      'cuserid': userid,
      'taskid': taskid,
      'userType': false,
      'time': Date.parse(new Date()),
      'taskType': taskType,
      'title': result[0]['title'],
      'descr': result[0]['descr'],
    })

    return {
      'status': 200,
      'data': 'Succussfully'
    }
  }

  

}

module.exports = ConfirmTask