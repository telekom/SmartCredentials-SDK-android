package com.operatortokenocb.network;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PartnerManagementApi {

    @GET("access-token/{credentials}")
    Observable<String> observeAccessToken(@Path("credentials") String credentials);

    @POST("bearer-token-hackathon")
    Observable<String> observeBearerToken(@Body GetBearerBody body);
}
