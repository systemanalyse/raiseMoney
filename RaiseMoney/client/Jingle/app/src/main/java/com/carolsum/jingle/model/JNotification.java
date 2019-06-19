package com.carolsum.jingle.model;

public class JNotification {
  /**
   * origin: 0 表示发起任务相关, 1 表示接受任务相关, 2 表示钱包相关
   */
  public int origin;
  public String title;
  /**
   * type: 0 表示 跑跑; 1 表示 点点; 2 表示其他
   */
  public int type;
  public String desc;
  public String date;
  public boolean read;

  public int getOrigin() {
    return origin;
  }

  public void setOrigin(int origin) {
    this.origin = origin;
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

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public boolean isRead() {
    return read;
  }

  public void setRead(boolean read) {
    this.read = read;
  }

  public JNotification(int origin, String title, int type, String desc, String date, boolean read) {
    this.origin = origin;
    this.title = title;
    this.type = type;
    this.desc = desc;
    this.date = date;
    this.read = read;
  }
}
