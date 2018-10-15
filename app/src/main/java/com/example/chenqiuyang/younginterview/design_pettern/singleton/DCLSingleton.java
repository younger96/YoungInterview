package com.example.chenqiuyang.younginterview.design_pettern.singleton;

/**
 * Created by young on 18-3-15.
 * 双重加锁
 * 这种方式采用双锁机制，安全且在多线程情况下能保持高性能。
 */

public class DCLSingleton {
    private volatile static DCLSingleton singleton;//锁1
    private DCLSingleton (){}
    public static DCLSingleton getSingleton() {//锁2
        if (singleton == null) {
            synchronized (DCLSingleton.class) {
                if (singleton == null) {
                    singleton = new DCLSingleton();
                }
            }
        }
        return singleton;
    }
}
