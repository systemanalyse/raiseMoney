var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var session = require('express-session');
var FileStore = require('session-file-store')(session);

var GetPrivaryUser = require('../controller/GetPrivaryUser')
var GetPublicUser = require('../controller/GetPublicUser')
var UpdateUser = require('../controller/UpdateUser')
var GetWallet = require('../controller/GetWallet')
var ChangeWallet = require('../controller/ChangeWallet')
var GetNotice = require('../controller/GetNotice')
var ReadNotice = require('../controller/ReadNotice')
var GetSetting = require('../controller/GetSetting')

router.use(session({
  secret: 'userid', // 用来对session id相关的cookie进行签名
  store: new FileStore(), // 本地存储session（文本文件，也可以选择其他store，比如redis的）
  saveUninitialized: false, // 是否自动保存未初始化的会话，建议false
  resave: false, // 是否每次都重新保存会话，建议false
  cookie: {
    maxAge: 24 * 60 * 60 * 1000 // 有效期，单位是毫秒
  }
}));

router.use('/', bodyParser.json());
router.use(bodyParser.urlencoded({
  extended: true
}));

/* GET users listing. */
router.get('/:userid/Privary', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetPrivaryUser(req.params.userid)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/:userid/Privary', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await UpdateUser(req.params.userid, req.body)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/:userid/Public', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(403).send('Request refused')
  }
  let result = await GetPublicUser(req.params.userid)
  res.status(result['status']).send(result['data'])
})

router.get('/:userid/Wallet', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetWallet(req.params.userid)
    res.status(result['status']).send(result['data'])
  }

})

router.put('/:userid/Wallet', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await ChangeWallet(req.params.userid, req.body.Jin)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/:userid/Notice', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetNotice(req.params.userid)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/:userid/ReadNotice', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await ReadNotice(req.params.userid, req.body.noticeid)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/:userid/Settings/Overview', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetSetting(req.session.userid)
    res.status(result['status']).send(result['data'])
  }
})

module.exports = router;