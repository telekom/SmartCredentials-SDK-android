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

package de.telekom.smartcredentials.qrlogin.callback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.controllers.callbackimplementations.RefreshTokenPluginCallback;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.networking.ServerSocket;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;
import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;

import static de.telekom.smartcredentials.core.qrlogin.TokenPluginError.ERROR_EXTRACTING_CONNECTION_PARAMS;

public class UserAuthorizedPluginCallback implements AuthorizationCallback {

    private static final String TAG = "UserAuthorizedPluginCallback";

    private final JSONObject mRequestParams;
    private final AuthenticationPluginCallback mCallback;
    private final ServerSocket mServerSocket;

    public UserAuthorizedPluginCallback(JSONObject requestParams, AuthenticationPluginCallback callback, ServerSocket serverSocket) {
        mRequestParams = requestParams;
        mCallback = callback;
        mServerSocket = serverSocket;
    }

    Map<String, String> getConnectionParams(String reqParams, TokenPluginCallback pluginCallback) {
        try {
            JSONObject jsonObject = new JSONObject(reqParams);
            Map<String, String> params = new HashMap<>();
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                params.put(key, jsonObject.get(key).toString());
            }
            return params;
        } catch (JSONException e) {
            if (pluginCallback != null) {
                pluginCallback.onFailed(ERROR_EXTRACTING_CONNECTION_PARAMS);
            }
            ApiLoggerResolver.logError(TAG, e.getMessage());
        }
        return null;
    }

    @Override
    public void onAuthorizationSucceeded() {
        ApiLoggerResolver.logCallbackMethod(TAG, AuthorizationPluginCallback.TAG, "userAuthorized: onAuthorized");
        TokenPluginCallback pluginCallback = new RefreshTokenPluginCallback(mCallback);
        mServerSocket.startServer(getConnectionParams(mRequestParams.toString(), pluginCallback), new RefreshTokenPluginCallback(mCallback));
    }

    @Override
    public void onAuthorizationError(String error) {
        ApiLoggerResolver.logCallbackMethod(TAG, AuthorizationPluginCallback.TAG, "userAuthorized: onUnavailable");
        mCallback.onFailed(error);
    }

    @Override
    public void onAuthorizationFailed(String error) {
        ApiLoggerResolver.logCallbackMethod(TAG, AuthorizationPluginCallback.TAG, "userAuthorized: onFailure");
        mCallback.onFailed(error);
    }
}
