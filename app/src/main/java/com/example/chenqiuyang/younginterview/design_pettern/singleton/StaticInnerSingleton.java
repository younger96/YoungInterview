package com.example.chenqiuyang.younginterview.design_pettern.singleton;

/**
 * Created by young on 18-3-15.
 * 静态内部类
 * 这种方式能达到双检锁方式一样的功效，但实现更简单。
 * 对静态域使用延迟初始化，应使用这种方式而不是双检锁方式。
 * 这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。
 */

public class StaticInnerSingleton {
    private static class SingletonHolder {
        private static final StaticInnerSingleton INSTANCE = new StaticInnerSingleton();
    }

    private StaticInnerSingleton (){

    }

    public static final StaticInnerSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
