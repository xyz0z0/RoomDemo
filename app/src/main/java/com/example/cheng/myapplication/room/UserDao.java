package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import io.reactivex.Flowable;
import java.util.List;

/**
 * Created by chengxg on 2019/1/10
 */
@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.FAIL)
  long[] insert(User... users);

  @Delete
  int delete(User... users);

  // 只有返回了 Flowable 才会注册观察者，并在后续的更新中自动调用
  @Query("SELECT * FROM user")
  Flowable<List<User>> queryAllUserFlowable();

  @Query("SELECT * FROM user")
  List<User> queryAllUser();

}
