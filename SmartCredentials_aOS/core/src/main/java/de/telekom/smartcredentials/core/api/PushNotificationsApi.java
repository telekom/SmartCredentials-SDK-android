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
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public interface PushNotificationsApi {

    /**
     * Method used to subscribe to notifications.
     *
     * @param callback {@link PushNotificationsCallback} for retrieving the subscribe success or failure event
     * @return {@link SmartCredentialsApiResponse} containing a {@link Void} if the response was returned on callback
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the subscribe method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsApiResponse<Void> subscribeAllNotifications(PushNotificationsCallback callback);

    /**
     * Method used to unsubscribe from notifications.
     *
     * @param callback {@link PushNotificationsCallback} for retrieving the unsubscribe success or failure event
     * @return {@link SmartCredentialsApiResponse} containing a {@link Void} if the response was returned on callback
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the unsubscribe method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsApiResponse<Void> unsubscribeAllNotifications(PushNotificationsCallback callback);

    /**
     * Method used to subscribe to a topic in order to receive notifications about it.
     *
     * @param callback {@link PushNotificationsCallback} for retrieving the subscribe success or failure event
     * @param topic the subscribe topic
     * @return {@link SmartCredentialsApiResponse} containing a {@link Void} if the response was returned on callback
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the subscribe to topic method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsApiResponse<Void> subscribeToTopic(String topic, PushNotificationsCallback callback);

    /**
     * Method used to unsubscribe from a topic in order to stop receiving notifications about it.
     *
     * @param callback {@link PushNotificationsCallback} for retrieving the unsubscribe success or failure event
     * @param topic the unsubscribe topic
     * @return {@link SmartCredentialsApiResponse} containing a {@link Void} if the response was returned on callback
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the unsubscribe to topic method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsApiResponse<Void> unsubscribeFromTopic(String topic, PushNotificationsCallback callback);

    /**
     * Method used to retrieve the token used for initializing the Firebase client.
     *
     * @return {@link SmartCredentialsApiResponse} containing a {@link String} if response was successful
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the retrieve token method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsResponse<String> retrieveToken();

    /**
     * Method used to retrieve the device id used for registering to TPNS.
     *
     * @return {@link SmartCredentialsApiResponse} containing a {@link String} if response was successful
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the retrieve device id method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsResponse<String> retrieveDeviceId();

    /**
     * Method used to retrieve the firebase configuration sender Id.
     *
     * @return {@link SmartCredentialsApiResponse} containing a {@link String} if response was successful
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the retrieve device id method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsResponse<String> retrieveSenderId();

    /**
     * Method used to listen for refreshed tokens.
     *
     * @param callback {@link PushNotificationsTokenCallback} for retrieving the refreshed token
     * @return {@link SmartCredentialsApiResponse} containing a {@link Void} if the callback was attached
     * successfully in order to return the refreshed tokens
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the set token refreshed callback method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsResponse<Void> setTokenRefreshedCallback(PushNotificationsTokenCallback callback);

    /**
     * Method used to listen for remote messages.
     *
     * @param callback {@link PushNotificationsMessageCallback} for retrieving the remote message
     * @return {@link SmartCredentialsApiResponse} containing a {@link Void} if the callback was attached
     * successfully in order to return the remote messages
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the set message received callback method is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     */
    SmartCredentialsResponse<Void> setMessageReceivedCallback(PushNotificationsMessageCallback callback);
}
