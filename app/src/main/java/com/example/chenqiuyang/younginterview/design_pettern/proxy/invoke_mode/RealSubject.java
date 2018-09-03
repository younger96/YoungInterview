package com.example.chenqiuyang.younginterview.design_pettern.proxy.invoke_mode;

/**
 * 委托类
 */
class RealSubject implements Subject{
    public void request(){
        System.out.println("====RealSubject Request====");
    }
}
