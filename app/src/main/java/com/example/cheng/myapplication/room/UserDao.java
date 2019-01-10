package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

/**
 * Created by chengxg on 2019/1/10
 */
@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long[] insert(User... users);

}
