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

package de.telekom.smartcredentials.networking.request.models;

import android.text.TextUtils;

import java.util.Map;
import java.util.Set;

import javax.net.SocketFactory;

import de.telekom.smartcredentials.networking.request.models.enums.ConnectionType;
import de.telekom.smartcredentials.networking.request.models.enums.HttpCrud;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RequestParams extends de.telekom.smartcredentials.core.model.request.RequestParams {

    private SocketFactory mSocketFactory;
    private final HttpCrud mHttpCrud;
    private final ConnectionType mConnectionType;
    private final RequestBody mRequestBody;

    public RequestParams(de.telekom.smartcredentials.core.model.request.RequestParams requestParams) {
        super(requestParams);
        this.mRequestBody = (!TextUtils.isEmpty(getBody())) ? FormBody.create(MediaType.parse(getBodyType()), getBody().getBytes()) : null;
        this.mHttpCrud = HttpCrud.map(getMethod());
        this.mConnectionType = ConnectionType.map(getNetworkConnectionType());
    }

    public void setSocketFactory(SocketFactory socketFactory) {
        mSocketFactory = socketFactory;
    }

    public String getEndpoint() {
        return mEndpoint;
    }

    public SocketFactory getSocketFactory() {
        return mSocketFactory;
    }

    public Set<String> getHeadersKeys() {
        return mHeaders.keySet();
    }

    public String getHeaderValue(String headerKey) {
        return mHeaders.get(headerKey);
    }

    public boolean hasHeaders() {
        return mHeaders != null && mHeaders.size() > 0;
    }

    public boolean hasCertificatesPins() {
        return getPinsCertificatesMap() != null && getPinsCertificatesMap().size() > 0;
    }

    public Map<String, String> getQueryParams() {
        return mQueryParams;
    }

    public RequestBody getRequestBody() {
        return mRequestBody;
    }

    public String getHttpMethod() {
        return mHttpCrud.name();
    }

    public ConnectionType getConnectionType() {
        return mConnectionType;
    }

    public boolean followsRedirects() {
        return mFollowsRedirects;
    }

    public long getTimeoutMillis() {
        return mTimeoutMillis;
    }
}
