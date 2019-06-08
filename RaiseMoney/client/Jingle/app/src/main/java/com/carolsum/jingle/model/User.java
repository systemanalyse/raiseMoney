package com.carolsum.jingle.model;

public class User {
  public String username;
  public String school;
  public String password;
  public int enrollment;
  public String introduction;
  public String phonenumber;
  public String wechatid;
  public String ppid;
  public int money;
  public String email;
  public int PPPnumber;
  public String sex;
  public int DDPnumber;
  public int PPRnumber;
  public int DDRnumber;
  public boolean online;
  public String dormitorysite;
  public String name;
  public String[] photourl;
  public int id;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getEnrollment() {
    return enrollment;
  }

  public void setEnrollment(int enrollment) {
    this.enrollment = enrollment;
  }

  public String getIntroduction() {
    return introduction;
  }

  public void setIntroduction(String introduction) {
    this.introduction = introduction;
  }

  public String getPhonenumber() {
    return phonenumber;
  }

  public void setPhonenumber(String phonenumber) {
    this.phonenumber = phonenumber;
  }

  public String getWechatid() {
    return wechatid;
  }

  public void setWechatid(String wechatid) {
    this.wechatid = wechatid;
  }

  public String getPpid() {
    return ppid;
  }

  public void setPpid(String ppid) {
    this.ppid = ppid;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getPPPnumber() {
    return PPPnumber;
  }

  public void setPPPnumber(int PPPnumber) {
    this.PPPnumber = PPPnumber;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public int getDDPnumber() {
    return DDPnumber;
  }

  public void setDDPnumber(int DDPnumber) {
    this.DDPnumber = DDPnumber;
  }

  public int getPPRnumber() {
    return PPRnumber;
  }

  public void setPPRnumber(int PPRnumber) {
    this.PPRnumber = PPRnumber;
  }

  public int getDDRnumber() {
    return DDRnumber;
  }

  public void setDDRnumber(int DDRnumber) {
    this.DDRnumber = DDRnumber;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public String getDormitorysite() {
    return dormitorysite;
  }

  public void setDormitorysite(String dormitorysite) {
    this.dormitorysite = dormitorysite;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getPhotourl() {
    return photourl;
  }

  public void setPhotourl(String[] photourl) {
    this.photourl = photourl;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public User() {
  }

  public User(String username, String school, String password, int enrollment, String introduction, String phonenumber, String wechatid, String ppid, int money, String email, int PPPnumber, String sex, int DDPnumber, int PPRnumber, int DDRnumber, boolean online, String dormitorysite, String name, String[] photourl, int id) {
    this.username = username;
    this.school = school;
    this.password = password;
    this.enrollment = enrollment;
    this.introduction = introduction;
    this.phonenumber = phonenumber;
    this.wechatid = wechatid;
    this.ppid = ppid;
    this.money = money;
    this.email = email;
    this.PPPnumber = PPPnumber;
    this.sex = sex;
    this.DDPnumber = DDPnumber;
    this.PPRnumber = PPRnumber;
    this.DDRnumber = DDRnumber;
    this.online = online;
    this.dormitorysite = dormitorysite;
    this.name = name;
    this.photourl = photourl;
    this.id = id;
  }

  public User(String username, String school, String password, int enrollment, String introduction, String phonenumber, String wechatid, String ppid, String email, String sex, String dormitorysite, String name, String[] photourl) {
    this.username = username;
    this.school = school;
    this.password = password;
    this.enrollment = enrollment;
    this.introduction = introduction;
    this.phonenumber = phonenumber;
    this.wechatid = wechatid;
    this.ppid = ppid;
    this.email = email;
    this.sex = sex;
    this.dormitorysite = dormitorysite;
    this.name = name;
    this.photourl = photourl;
  }
}
