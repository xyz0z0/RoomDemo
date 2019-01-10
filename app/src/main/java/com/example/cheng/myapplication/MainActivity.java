package com.example.cheng.myapplication;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.cheng.myapplication.room.AddressDao;
import com.example.cheng.myapplication.room.ConnectDao;
import com.example.cheng.myapplication.room.DataBase;
import com.example.cheng.myapplication.room.User;
import com.example.cheng.myapplication.room.UserDao;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

  DataBase dataBase;
  AddressDao addressDao;
  ConnectDao connectDao;
  UserDao userDao;

  Button btnAddUser;
  Button btnAdd;
  Button btnAddFlow;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btnAddUser = findViewById(R.id.btn_add_user);
    dataBase = DataBase.getInstance(this);
    addressDao = dataBase.getAddressDao();
    connectDao = dataBase.getConnectDao();
    userDao = dataBase.getUserDao();

    btnAddUser.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        new Thread(new Runnable() {
          @Override public void run() {

            // List<Address> addresses = connectDao.getAddressForUsers(1);
            // if (addresses.size() > 0) {
            //   for (Address address : addresses) {
            //     Log.d("cxg", "addresses " + address.getStreet());
            //   }
            // } else {
            //   Log.d("cxg", "addresses 为空");
            // }
            //
            // List<User> users = connectDao.getUsersForAddress(1);
            // if (users.size() > 0) {
            //   for (User user : users) {
            //     Log.d("cxg", "users " + user.getFirstName());
            //   }
            // } else {
            //   Log.d("cxg", "users 为空");
            // }
            for (int i = 0; i < 30; i++) {
              Random r = new Random();
              int i1 = r.nextInt(20);
              Log.d("cxg-r", "i1 " + i1);
            }
            Random r = new Random();
            int i1 = r.nextInt(20);
            User user = new User("姓名 " + i1);
            long[] l = userDao.insert(user);
            Log.d("cxg", "long " + l.length);
            Log.d("cxg", "long " + l[0]);

          }
        }).start();

      }
    });

    new Thread(new Runnable() {
      @Override public void run() {
        List<User> users = userDao.queryAllUser();
        Log.d("cxg", "queryAllUser " + users.size());
      }
    }).start();

    Disposable d1 =  Observable.just(1)
        .map(new Function<Integer, Object>() {
          @Override public Object apply(Integer integer) throws Exception {
            List<User> list = userDao.queryAllUser();
            Log.d("cxg","list "+list.size());
            return 1;
          }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<Object>() {
          @Override public void accept(Object o) throws Exception {

          }
        });

    Disposable d = userDao.queryAllUserFlowable()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<List<User>>() {
          @Override public void accept(List<User> users) throws Exception {
            Log.d("cxg", "queryAllUserFlowable " + users.size());
          }
        });
    // d.dispose();

    // new Thread(new Runnable() {
    //   @Override public void run() {
    //     Address address = new Address("合肥1");
    //     List<String> details = new ArrayList<>();
    //     details.add("test1-1");
    //     details.add("test1-2");
    //     Detail detail = new Detail("测试1", "测试2", "测试3", details);
    //     address.setDetail(detail);
    //     addressDao.insert(address);
    //     User user = new User("合肥1人");
    //     userDao.insert(user);
    //     Connect connect = new Connect(1, 2);
    //     connectDao.insert(connect);
    //
    //   }
    // }).start();
  }

}
