const {
  insertDB,
  updateDB,
  queryDB,
  deleteDB
} = require('./db.js')

class Notice {

  constructor (userid) {
    this.userid = userid
    this.condition = {'userid': userid}
  }

  async insertNotice(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await insertDB('notice', key, value)
    return result
  }

  async updateNotice(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await updateDB('notice', key, value, this.condition)
    return result
  }

  async queryNotice(key) {
    let result = await queryDB('notice', key, this.condition)
    return result
  }

  async deleteNotice() {
    let result = await deleteDB('notice', this.condition)
    return result
  }

}


module.exports = Notice


