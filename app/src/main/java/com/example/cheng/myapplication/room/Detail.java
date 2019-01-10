package com.example.cheng.myapplication.room;

import java.util.List;

/**
 * Created by chengxg on 2019/1/10
 */
public class Detail {

  public String street;
  public String state;
  public String city;
  public List<String> detail;


  public Detail(String street, String state, String city, List<String> detail) {
    this.street = street;
    this.state = state;
    this.city = city;
    this.detail = detail;
  }


  public String getStreet() {
    return street;
  }


  public void setStreet(String street) {
    this.street = street;
  }


  public String getState() {
    return state;
  }


  public void setState(String state) {
    this.state = state;
  }


  public String getCity() {
    return city;
  }


  public void setCity(String city) {
    this.city = city;
  }


  public List<String> getDetail() {
    return detail;
  }


  public void setDetail(List<String> detail) {
    this.detail = detail;
  }
}
