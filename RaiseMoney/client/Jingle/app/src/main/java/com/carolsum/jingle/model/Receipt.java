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
  public String time;

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

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

}
