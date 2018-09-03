package com.example.chenqiuyang.younginterview.sourse.retrofit;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface BlueService {
    @GET("book/search")
    Call<BookSearchResponse> getSearchBooks(@Query("q") String name,
                                            @Query("tag") String tag, @Query("start") int start,
                                            @Query("count") int count);


    @GET("book/search")
    Call<BookSearchResponse> getSearchBooks1(@QueryMap Map map);

}