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
   * 0: 待确认
   * 1: 待接单
   * 2：已接单
   * 3：进行中
   * 4：已完成
   * 5：已超期
   * 6：未按时
   */
  public int status;
  public String time;
  public String startPos;
  public String endPos;
  public int finishNum;
  public int totalNum;

  public Assignment(String title, int type, int value, int status, String time, String startPos, String endPos, int finishNum, int totalNum) {
    this.title = title;
    this.type = type;
    this.value = value;
    this.status = status;
    this.time = time;
    this.startPos = startPos;
    this.endPos = endPos;
    this.finishNum = finishNum;
    this.totalNum = totalNum;
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

  public String getStartPos() {
    return startPos;
  }

  public void setStartPos(String startPos) {
    this.startPos = startPos;
  }

  public String getEndPos() {
    return endPos;
  }

  public void setEndPos(String endPos) {
    this.endPos = endPos;
  }

  public Assignment(String title, int type, int value, int status, String time, String startPos, String endPos) {
    this.title = title;
    this.type = type;
    this.value = value;
    this.status = status;
    this.time = time;
    this.startPos = startPos;
    this.endPos = endPos;
  }

  public Assignment(String title, int type, int value, int status, String time) {
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

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }
}
