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

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.core.pushnotifications.models.PushNotificationsError;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.enums.PushNotificationsMessages;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;
import de.telekom.smartcredentials.pushnotifications.rest.RetrofitClient;
import de.telekom.smartcredentials.pushnotifications.rest.http.HttpClientFactory;
import de.telekom.smartcredentials.pushnotifications.rest.models.TpnsRequestBody;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Alex.Graur@endava.com at 5/22/2020
 */
public class TpnsController extends BaseController {

    private final RetrofitClient mRetrofitClient;
    private final CompositeDisposable mCompositeDisposable;

    TpnsController() {
        mRetrofitClient = new RetrofitClient(new HttpClientFactory());
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public SmartCredentialsApiResponse<Void> subscribe(PushNotificationsCallback callback) {
        registerToTPNS(new PushNotificationsCallback() {
            @Override
            public void onSuccess(String message) {
                mStorageRepository.saveConfigurationValue(
                        PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE, true);
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                        PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
                if (callback != null) {
                    callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
                }
            }

            @Override
            public void onFailure(String message, List<PushNotificationsError> errors) {
                ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                        PushNotificationsMessages.UNSUCCESSFULLY_SUBSCRIBED.getMessage());
                if (callback != null) {
                    callback.onFailure(message, errors);
                }
            }
        });
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unsubscribe(PushNotificationsCallback callback) {
        unregisterFromTPNS(new PushNotificationsCallback() {
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
        return new SmartCredentialsResponse<>();
    }

    private void registerToTPNS(PushNotificationsCallback callback) {
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
    }

    private void unregisterFromTPNS(PushNotificationsCallback callback) {
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
    }
}
