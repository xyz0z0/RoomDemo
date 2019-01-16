package com.example.cheng.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.cheng.myapplication.room.Address;
import com.example.cheng.myapplication.room.AddressDao;
import com.example.cheng.myapplication.room.Connect;
import com.example.cheng.myapplication.room.ConnectDao;
import com.example.cheng.myapplication.room.DataBase;
import com.example.cheng.myapplication.room.User;
import com.example.cheng.myapplication.room.UserDao;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.cheng.myapplication.room.DataBase.DB_NAME;

public class MainActivity extends AppCompatActivity {

  DataBase dataBase;
  AddressDao addressDao;
  ConnectDao connectDao;
  UserDao userDao;

  Button btnAddUser;
  Button btnAdd;
  Button btnAddFlow;
  private Button btnDelete;
  private Button btnMoveDb;
  private Button btnRestoreDb;

  private long deleteId;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // rxDemo();
    // rx 订阅的时候如果 query 不返回 flowable 的话则不会响应式更新

    btnAddUser = findViewById(R.id.btn_add_user);
    btnDelete = findViewById(R.id.btn_delete);
    btnMoveDb = findViewById(R.id.btn_move_db);
    btnRestoreDb = findViewById(R.id.btn_restore_db);
    dataBase = DataBase.getInstance(this);
    addressDao = dataBase.getAddressDao();
    connectDao = dataBase.getConnectDao();
    userDao = dataBase.getUserDao();

    btnRestoreDb.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        new Thread(new Runnable() {
          @Override public void run() {
            dataBase.close();
            restoreDb();
            dataBase = DataBase.getInstance(MainActivity.this);
            addressDao = dataBase.getAddressDao();
            connectDao = dataBase.getConnectDao();
            userDao = dataBase.getUserDao();
          }
        }).start();

      }
    });

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
            List<User> users = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
              Random r = new Random();
              int i1 = r.nextInt(20);
              // Log.d("cxg-r", "i1 " + i1);
              User user = new User(i + " - 姓名 - " + i1);
              users.add(user);
            }
            User[] users1 = new User[users.size()];
            long[] l = userDao.insert(users.toArray(users1));
            // deleteId = l[19];
            // Log.d("cxg", "long length " + l.length);
            // Log.d("cxg", "long [0] " + l[19]);

            List<Address> addresses = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
              Random r = new Random();
              int i1 = r.nextInt(20);
              // Log.d("cxg-r", "i1 " + i1);
              Address address = new Address(i + " - 地址 - " + i1);
              addresses.add(address);
            }
            Address[] addresses1 = new Address[addresses.size()];
            long[] l1 = addressDao.insert(addresses.toArray(addresses1));

            Connect connect1 = new Connect(1, 2);
            Connect connect2 = new Connect(1, 3);
            Connect connect3 = new Connect(1, 4);
            Connect connect4 = new Connect(2, 2);
            connectDao.insert(connect1, connect2, connect3, connect4);

          }
        }).start();

      }
    });

    // 删除的时候只要 pojo id 是一样的就可以，在数据库里面的 id
    btnDelete.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        new Thread(new Runnable() {
          @Override public void run() {
            User user = new User((int) deleteId);
            int i = userDao.delete(user);
            Log.d("cxg", deleteId + " 删除  " + i);
            Connect connect1 = new Connect(1, 2);
            Connect connect2 = new Connect(1, 3);
            int c = connectDao.delete(connect1, connect2);
            Log.d("cxg", c + " 删除 Connect  ");
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

    Disposable d1 = Observable.just(1)
        .map(new Function<Integer, Object>() {
          @Override public Object apply(Integer integer) throws Exception {
            List<User> list = userDao.queryAllUser();
            Log.d("cxg", "list " + list.size());
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

    // move to sd
    btnMoveDb.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {

        moveDbToSd();
      }
    });

  }


  private void moveDbToSd() {
    try {
      File sd = Environment.getExternalStorageDirectory();
      File data = Environment.getDataDirectory();

      if (sd.canWrite()) {
        String packageName = BuildConfig.APPLICATION_ID;

        String currentDBPath = "//data//" + packageName + "//databases//" + DB_NAME;
        String backupDBPath = DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);

        if (currentDB.exists()) {
          FileChannel src = new FileInputStream(currentDB).getChannel();
          FileChannel dst = new FileOutputStream(backupDB).getChannel();
          dst.transferFrom(src, 0, src.size());
          src.close();
          dst.close();
        }
      }
      if (sd.canWrite()) {
        String packageName = BuildConfig.APPLICATION_ID;

        String currentDBPath = "//data//" + packageName + "//databases//" + DB_NAME + "-shm";
        String backupDBPath = DB_NAME + "-shm";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);

        if (currentDB.exists()) {
          FileChannel src = new FileInputStream(currentDB).getChannel();
          FileChannel dst = new FileOutputStream(backupDB).getChannel();
          dst.transferFrom(src, 0, src.size());
          src.close();
          dst.close();
        }
      }
      if (sd.canWrite()) {
        String packageName = BuildConfig.APPLICATION_ID;

        String currentDBPath = "//data//" + packageName + "//databases//" + DB_NAME + "-wal";
        String backupDBPath = DB_NAME + "-wal";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);

        if (currentDB.exists()) {
          FileChannel src = new FileInputStream(currentDB).getChannel();
          FileChannel dst = new FileOutputStream(backupDB).getChannel();
          dst.transferFrom(src, 0, src.size());
          src.close();
          dst.close();
        }
      }
    } catch (Exception e) {
    }
  }


  private void restoreDb() {
    try {
      File sd = Environment.getExternalStorageDirectory();
      File data = Environment.getDataDirectory();

      if (sd.canWrite()) {
        String packageName = BuildConfig.APPLICATION_ID;

        String currentDBPath = "//data//" + packageName + "//databases//" + DB_NAME;
        String backupDBPath = DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);

        if (backupDB.exists()) {
          FileChannel src = new FileInputStream(backupDB).getChannel();
          FileChannel dst = new FileOutputStream(currentDB).getChannel();
          dst.transferFrom(src, 0, src.size());
          src.close();
          dst.close();
        }
      }
      if (sd.canWrite()) {
        String packageName = BuildConfig.APPLICATION_ID;

        String currentDBPath = "//data//" + packageName + "//databases//" + DB_NAME + "-shm";
        String backupDBPath = DB_NAME + "-shm";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);

        if (backupDB.exists()) {
          FileChannel src = new FileInputStream(backupDB).getChannel();
          FileChannel dst = new FileOutputStream(currentDB).getChannel();
          dst.transferFrom(src, 0, src.size());
          src.close();
          dst.close();
        }
      }
      if (sd.canWrite()) {
        String packageName = BuildConfig.APPLICATION_ID;

        String currentDBPath = "//data//" + packageName + "//databases//" + DB_NAME + "-wal";
        String backupDBPath = DB_NAME + "-wal";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);

        if (backupDB.exists()) {
          FileChannel src = new FileInputStream(backupDB).getChannel();
          FileChannel dst = new FileOutputStream(currentDB).getChannel();
          dst.transferFrom(src, 0, src.size());
          src.close();
          dst.close();
        }
      }
    } catch (Exception e) {
    }
  }

  // private void rxDemo() {
  //   Observable.interval(2, 2, TimeUnit.SECONDS)
  //       .map(new Function<Long, Long>() {
  //         @Override public Long apply(Long aLong) throws Exception {
  //           if (aLong == 5) {
  //             return null;
  //           }
  //           return aLong;
  //         }
  //       })
  //       .retry()
  //       .subscribeOn(Schedulers.io())
  //       .subscribe(new Consumer<Long>() {
  //         @Override public void accept(Long aLong) throws Exception {
  //           Log.d("cxg", "long " + aLong);
  //         }
  //       }, new Consumer<Throwable>() {
  //         @Override public void accept(Throwable throwable) throws Exception {
  //           throwable.printStackTrace();
  //         }
  //       });
  // }

}
