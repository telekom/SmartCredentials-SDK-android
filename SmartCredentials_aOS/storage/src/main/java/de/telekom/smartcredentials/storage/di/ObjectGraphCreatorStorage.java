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

package de.telekom.smartcredentials.storage.di;

import android.content.Context;
import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.di.Provides;
import de.telekom.smartcredentials.core.repositories.Repository;
import de.telekom.smartcredentials.storage.controllers.StorageController;
import de.telekom.smartcredentials.storage.repositories.RepositoryObjectCreator;
import de.telekom.smartcredentials.storage.repositories.RepositoryProvider;


/**
 * Created by Lucian Iacob on November 09, 2018.
 */
public class ObjectGraphCreatorStorage {

    private static ObjectGraphCreatorStorage sInstance;

    private SecurityApi mSecurityApi;

    private final RepositoryObjectCreator mRepositoryObjectCreator;

    private ObjectGraphCreatorStorage(final Context context) {
        mRepositoryObjectCreator = new RepositoryObjectCreator(context, new GsonBuilder().create());
    }

    public static ObjectGraphCreatorStorage getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorStorage(context);
        }
        return sInstance;
    }

    public void init(SecurityApi securityApi) {
        mSecurityApi = securityApi;
    }

    public static void destroy() {
        sInstance = null;
    }

    @NonNull
    public StorageController provideStorageController(CoreController coreController) {
        return new StorageController(coreController, provideRepository(), getSecurityApi().getEncryptionStrategy(), new GsonBuilder().create());
    }

    @Provides
    @NonNull
    private Repository provideRepository() {
        return new RepositoryProvider(mRepositoryObjectCreator.provideSensitiveDataRepo(),
                mRepositoryObjectCreator.provideNonSensitiveDataRepo());
    }

    private SecurityApi getSecurityApi() {
        if (mSecurityApi == null) {
            throw new RuntimeException(SmartCredentialsModuleSet.SECURITY_MODULE + " from "
                    + SmartCredentialsModuleSet.STORAGE_MODULE + " has not been initialized");
        }
        return mSecurityApi;
    }
}
