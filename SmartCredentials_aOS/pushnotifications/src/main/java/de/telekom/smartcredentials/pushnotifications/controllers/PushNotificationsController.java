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

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsDataCallback;
import de.telekom.smartcredentials.core.pushnotifications.models.PushNotificationsError;
import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsMessage;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.pushnotifications.enums.PushNotificationsMessages;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;
import de.telekom.smartcredentials.pushnotifications.rest.RetrofitClient;
import de.telekom.smartcredentials.pushnotifications.rest.http.HttpClientFactory;
import de.telekom.smartcredentials.pushnotifications.rest.models.TpnsRequestBody;
import de.telekom.smartcredentials.pushnotifications.utils.MessageMapper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class PushNotificationsController implements PushNotificationsApi {

    private final CoreController mCoreController;
    private final MessageMapper mMessageMapper;
    private final RetrofitClient mRetrofitClient;
    private final PushNotificationsStorageRepository mStorageRepository;
    private final CompositeDisposable mCompositeDisposable;

    public PushNotificationsController(CoreController coreController) {
        mCoreController = coreController;
        mMessageMapper = new MessageMapper();
        mRetrofitClient = new RetrofitClient(new HttpClientFactory());
        mCompositeDisposable = new CompositeDisposable();
        mStorageRepository = ObjectGraphCreatorPushNotifications.getInstance().providePushNotificationsStorageRepository();
    }

    @Override
    public SmartCredentialsApiResponse<Void> subscribe(PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.SUBSCRIBE)) {
            String errorMessage = SmartCredentialsFeatureSet.SUBSCRIBE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mStorageRepository.getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_SERVICE_TYPE)
                .equals(ServiceType.TPNS.name())) {

            registerToTPNS(new PushNotificationsCallback() {
                @Override
                public void onSuccess(String message) {
                    mStorageRepository.saveConfigurationValue(
                            PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE, true);
                    ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                            PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
                    if(callback != null) {
                        callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
                    }
                }

                @Override
                public void onFailure(String message, List<PushNotificationsError> errors) {
                    ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                            PushNotificationsMessages.UNSUCCESSFULLY_SUBSCRIBED.getMessage());
                    if(callback != null) {
                        callback.onFailure(message, errors);
                    }
                }
            });
        } else {
            if (mStorageRepository.getPushNotificationsConfigBoolean(
                    PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE)) {
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), PushNotificationsMessages.ALREADY_SUBSCRIBED.getMessage());
                if(callback != null) {
                    callback.onFailure(PushNotificationsMessages.ALREADY_SUBSCRIBED.getMessage(), new ArrayList<>());
                }
            } else {
                mStorageRepository.saveConfigurationValue(
                        PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE, true);
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                        PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
                if(callback != null) {
                    callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
                }
            }
        }
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> registerToTPNS(PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.REGISTER_TO_TPNS)) {
            String errorMessage = SmartCredentialsFeatureSet.REGISTER_TO_TPNS.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mStorageRepository.getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_SERVICE_TYPE)
                .equals(ServiceType.TPNS.name())) {
            if (mStorageRepository.getPushNotificationsConfigBoolean(
                    PushNotificationsStorageRepository.KEY_TPNS_REGISTRATION_STATE)) {
                callback.onFailure(PushNotificationsMessages.ALREADY_REGISTERED.getMessage(), new ArrayList<>());
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), PushNotificationsMessages.ALREADY_REGISTERED.getMessage());
            } else {
                JsonObject registrationBody = ObjectGraphCreatorPushNotifications.getInstance()
                        .provideTpnsRequestBody().getRegistrationBodyJson();
                String url = mRetrofitClient.getTpnsUrl(mStorageRepository.getPushNotificationsConfigBoolean(
                        PushNotificationsStorageRepository.KEY_TPNS_PRODUCTION_STATE));
                mCompositeDisposable.add(
                        mRetrofitClient.createTpnsService(url)
                                .register(registrationBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> {
                                    if (response.isSuccessful()) {
                                        mStorageRepository.saveConfigurationValue(
                                                PushNotificationsStorageRepository.KEY_TPNS_REGISTRATION_STATE, true);
                                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                                                PushNotificationsMessages.SUCCESSFULLY_REGISTERED.getMessage());
                                        callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_REGISTERED.getMessage());
                                    } else {
                                        ApiLoggerResolver.logError(getClass().getSimpleName(), response.getMessage());
                                        callback.onFailure(response.getMessage(), response.getErrorsList());
                                    }
                                }, throwable -> {
                                    ApiLoggerResolver.logError(getClass().getSimpleName(), throwable.getLocalizedMessage());
                                    callback.onFailure(throwable.getLocalizedMessage(), new ArrayList<>());
                                })
                );
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), PushNotificationsMessages.INVALID_TPNS_CONFIGURATION.getMessage());
            callback.onFailure(PushNotificationsMessages.INVALID_TPNS_CONFIGURATION.getMessage(), new ArrayList<>());
        }
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unsubscribe(PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNSUBSCRIBE)) {
            String errorMessage = SmartCredentialsFeatureSet.UNSUBSCRIBE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mStorageRepository.getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_SERVICE_TYPE)
                .equals(ServiceType.TPNS.name())) {

            unregisterToTPNS(new PushNotificationsCallback() {
                @Override
                public void onSuccess(String message) {
                    mStorageRepository.saveConfigurationValue(
                            PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE, false);
                    ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                            PushNotificationsMessages.SUCCESSFULLY_UNSUBSCRIBED.getMessage());
                    callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_UNSUBSCRIBED.getMessage());
                }

                @Override
                public void onFailure(String message, List<PushNotificationsError> errors) {
                    ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                            PushNotificationsMessages.UNSUCCESSFULLY_UNSUBSCRIBED.getMessage());
                    callback.onFailure(message, errors);
                }
            });
        } else {
            if (mStorageRepository.getPushNotificationsConfigBoolean(
                    PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE)) {
                mStorageRepository.saveConfigurationValue(
                        PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE, false);
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                        PushNotificationsMessages.SUCCESSFULLY_UNSUBSCRIBED.getMessage());
                callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_UNSUBSCRIBED.getMessage());
            } else {
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                        PushNotificationsMessages.NOT_SUBSCRIBED.getMessage());
                callback.onFailure(PushNotificationsMessages.NOT_SUBSCRIBED.getMessage(), new ArrayList<>());
            }
        }
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unregisterToTPNS(PushNotificationsCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.UNREGISTER_TO_TPNS)) {
            String errorMessage = SmartCredentialsFeatureSet.UNREGISTER_TO_TPNS.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        if (mStorageRepository.getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_SERVICE_TYPE)
                .equals(ServiceType.TPNS.name())) {
            if (mStorageRepository.getPushNotificationsConfigBoolean(
                    PushNotificationsStorageRepository.KEY_TPNS_REGISTRATION_STATE)) {
                TpnsRequestBody requestBody = ObjectGraphCreatorPushNotifications.getInstance()
                        .provideTpnsRequestBody();
                String url = mRetrofitClient.getTpnsUrl(mStorageRepository.getPushNotificationsConfigBoolean(
                        PushNotificationsStorageRepository.KEY_TPNS_PRODUCTION_STATE));
                mCompositeDisposable.add(
                        mRetrofitClient.createTpnsService(url)
                                .unregister(requestBody.getApplicationKey(), requestBody.getDeviceId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(response -> {
                                    if (response.isSuccessful()) {
                                        mStorageRepository.saveConfigurationValue(
                                                PushNotificationsStorageRepository.KEY_TPNS_REGISTRATION_STATE, false);
                                        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                                                PushNotificationsMessages.SUCCESSFULLY_UNREGISTERED.getMessage());
                                        callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_UNREGISTERED.getMessage());
                                    } else {
                                        ApiLoggerResolver.logError(getClass().getSimpleName(), response.getMessage());
                                        callback.onFailure(response.getMessage(), response.getErrorsList());
                                    }
                                }, throwable -> {
                                    ApiLoggerResolver.logError(getClass().getSimpleName(), throwable.getLocalizedMessage());
                                    callback.onFailure(throwable.getLocalizedMessage(), new ArrayList<>());
                                })
                );
            } else {
                callback.onFailure(PushNotificationsMessages.NOT_REGISTERED_TO_TPNS.getMessage(), new ArrayList<>());
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), PushNotificationsMessages.NOT_REGISTERED_TO_TPNS.getMessage());
            }
        } else {
            ApiLoggerResolver.logError(getClass().getSimpleName(), PushNotificationsMessages.INVALID_TPNS_CONFIGURATION.getMessage());
            callback.onFailure(PushNotificationsMessages.INVALID_TPNS_CONFIGURATION.getMessage(), new ArrayList<>());
        }
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> subscribeToTopic(String topic, PushNotificationsCallback callback) {
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
    public SmartCredentialsApiResponse<Void> unsubscribeToTopic(String topic, PushNotificationsCallback callback) {
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
    public SmartCredentialsResponse<String> retrieveDeviceId() {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.RETRIEVE_DEVICE_ID)) {
            String errorMessage = SmartCredentialsFeatureSet.RETRIEVE_DEVICE_ID.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }
        String deviceId = mStorageRepository.getPushNotificationsConfigString(PushNotificationsStorageRepository.KEY_DEVICE_ID);
        return new SmartCredentialsResponse<>(deviceId);
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
    public SmartCredentialsResponse<Void> createDataGenerator(PushNotificationsDataCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.DATA_GENERATOR)) {
            String errorMessage = SmartCredentialsFeatureSet.DATA_GENERATOR.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }
        ObjectGraphCreatorPushNotifications.getInstance().providePushNotificationsHandler()
                .instantiateCallback(callback);
        return new SmartCredentialsResponse<>();
    }
}
