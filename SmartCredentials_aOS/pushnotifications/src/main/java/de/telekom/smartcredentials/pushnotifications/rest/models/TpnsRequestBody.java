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

package de.telekom.smartcredentials.pushnotifications.rest.models;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;

import java.util.UUID;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.pushnotifications.di.ObjectGraphCreatorPushNotifications;
import de.telekom.smartcredentials.pushnotifications.repositories.PushNotificationsStorageRepository;

/**
 * Created by gabriel.blaj@endava.com at 5/18/2020
 */
public class TpnsRequestBody {

    private static final String NULL_APPLICATION_KEY = "Please provide the TPNS Application Key.";

    private final static String KEY_APPLICATION_TYPE = "applicationType";
    private final static String KEY_APPLICATION_KEY = "applicationKey";
    private final static String KEY_DEVICE_ID = "deviceId";
    private final static String KEY_DEVICE_REGISTRATION_ID = "deviceRegistrationId";
    private final static String APPLICATION_TYPE_AOS = "AOS";

    private final PushNotificationsStorageRepository mStorageRepository;

    private final String mApplicationType;
    private final String mApplicationKey;
    private final String mDeviceId;
    private String mDeviceRegistrationId;

    public TpnsRequestBody() {
        retrieveDeviceRegistrationId();
        mStorageRepository = ObjectGraphCreatorPushNotifications.getInstance()
                .providePushNotificationsStorageRepository();
        mApplicationType = APPLICATION_TYPE_AOS;
        mApplicationKey = retrieveApplicationKey();
        mDeviceId = retrieveDeviceId();
    }

    private String getApplicationType() {
        return mApplicationType;
    }

    public String getApplicationKey() {
        return mApplicationKey;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    private String getDeviceRegistrationId() {
        return mDeviceRegistrationId;
    }

    public JsonObject getRegistrationBodyJson() {
        JsonObject registrationBody = new JsonObject();
        registrationBody.addProperty(KEY_APPLICATION_TYPE, getApplicationType());
        registrationBody.addProperty(KEY_APPLICATION_KEY, getApplicationKey());
        registrationBody.addProperty(KEY_DEVICE_ID, getDeviceId());
        registrationBody.addProperty(KEY_DEVICE_REGISTRATION_ID, getDeviceRegistrationId());
        return registrationBody;
    }

    private String retrieveApplicationKey() {
        String applicationKey = mStorageRepository.getPushNotificationsConfigString(
                PushNotificationsStorageRepository.KEY_TPNS_APPLICATION_KEY);
        if (applicationKey.equals("")) {
            ApiLoggerResolver.logError(getClass().getSimpleName(), NULL_APPLICATION_KEY);
        } else {
            ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "ApplicationKey: " + applicationKey);
        }
        return applicationKey;
    }

    private String retrieveDeviceId() {
        String deviceId = mStorageRepository.getPushNotificationsConfigString(
                PushNotificationsStorageRepository.KEY_DEVICE_ID);
        if (deviceId.equals("")) {
            deviceId = UUID.randomUUID().toString();
            mStorageRepository.saveConfigurationValue(PushNotificationsStorageRepository.KEY_DEVICE_ID, deviceId);
        }
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "DeviceId: " + deviceId);
        return deviceId;
    }

    private void retrieveDeviceRegistrationId() {
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        mDeviceRegistrationId = task.getResult();
                    }
                }
        );
    }

}
