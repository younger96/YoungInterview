package com.example.chenqiuyang.younginterview.packing.network;



import com.example.chenqiuyang.younginterview.packing.network.rx.RxRestService;

import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * Created by young on 18-3-21.
 */

public class RestCreator {
    /**
     * 参数容器
     */
    private static final class ParamsHolder {
        private static final WeakHashMap<String, Object> PARAMS = new WeakHashMap<>();
    }

    public static WeakHashMap<String, Object> getParams() {
        return ParamsHolder.PARAMS;
    }


    /**
     * Retrofit  service接口
     * @return
     */
    public static  RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }


    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    /**
     * Rx  service接口
     * @return
     */
    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.REST_SERVICE;
    }


    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }


    public static final  class RetrofitHolder{
        private static final String BASE_URL = "http://route.showapi.com/9-3/";
        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    private static final class OkHttpHolder{
        private static final int TIME_OUT = 60;
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
//        private static final ArrayList<Interceptor> INTERCEPTORS
//                = App.getConfiguration(ConfigKeys.INTERCEPTOR);

        //添加拦截器
        private static OkHttpClient.Builder addInterceptor(){
//            if(INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty()){
//                for (Interceptor interceptor : INTERCEPTORS){
//                    BUILDER.addInterceptor(interceptor);
//                }
//            }
            return BUILDER;
        }


        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }


}
