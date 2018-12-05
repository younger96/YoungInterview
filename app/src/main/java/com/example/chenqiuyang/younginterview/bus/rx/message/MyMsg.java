package com.example.chenqiuyang.younginterview.bus.rx.message;

/**
 * 2018/12/5
 * from 陈秋阳
 * 功能描述：
 */
public class MyMsg {
    private int id;
    private String msg;

    public MyMsg(int id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
