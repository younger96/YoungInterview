package com.example.chenqiuyang.younginterview.design_pettern.proxy.static_mode;


/**
 * 静态代理：代理类是在编译时就实现好的。
 * 也就是说 Java 编译完成后代理类是一个实际的 class 文件。
 */
public class ProxyDemo {
    public static void main(String args[]){
        RealSubject subject = new RealSubject();
        Proxy p = new Proxy(subject);
        p.request();
    }
}