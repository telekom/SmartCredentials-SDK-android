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

package de.telekom.smartcredentials.pushnotifications.controllers;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsMessage;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsRemoteMessage;
import de.telekom.smartcredentials.core.pushnotifications.SmartCredentialsSendError;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.pushnotifications.utils.MessageMapper;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class PushNotificationsController implements PushNotificationsApi {

    private final CoreController mCoreController;
    private final MessageMapper mMessageMapper;

    public PushNotificationsController(CoreController coreController) {
        mCoreController = coreController;
        mMessageMapper = new MessageMapper();
    }

    @Override
    public SmartCredentialsApiResponse<Void> subscribe() {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SUBSCRIBE)) {
            String errorMessage = SmartCredentialsFeatureSet.SUBSCRIBE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (ObjectGraphCreatorPushNotifications.getInstance().isSubscribed()) {
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "already subscribed to push notifications");
        } else {
            ObjectGraphCreatorPushNotifications.getInstance().setSubscriptionState(true);
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "successfully subscribed to push notifications");
        }

        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unsubscribe() {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNSUBSCRIBE)) {
            String errorMessage = SmartCredentialsFeatureSet.UNSUBSCRIBE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (ObjectGraphCreatorPushNotifications.getInstance().isSubscribed()) {
            ObjectGraphCreatorPushNotifications.getInstance().setSubscriptionState(false);
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "successfully unsubscribed to push notifications");
        } else {
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "app is not subscribed to push notifications");
        }

        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> subscribeToTopic(String topic) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SUBSCRIBE_TO_TOPIC)) {
            String errorMessage = SmartCredentialsFeatureSet.SUBSCRIBE_TO_TOPIC.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "successfully subscribed to " + topic + " topic");
                        if (!ObjectGraphCreatorPushNotifications.getInstance().isSubscribed()) {
                            ApiLoggerResolver.logInfo("Please use the subscribe method in order to receive notifications about this topic ");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "could not subscribe to " + topic + " topic");
                    }
                });
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unsubscribeToTopic(String topic) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNSUBSCRIBE_TO_TOPIC)) {
            String errorMessage = SmartCredentialsFeatureSet.UNSUBSCRIBE_TO_TOPIC.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "successfully unsubscribed to " + topic + " topic");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "could not unsubscribe to " + topic + " topic");
                    }
                });
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsResponse<String> retrieveToken() {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.RETRIEVE_TOKEN)) {
            String errorMessage = SmartCredentialsFeatureSet.RETRIEVE_TOKEN.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }
        String token = FirebaseInstanceId.getInstance().getToken();
        return new SmartCredentialsResponse<>(token);
    }

    @Override
    public SmartCredentialsResponse<Void> sendMessage(SmartCredentialsMessage message) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SEND_MESSAGE)) {
            String errorMessage = SmartCredentialsFeatureSet.SEND_MESSAGE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        FirebaseMessaging.getInstance().send(mMessageMapper.mapToRemoteMessage(message));
        return new SmartCredentialsResponse<>();
    }

    @Override
    public LiveData<String> getTokenData() {
        return ObjectGraphCreatorPushNotifications.getInstance().getTokenData();
    }

    @Override
    public LiveData<SmartCredentialsRemoteMessage> getMessageData() {
        return ObjectGraphCreatorPushNotifications.getInstance().getRemoteMessageData();
    }

    @Override
    public LiveData<String> getSentMessageIdData() {
        return ObjectGraphCreatorPushNotifications.getInstance().getSentMessageIdData();
    }

    @Override
    public LiveData<SmartCredentialsSendError> getSendErrorData() {
        return ObjectGraphCreatorPushNotifications.getInstance().getSendErrorData();
    }
}
