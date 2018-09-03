package com.example.chenqiuyang.younginterview.packing.ui;

import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by young on 18-3-22.
 * 创建缓存的Loader
 */

public class LoaderCreator {
    private static final WeakHashMap<String,Indicator> LOADING_MAP = new WeakHashMap<>();

    //缓存添加LoadingView
    static AVLoadingIndicatorView create(String type, Context context){
        final AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        if(LOADING_MAP.get(type) == null){
            final Indicator indicator = getIndicator(type);
            LOADING_MAP.put(type,indicator);
        }

        avLoadingIndicatorView.setIndicator(LOADING_MAP.get(type));
        return avLoadingIndicatorView;
    }


    //用反射获取loader的包名,创建Indicator实例
    private static Indicator getIndicator(String name) {
        if(name == null || name.isEmpty()){
            return null;
        }

        final StringBuilder drawableClassName = new StringBuilder();
        if(!name.contains(".")){
            final String defaultPackageName = AVLoadingIndicatorView.class.getPackage().getName();
            drawableClassName.append(defaultPackageName)
                    .append(".indicators")
                    .append(".");
        }

        drawableClassName.append(name);

        try {
            final Class<?> drawClass = Class.forName(drawableClassName.toString());
            return (Indicator) drawClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
}
