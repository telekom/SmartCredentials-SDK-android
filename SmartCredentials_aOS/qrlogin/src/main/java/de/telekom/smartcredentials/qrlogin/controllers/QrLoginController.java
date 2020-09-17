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

package de.telekom.smartcredentials.qrlogin.controllers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.api.NetworkingApi;
import de.telekom.smartcredentials.core.api.QrLoginApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
import de.telekom.smartcredentials.core.qrlogin.AuthenticationCallback;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.qrlogin.callback.PluginCallbackQrLoginCallback;
import de.telekom.smartcredentials.qrlogin.callback.UserAuthorizedPluginCallback;

public class QrLoginController implements QrLoginApi {

    private final CoreController mCoreController;
    private final AuthorizationApi mAuthorizationApi;
    private final NetworkingApi mNetworkingApi;

    public QrLoginController(CoreController coreController,
                             AuthorizationApi authorizationApi,
                             NetworkingApi networkingApi) {
        mCoreController = coreController;
        mAuthorizationApi = authorizationApi;
        mNetworkingApi = networkingApi;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<Fragment> getAuthorizeUserLogInFragment(@NonNull AuthenticationCallback callback, ItemEnvelope itemEnvelope) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getAuthorizeUserLogInFragment");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.QR_LOGIN)) {
            String errorMessage = SmartCredentialsFeatureSet.QR_LOGIN.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (itemEnvelope == null || itemEnvelope.getIdentifier() == null) {
            return new SmartCredentialsResponse<>(new EnvelopeException(EnvelopeExceptionReason.NO_IDENTIFIER_EXCEPTION_MSG));
        }

        JSONObject requestParams = itemEnvelope.getIdentifier();
        Fragment authFragment = (Fragment) getLogInWithAuthorizationTool(PluginCallbackQrLoginCallback.convertToDomainPluginCallback(callback, getClass().getSimpleName()),
                requestParams);
        return new SmartCredentialsResponse<>(authFragment);
    }

    private Object getLogInWithAuthorizationTool(AuthenticationPluginCallback callback, JSONObject requestParams) {
        AuthorizationCallback authorizationCallback = new UserAuthorizedPluginCallback(requestParams, callback, mNetworkingApi.getServerSocket());
        return mAuthorizationApi.getAuthorizeUserFragment(authorizationCallback);
    }
}
