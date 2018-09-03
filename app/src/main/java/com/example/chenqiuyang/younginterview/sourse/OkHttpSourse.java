package com.example.chenqiuyang.younginterview.sourse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * TODO:功能说明
 *
 * @author: chenqiuyang
 * @date: 2018-07-31 08:57
 */
public class OkHttpSourse {
    public static void main(String arg[]) {
        System.out.println("hello java");
        OkHttpClient client = new OkHttpClient();

        String url = null  ;
        Request request = new Request.Builder()
                .url(url)
                .build();


        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
