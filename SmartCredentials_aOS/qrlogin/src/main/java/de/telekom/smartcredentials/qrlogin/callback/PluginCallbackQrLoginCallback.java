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

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthenticationPluginCallback;
import de.telekom.smartcredentials.core.qrlogin.AuthenticationCallback;
import de.telekom.smartcredentials.core.qrlogin.TokenPluginError;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public class PluginCallbackQrLoginCallback {

    public static AuthenticationPluginCallback<String, String, String> convertToDomainPluginCallback(final AuthenticationCallback callback, final String tag) {
        return new AuthenticationPluginCallback<String, String, String>() {
            @Override
            public void onRetrievingAuthInfoFailed(String message) {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onRetrievingAuthInfoFailed");
                if (callback == null) {
                    return;
                }
                callback.onFailure(message);
            }

            @Override
            public void onAuthenticated(@NonNull TokenResponse authResponse) {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onAuthenticated");
                if (callback == null) {
                    return;
                }
                callback.onAuthenticated(authResponse.getValue(), authResponse.getValidUntil());
            }

            @Override
            public void onPluginUnavailable(String errorMessage) {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onPluginUnavailable");
                if (callback == null) {
                    return;
                }
                callback.onPluginUnavailable(errorMessage);
            }

            @Override
            public void onFailed(String errorMessage) {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onFailed");
                if (callback == null) {
                    return;
                }
                callback.onFailure(TokenPluginError.FAILED_AUTHENTICATION.getDesc());
            }
        };
    }

}
