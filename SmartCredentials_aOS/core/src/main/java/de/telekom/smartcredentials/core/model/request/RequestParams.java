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

package de.telekom.smartcredentials.core.model.request;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

public class RequestParams {

    protected final String mEndpoint;
    protected final Map<String, String> mHeaders;
    protected final Map<String, String> mQueryParams;
    protected final boolean mFollowsRedirects;
    protected final long mTimeoutMillis;
    private final String mBody;
    private final String mBodyType;
    private final Method mMethod;
    private final NetworkConnectionType mNetworkConnectionType;
    private final Map<String, List<String>> mPinsCertificatesMap;
    private final List mInterceptorList;
    private final Object mDns;
    private final InputStream mTrustCertificate;
    private final HostnameVerifier mHostnameVerifier;

    protected RequestParams(de.telekom.smartcredentials.core.model.request.RequestParams requestParams) {
        mEndpoint = requestParams.getEndpoint();
        mHeaders = requestParams.getHeaders();
        mQueryParams = requestParams.getQueryParams();
        mBody = requestParams.getBody();
        mBodyType = requestParams.getBodyType();
        mMethod = requestParams.getMethod();
        mNetworkConnectionType = requestParams.getNetworkConnectionType();
        mPinsCertificatesMap = requestParams.getPinsCertificatesMap();
        mFollowsRedirects = requestParams.followsRedirects();
        mTimeoutMillis = requestParams.getTimeoutMillis();
        mInterceptorList = requestParams.getInterceptorList();
        mDns = requestParams.getDns();
        mTrustCertificate = requestParams.getTrustCertificate();
        mHostnameVerifier = requestParams.getHostnameVerifier();
    }

    private RequestParams(Builder builder) {
        mEndpoint = builder.mEndpoint;
        mHeaders = builder.mHeaders;
        mQueryParams = builder.mQueryParams;
        mBody = builder.mRequestBody;
        mBodyType = builder.mRequestBodyMediaType;
        mMethod = builder.mMethod;
        mNetworkConnectionType = builder.mNetworkConnectionType;
        mPinsCertificatesMap = builder.mPinsCertificatesMap;
        mFollowsRedirects = builder.mFollowsRedirects;
        mTimeoutMillis = builder.mTimeoutMillis;
        mInterceptorList = builder.mInterceptorList;
        mDns = builder.mDns;
        mTrustCertificate = builder.mTrustCertificate;
        mHostnameVerifier = builder.mHostnameVerifier;
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

    public Method getMethod() {
        return mMethod;
    }

    public String getBody() {
        return mBody;
    }

    public String getBodyType() {
        return mBodyType;
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

    public List getInterceptorList() {
        return mInterceptorList;
    }

    public boolean hasInterceptors() {
        return mInterceptorList != null && mInterceptorList.size() > 0;
    }

    public Object getDns() {
        return mDns;
    }

    public InputStream getTrustCertificate() {
        return mTrustCertificate;
    }

    public HostnameVerifier getHostnameVerifier() {
        return mHostnameVerifier;
    }

    public static final class Builder {
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
        private List mInterceptorList;
        private Object mDns;
        private InputStream mTrustCertificate;
        private HostnameVerifier mHostnameVerifier;

        public Builder() {
            mEndpoint = "";
            mHeaders = new HashMap<>();
            mQueryParams = new HashMap<>();
            mRequestBody = "";
            mRequestBodyMediaType = "application/json";
            mMethod = Method.GET;
            mNetworkConnectionType = NetworkConnectionType.DEFAULT;
            mFollowsRedirects = true;
            mTimeoutMillis = 0;
            mPinsCertificatesMap = new HashMap<>();
            mInterceptorList = new ArrayList();
            mTrustCertificate = new InputStream() {
                @Override
                public int read() {
                    return -1;
                }
            };
            mHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
        }

        public Builder setEndpoint(String endpoint) {
            mEndpoint = endpoint;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public Builder setQueryParams(Map<String, String> queryParams) {
            mQueryParams = queryParams;
            return this;
        }

        public Builder setRequestBody(String requestBody) {
            mRequestBody = requestBody;
            return this;
        }

        public Builder setRequestBodyMediaType(String requestBodyMediaType) {
            mRequestBodyMediaType = requestBodyMediaType;
            return this;
        }

        public Builder setMethod(Method method) {
            mMethod = method;
            return this;
        }

        public Builder setNetworkConnectionType(NetworkConnectionType networkConnectionType) {
            mNetworkConnectionType = networkConnectionType;
            return this;
        }

        public Builder setPinsCertificatesMap(Map<String, List<String>> pinsCertificates) {
            mPinsCertificatesMap = pinsCertificates;
            return this;
        }

        public Builder setFollowsRedirects(boolean followsRedirects) {
            mFollowsRedirects = followsRedirects;
            return this;
        }

        public Builder setTimeoutMillis(long timeoutMillis) {
            mTimeoutMillis = timeoutMillis;
            return this;
        }

        public Builder setInterceptorList(List interceptorList) {
            mInterceptorList = interceptorList;
            return this;
        }

        public Builder setDns(Object dns) {
            mDns = dns;
            return this;
        }

        public Builder setTrustCertificate(InputStream trustCertificate) {
            mTrustCertificate = trustCertificate;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            mHostnameVerifier = hostnameVerifier;
            return this;
        }

        public RequestParams build() {
            return new RequestParams(this);
        }
    }
}
