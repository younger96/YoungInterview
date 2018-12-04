package com.example.chenqiuyang.younginterview.util;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.chenqiuyang.younginterview.cache.greendao.DaoMaster;
import com.example.chenqiuyang.younginterview.cache.greendao.DaoSession;
import com.squareup.leakcanary.LeakCanary;

public class MyApp extends Application {
    private DaoSession daoSession;
    private DaoMaster daoMaster;
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        initGreenDAO();
        mContext = this;
    }

    public static Context getmContext(){
        return mContext;
    }

    private void initGreenDAO() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"test.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
    }


    /**
     * 获取DaoSession
     * @return
     */
    public DaoSession getUserDaoSession(){
        if (daoSession == null){
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}
