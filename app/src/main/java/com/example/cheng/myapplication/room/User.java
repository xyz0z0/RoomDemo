package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by chengxg on 2019/1/10
 */
@Entity
public class User {

  @PrimaryKey(autoGenerate = true)
  public int id;

  public String firstName;


  public User(String firstName) {
    this.firstName = firstName;
  }


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }


  public String getFirstName() {
    return firstName;
  }


  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
}
