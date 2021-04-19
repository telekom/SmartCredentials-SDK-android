/*
 * Copyright (c) 2021 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.core.authorization;

public enum AuthorizationError {

    DEVICE_NOT_SECURED("Cannot perform authorization. Device is not protected by PIN, password or pattern."),
    AUTHORIZATION_CANCELED("Authorization canceled"),
    AUTHORIZATION_FAILED("Authorization failed."),
    INVALID_AUTHORIZATION_VIEW("Authorization failed. Invalid Authorization View."),
    COULD_NOT_RETRIEVE_CIPHER("Authorization failed. Could not retrieve Authorization Cipher.");

    private final String mMessage;

    AuthorizationError(String message) {
        this.mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
