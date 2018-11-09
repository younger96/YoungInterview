package com.example.chenqiuyang.younginterview.customView.view.brick.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * BrickItem点击访问注解
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnBrickItemClick {
    /**
     * 组件类型
     *
     * @return
     */
    String value();
}
