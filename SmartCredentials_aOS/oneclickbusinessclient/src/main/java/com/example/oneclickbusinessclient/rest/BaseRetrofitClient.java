package com.example.oneclickbusinessclient.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseRetrofitClient {

    public BaseRetrofitClient() {
        // required empty constructor
    }

    public abstract String getRecommenderUrl();

    public Retrofit createRetrofitClient(String url) {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setLenient()
                .create();
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
