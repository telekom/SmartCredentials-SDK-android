package com.example.oneclickbusinessclient.recommendation;

public class Recommendation {

    private String recommendationId;
    private String clientId;

    public Recommendation(String recommendationId, String clientId) {
        this.recommendationId = recommendationId;
        this.clientId = clientId;
    }

    public String getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(String recommendationId) {
        this.recommendationId = recommendationId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
