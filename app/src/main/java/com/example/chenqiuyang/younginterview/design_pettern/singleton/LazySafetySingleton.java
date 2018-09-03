package com.example.chenqiuyang.younginterview.design_pettern.singleton;

/**
 * Created by young on 18-3-15.
 * 懒汉模式线程安全版本
 * 效率低
 */

public class LazySafetySingleton {
    private static LazySafetySingleton instance;
    private LazySafetySingleton (){}
    public static synchronized LazySafetySingleton getInstance() {
        if (instance == null) {
            instance = new LazySafetySingleton();
        }
        return instance;
    }

}
