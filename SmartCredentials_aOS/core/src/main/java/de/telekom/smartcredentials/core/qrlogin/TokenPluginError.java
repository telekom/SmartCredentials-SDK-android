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

package de.telekom.smartcredentials.core.qrlogin;

public enum TokenPluginError {

    UNDEFINED_URL("Request URL for QR authentication has not been set."),
    NULL_ACCESS_TOKEN("Access token received through the web socket was null."),
    FAILED_AUTHENTICATION("Authentication failed."),
    UNKNOWN_EVENT("Web socket used for authentication received an unknown external event."),
    SOCKET_FAILED("Web socket used for authentication has been closed due to an error reading from or writing to the network."),
    EMPTY_MESSAGE("Message that should be sent through web socket is null."),
    ERROR_EXTRACTING_CONNECTION_PARAMS("Could not extract connection params needed for communication through web socket."),
    INVALID_URL("Request URL for QR authentication is invalid.");

    private final String mDesc;

    TokenPluginError(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }
}
