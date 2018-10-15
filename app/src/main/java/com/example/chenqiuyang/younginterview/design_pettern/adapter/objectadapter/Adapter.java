package com.example.chenqiuyang.younginterview.design_pettern.adapter.objectadapter;

class Adapter implements Target{
    // 直接关联被适配类  
    private Adaptee adaptee;  

    // 可以通过构造函数传入具体需要适配的被适配类对象  
    public Adapter (Adaptee adaptee) {  
        this.adaptee = adaptee;  
    }  

    @Override
    public void Request() {  
        // 这里是使用委托的方式完成特殊功能  
        this.adaptee.SpecificRequest();  
    }  
}  