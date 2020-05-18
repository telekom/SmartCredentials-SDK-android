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

package de.telekom.smartcredentials.core.api;

import android.arch.lifecycle.LiveData;

import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsSendError;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsMessage;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsRemoteMessage;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public interface PushNotificationsApi {

    SmartCredentialsApiResponse<Void> subscribe();

    SmartCredentialsApiResponse<Void> unsubscribe();

    SmartCredentialsApiResponse<Void> subscribeToTopic(String topic);

    SmartCredentialsApiResponse<Void> unsubscribeToTopic(String topic);

    SmartCredentialsResponse<String> retrieveToken();

    SmartCredentialsResponse<Void> sendMessage(SmartCredentialsMessage message);

    LiveData<String> getTokenData();

    LiveData<SmartCredentialsRemoteMessage> getMessageData();

    LiveData<String> getSentMessageIdData();

    LiveData<SmartCredentialsSendError> getSendErrorData();
}
