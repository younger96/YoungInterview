package com.example.chenqiuyang.younginterview.design_pettern.proxy.invoke_mode;

import java.lang.reflect.Proxy;


/**
 * 动态代理：代理类是在运行时生成的。
 * 也就是说 Java 编译完之后并没有实际的 class 文件，
 * 而是在运行时动态生成的类字节码，并加载到JVM中。
 */
public class DynamicProxyDemo01 {
    public static void main(String[] args) {
        RealSubject realSubject = new RealSubject();    //1.创建委托对象
        ProxyHandler handler = new ProxyHandler(realSubject);   //2.创建调用处理器对象
        //3.动态生成代理对象
        Subject proxySubject =
                (Subject) Proxy.newProxyInstance(RealSubject.class.getClassLoader(),
                                                        RealSubject.class.getInterfaces(), handler);
        proxySubject.request(); //4.通过代理对象调用方法
    }
}
