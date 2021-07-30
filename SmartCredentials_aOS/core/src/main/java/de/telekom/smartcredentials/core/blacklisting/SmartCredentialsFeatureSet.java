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

/**
 * Created by Lucian Iacob on January 30, 2019.
 */
public enum SmartCredentialsFeatureSet {
    AUTHORIZE("Authorize", SmartCredentialsModuleSet.AUTHORIZATION_MODULE,
            "The Authorize feature is not supported on this device model."),
    OCR("OCR Scanner", SmartCredentialsModuleSet.CAMERA_MODULE,
            "The OCR feature is not supported on this device model."),
    QR("QR Scanner", SmartCredentialsModuleSet.CAMERA_MODULE,
            "The QR feature is not supported on this device model."),
    DOCUMENT_SCANNER("Document Scanner", SmartCredentialsModuleSet.DOCUMENT_SCANNER_MODULE,
            "The document scanner feature is not supported on this device model."),
    PUBLIC_KEY_GENERATION("Public Key Generator", SmartCredentialsModuleSet.SECURITY_MODULE,
            "The public key feature is not supported on this device model."),
    ENCRYPT("Encrypt", SmartCredentialsModuleSet.SECURITY_MODULE,
            "The encrypt feature is not supported on this device model."),
    CALL_SERVICE("Call Service", SmartCredentialsModuleSet.NETWORKING_MODULE,
            "The call service feature is not supported on this device model."),
    STORAGE("Storage", SmartCredentialsModuleSet.STORAGE_MODULE,
            "The storage feature is not supported on this device model."),
    QR_LOGIN("QR Login", SmartCredentialsModuleSet.QR_LOGIN_MODULE,
            "The QR Login feature is not supported on this device model."),
    OTP_HOTP("HOTP Generator", SmartCredentialsModuleSet.OTP_MODULE,
            "The HOTP Generator feature is not supported on this device model."),
    OTP_TOTP("TOTP Generator", SmartCredentialsModuleSet.OTP_MODULE,
            "The TOTP Generator feature is not supported on this device model."),
    OTP_VIA_QR("OTP via QR", SmartCredentialsModuleSet.OTP_MODULE,
            "The OTP via QR feature is not supported on this device model."),
    AUTHENTICATION("OAuth2.0 & OpenID Connect Authentication", SmartCredentialsModuleSet.AUTHENTICATION_MODULE,
            "The Authentication Service is not supported on this device model."),
    SUBSCRIBE("Subscribe", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Subscribe feature is not supported on this device model."),
    UNSUBSCRIBE("Unsubscribe", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Unsubscribe feature is not supported on this device model."),
    SUBSCRIBE_TO_TOPIC("Subscribe To Topic", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Subscribe to topic feature is not supported on this device model."),
    UNSUBSCRIBE_FROM_TOPIC("Unsubscribe From Topic", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Unsubscribe from topic feature is not supported on this device model."),
    RETRIEVE_TOKEN("Retrieve Token", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Retrieve Token feature is not supported on this device model."),
    RETRIEVE_DEVICE_ID("Retrieve Device Id", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Retrieve Device Id feature is not supported on this device model."),
    SEND_MESSAGE("Send Message", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Send Message feature is not supported on this device model."),
    SET_MESSAGE_RECEIVED_CALLBACK("Set Message Received Callback", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Set Message Received Callback feature is not supported on this device model."),
    SET_TOKEN_REFRESHED_CALLBACK("Set Token Refreshed Callback", SmartCredentialsModuleSet.PUSH_NOTIFICATIONS_MODULE,
            "The Set Token Refreshed Callback feature is not supported on this device model."),
    GET_TRANSACTION_TOKEN("Get Transaction Token", SmartCredentialsModuleSet.ENTITLEMENTS_MODULE,
            "The Get Transaction Token feature is not supported on this device model."),
    GET_ACCESS_TOKEN("Get Access Token", SmartCredentialsModuleSet.ENTITLEMENTS_MODULE,
            "The Get Access Token feature is not supported on this device model."),
    GET_BEARER_TOKEN("Get Bearer Token", SmartCredentialsModuleSet.ENTITLEMENTS_MODULE,
            "The Get Bearer Token feature is not supported on this device model.");

    private final SmartCredentialsModuleSet mModule;
    private final String mFeatureName;
    private final String mNotSupportedDesc;

    SmartCredentialsFeatureSet(String name, SmartCredentialsModuleSet module, String notSupportedDescription) {
        mFeatureName = name;
        mModule = module;
        mNotSupportedDesc = notSupportedDescription;
    }

    @SuppressWarnings("unused")
    public String getFeatureName() {
        return mFeatureName;
    }

    public SmartCredentialsModuleSet getModule() {
        return mModule;
    }

    public String getNotSupportedDesc() {
        return mNotSupportedDesc;
    }

}
