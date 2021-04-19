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

package de.telekom.smartcredentials.authorization.di;

import android.content.Context;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.authorization.controllers.AuthorizationController;
import de.telekom.smartcredentials.authorization.security.CipherManager;
import de.telekom.smartcredentials.authorization.security.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.di.Provides;

/**
 * Created by Gabriel Blaj on April 12, 2020.
 */
public class ObjectGraphCreatorAuthorization {

    private static ObjectGraphCreatorAuthorization sInstance;

    private SecurityApi mSecurityApi;
    private StorageApi mStorageApi;

    private ObjectGraphCreatorAuthorization() {
        // empty constructor
    }

    public static ObjectGraphCreatorAuthorization getInstance() {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorAuthorization();
        }
        return sInstance;
    }

    public AuthorizationController provideAuthorizationController(CoreController coreController) {
        return new AuthorizationController(coreController, provideCipherManager(getSecurityApi()));
    }

    public void init(SecurityApi securityApi, StorageApi storageApi) {
        mSecurityApi = securityApi;
        mStorageApi = storageApi;
    }

    public SecurityApi getSecurityApi() {
        if (mSecurityApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.SECURITY_MODULE + " from "
                    + SmartCredentialsModuleSet.AUTHORIZATION_MODULE + " has not been initialized");
        }
        return mSecurityApi;
    }

    public StorageApi getStorageApi() {
        if (mStorageApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.STORAGE_MODULE + " from "
                    + SmartCredentialsModuleSet.AUTHORIZATION_MODULE + " has not been initialized");
        }
        return mStorageApi;
    }

    @Provides
    @NonNull
    private CipherManager provideCipherManager(SecurityApi securityApi) {
        return new CipherManager(securityApi);
    }

    public KeyguardManagerWrapper provideKeyguardManagerWrapper(Context context) {
        return new KeyguardManagerWrapper(context);
    }

    public static void destroy() {
        sInstance = null;
    }
}