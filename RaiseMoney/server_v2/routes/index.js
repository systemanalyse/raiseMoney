var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var session = require('express-session');
var FileStore = require('session-file-store')(session);

var Login = require('../controller/Login')
var Rigist = require('../controller/Regist')
var ConcludeWhetherRunning = require('../controller/ConcludeWhetherRunning')

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

/* GET home page. */
router.post('/login', async (req, res) => {
  if (!!req.session.userid) {
    res.status(406).send({
      'data': 'Have Logined.',
      'userid': req.session.userid
    })
  } else {
    let email = req.body.email
    let password = req.body.password
    let result = await Login(email, password)
    if (result['status'] == 200) {
      req.session.userid = result['data']['userid']
      res.status(200).send(result['data'])
    } else {
      res.status(result['status']).send(result['data'])
    }
  }
})

router.post('/regist', async (req, res) => {
  if (!!req.session.userid) {
    res.status(406).send({
      'data': 'Have Logined.',
      'userid': req.session.userid
    })
  } else {
    let result = await Rigist(req.body)
    if (result['status'] == 200) {
      req.session.userid = result['data']['userid']
      res.status(200).send(result['data'])
    } else {
      res.status(result['status']).send(result['data'])
    }
  }
})

router.get('/robot', async (req, res) => {
  await ConcludeWhetherRunning()
  res.status(200).send('ok')
})

router.get('/logout', async (req, res) => {
  if (!req.session.userid) {
    res.status(406).send({
      'data': 'Have not logined.'
    })
  } else {
    req.session.destroy()
    res.status(200).send({
      'data': 'Successfully'
    })
  }
})

module.exports = router;