package com.example.chenqiuyang.younginterview.butterkinfe;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented // 标记为公共api
@Target(ElementType.FIELD) //修饰对象的范围
@Retention(RetentionPolicy.RUNTIME) //运行时有效
@Inherited //可继承
public @interface MyAnnotation {
    String value() default "";
}