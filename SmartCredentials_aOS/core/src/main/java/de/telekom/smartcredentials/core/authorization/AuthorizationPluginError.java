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

package de.telekom.smartcredentials.core.authorization;

public enum AuthorizationPluginError {

    MISSING_VIEW("Cannot show view for alternative authorization, as the view is null."),
    DEVICE_NOT_SECURED("Cannot perform authorization; device is not protected by PIN, password or pattern."),
    AUTH_FAILED("Authorization failed."),
    TOO_MANY_ATTEMPTS("Too many attempts. Try again later."),
    AUTH_CANCELED_BY_USER("Fingerprint operation canceled by user."),
    AUTH_CANCELED("Fingerprint operation canceled."),
    FINGERPRINT_TOO_FAST("Finger moved too fast. Please try again."),
    AUTH_BECAME_UNAVAILABLE("Authorization view is not available any more."),
    HARDWARE_NOT_DETECTED("Fingerprint hardware not detected."),
    FINGERPRINT_NOT_ENROLLED("No fingerprints enrolled.");

    final String mDesc;

    AuthorizationPluginError(String desc) {
        mDesc = desc;
    }

    public static AuthorizationPluginError map(String string) {
        for (AuthorizationPluginError error : AuthorizationPluginError.values()) {
            if (error.getDesc().equals(string)) {
                return error;
            }
        }
        return null;
    }

    public String getDesc() {
        return mDesc;
    }

}
