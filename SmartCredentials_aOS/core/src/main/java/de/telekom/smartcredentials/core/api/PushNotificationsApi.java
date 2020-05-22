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

import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsMessageCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsTokenCallback;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public interface PushNotificationsApi {

    SmartCredentialsApiResponse<Void> subscribe(PushNotificationsCallback callback);

    SmartCredentialsApiResponse<Void> unsubscribe(PushNotificationsCallback callback);

    SmartCredentialsApiResponse<Void> subscribeToTopic(String topic, PushNotificationsCallback callback);

    SmartCredentialsApiResponse<Void> unsubscribeFromTopic(String topic, PushNotificationsCallback callback);

    SmartCredentialsResponse<String> retrieveToken();

    SmartCredentialsResponse<String> retrieveDeviceId();

    SmartCredentialsResponse<Void> setTokenRefreshedCallback(PushNotificationsTokenCallback callback);

    SmartCredentialsResponse<Void> setMessageReceivedCallback(PushNotificationsMessageCallback callback);
}
