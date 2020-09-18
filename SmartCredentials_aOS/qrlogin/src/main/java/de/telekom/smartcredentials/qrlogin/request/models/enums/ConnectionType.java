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

package de.telekom.smartcredentials.qrlogin.request.models.enums;

import android.net.NetworkCapabilities;

import de.telekom.smartcredentials.qrlogin.NetworkConnectionType;

public enum ConnectionType {

    MOBILE(NetworkCapabilities.TRANSPORT_CELLULAR),
    WIFI(NetworkCapabilities.TRANSPORT_WIFI),
    DEFAULT(-1);

    private final int mTransportType;

    ConnectionType(int transportType) {
        mTransportType = transportType;
    }

    public static ConnectionType map(NetworkConnectionType networkConnectionType) {
        switch (networkConnectionType) {
            case WIFI:
                return WIFI;
            case MOBILE:
                return MOBILE;
            default:
                return DEFAULT;
        }
    }

    public int getTransportType() {
        return mTransportType;
    }
}
