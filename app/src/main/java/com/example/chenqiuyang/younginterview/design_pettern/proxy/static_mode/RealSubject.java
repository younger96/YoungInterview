package com.example.chenqiuyang.younginterview.design_pettern.proxy.static_mode;

class RealSubject implements Subject{
    public void request(){
        System.out.println("request");
    }
}