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

package de.telekom.smartcredentials.core.controllers.callbackimplementations;


import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;

public class RefreshTokenPluginCallback extends TokenPluginCallback<TokenResponse, Object> {

    private final AuthenticationPluginCallback<Object, Object, Object> mCallback;

    public RefreshTokenPluginCallback(AuthenticationPluginCallback<Object, Object, Object> callback) {
        mCallback = callback;
    }

    @Override
    public void onSuccess(TokenResponse refreshToken) {
        ApiLoggerResolver.logCallbackMethod(TAG, AuthorizationPluginCallback.TAG, "getToken: onAuthenticated");
        ApiLoggerResolver.logEvent("refresh token received");

        mCallback.onAuthenticated(refreshToken);
    }

    @Override
    public void onFailed(Object message) {
        ApiLoggerResolver.logCallbackMethod(TAG, AuthorizationPluginCallback.TAG, "getToken: onRetrievingAuthInfoFailed");
        mCallback.onRetrievingAuthInfoFailed(message);
    }
}
