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

package de.telekom.smartcredentials.core.pushnotifications.configuration;

import android.content.Context;

import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.core.pushnotifications.enums.TpnsEnvironment;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class PushNotificationsConfiguration {

    private final Context mContext;
    private final String mApiKey;
    private final String mProjectId;
    private final String mDatabaseUrl;
    private final String mApplicationId;
    private final String mGcmSenderId;
    private final String mStorageBucket;
    private final ServiceType mServiceType;
    private final String mTpnsApplicationKey;
    private final TpnsEnvironment mTpnsEnvironment;
    private final boolean mAutoSubscribeState;

    private PushNotificationsConfiguration(ConfigurationBuilder builder) {
        mContext = builder.context;
        mApiKey = builder.apiKey;
        mProjectId = builder.projectId;
        mDatabaseUrl = builder.databaseUrl;
        mApplicationId = builder.applicationId;
        mGcmSenderId = builder.gcmSenderId;
        mStorageBucket = builder.storageBucket;
        mServiceType = builder.serviceType;
        mTpnsApplicationKey = builder.tpnsApplicationKey;
        mTpnsEnvironment = builder.tpnsEnvironment;
        mAutoSubscribeState = builder.autoSubscribeState;
    }

    public Context getContext() {
        return mContext;
    }

    public String getApiKey() {
        return mApiKey;
    }

    public String getProjectId() {
        return mProjectId;
    }

    public String getDatabaseUrl() {
        return mDatabaseUrl;
    }

    public String getApplicationId() {
        return mApplicationId;
    }

    public String getGcmSenderId() {
        return mGcmSenderId;
    }

    public String getStorageBucket() {
        return mStorageBucket;
    }

    public ServiceType getServiceType() {
        return mServiceType;
    }

    public String getTpnsApplicationKey() {
        return mTpnsApplicationKey;
    }

    public TpnsEnvironment getTpnsEnvironment() {
        return mTpnsEnvironment;
    }

    public boolean getAutoSubscribeState() {
        return mAutoSubscribeState;
    }

    public static class ConfigurationBuilder {
        private Context context;
        private String apiKey;
        private String projectId;
        private String databaseUrl;
        private String applicationId;
        private String gcmSenderId;
        private String storageBucket;
        private String tpnsApplicationKey = "";
        private ServiceType serviceType;
        private TpnsEnvironment tpnsEnvironment = TpnsEnvironment.PRODUCTION;
        private boolean autoSubscribeState = true;

        public ConfigurationBuilder(Context context, String apikey, String projectId,
                                    String databaseUrl, String applicationId, String gcmSenderId,
                                    String storageBucket, ServiceType serviceType) {
            this.context = context;
            this.apiKey = apikey;
            this.projectId = projectId;
            this.databaseUrl = databaseUrl;
            this.applicationId = applicationId;
            this.gcmSenderId = gcmSenderId;
            this.storageBucket = storageBucket;
            this.serviceType = serviceType;
        }

        public ConfigurationBuilder setTpnsApplicationKey(String tpnsApplicationKey) {
            this.tpnsApplicationKey = tpnsApplicationKey;
            return this;
        }

        public ConfigurationBuilder setTpnsEnvironment(TpnsEnvironment tpnsEnvironment) {
            this.tpnsEnvironment = tpnsEnvironment;
            return this;
        }

        public ConfigurationBuilder setAutoSubscribeState(boolean autoSubscribeState) {
            this.autoSubscribeState = autoSubscribeState;
            return this;
        }

        public PushNotificationsConfiguration build() {
            return new PushNotificationsConfiguration(this);
        }
    }

}
