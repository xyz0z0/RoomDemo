package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import java.util.List;

/**
 * Created by chengxg on 2019/1/10
 */
@Dao
public interface ConnectDao {

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  long[] insert(Connect... connects);

  @Delete
  int delete(Connect... connects);

  @Query("SELECT * FROM user INNER JOIN user_repo_join ON user.id=user_repo_join.user_id WHERE user_repo_join.address_id =:addressId")
  List<User> getUsersForAddress(final int addressId);

  @Query("SELECT * FROM address INNER JOIN user_repo_join ON address.id = user_repo_join.address_id WHERE user_repo_join.user_id =:userId")
  List<Address> getAddressForUsers(final int userId);

}
