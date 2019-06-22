package com.carolsum.jingle.model;

import java.io.Serializable;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;
  public int userId;
  public String email;
  public String password;
  public String name;
  public int gender;
  public String school;
  public String enrollment;
  public String dormitory;
  public String studentCardURL;
  public String avatarURL;
  public String signature;
  public String phone;
  public String wechat;
  public String qq;
  public int acceptNum;
  public int publishNum;
  public int Jin;

  public User(int userId, String email, String password, String name, int gender, String school, String enrollment, String dormitory, String studentCardURL, String avatarURL, String signature, String phone, String wechat, String qq, int acceptNum, int publishNum, int jin) {
    this.userId = userId;
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.school = school;
    this.enrollment = enrollment;
    this.dormitory = dormitory;
    this.studentCardURL = studentCardURL;
    this.avatarURL = avatarURL;
    this.signature = signature;
    this.phone = phone;
    this.wechat = wechat;
    this.qq = qq;
    this.acceptNum = acceptNum;
    this.publishNum = publishNum;
    Jin = jin;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getGender() {
    return gender;
  }

  public void setGender(int gender) {
    this.gender = gender;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getEnrollment() {
    return enrollment;
  }

  public void setEnrollment(String enrollment) {
    this.enrollment = enrollment;
  }

  public String getDormitory() {
    return dormitory;
  }

  public void setDormitory(String dormitory) {
    this.dormitory = dormitory;
  }

  public String getStudentCardURL() {
    return studentCardURL;
  }

  public void setStudentCardURL(String studentCardURL) {
    this.studentCardURL = studentCardURL;
  }

  public String getAvatarURL() {
    return avatarURL;
  }

  public void setAvatarURL(String avatarURL) {
    this.avatarURL = avatarURL;
  }

  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getWechat() {
    return wechat;
  }

  public void setWechat(String wechat) {
    this.wechat = wechat;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public int getAcceptNum() {
    return acceptNum;
  }

  public void setAcceptNum(int acceptNum) {
    this.acceptNum = acceptNum;
  }

  public int getPublishNum() {
    return publishNum;
  }

  public void setPublishNum(int publishNum) {
    this.publishNum = publishNum;
  }

  public int getJin() {
    return Jin;
  }

  public void setJin(int jin) {
    Jin = jin;
  }
}
