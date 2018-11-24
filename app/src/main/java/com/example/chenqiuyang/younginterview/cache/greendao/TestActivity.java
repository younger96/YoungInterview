package com.example.chenqiuyang.younginterview.cache.greendao;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.example.chenqiuyang.younginterview.R;
import com.example.chenqiuyang.younginterview.util.MyApp;

import java.util.List;

/**
 * 2018/11/24
 * from 陈秋阳
 * 功能描述：
 */
public class TestActivity extends Activity {
    private static int id = 0 ;
    private static final String TAG = "TestActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dao_test);
        MyApp myApp = (MyApp) getApplication();
        DaoSession daoSession =  myApp.getUserDaoSession();
        final UserDao userDao = daoSession.getUserDao();



        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: add");
                id++;
                User user = new User();
                user.setId(id);
                user.setName("小明"+id);
                user.setAge(16);
                userDao.insert(user); //userDao.insertOrReplace(user);
            }
        });


        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: remove");
//                User user = new User();
//                user.setId(id);
//                userDao.delete(user);
                userDao.deleteByKey((long)id);
                id--;
            }
        });


        findViewById(R.id.print).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: print");
                List<User> users =  userDao.loadAll();
                for (int i = 0; i < users.size(); i++) {
                    Log.i(TAG, "onClick: "+users.get(i).toString());
                }
            }
        });
    }
}
