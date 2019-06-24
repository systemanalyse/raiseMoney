const {
  query
} = require('./async-db')

async function selectAllData(sql, value) {
  let dataList = await query(sql, value)
  return dataList
}

function toArraySafe(key) {
  for (let i = 0; i < key.length; i++) {
    key[i] = String(key[i]).replace("'", "\\'").replace("\\", "\\\\")
  }
  return key
}

function toDicSafe(dic) {
  dic_new = {}
  for (let i in dic) {
    dic_new[i] = String(dic[i]).replace("'", "\\'").replace("\\", "\\\\") 
  }
  return dic_new
}

async function insertDB(table, key, value) {
  value = toArraySafe(value)
  let sql = 'insert into ' + table + '('
  for (let i = 0; i < key.length - 1; i++) {
    sql += key[i] + ','
  }
  sql += key[key.length - 1] + ') values('
  for (let i = 0; i < value.length - 1; i++) {
    sql += '?,'
  }
  sql += '?)'
  console.log(sql)
  let result = await selectAllData(sql, value);
  if (result) {
    return result['insertId']
  } else {
    return false
  }
}

async function updateDB(table, key, value, condition) {
  value = toArraySafe(value)
  condition = toDicSafe(condition)
  let sql = 'update ' + table + ' set '
  for (let i = 0; i < key.length - 1; i++) {
    sql += key[i] + '= ?,'
  }
  sql += key[key.length - 1] + '= ? where '
  for (let i in condition) {
    sql += i + '=\'' + condition[i] + '\' and '
  }
  sql += '1=1'
  console.log(sql)
  let result = await selectAllData(sql, value);
  if (result) {
    return true
  } else {
    return false
  }
}

async function queryDB(table, key, condition) {
  condition = toDicSafe(condition)
  let sql = 'select ';
  for (let i = 0; i < key.length - 1; i++) {
    sql += key[i] + ','
  }
  sql += key[key.length - 1] + ' from ' + table + ' where '
  for (let i in condition) {
    sql += i + '=\'' + condition[i] + '\' and '
  }
  sql += '1=1'
  console.log(sql)
  let result = await selectAllData(sql);
  return result
}

async function deleteDB(table, condition) {
  condition = toDicSafe(condition)
  let sql = 'DELETE FROM '
  sql += table + ' where '
  for (let i in condition) {
    sql += i + '=\'' + condition[i] + '\' and '
  }
  sql += '1=1'
  console.log(sql)
  let result = await selectAllData(sql);
  if (result) {
    return true
  } else {
    return false
  }
}

module.exports = {
  insertDB,
  updateDB,
  queryDB,
  deleteDB
}