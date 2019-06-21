const {
  insertDB,
  updateDB,
  queryDB,
  deleteDB
} = require('./db.js')

class User {

  constructor (userid) {
    this.userid = userid
    this.condition = {'id': userid}
  }

  async insertUser(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await insertDB('user', key, value)
    return result
  }

  async updateUser(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await updateDB('user', key, value, this.condition)
    return result
  }

  async queryUser(key) {
    let result = await queryDB('user', key, this.condition)
    if (result.length != 0) {
      return result[0];
    } else {
      return false;
    }
  }

  async deleteUser() {
    let result = await deleteDB('user', this.condition)
    return result
  }


}


module.exports = User


