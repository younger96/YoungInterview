package com.example.chenqiuyang.younginterview.ipc.serialize;

import java.io.Serializable;
/**
 * User: LJM
 * Date&Time: 2016-02-22 & 14:16
 * Describe: Describe Text
 * serializable的迷人之处在于你只需要对某个类以及它的属性实现Serializable 接口即可。
 * Serializable 接口是一种标识接口（marker interface），这意味着无需实现方法，Java便会对这个对象进行高效的序列化操作。
 * 这种方法的缺点是使用了反射，序列化的过程较慢。
 * 这种机制会在序列化的时候创建许多的临时对象，容易触发垃圾回收。
 */
public class PersonSerial implements Serializable{
    private static final long serialVersionUID = 7382351359868556980L;
    private String name;
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}