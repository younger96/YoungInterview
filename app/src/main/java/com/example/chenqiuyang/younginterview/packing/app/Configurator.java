package com.example.chenqiuyang.younginterview.packing.app;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by young on 18-3-23.
 * 统一管理资源
 */

public final class Configurator {
    private static final HashMap<Object, Object> APP_CONFIGS = new HashMap<>();

    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();


    public Configurator() {

    }

    static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }


    //****拦截器
    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        APP_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        APP_CONFIGS.put(ConfigKeys.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    //****拦截器

//
//    private void checkConfiguration() {
//        final boolean isReady = (boolean) APP_CONFIGS.get(ConfigKeys.CONFIG_READY);
//        if (!isReady) {
//            throw new RuntimeException("Configuration is not ready,call configure");
//        }
//    }
//
//
//    @SuppressWarnings("unchecked")
//    final <T> T getConfiguration(Object key) {
//        checkConfiguration();
//        final Object value = APP_CONFIGS.get(key);
//        if (value == null) {
//            throw new NullPointerException(key.toString() + " IS NULL");
//        }
//        return (T) APP_CONFIGS.get(key);
//    }

}
