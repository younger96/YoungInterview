package com.example.chenqiuyang.younginterview.sourse.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * TODO:功能说明
 * @author: chenqiuyang
 * @date: 2018-07-31 08:58
 */
public class RetrofitTest {

    private static final String TAG = "RetrofitTest";


    public static void main(String arg[]) {
        System.out.println("hello java");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BlueService service = retrofit.create(BlueService.class);



        Call<BookSearchResponse> call = service.getSearchBooks("小王子", "", 0, 3);

        //调用用法1
        try {
            BookSearchResponse response = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //调用用法2
        call.enqueue(new Callback<BookSearchResponse>() {
            @Override
            public void onResponse(Call<BookSearchResponse> call,Response<BookSearchResponse> response) {
                Log.i(TAG, "onResponse: "+"异步请求结果: " + response.body());
            }
            @Override
            public void onFailure(Call<BookSearchResponse> call, Throwable t) {

            }
        });

//        Map<String, String> options = new HashMap<>();
//        options.put("q", "小王子");
//        options.put("tag", null);
//        options.put("start", "0");
//        options.put("count", "3");
//        Call<BookSearchResponse> call1 = service.getSearchBooks1(options);



    }
}
