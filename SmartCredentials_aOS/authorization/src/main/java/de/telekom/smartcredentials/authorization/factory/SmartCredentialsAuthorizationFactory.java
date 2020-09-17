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

package de.telekom.smartcredentials.authorization.factory;

import android.content.Context;
import androidx.annotation.NonNull;

import de.telekom.smartcredentials.authorization.controllers.AuthorizationController;
import de.telekom.smartcredentials.authorization.di.ObjectGraphCreatorAuthorization;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;

@SuppressWarnings("unused")
public class SmartCredentialsAuthorizationFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Authorization Module have not been initialized";

    private static AuthorizationController sAuthorizationController;

    private SmartCredentialsAuthorizationFactory() {
        // required empty constructor
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized AuthorizationApi initSmartCredentialsAuthorizationModule(@NonNull final Context context,
                                                                                        @NonNull final CoreApi coreApi,
                                                                                        @NonNull final SecurityApi securityApi,
                                                                                        @NonNull final StorageApi storageApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.AUTHORIZATION_MODULE.getModuleName());
        }
        ObjectGraphCreatorAuthorization objectGraphCreatorAuthorization = ObjectGraphCreatorAuthorization.getInstance();
        objectGraphCreatorAuthorization.init(securityApi, storageApi);
        sAuthorizationController = ObjectGraphCreatorAuthorization.getInstance()
                .provideAuthorizationController(context, coreController);
        return sAuthorizationController;
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized AuthorizationApi getAuthorizationApi() {
        if (sAuthorizationController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sAuthorizationController;
    }

    public static void clear() {
        ObjectGraphCreatorAuthorization.destroy();
        sAuthorizationController = null;
    }
}
