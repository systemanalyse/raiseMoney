package com.carolsum.jingle.model;

public class JNotification {

  public int noticeId;
  public String time;
  public boolean status;

  /**
   * origin: 0 表示发起任务相关, 1 表示接受任务相关, 2 表示钱包相关
   */
  public boolean userType;
  /**
   * type: 0 表示 跑跑; 1 表示 点点; 2 表示其他
   */
  public boolean taskType;

  public String title;
  public String desc;

  public int getNoticeId() {
    return noticeId;
  }

  public void setNoticeId(int noticeId) {
    this.noticeId = noticeId;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public boolean isUserType() {
    return userType;
  }

  public void setUserType(boolean userType) {
    this.userType = userType;
  }

  public boolean isTaskType() {
    return taskType;
  }

  public void setTaskType(boolean taskType) {
    this.taskType = taskType;
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

  public JNotification(int noticeId, String time, boolean status, boolean userType, boolean taskType, String title, String desc) {
    this.noticeId = noticeId;
    this.time = time;
    this.status = status;
    this.userType = userType;
    this.taskType = taskType;
    this.title = title;
    this.desc = desc;
  }
}
