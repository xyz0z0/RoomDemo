package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by chengxg on 2019/1/10
 */
@Entity
public class Address {

  @PrimaryKey(autoGenerate = true)
  public int id;
  public String street;
  @Embedded(prefix = "test")
  public Detail detail;


  public Address(String street) {
    this.street = street;
  }


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public String getStreet() {
    return street;
  }


  public void setStreet(String street) {
    this.street = street;
  }


  public Detail getDetail() {
    return detail;
  }


  public void setDetail(Detail detail) {
    this.detail = detail;
  }
}
