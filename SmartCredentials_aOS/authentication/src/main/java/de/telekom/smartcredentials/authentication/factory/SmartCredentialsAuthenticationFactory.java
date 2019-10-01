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

package de.telekom.smartcredentials.authentication.factory;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.authentication.controllers.AuthenticationController;
import de.telekom.smartcredentials.authentication.di.ObjectGraphCreatorAuthentication;
import de.telekom.smartcredentials.core.api.AuthenticationApi;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;

@SuppressWarnings("unused")
public class SmartCredentialsAuthenticationFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Authentication Module have not been initialized";

    private static AuthenticationController sAuthenticationController;

    private SmartCredentialsAuthenticationFactory() {
        // required empty constructor
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized AuthenticationApi initSmartCredentialsAuthenticationModule(@NonNull CoreApi coreApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.AUTHENTICATION_MODULE.getModuleName());
        }
        sAuthenticationController = ObjectGraphCreatorAuthentication.getInstance().provideApiControllerAuthentication(coreController);
        return sAuthenticationController;
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized AuthenticationApi getAuthenticationApi() {
        if (sAuthenticationController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sAuthenticationController;
    }

    public static void clear() {
        ObjectGraphCreatorAuthentication.destroy();
        sAuthenticationController = null;
    }
}
