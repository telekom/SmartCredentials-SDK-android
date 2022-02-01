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

import android.annotation.SuppressLint;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import de.telekom.smartcredentials.networking.request.models.enums.ConnectionType;

class ConnectionTypeRequest {

    @SuppressLint("WrongConstant")
    static NetworkRequest getNetworkRequest(ConnectionType connectionType) {
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        if (connectionType != ConnectionType.DEFAULT) {
            builder.addTransportType(connectionType.getTransportType())
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return builder.build();
    }
}
