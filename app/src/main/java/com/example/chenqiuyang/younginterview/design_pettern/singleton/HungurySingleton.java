package com.example.chenqiuyang.younginterview.design_pettern.singleton;


/*
饿汉模式,没有延时加载,线程不安全
 */
public class HungurySingleton {
    //创建 HungurySingleton 的一个对象
    private static HungurySingleton instance = new HungurySingleton();

    //让构造函数为 private，这样该类就不会被实例化
    private HungurySingleton() {
    }

    //获取唯一可用的对象
    public static HungurySingleton getInstance() {
        return instance;
    }
}
