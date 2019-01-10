package com.example.cheng.myapplication.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

/**
 * Created by chengxg on 2019/1/10
 */
@Entity(
    tableName = "user_repo_join",
    primaryKeys = { "user_id", "address_id" },
    foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "user_id",
                    onUpdate = ForeignKey.CASCADE,
                    onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Address.class, parentColumns = "id", childColumns = "address_id",
                    onUpdate = ForeignKey.CASCADE,
                    onDelete = ForeignKey.CASCADE) },
    indices = { @Index(value = { "user_id" }), @Index(value = { "address_id" }) })
public class Connect {

  // @PrimaryKey(autoGenerate = true)
  // public int id;

  @ColumnInfo(name = "user_id")
  public int userId;

  @ColumnInfo(name = "address_id")
  public int addressId;


  public Connect(int userId, int addressId) {
    this.userId = userId;
    this.addressId = addressId;
  }

  // public int getId() {
  //   return id;
  // }
  //
  //
  // public void setId(int id) {
  //   this.id = id;
  // }


  public int getUserId() {
    return userId;
  }


  public void setUserId(int userId) {
    this.userId = userId;
  }


  public int getAddressId() {
    return addressId;
  }


  public void setAddressId(int addressId) {
    this.addressId = addressId;
  }
}
