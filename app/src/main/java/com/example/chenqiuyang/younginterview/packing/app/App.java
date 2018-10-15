package com.example.chenqiuyang.younginterview.packing.app;

import android.app.Application;

/**
 * Created by young on 18-3-22.
 */

public class App extends Application{

    private static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp =this;

    }

    public static App getApplicationInstance(){
        return sApp;
    }

//    public static Configurator getConfigurator() {
//        return Configurator.getInstance();
//    }
//
//    public static <T> T getConfiguration(Object key) {
//        return getConfigurator().getConfiguration(key);
//    }

}
