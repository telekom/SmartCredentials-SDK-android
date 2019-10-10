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

package de.telekom.smartcredentials.core.authentication;

/**
 * Created by Lucian Iacob on March 14, 2019.
 */
public enum AuthenticationError {

    NO_INSTALLED_BROWSERS("There are no browsers installed on the device"),
    INVALID_DISCOVERY_DOCUMENT("Invalid discovery document"),
    ERROR_RETRIEVING_DISCOVERY_DOCUMENT("Failed to retrieve discovery document"),
    DYNAMIC_REGISTRATION_FAILED("Failed to dynamically register client."),
    ERROR_NULL_REFRESH_TOKEN("Attempt to refresh the access token with a null refresh token. Operation aborted"),
    UNSUPPORTED_CLIENT_AUTH("Client authentication for the token endpoint could not be constructed.");

    private final String mDescription;

    AuthenticationError(String description) {
        mDescription = description;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return mDescription;
    }
}
