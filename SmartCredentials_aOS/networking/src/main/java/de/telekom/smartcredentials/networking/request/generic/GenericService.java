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

package de.telekom.smartcredentials.networking.request.generic;

import android.content.Context;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import de.telekom.smartcredentials.networking.request.models.FailedRequest;
import de.telekom.smartcredentials.networking.request.models.RequestParams;
import de.telekom.smartcredentials.networking.request.models.enums.ConnectionType;
import de.telekom.smartcredentials.networking.request.service.Service;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;
import okhttp3.Callback;
import okhttp3.CertificatePinner;
import okhttp3.HttpUrl;

import static de.telekom.smartcredentials.networking.request.models.enums.RequestFailure.INVALID_PARAMETERS;
import static de.telekom.smartcredentials.networking.request.models.enums.RequestFailure.NULL_PARSED_URL;
import static de.telekom.smartcredentials.networking.request.utils.HttpClientBuilder.buildCallFactory;
import static de.telekom.smartcredentials.networking.request.utils.HttpClientBuilder.buildRequest;

public class GenericService implements Service {

    private final CertificatePinner.Builder mCertificatePinnerBuilder;

    public GenericService(CertificatePinner.Builder certificatePinnerBuilder) {
        mCertificatePinnerBuilder = certificatePinnerBuilder;
    }

    @Override
    public void prepareSocketFactory(Context context, ConnectionType connectionType, SocketFactoryCallback socketFactoryCallback) {
        GenericSocketFactory.createSocketFactory(context, connectionType, socketFactoryCallback);
    }

    @Override
    public void createRequest(RequestParams requestParams, ServicePluginCallback servicePluginCallback) {
        HttpUrl httpUrl = HttpUrl.parse(requestParams.getEndpoint());
        if (httpUrl == null) {
            servicePluginCallback.onFailed(FailedRequest.newInstance(RequestFailureLevel.REQUEST, NULL_PARSED_URL));
            return;
        }

        try {
            buildCallFactory(requestParams, mCertificatePinnerBuilder)
                    .newCall(buildRequest(httpUrl, requestParams))
                    .enqueue(getCallback(servicePluginCallback));
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            servicePluginCallback.onFailed(FailedRequest.newInstance(RequestFailureLevel.REQUEST, e, e.getMessage()));
        } catch (IllegalArgumentException e) {
            servicePluginCallback.onFailed(FailedRequest.newInstance(RequestFailureLevel.REQUEST, INVALID_PARAMETERS));
        }
    }

    Callback getCallback(ServicePluginCallback servicePluginCallback) {
        return new GenericRequestCallback(servicePluginCallback);
    }
}
