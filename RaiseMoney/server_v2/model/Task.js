const {
  insertDB,
  updateDB,
  queryDB,
  deleteDB
} = require('./db.js')

class Task {

  constructor (taskid) {
    this.taskid = taskid
    this.condition = {'id': taskid}
  }

  async insertTask(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await insertDB('task', key, value)
    return result
  }

  async updateTask(values) {
    let key = []
    let value = []
    for (let i in values) {
      key.push(i)
      value.push(values[i])
    }
    let result = await updateDB('task', key, value, this.condition)
    return result
  }

  async queryTask(key) {
    let result = await queryDB('task', key, this.condition)
    return result
  }

  async deleteTask() {
    let result = await deleteDB('task', this.condition)
    return result
  }

}


module.exports = Task


