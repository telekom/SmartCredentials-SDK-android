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

package de.telekom.smartcredentials.wispr.factory;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.WisprApi;
import de.telekom.smartcredentials.wispr.controllers.WisprController;
import okhttp3.CertificatePinner;

@SuppressWarnings("unused")
public class SmartCredentialsWisprFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials WISPr Module have not been initialized";

    private static WisprController sWisprController;

    private SmartCredentialsWisprFactory() {
        // required empty constructor
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized WisprApi initSmartCredentialsWisprModule(CertificatePinner certificatePinner) {
        sWisprController = new WisprController(certificatePinner);
        return sWisprController;
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized WisprApi getWisprApi() {
        if (sWisprController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sWisprController;
    }

    @SuppressWarnings("unused")
    public static void clear() {
        sWisprController = null;
    }
}
