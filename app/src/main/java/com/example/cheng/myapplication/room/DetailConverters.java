package com.example.cheng.myapplication.room;

import android.arch.persistence.room.TypeConverter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chengxg on 2019/1/10
 */
public class DetailConverters {
  @TypeConverter
  public static List<String> strToList(String value) {
    // List<String> myList1 = new ArrayList<String>(Arrays.asList(value.split(",")));
    List<String> myList2 = Arrays.asList(value.split(","));
    // List<String> lst = List.of(s.split(","));
    // return value == null ? null : new Date(value);
    return myList2;
  }


  @TypeConverter
  public static String listToStr(List<String> list) {
    String str = "";
    for (String s : list) {
      str += s + ",";
    }
    return str;
  }

}
