package com.example.oneclickbusinessclient.rest;

public class RetrofitClient extends BaseRetrofitClient {

    private static final String RECOMMENDER_URL = "http://lbl-recommender.superdtaglb.cf/debug/";

    @Override
    public String getRecommenderUrl() {
        return RECOMMENDER_URL;
    }
}
