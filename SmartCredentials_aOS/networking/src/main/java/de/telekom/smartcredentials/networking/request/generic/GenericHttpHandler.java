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

import javax.net.SocketFactory;

import de.telekom.smartcredentials.core.handlers.HttpHandler;
import de.telekom.smartcredentials.core.networking.RequestFailureLevel;
import de.telekom.smartcredentials.networking.request.models.FailedRequest;
import de.telekom.smartcredentials.networking.request.models.ModelConverter;
import de.telekom.smartcredentials.networking.request.models.RequestParams;
import de.telekom.smartcredentials.networking.request.models.enums.SocketFactoryFailure;
import de.telekom.smartcredentials.networking.request.service.Service;
import de.telekom.smartcredentials.networking.request.socket.SocketFactoryCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.ServicePluginCallback;

public class GenericHttpHandler implements HttpHandler {

    private final Service mService;

    public GenericHttpHandler(Service service) {
        mService = service;
    }

    @Override
    public void performRequest(Context context, de.telekom.smartcredentials.core.model.request.RequestParams reqParams, ServicePluginCallback servicePluginCallback) {
        RequestParams requestParams = ModelConverter.toRequestParams(reqParams);
        mService.prepareSocketFactory(context, requestParams.getConnectionType(), getSocketFactoryCallback(requestParams, servicePluginCallback));
    }

    SocketFactoryCallback getSocketFactoryCallback(RequestParams requestParams, ServicePluginCallback servicePluginCallback) {
        return new SocketFactoryCallback() {
            @Override
            public void onSocketFactoryCreated(SocketFactory socketFactory) {
                requestParams.setSocketFactory(socketFactory);
                mService.createRequest(requestParams, servicePluginCallback);
            }

            @Override
            public void onSocketFactoryFailed(SocketFactoryFailure socketFactoryFailure) {
                FailedRequest request = FailedRequest.newInstance(RequestFailureLevel.SOCKET_CREATION, socketFactoryFailure);
                servicePluginCallback.onFailed(request);
            }
        };
    }
}
