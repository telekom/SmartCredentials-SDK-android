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

package de.telekom.smartcredentials.pushnotifications.repositories;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilter;
import de.telekom.smartcredentials.core.filter.SmartCredentialsFilterFactory;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by gabriel.blaj@endava.com at 5/18/2020
 */
public class PushNotificationsStorageRepository {

    private static PushNotificationsStorageRepository INSTANCE;

    public final static String KEY_SUBSCRIPTION_STATE = "subscription_state";
    public final static String KEY_TPNS_REGISTRATION_STATE = "registration_state";
    public final static String KEY_SERVICE_TYPE = "service_type";
    public final static String KEY_DEVICE_ID = "device_id";
    public final static String KEY_TPNS_APPLICATION_KEY = "tpns_application_key";
    public final static String KEY_TPNS_PRODUCTION_STATE = "tpns_production_state";
    public final static String KEY_AUTO_SUBSCRIBE = "auto_subscribe";
    private final static String KEY_PUSH_NOTIFICATIONS_CONFIG_ID = "smartcredentials_push_notifications_config";
    private final static String KEY_PUSH_NOTIFICATIONS_CONFIG_TYPE = "push_notifications_config";

    private StorageApi mStorageApi;
    private ItemContext mItemContext;

    private PushNotificationsStorageRepository(StorageApi storageApi) {
        mStorageApi = storageApi;
        mItemContext = ItemContextFactory.createEncryptedSensitiveItemContext(KEY_PUSH_NOTIFICATIONS_CONFIG_TYPE);
    }

    public static PushNotificationsStorageRepository getInstance(StorageApi storageApi) {
        if (INSTANCE == null) {
            INSTANCE = new PushNotificationsStorageRepository(storageApi);
        }
        return INSTANCE;
    }

    public void saveConfigurationValue(String key, String value) {
        ItemEnvelope pushNotificationsConfig = getPushNotificationsConfigItem();
        JSONObject identifier = pushNotificationsConfig.getIdentifier();
        try {
            identifier.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pushNotificationsConfig.setIdentifier(identifier);
        mStorageApi.putItem(pushNotificationsConfig, mItemContext);
    }

    public void saveConfigurationValue(String key, boolean value) {
        ItemEnvelope pushNotificationsConfig = getPushNotificationsConfigItem();
        JSONObject identifier = pushNotificationsConfig.getIdentifier();
        try {
            identifier.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pushNotificationsConfig.setIdentifier(identifier);
        mStorageApi.putItem(pushNotificationsConfig, mItemContext);
    }


    private ItemEnvelope getPushNotificationsConfigItem() {
        SmartCredentialsFilter filter = SmartCredentialsFilterFactory
                .createSensitiveItemFilter(KEY_PUSH_NOTIFICATIONS_CONFIG_ID, KEY_PUSH_NOTIFICATIONS_CONFIG_TYPE);
        ItemContext itemContext = ItemContextFactory
                .createEncryptedSensitiveItemContext(KEY_PUSH_NOTIFICATIONS_CONFIG_TYPE);
        SmartCredentialsApiResponse<ItemEnvelope> response = mStorageApi.getItemDetailsById(filter);
        if (response.isSuccessful()) {
            return response.getData();
        } else {
            ItemEnvelope itemEnvelope = ItemEnvelopeFactory
                    .createItemEnvelope(KEY_PUSH_NOTIFICATIONS_CONFIG_ID, new JSONObject());
            mStorageApi.putItem(itemEnvelope, itemContext);
            return itemEnvelope;
        }
    }

    public String getPushNotificationsConfigString(String key) {
        try {
            return getPushNotificationsConfigItem().getIdentifier().getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean getPushNotificationsConfigBoolean(String key) {
        try {
            return getPushNotificationsConfigItem().getIdentifier().getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

}
