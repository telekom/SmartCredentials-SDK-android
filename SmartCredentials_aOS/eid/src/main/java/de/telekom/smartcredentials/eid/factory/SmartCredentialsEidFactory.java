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

package de.telekom.smartcredentials.eid.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.EidApi;
import de.telekom.smartcredentials.eid.controllers.EidController;

/**
 * Created by Alex.Graur@endava.com at 11/8/2019
 */
@SuppressWarnings("unused")
public class SmartCredentialsEidFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials e-ID Module have not been initialized";

    private static EidController sEidController;

    private SmartCredentialsEidFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized EidApi initSmartCredentialsEidModule(Context context, String appPackage) {
        sEidController = new EidController(context, appPackage);
        return sEidController;
    }

    @NonNull
    public static synchronized EidController getEidApi() {
        if (sEidController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sEidController;
    }

    public static void clear(Context context) {
        sEidController.close(context);
        sEidController = null;
    }
}
