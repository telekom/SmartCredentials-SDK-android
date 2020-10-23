/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.pushnotifications.controllers;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsMessageCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsTokenCallback;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.enums.PushNotificationsMessages;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;

/**
 * Created by Alex.Graur@endava.com at 5/22/2020
 */
public abstract class BaseController implements PushNotificationsApi {

    final PushNotificationsStorageRepository mStorageRepository;

    BaseController() {
        mStorageRepository = ObjectGraphCreatorPushNotifications.getInstance()
                .providePushNotificationsStorageRepository();
    }

    @Override
    public SmartCredentialsApiResponse<Void> subscribeToTopic(String topic, PushNotificationsCallback callback) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String message = PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED_TO.getMessage() + topic + " topic";
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), message);
                        if (!mStorageRepository.getPushNotificationsConfigBoolean(
                                PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE)) {
                            ApiLoggerResolver.logInfo(PushNotificationsMessages.SUBSCRIBE_FOR_NOTIFICATIONS.getMessage());
                        }
                        callback.onSuccess(message);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), e.getLocalizedMessage());
                        callback.onFailure(e.getLocalizedMessage(), new ArrayList<>());
                    }
                });
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unsubscribeFromTopic(String topic, PushNotificationsCallback callback) {
        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        String message = PushNotificationsMessages.SUCCESSFULLY_UNSUBSCRIBED_TO.getMessage() + topic + " topic";
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), message);
                        callback.onSuccess(message);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), e.getLocalizedMessage());
                        callback.onFailure(e.getLocalizedMessage(), new ArrayList<>());
                    }
                });
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsResponse<String> retrieveToken() {
        return new SmartCredentialsResponse<>(FirebaseInstanceId.getInstance().getToken());
    }

    @Override
    public SmartCredentialsResponse<String> retrieveDeviceId() {
        return new SmartCredentialsResponse<>(mStorageRepository
                .getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_DEVICE_ID));
    }

    @Override
    public SmartCredentialsResponse<String> retrieveSenderId() {
        return new SmartCredentialsResponse<>(ObjectGraphCreatorPushNotifications.getInstance().getSenderId());
    }

    @Override
    public SmartCredentialsResponse<Void> setTokenRefreshedCallback(PushNotificationsTokenCallback callback) {
        ObjectGraphCreatorPushNotifications.getInstance().providePushNotificationsHandler()
                .addTokenCallback(callback);
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsResponse<Void> setMessageReceivedCallback(PushNotificationsMessageCallback callback) {
        ObjectGraphCreatorPushNotifications.getInstance().providePushNotificationsHandler()
                .addMessageCallback(callback);
        return new SmartCredentialsResponse<>();
    }
}
