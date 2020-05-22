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

package de.telekom.smartcredentials.pushnotifications.handlers;

import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsMessageCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsTokenCallback;
import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsRemoteMessage;

/**
 * Created by gabriel.blaj@endava.com at 5/20/2020
 */
public class PushNotificationsHandler {

    private static PushNotificationsHandler INSTANCE;

    private PushNotificationsTokenCallback mTokenCallback;
    private PushNotificationsMessageCallback mMessageCallback;

    private PushNotificationsHandler() {
    }

    public static PushNotificationsHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PushNotificationsHandler();
        }
        return INSTANCE;
    }

    public void addTokenCallback(PushNotificationsTokenCallback callback) {
        mTokenCallback = callback;
    }

    public void addMessageCallback(PushNotificationsMessageCallback callback) {
        mMessageCallback = callback;
    }

    public void onNewToken(String token) {
        if (mTokenCallback != null) {
            mTokenCallback.onNewToken(token);
        }
    }

    public void onMessageReceived(SmartCredentialsRemoteMessage remoteMessage) {
        if (mMessageCallback != null) {
            mMessageCallback.onMessageReceived(remoteMessage);
        }
    }
}
