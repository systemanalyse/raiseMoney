const Feedback = require('../model/Feedback')
const GetPublicUser = require('./GetPublicUser')

var GetFeedback = async (userid, taskid) => {
  let feedback = new Feedback()
  feedback.condition = {
    'taskid': taskid,
    'puserid': userid
  }
  let result = await feedback.queryFeedback(['*'])
  if (result.length == 0) {
    return {
      'status': 403,
      'data': '别黑我'
    }
  }
  values = []
  for (let i = 0; i < result.length; i++) {
    let userinfo = await GetPublicUser(result[i]['userid'])
    values.push(Object.assign({
      'photourl': result[i]['photourl'].split(','),
      'userid': result[i]['userid']
    }, userinfo['data']))
  }
  return {
    'status': 200,
    'data': values
  }
}


module.exports = GetFeedback