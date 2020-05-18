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

package de.telekom.smartcredentials.pushnotifications.service;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsSendError;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.models.FirebaseRemoteMessage;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        if (ObjectGraphCreatorPushNotifications.getInstance().isSubscribed()) {
            ObjectGraphCreatorPushNotifications.getInstance().setTokenData(token);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (ObjectGraphCreatorPushNotifications.getInstance().isSubscribed()) {
            ObjectGraphCreatorPushNotifications.getInstance().setRemoteMessageData(new FirebaseRemoteMessage(remoteMessage));
        }
    }

    @Override
    public void onMessageSent(String messageId) {
        ObjectGraphCreatorPushNotifications.getInstance().setSentMessageId(messageId);
    }

    @Override
    public void onSendError(String messageId, Exception exception) {
        ObjectGraphCreatorPushNotifications.getInstance().setSendErrorData(
                new SmartCredentialsSendError(messageId, exception));
    }
}