package com.example.cheng.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.cheng.myapplication.room.Address;
import com.example.cheng.myapplication.room.AddressDao;
import com.example.cheng.myapplication.room.Connect;
import com.example.cheng.myapplication.room.ConnectDao;
import com.example.cheng.myapplication.room.DataBase;
import com.example.cheng.myapplication.room.Detail;
import com.example.cheng.myapplication.room.User;
import com.example.cheng.myapplication.room.UserDao;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  DataBase dataBase;
  AddressDao addressDao;
  ConnectDao connectDao;
  UserDao userDao;

  Button btnDelete;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnDelete = findViewById(R.id.btn_delete);
    dataBase = DataBase.getInstance(this);
    addressDao = dataBase.getAddressDao();
    connectDao = dataBase.getConnectDao();
    userDao = dataBase.getUserDao();

    btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        new Thread(new Runnable() {
          @Override public void run() {
            new Thread(new Runnable() {
              @Override public void run() {

                List<Address> addresses = connectDao.getAddressForUsers(1);
                if (addresses.size() > 0) {
                  for (Address address : addresses) {
                    Log.d("cxg", "addresses " + address.getStreet());
                  }
                } else {
                  Log.d("cxg", "addresses 为空");
                }

                List<User> users = connectDao.getUsersForAddress(1);
                if (users.size() > 0) {
                  for (User user : users) {
                    Log.d("cxg", "users " + user.getFirstName());
                  }
                } else {
                  Log.d("cxg", "users 为空");
                }

              }
            }).start();

          }
        }).start();

      }
    });

    new Thread(new Runnable() {
      @Override public void run() {
        Address address = new Address("合肥1");
        List<String> details = new ArrayList<>();
        details.add("test1-1");
        details.add("test1-2");
        Detail detail = new Detail("测试1", "测试2", "测试3", details);
        address.setDetail(detail);
        addressDao.insert(address);
        User user = new User("合肥1人");
        userDao.insert(user);
        Connect connect = new Connect(1, 2);
        connectDao.insert(connect);

      }
    }).start();
  }

}
