package com.example.chenqiuyang.younginterview.bus.eventbus;

/**
 * 自定义发送消息的类型
 */
public class MyEvent {

    private String mString;

    public MyEvent(String string) {
        mString = string;
    }

    public String getString() {
        return mString;
    }
}
