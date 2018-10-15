package com.example.chenqiuyang.younginterview.algorithm.test;

public class Test {
    public static void main(String[] args) {
        Person p = new Man();
        p.say();
        ((Man) p).sayM();

    }
}
