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

import java.util.ArrayList;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.pushnotifications.enums.PushNotificationsMessages;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;

/**
 * Created by Alex.Graur@endava.com at 5/22/2020
 */
public class FcmController extends BaseController {

    @Override
    public SmartCredentialsApiResponse<Void> subscribe(PushNotificationsCallback callback) {
        if (mStorageRepository.getPushNotificationsConfigBoolean(
                PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE)) {
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), PushNotificationsMessages.ALREADY_SUBSCRIBED.getMessage());
            if (callback != null) {
                callback.onFailure(PushNotificationsMessages.ALREADY_SUBSCRIBED.getMessage(), new ArrayList<>());
            }
        } else {
            mStorageRepository.saveConfigurationValue(
                    PushNotificationsStorageRepository.KEY_SUBSCRIPTION_STATE, true);
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(),
                    PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
            if (callback != null) {
                callback.onSuccess(PushNotificationsMessages.SUCCESSFULLY_SUBSCRIBED.getMessage());
            }
        }
        return new SmartCredentialsResponse<>();
    }

    @Override
    public SmartCredentialsApiResponse<Void> unsubscribe(PushNotificationsCallback callback) {
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
        return new SmartCredentialsResponse<>();
    }
}
