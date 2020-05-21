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

import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsDataCallback;
import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsRemoteMessage;
import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsSendError;

/**
 * Created by gabriel.blaj@endava.com at 5/20/2020
 */
public class PushNotificationsHandler implements PushNotificationsDataCallback {

    private static PushNotificationsHandler INSTANCE;

    private PushNotificationsDataCallback mCallback;
    private boolean isInstantiated;

    private PushNotificationsHandler() {
    }

    public static PushNotificationsHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PushNotificationsHandler();
        }
        return INSTANCE;
    }

    public void instantiateCallback(PushNotificationsDataCallback callback) {
        this.mCallback = callback;
        isInstantiated = true;
    }

    public void onNewToken(String token) {
        if (isInstantiated) {
            mCallback.onNewToken(token);
        }
    }

    @Override
    public void onMessageReceived(SmartCredentialsRemoteMessage remoteMessage) {
        if (isInstantiated) {
            mCallback.onMessageReceived(remoteMessage);
        }
    }

    @Override
    public void onMessageSent(String messageId) {
        if (isInstantiated) {
            mCallback.onMessageSent(messageId);
        }
    }

    @Override
    public void onSendError(SmartCredentialsSendError error) {
        if (isInstantiated) {
            mCallback.onSendError(error);
        }
    }
}
