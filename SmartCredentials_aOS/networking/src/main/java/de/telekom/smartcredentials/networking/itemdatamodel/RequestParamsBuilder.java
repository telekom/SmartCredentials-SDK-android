/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.networking.itemdatamodel;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.telekom.smartcredentials.core.model.request.Method;
import de.telekom.smartcredentials.core.model.request.NetworkConnectionType;
import de.telekom.smartcredentials.core.model.request.RequestParams;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;
import okhttp3.Dns;
import okhttp3.Interceptor;

public class RequestParamsBuilder {

    static final String JSON_MEDIA_TYPE = "application/json";
    static final NetworkConnectionType DEFAULT_NETWORK_CONNECTION = NetworkConnectionType.DEFAULT;
    static final Method DEFAULT_METHOD = Method.GET;

    private String mEndpoint;
    private Map<String, String> mHeaders;
    private Map<String, String> mQueryParams;
    private String mRequestBody;
    private String mRequestBodyMediaType;
    private Method mMethod;
    private NetworkConnectionType mNetworkConnectionType;
    private Map<String, List<String>> mPinsCertificatesMap;
    private boolean mFollowsRedirects;
    private long mTimeoutMillis;
    private List<Interceptor> mInterceptorList;
    private Dns mDns;

    public RequestParamsBuilder() {
        mEndpoint = "";
        mHeaders = new HashMap<>();
        mQueryParams = new HashMap<>();
        mRequestBody = "";
        mRequestBodyMediaType = JSON_MEDIA_TYPE;
        mMethod = DEFAULT_METHOD;
        mNetworkConnectionType = DEFAULT_NETWORK_CONNECTION;
        mFollowsRedirects = true;
        mTimeoutMillis = 0;
        mPinsCertificatesMap = new HashMap<>();
        mInterceptorList = new ArrayList<>();
        mDns = Dns.SYSTEM;
    }

    public RequestParamsBuilder setEndpoint(String endpoint) {
        mEndpoint = endpoint;
        return this;
    }

    public RequestParamsBuilder setHeaders(Map<String, String> headers) {
        mHeaders = headers;
        return this;
    }

    public RequestParamsBuilder setQueryParams(Map<String, String> queryParams) {
        mQueryParams = queryParams;
        return this;
    }

    public RequestParamsBuilder setRequestBody(String requestBody) {
        mRequestBody = requestBody;
        return this;
    }

    public RequestParamsBuilder setRequestBodyMediaType(String requestBodyMediaType) {
        mRequestBodyMediaType = requestBodyMediaType;
        return this;
    }

    public RequestParamsBuilder setRequestMethod(Method method) {
        mMethod = method;
        return this;
    }

    public RequestParamsBuilder setNetworkConnectionType(NetworkConnectionType networkConnectionType) {
        mNetworkConnectionType = networkConnectionType;
        return this;
    }

    public RequestParamsBuilder setPinsCertificatesMap(Map<String, List<String>> pinsCertificates) {
        mPinsCertificatesMap = pinsCertificates;
        return this;
    }

    public RequestParamsBuilder setFollowsRedirects(boolean followsRedirects) {
        mFollowsRedirects = followsRedirects;
        return this;
    }

    public RequestParamsBuilder setTimeoutMillis(long timeoutMillis) {
        mTimeoutMillis = timeoutMillis;
        return this;
    }

    public RequestParamsBuilder setInterceptorList(List<Interceptor> interceptorList) {
        mInterceptorList = interceptorList;
        return this;
    }

    public RequestParamsBuilder setDns(Dns dns) {
        mDns = dns;
        return this;
    }

    public String getEndpoint() {
        return mEndpoint;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public Map<String, String> getQueryParams() {
        return mQueryParams;
    }

    public String getRequestBody() {
        return mRequestBody;
    }

    public String getRequestBodyMediaType() {
        return mRequestBodyMediaType;
    }

    public Method getMethod() {
        return mMethod;
    }

    public NetworkConnectionType getNetworkConnectionType() {
        return mNetworkConnectionType;
    }

    public Map<String, List<String>> getPinsCertificatesMap() {
        return mPinsCertificatesMap;
    }

    public boolean followsRedirects() {
        return mFollowsRedirects;
    }

    public long getTimeoutMillis() {
        return mTimeoutMillis;
    }

    public List<Interceptor> getInterceptorList() {
        return mInterceptorList;
    }

    public Dns getDns() {
        return mDns;
    }

    public RequestParams build() {
        if (TextUtils.isEmpty(getEndpoint())) {
            throw new EnvelopeException(EnvelopeExceptionReason.REQUEST_PARAMS_EXCEPTION);
        }

        return new RequestParams.Builder()
                .setEndpoint(getEndpoint())
                .setHeaders(getHeaders())
                .setQueryParams(getQueryParams())
                .setRequestBody(getRequestBody())
                .setRequestBodyMediaType(getRequestBodyMediaType())
                .setMethod(getMethod())
                .setNetworkConnectionType(getNetworkConnectionType())
                .setPinsCertificatesMap(getPinsCertificatesMap())
                .setFollowsRedirects(followsRedirects())
                .setTimeoutMillis(getTimeoutMillis())
                .setInterceptorList(getInterceptorList())
                .setDns(getDns())
                .build();
    }
}
