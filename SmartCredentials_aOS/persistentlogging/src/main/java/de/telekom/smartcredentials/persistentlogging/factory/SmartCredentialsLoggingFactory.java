/*
 * Copyright (c) 2019 Telekom Deutschland AG
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

package de.telekom.smartcredentials.persistentlogging.factory;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.LoggingApi;
import de.telekom.smartcredentials.persistentlogging.controllers.LoggingController;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
@SuppressWarnings("unused")
public class SmartCredentialsLoggingFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Persistent Logging Module have not been initialized";

    private static LoggingApi sLoggingController;

    private SmartCredentialsLoggingFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized LoggingApi initSmartCredentialsLoggingModule() {
        sLoggingController = new LoggingController();
        return sLoggingController;
    }

    @NonNull
    public static synchronized LoggingApi getLoggingApi() {
        if (sLoggingController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sLoggingController;
    }

    public static void clear() {
        sLoggingController = null;
    }
}
