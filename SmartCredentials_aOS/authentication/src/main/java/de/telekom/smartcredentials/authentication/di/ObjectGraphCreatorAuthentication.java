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

package de.telekom.smartcredentials.authentication.di;

import android.support.annotation.NonNull;

import de.telekom.smartcredentials.authentication.AuthenticationStorageRepository;
import de.telekom.smartcredentials.authentication.controllers.AuthenticationController;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;

public class ObjectGraphCreatorAuthentication {

    private static ObjectGraphCreatorAuthentication sInstance;
    private StorageApi mStorageApi;

    private ObjectGraphCreatorAuthentication() {
        // required empty constructor
    }

    public static ObjectGraphCreatorAuthentication getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorAuthentication();
        }
        return sInstance;
    }

    public void init(StorageApi storageApi) {
        mStorageApi = storageApi;
    }

    @NonNull
    public AuthenticationController provideApiControllerAuthentication(CoreController coreController) {
        return AuthenticationController.getInstance(coreController);
    }

    private StorageApi getStorageApi() {
        if (mStorageApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.STORAGE_MODULE + " from "
                    + SmartCredentialsModuleSet.OTP_MODULE + " has not been initialized");
        }
        return mStorageApi;
    }

    public AuthenticationStorageRepository provideAuthenticationStorageRepository(){
        return AuthenticationStorageRepository.getInstance(getStorageApi());
    }

    public static void destroy() {
        sInstance = null;
    }
}
