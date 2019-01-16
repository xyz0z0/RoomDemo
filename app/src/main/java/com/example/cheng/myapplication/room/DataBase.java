package com.example.cheng.myapplication.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

/**
 * Created by chengxg on 2019/1/10
 */
@Database(entities = { Connect.class, User.class, Address.class }, version = 1, exportSchema = false)
@TypeConverters({ DetailConverters.class })
public abstract class DataBase extends RoomDatabase {
  public static final String DB_NAME = "User.db";
  private static volatile DataBase instance;


  public static synchronized DataBase getInstance(Context context) {
    // if (instance == null) {
      instance = create(context);
    // }
    return instance;
  }


  private static DataBase create(final Context context) {
    return Room.databaseBuilder(
        context,
        DataBase.class,
        DB_NAME).build();
  }


  public abstract AddressDao getAddressDao();

  public abstract ConnectDao getConnectDao();

  public abstract UserDao getUserDao();

}


