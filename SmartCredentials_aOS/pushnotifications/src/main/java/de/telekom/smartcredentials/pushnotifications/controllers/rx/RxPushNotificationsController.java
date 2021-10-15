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

package de.telekom.smartcredentials.pushnotifications.controllers.rx;

import java.util.Objects;

import de.telekom.smartcredentials.core.api.rx.RxPushNotificationsApi;
import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsRemoteMessage;
import de.telekom.smartcredentials.pushnotifications.controllers.PushNotificationsController;
import de.telekom.smartcredentials.pushnotifications.rx.MessageReceivedObservable;
import de.telekom.smartcredentials.pushnotifications.rx.SubscribeCompletable;
import de.telekom.smartcredentials.pushnotifications.rx.SubscribeToTopicCompletable;
import de.telekom.smartcredentials.pushnotifications.rx.TokenRefreshedObservable;
import de.telekom.smartcredentials.pushnotifications.rx.TokenRetrievedSingle;
import de.telekom.smartcredentials.pushnotifications.rx.UnsubscribeCompletable;
import de.telekom.smartcredentials.pushnotifications.rx.UnsubscribeFromTopicCompletable;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by gabriel.blaj@endava.com at 5/21/2020
 */
public class RxPushNotificationsController implements RxPushNotificationsApi {

    private final PushNotificationsController controller;

    public RxPushNotificationsController(PushNotificationsController controller) {
        this.controller = controller;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Completable subscribeAllNotifications() {
        return Completable.create(new SubscribeCompletable(controller));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Completable unsubscribeAllNotifications() {
        return Completable.create(new UnsubscribeCompletable(controller));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Completable subscribeToTopic(String topic) {
        return Completable.create(new SubscribeToTopicCompletable(controller, topic));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Completable unsubscribeFromTopic(String topic) {
        return Completable.create(new UnsubscribeFromTopicCompletable(controller, topic));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<String> retrieveToken() {
        return Single.create(new TokenRetrievedSingle(controller));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Single<String> retrieveDeviceId() {
        return Single.just(Objects.requireNonNull(controller.retrieveDeviceId().getData()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<String> observeTokenRefreshed() {
        return Observable.create(new TokenRefreshedObservable(controller)).share();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Observable<SmartCredentialsRemoteMessage> observeMessageReceived() {
        return Observable.create(new MessageReceivedObservable(controller)).share();
    }
}
