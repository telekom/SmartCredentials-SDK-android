package com.example.oneclickbusinessclient.rest;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RecommenderApi {

    @POST("recommendations")
    Observable<Response<Void>> observeMakeRecommendations(@Body MakeRecommendationsBody body);
}
