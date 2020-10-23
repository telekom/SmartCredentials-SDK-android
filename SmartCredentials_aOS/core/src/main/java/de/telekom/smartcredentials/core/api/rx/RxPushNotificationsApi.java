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

package de.telekom.smartcredentials.core.api.rx;

import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsRemoteMessage;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by gabriel.blaj@endava.com at 5/21/2020
 */
public interface RxPushNotificationsApi {

    /**
     * Method used to subscribe to notifications.
     *
     * @return {@link Completable} that emits the success or failure of the subscribe
     */
    @SuppressWarnings("unused")
    Completable subscribeAllNotifications();

    /**
     * Method used to unsubscribe from notifications.
     *
     * @return {@link Completable} that emits the success or failure of the unsubscribe
     */
    @SuppressWarnings("unused")
    Completable unsubscribeAllNotifications();

    /**
     * Method used to subscribe to a topic in order to receive notifications about it.
     *
     * @param topic the subscribe topic
     * @return {@link Completable} that emits the success or failure of the subscribe
     */
    @SuppressWarnings("unused")
    Completable subscribeToTopic(String topic);

    /**
     * Method used to unsubscribe from a topic in order to stop receiving notifications about it.
     *
     * @param topic the subscribe topic
     * @return {@link Completable} that emits the success or failure of the unsubscribe
     */
    @SuppressWarnings("unused")
    Completable unsubscribeFromTopic(String topic);

    /**
     * Method used to retrieve the token used for initializing the Firebase client.
     *
     * @return {@link Single} containing a {@link String} if the response was successful
     */
    @SuppressWarnings("unused")
    Single<String> retrieveToken();

    /**
     * Method used to retrieve the device id used for registering to TPNS.
     *
     * @return {@link Single} containing a {@link String} if the response was successful
     */
    @SuppressWarnings("unused")
    Single<String> retrieveDeviceId();

    /**
     * Method used to retrieve the firebase configuration sender Id.
     *
     * @return {@link Single} containing a {@link String} if the response was successful
     */
    @SuppressWarnings("unused")
    Single<String> retrieveSenderId();

    /**
     * Method used to observe for refreshed tokens.
     *
     * @return {@link Observable} that emits {@link String} the refreshed tokens
     */
    @SuppressWarnings("unused")
    Observable<String> observeTokenRefreshed();

    /**
     * Method used to observe for remote messages.
     *
     * @return {@link Observable} that emits {@link String} the remote messages
     */
    @SuppressWarnings("unused")
    Observable<SmartCredentialsRemoteMessage> observeMessageReceived();
}
