const Task = require('../model/Task')
const GetWallet = require('./GetWallet')
const ChangeWallet = require('./ChangeWallet')

var randAlloc = async (total, min, max, length) => {
  let result = [];
  let restValue = total;
  let restLength = length;
  for (let i = 0; i < length - 1; i++) {
    restLength--;
    const restMin = restLength * min;
    const restMax = restLength * max;
    const usable = restValue - restMin;
    const minValue = Math.max(min, restValue - restMax);
    const limit = Math.min(usable - minValue, (max - minValue) * 2);
    result[i] = Math.min(max, minValue + Math.floor(limit * Math.random())).toFixed(1);
    restValue -= result[i];
  }
  result[length - 1] = parseFloat(restValue).toFixed(1);
  return result;
}

var PublishTask = async (userid, params) => {
  let task = new Task(userid)
  values = {
    "userid": userid,
    "taskType": params['taskType'],
    "statusCode": 0,
    "beginTime": params['beginTime'],
    "allocation": params['allocation'],
    "totalValue": params['value'],
    "title": params['title'],
    "descr": params['desc'],
    "startPosition": params['startPosition'],
    "endPosition": params['endPosition'],
    "ddl": params['ddl'],
    "totalNum": params['totalNum']
  }
  let wallet = await GetWallet(userid)
  if (parseFloat(wallet['data']) < parseFloat(params['value'])) {
    return {
      'status': 403,
      'data': 'Your wallet is poor'
    }
  }
  if (params['taskType'] == 0) {
    values["value"] = params['value']
  } else if (params['allocation'] == 0) {
    let r = await randAlloc(params['value'], 0.1, params['value'] / 3, params['totalNum'])
    values["value"] = r.join(',')
  } else {
    let value = []
    for (let i = 0; i < params['totalNum']; i++) {
      value.push(params['value'] / params['totalNum'])
    }
    values["value"] = value.join(',')
  }
  let result = await task.insertTask(values)
  if (!result) {
    return {
      'status': 400,
      'data': 'Wrong Format'
    }
  } else {
    ChangeWallet(userid, parseFloat(wallet['data']) - parseFloat(params['value']))
    return {
      'status': 200,
      'data': String(result)
    }
  }
}

module.exports = PublishTask