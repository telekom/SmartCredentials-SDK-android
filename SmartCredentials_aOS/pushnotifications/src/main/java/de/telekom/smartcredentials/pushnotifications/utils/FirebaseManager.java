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

package de.telekom.smartcredentials.pushnotifications.utils;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import de.telekom.smartcredentials.core.pushnotifications.configuration.PushNotificationsConfiguration;

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public class FirebaseManager {

    public static void initializeFirebase(PushNotificationsConfiguration configuration) {
        FirebaseApp.initializeApp(configuration.getContext(), new FirebaseOptions.Builder().
                setApiKey(configuration.getApiKey()).
                setProjectId(configuration.getProjectId()).
                setDatabaseUrl(configuration.getDatabaseUrl()).
                setApplicationId(configuration.getApplicationId()).
                setGcmSenderId(configuration.getGcmSenderId()).
                setStorageBucket(configuration.getStorageBucket()).build());
    }
}
