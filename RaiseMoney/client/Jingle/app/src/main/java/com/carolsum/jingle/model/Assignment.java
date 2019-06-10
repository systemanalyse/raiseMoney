package com.carolsum.jingle.model;

public class Assignment {
  public String title;
  /**
   * @attr type 表示任务类型：0 for 跑跑, 1 for 点点
   */
  public int type;
  public int value;
  /**
   * statue 应该是一个枚举值
   */
  public String status;
  public String time;

  public Assignment(String title, int type, int value, String status, String time) {
    this.title = title;
    this.type = type;
    this.value = value;
    this.status = status;
    this.time = time;
  }

  public Assignment() {
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
