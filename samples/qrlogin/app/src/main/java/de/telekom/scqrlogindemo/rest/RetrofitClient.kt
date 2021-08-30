package de.telekom.scqrlogindemo.rest

import com.google.gson.GsonBuilder
import de.telekom.scqrlogindemo.rest.http.HttpClientFactory
import de.telekom.scqrlogindemo.rest.service.AuthenticationService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient(private val httpClientFactory: HttpClientFactory) {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    fun createRetrofitService(url: String): AuthenticationService {
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClientFactory.createHttpClient())
            .baseUrl(url)
            .build().create(AuthenticationService::class.java)
    }
}