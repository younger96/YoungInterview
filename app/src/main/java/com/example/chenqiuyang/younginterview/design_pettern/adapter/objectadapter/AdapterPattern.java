package com.example.chenqiuyang.younginterview.design_pettern.adapter.objectadapter;

/**
 * 定义具体使用目标类，并通过Adapter类调用所需要的方法从而实现目标
 */
public class AdapterPattern {
    public static void main(String[] args){
        //需要先创建一个被适配类的对象作为参数  
        Target mAdapter = new Adapter(new Adaptee());
        mAdapter.Request();

    }
}