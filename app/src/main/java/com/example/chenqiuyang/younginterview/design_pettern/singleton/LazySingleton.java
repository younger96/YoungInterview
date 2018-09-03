package com.example.chenqiuyang.younginterview.design_pettern.singleton;

/**
 * Created by young on 18-3-15.
 * 懒汉模式
 * 有实现延时加载,但是线程不安全
 */

public class LazySingleton {
    private static LazySingleton instance;
    private LazySingleton (){}

    public static LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

}
