package com.operatortokenocb.network
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseRetrofitClient {
    abstract fun recommenderUrl(): String

    fun createRetrofitClient(url: String): Retrofit {
        val gson = GsonBuilder()
            .serializeNulls()
            .setLenient()
            .create()
        val client: OkHttpClient = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        const val PARTNER_APPLICATION_URL = "https://lbl-partmgmr.superdtaglb.cf/"
    }
}