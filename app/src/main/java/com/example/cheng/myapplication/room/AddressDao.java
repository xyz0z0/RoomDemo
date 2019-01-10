package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

/**
 * Created by chengxg on 2019/1/10
 */
@Dao
public interface AddressDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long[] insert(Address... addresses);


  @Query("DELETE FROM address WHERE id = :id")
  int delete(int id);

}
