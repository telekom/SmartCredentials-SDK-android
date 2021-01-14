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

package de.telekom.smartcredentials.security.factory;

import android.content.Context;
import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.security.controllers.SecurityController;
import de.telekom.smartcredentials.security.di.ObjectGraphCreatorSecurity;

@SuppressWarnings("unused")
public class SmartCredentialsSecurityFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Security Module have not been initialized";
    private static final String PROVIDER_NOT_INITIALIZED_EXCEPTION = "Persistence Provider from the Security Module have not been initialized";

    private static SecurityController sSecurityController;

    private SmartCredentialsSecurityFactory() {
        // required empty constructor
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized SecurityApi initSmartCredentialsSecurityModule(@NonNull final Context context,
                                                                              @NonNull final CoreApi coreApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.SECURITY_MODULE.getModuleName());
        }
        sSecurityController = ObjectGraphCreatorSecurity.getInstance(context, coreController.getAppAlias()).provideSecurityController(context, coreController);
        return sSecurityController;
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized SecurityApi getSecurityApi() {
        if (sSecurityController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sSecurityController;
    }

    public static void clear() {
        ObjectGraphCreatorSecurity.destroy();
        sSecurityController = null;
    }
}
