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

package de.telekom.smartcredentials.networking.controllers;

import android.content.Context;
import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.NetworkingApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.handlers.HttpHandler;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.request.RequestParams;
import de.telekom.smartcredentials.core.networking.ServerSocket;
import de.telekom.smartcredentials.core.networking.ServiceCallback;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.networking.websocket.SmartCredentialsServerSocket;
import okhttp3.CertificatePinner;
import okhttp3.Request;

public class NetworkingController implements NetworkingApi {

    private final CoreController mCoreController;
    private final HttpHandler mHttpHandler;

    public NetworkingController(CoreController coreController, HttpHandler httpHandler) {
        mCoreController = coreController;
        mHttpHandler = httpHandler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Void> callService(@NonNull Context context,
                                                         @NonNull RequestParams requestParams,
                                                         @NonNull ServiceCallback serviceCallback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "callService");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.CALL_SERVICE)) {
            String errorMessage = SmartCredentialsFeatureSet.CALL_SERVICE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        mHttpHandler.performRequest(context, requestParams, serviceCallback);
        return new SmartCredentialsResponse<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServerSocket getServerSocket() {
        return new SmartCredentialsServerSocket(new Request.Builder(), new CertificatePinner.Builder());
    }
}
