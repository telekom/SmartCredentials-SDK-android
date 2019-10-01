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

package de.telekom.smartcredentials.core.networking;

import java.util.Map;

import de.telekom.smartcredentials.core.plugins.callbacks.TokenPluginCallback;

public interface ServerSocket<R, F> {

    /**
     * Method used to start a web socket and tries to connect to it based on the data provided. If
     * successful then it tries an authentication based on a QR code and sends the result of the
     * action on a listener.
     *
     * @param params         is a map of parameters used in the action and the map keys should be declared
     *                       in regard to {@link AuthParamKey}
     * @param pluginCallback is the listener used for receiving the web socket response
     */
    void startServer(Map<String, String> params, TokenPluginCallback<R, F> pluginCallback);
}
