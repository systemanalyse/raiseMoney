package com.carolsum.jingle.model;

import java.io.Serializable;

public class Receipt implements Serializable {
  /**
  {
    "imgURL": "string",
    "desc": "string",
    "time": "string"
  }
  */
  public String imgURL;
  public String desc;
  public String time;

  public String getImgURL() {
    return imgURL;
  }

  public void setImgURL(String imgURL) {
    this.imgURL = imgURL;
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
