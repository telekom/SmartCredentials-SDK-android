package com.example.oneclickbusinessclient.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MakeRecommendationsBody {

    @SerializedName("firebaseId")
    @Expose
    private String firebaseId;
    @SerializedName("productIds")
    @Expose
    private List<String> productIds;
    @SerializedName("serverKey")
    @Expose
    private String serverKey;

    public MakeRecommendationsBody(String firebaseId, List<String> productIds, String serverKey) {
        this.firebaseId = firebaseId;
        this.productIds = productIds;
        this.serverKey = serverKey;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public List<String> getRecommendations() {
        return productIds;
    }

    public void setRecommendations(List<String> recommendations) {
        this.productIds = recommendations;
    }

    public String getServerKey() {
        return serverKey;
    }

    public void setServerKey(String serverKey) {
        this.serverKey = serverKey;
    }
}
