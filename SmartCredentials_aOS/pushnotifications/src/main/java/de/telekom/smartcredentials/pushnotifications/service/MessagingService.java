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

import org.jetbrains.annotations.NotNull;

import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.factory.SmartCredentialsPushNotificationsFactory;
import de.telekom.smartcredentials.pushnotifications.handlers.PushNotificationsHandler;
import de.telekom.smartcredentials.pushnotifications.models.FirebaseRemoteMessage;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class MessagingService extends FirebaseMessagingService {

    private final PushNotificationsStorageRepository mStorageRepository;
    private final PushNotificationsHandler mHandler;
    private CompositeDisposable mCompositeDisposable;

    public MessagingService() {
        mStorageRepository = ObjectGraphCreatorPushNotifications
                .getInstance().providePushNotificationsStorageRepository();
        mHandler = ObjectGraphCreatorPushNotifications.getInstance().providePushNotificationsHandler();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public void onNewToken(@NotNull String token) {
        if (mStorageRepository.getPushNotificationsConfigBoolean(PushNotificationsStorageRepository.KEY_AUTO_SUBSCRIBE)) {
            mCompositeDisposable.add(SmartCredentialsPushNotificationsFactory.getRxPushNotificationsApi().subscribeAllNotifications()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        }
        if (mStorageRepository.getPushNotificationsConfigBoolean(
                PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE)) {
            mHandler.onNewToken(token);
        }
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        if (mStorageRepository.getPushNotificationsConfigBoolean(
                PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE)) {
            mHandler.onMessageReceived(new FirebaseRemoteMessage(remoteMessage));
        }
    }
}