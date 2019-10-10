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

package de.telekom.smartcredentials.authorization.callback;

import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginUnavailable;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

/**
 * Created by Lucian Iacob on November 09, 2018.
 */
public class PluginCallbackAuthorizationConverter {

    public static AuthorizationPluginCallback convertToDomainPluginCallback(final AuthorizationCallback callback, String tag) {
        return new AuthorizationPluginCallback<AuthorizationPluginUnavailable, AuthorizationPluginError>() {
            @Override
            public void onAuthorized() {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onAuthorized");
                if (callback == null) {
                    return;
                }
                callback.onAuthorized();
            }

            @Override
            public void onPluginUnavailable(AuthorizationPluginUnavailable errorMessage) {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onPluginUnavailable");
                if (callback == null) {
                    return;
                }
                callback.onUnavailable(errorMessage);
            }

            @Override
            public void onFailed(AuthorizationPluginError message) {
                ApiLoggerResolver.logCallbackMethod(tag, TAG, "onFailed");
                if (callback == null) {
                    return;
                }
                callback.onFailure(message);
            }
        };
    }

}
