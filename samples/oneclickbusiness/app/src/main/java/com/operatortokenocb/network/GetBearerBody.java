package com.operatortokenocb.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBearerBody {

    @SerializedName("accessToken")
    @Expose
    private String accessToken;
    @SerializedName("bundleId")
    @Expose
    private String bundleId;
    @SerializedName("clientId")
    @Expose
    private String clientId;
    @SerializedName("packageName")
    @Expose
    private String packageName;

    public GetBearerBody(String accessToken, String bundleId, String clientId, String packageName) {
        this.accessToken = accessToken;
        this.bundleId = bundleId;
        this.clientId = clientId;
        this.packageName = packageName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
