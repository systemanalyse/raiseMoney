package com.carolsum.jingle.model;

import java.io.Serializable;
import java.util.List;

public class Receipt implements Serializable {
  /**
  {
    "imgURL": "string",
    "desc": "string",
    "time": "string"
  }
  */
  public List<String> photourl;
  public String desc;
  public String finishtime;
  public User userinfo;
  public int whetherConfirm;

  public Receipt() {};

  public List<String> getPhotourl() {
    return photourl;
  }

  public void setPhotourl(List<String> photourl) {
    this.photourl = photourl;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public String getFinishtime() {
    return finishtime;
  }

  public void setFinishtime(String finishtime) {
    this.finishtime = finishtime;
  }

  public User getUserinfo() {
    return userinfo;
  }

  public void setUserinfo(User userinfo) {
    this.userinfo = userinfo;
  }

  public int getWhetherConfirm() {
    return whetherConfirm;
  }

  public void setWhetherConfirm(int whetherConfirm) {
    this.whetherConfirm = whetherConfirm;
  }
}
