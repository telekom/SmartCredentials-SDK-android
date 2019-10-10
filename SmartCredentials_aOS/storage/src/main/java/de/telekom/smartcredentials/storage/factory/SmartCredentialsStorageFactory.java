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

package de.telekom.smartcredentials.storage.factory;

import android.content.Context;
import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.storage.controllers.StorageController;
import de.telekom.smartcredentials.storage.di.ObjectGraphCreatorStorage;

@SuppressWarnings("unused")
public class SmartCredentialsStorageFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials Storage Module have not been initialized";
    private static final String PROVIDER_NOT_INITIALIZED_EXCEPTION = "OTP Provider from the Persistence Module have not been initialized";

    private static StorageController sStorageController;

    private SmartCredentialsStorageFactory() {
        // required empty constructor
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized StorageApi initSmartCredentialsStorageModule(@NonNull final Context context,
                                                                            @NonNull final CoreApi coreApi,
                                                                            @NonNull final SecurityApi securityApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.STORAGE_MODULE.getModuleName());
        }
        ObjectGraphCreatorStorage objectGraphCreatorStorage = ObjectGraphCreatorStorage.getInstance(context);
        objectGraphCreatorStorage.init(securityApi);
        sStorageController = ObjectGraphCreatorStorage.getInstance(context).provideStorageController(coreController);
        return sStorageController;
    }

    @NonNull
    @SuppressWarnings("unused")
    public static synchronized StorageApi getStorageApi() {
        if (sStorageController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sStorageController;
    }

    public static void clear() {
        ObjectGraphCreatorStorage.destroy();
        sStorageController.clearStorage();
        sStorageController.detach();
        sStorageController = null;
    }
}
