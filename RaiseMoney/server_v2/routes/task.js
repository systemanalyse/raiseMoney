var express = require('express');
var router = express.Router();
var bodyParser = require('body-parser');
var session = require('express-session');
var FileStore = require('session-file-store')(session);

var PublishTask = require('../controller/PublishTask')
var GetReceivedTask = require('../controller/GetReceivedTask')
var GetUserPublishTask = require('../controller/GetUserPublishTask')
var GetAllDD = require('../controller/GetAllDD')
var GetAllPP = require('../controller/GetAllPP')
var ReceivePP = require('../controller/ReceivePP')
var ReceiveDD = require('../controller/ReceiveDD')
var FinishPP = require('../controller/FinishPP')
var FinishDD = require('../controller/FinishDD')
var GetFeedback = require('../controller/GetFeedback')
var ConfirmTask = require('../controller/ConfirmTask')
var GetDDValue = require('../controller/GetDDValue')


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


router.post('/Publish/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await PublishTask(req.session.userid, req.body)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/DD', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else {
    let result = await GetAllDD()
    res.status(result['status']).send(result['data'])
  }
})

router.get('/PP', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else {
    let result = await GetAllPP()
    res.status(result['status']).send(result['data'])
  }
})

router.get('/Publish/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetUserPublishTask(req.session.userid)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/Received/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetReceivedTask(req.session.userid)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/ReceiveDD/:taskid/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await ReceiveDD(req.params.taskid, req.params.userid, req.body.photourl)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/ReceivePP/:taskid/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await ReceivePP(req.params.taskid, req.params.userid, req.body.photourl)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/FinishDD/:taskid/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await FinishDD(req.params.taskid, req.params.userid, req.body.photourl)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/FinishPP/:taskid/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await FinishPP(req.params.taskid, req.params.userid, req.body.photourl)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/Feedback/:taskid/:userid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetFeedback(req.params.userid, req.params.taskid)
    res.status(result['status']).send(result['data'])
  }
})

router.put('/Confirm/:taskid/:userid/:fuserid', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await ConfirmTask(req.params.taskid, req.params.userid, req.params.fuserid)
    res.status(result['status']).send(result['data'])
  }
})

router.get('/DD/:taskid/:userid/:number', async (req, res) => {
  if (!!!req.session.userid) {
    res.status(401).send('Unauthorized, need to authorized')
  } else if (req.session.userid != req.params.userid) {
    res.status(403).send('Request refused')
  } else {
    let result = await GetDDValue(req.params.taskid, req.params.userid, req.params.number)
    res.status(result['status']).send(result['data'])
  }
})


module.exports = router;