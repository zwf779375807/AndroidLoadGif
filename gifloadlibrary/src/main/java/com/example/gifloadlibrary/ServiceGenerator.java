package com.example.gifloadlibrary;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ziv on 2017/10/20.
 */

public class ServiceGenerator {

    private static final String HOST = "http://203.110.166.200:3000";
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    public static <T> T createDownLoadService(Class<T> clazz) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggerInterceptor("tag")).build();
        return builder
                .client(client)
                .build()
                .create(clazz);
    }
}
