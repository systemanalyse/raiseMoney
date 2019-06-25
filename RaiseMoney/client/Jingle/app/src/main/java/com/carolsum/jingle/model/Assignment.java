package com.carolsum.jingle.model;

import java.io.Serializable;
import java.util.List;

public class Assignment implements Serializable {
  /**
   *  [
   *   {
   *     "taskId": "string",
   *     "origin": 0,
   *     "taskStatus": true,
   *     "taskType": true,
   *     "status": "string",
   *     "beginTime": "string",
   *     "value": 0,
   *     "title": "string",
   *     "desc": "string",
   *     "time": "string",
   *     "publisherInfo": {
   *     "schema": {
   *       "userId": 0,
   *         "email": "string",
   *         "name": "string",
   *         "dormitory": "string",
   *         "signature": "string",
   *         "acceptNum": 0,
   *         "publishNum": 0,
   *         "phone": "string"
   *     }
   *   },
   *     "accepterInfo": {
   *     "schema": [
   *     {
   *       "userId": 0,
   *       "email": "string",
   *       "name": "string",
   *       "dormitory": "string",
   *       "signature": "string",
   *       "acceptNum": 0,
   *       "publishNum": 0,
   *       "phone": "string"
   *     }
   *       ]
   *   },
   *     "startPosition": "string",
   *     "endPosition": "string",
   *     "ddl": "string",
   *     "finishNum": 0,
   *     "totalNum": 0
   *   }
   * ]
   */


  public int taskid;
  public int userid;
  /**
   * @attr origin 表示任务类型：1 for 发布, 2 for 接受, 0 for 其他
   */
  public int origin;
  /**
   * taskStatus: running(1) & finish(0)
   */
  public int taskStatus;
  /**
   * taskType: PP(1) & DD(0)
   */
  public int taskType;
  /**
   * statusCode 应该是一个枚举值
   * 0: 待接单
   * 1: 进行中
   * 2：待确认
   * 3：已完成
   * 4：已超期
   * 5：未按时
   */
  public int statusCode;
  /**
   * publish time
   */
  public String beginTime;
  public String value;
  public String title;
  public String desc;

  public List<String> finishor;
  public List<User> acceptor;

  public String startPosition;
  public String endPosition;
  public String ddl;
  public int finishNum;
  public int totalNum;

  public User publishorInfo;

  public Assignment(int taskid, int userid, int origin, int taskStatus, int taskType, int statusCode, String beginTime, String value, String title, String desc, List<String> finishor, List<User> acceptor, String startPosition, String endPosition, String ddl, int finishNum, int totalNum) {
    this.taskid = taskid;
    this.userid = userid;
    this.origin = origin;
    this.taskStatus = taskStatus;
    this.taskType = taskType;
    this.statusCode = statusCode;
    this.beginTime = beginTime;
    this.value = value;
    this.title = title;
    this.desc = desc;
    this.finishor = finishor;
    this.acceptor = acceptor;
    this.startPosition = startPosition;
    this.endPosition = endPosition;
    this.ddl = ddl;
    this.finishNum = finishNum;
    this.totalNum = totalNum;
  }

  public int getTaskid() {
    return taskid;
  }

  public void setTaskid(int taskid) {
    this.taskid = taskid;
  }

  public int getUserid() {
    return userid;
  }

  public void setUserid(int userid) {
    this.userid = userid;
  }

  public int getOrigin() {
    return origin;
  }

  public void setOrigin(int origin) {
    this.origin = origin;
  }

  public int getTaskStatus() {
    return taskStatus;
  }

  public void setTaskStatus(int taskStatus) {
    this.taskStatus = taskStatus;
  }

  public int getTaskType() {
    return taskType;
  }

  public void setTaskType(int taskType) {
    this.taskType = taskType;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(String beginTime) {
    this.beginTime = beginTime;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public List<String> getFinishor() {
    return finishor;
  }

  public void setFinishor(List<String> finishor) {
    this.finishor = finishor;
  }

  public List<User> getAcceptor() {
    return acceptor;
  }

  public void setAcceptor(List<User> acceptor) {
    this.acceptor = acceptor;
  }

  public String getStartPosition() {
    return startPosition;
  }

  public void setStartPosition(String startPosition) {
    this.startPosition = startPosition;
  }

  public String getEndPosition() {
    return endPosition;
  }

  public void setEndPosition(String endPosition) {
    this.endPosition = endPosition;
  }

  public String getDdl() {
    return ddl;
  }

  public void setDdl(String ddl) {
    this.ddl = ddl;
  }

  public int getFinishNum() {
    return finishNum;
  }

  public void setFinishNum(int finishNum) {
    this.finishNum = finishNum;
  }

  public int getTotalNum() {
    return totalNum;
  }

  public void setTotalNum(int totalNum) {
    this.totalNum = totalNum;
  }

  public User getPublishorInfo() {
    return publishorInfo;
  }

  public void setPublishorInfo(User publishorInfo) {
    this.publishorInfo = publishorInfo;
  }
}
