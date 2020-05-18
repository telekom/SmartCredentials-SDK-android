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

package de.telekom.smartcredentials.core.configurations;

import android.content.Context;

/**
 * Created by gabriel.blaj@endava.com at 5/14/2020
 */
public class PushNotificationsConfiguration {

    private final Context context;
    private final String apiKey;
    private final String projectId;
    private final String databaseUrl;
    private final String applicationId;
    private final String gcmSenderId;
    private final String storageBucket;

    public PushNotificationsConfiguration(Context context, String apiKey, String projectId, String databaseUrl, String applicationId, String gcmSenderId, String storageBucket) {
        this.context = context;
        this.apiKey = apiKey;
        this.projectId = projectId;
        this.databaseUrl = databaseUrl;
        this.applicationId = applicationId;
        this.gcmSenderId = gcmSenderId;
        this.storageBucket = storageBucket;
    }

    public Context getContext() {
        return context;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getProjectId() {
        return projectId;
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getGcmSenderId() {
        return gcmSenderId;
    }

    public String getStorageBucket() {
        return storageBucket;
    }
}
