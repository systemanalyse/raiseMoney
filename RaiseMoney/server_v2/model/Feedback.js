const {
  insertDB,
  updateDB,
  queryDB,
  deleteDB
} = require('./db.js')

class Feedback {

  constructor (feedbackid) {
    this.feedbackid = feedbackid
    this.condition = {'id': feedbackid}
  }

  async insertFeedback(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await insertDB('feedback', key, value)
    return result
  }

  async updateFeedback(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await updateDB('feedback', key, value, this.condition)
    return result
  }

  async queryFeedback(key) {
    let result = await queryDB('feedback', key, this.condition)
    return result
  }

  async deleteFeedback() {
    let result = await deleteDB('feedback', this.condition)
    return result
  }

}


module.exports = Feedback


