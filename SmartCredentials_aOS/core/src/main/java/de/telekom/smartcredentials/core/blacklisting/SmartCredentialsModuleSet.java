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

package de.telekom.smartcredentials.core.blacklisting;

public enum SmartCredentialsModuleSet {

    AUTHORIZATION_MODULE("Authorization Module"),
    CAMERA_MODULE("Camera Module"),
    DOCUMENT_SCANNER_MODULE("Document Scanner Module"),
    SECURITY_MODULE("Security Module"),
    NETWORKING_MODULE("Networking Module"),
    STORAGE_MODULE("Storage Module"),
    QR_LOGIN_MODULE("QR Login Module"),
    OTP_MODULE("OTP Module"),
    AUTHENTICATION_MODULE("Authentication Module"),
    EID_MODULE("e-ID module"),
    PERSISTENT_LOGGING("Persistent Logging Module"),
    PUSH_NOTIFICATIONS_MODULE("Push Notifications Module"),
    ONE_CLICK_BUSINESS_MODULE("One Click Business Module"),
    SIM_MODULE("Sim Module"),
    IDENTITY_PROVIDER("Identity Provider Module"),
    ONE_CLICK_BUSINESS_CLIENT_MODULE("One Click Business Client Module"),
    CARRIER_CONFIGURATION_MODULE("Carrier Configuration Module"),
    CARRIER_SETTINGS_MODULE("Carrier Settings Module");

    private final String mModuleName;

    SmartCredentialsModuleSet(String name) {
        mModuleName = name;
    }

    @SuppressWarnings("unused")
    public String getModuleName() {
        return mModuleName;
    }
}
