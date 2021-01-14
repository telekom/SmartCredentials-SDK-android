/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.pushnotifications.enums;

/**
 * Created by gabriel.blaj@endava.com at 5/20/2020
 */
public enum PushNotificationsMessages {
    SUCCESSFULLY_SUBSCRIBED("Successfully subscribed to push notifications"),
    UNSUCCESSFULLY_SUBSCRIBED("Could not subscribe to push notifications"),
    SUCCESSFULLY_REGISTERED("Successfully registered to TPNS"),
    ALREADY_SUBSCRIBED("Already subscribed to push notifications"),
    ALREADY_REGISTERED("Already registered to TPNS"),
    INVALID_TPNS_CONFIGURATION("Invalid configuration for TPNS"),
    NOT_REGISTERED_TO_TPNS("Not registered to TPNS"),
    SUCCESSFULLY_UNREGISTERED("Successfully unregistered from TPNS"),
    SUCCESSFULLY_UNSUBSCRIBED("Successfully unsubscribed from push notifications"),
    UNSUCCESSFULLY_UNSUBSCRIBED("Could not unsubscribe from push notifications"),
    NOT_SUBSCRIBED("Not Subscribed to push notifications"),
    SUCCESSFULLY_SUBSCRIBED_TO("Successfully subscribed to "),
    SUCCESSFULLY_UNSUBSCRIBED_TO("Successfully unsubscribed from "),
    SUBSCRIBE_FOR_NOTIFICATIONS("Please use the subscribe method in order to receive notifications about this topic");

    String mMessage;

    PushNotificationsMessages(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }
}
