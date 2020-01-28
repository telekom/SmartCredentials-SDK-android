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

package de.telekom.smartcredentials.security.di;

import android.content.Context;
import android.support.annotation.NonNull;

import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.controllers.CreationController;
import de.telekom.smartcredentials.core.di.Provides;
import de.telekom.smartcredentials.core.handlers.CreationHandler;
import de.telekom.smartcredentials.core.handlers.defaulthandlers.DefaultCreationHandler;
import de.telekom.smartcredentials.core.repositories.AliasNative;
import de.telekom.smartcredentials.security.controllers.SecurityController;
import de.telekom.smartcredentials.security.encryption.Base64EncryptionManager;
import de.telekom.smartcredentials.security.encryption.EncryptionManager;
import de.telekom.smartcredentials.security.strategies.EncryptionStrategyAdapter;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;

/**
 * Object-Creation Handling class
 * <p>
 * Created by Lucian Iacob on November 05, 2018.
 */
public class ObjectGraphCreatorSecurity {

    private static ObjectGraphCreatorSecurity sInstance;

    private final EncryptionObjectCreator mEncryptionObjectCreator;

    private ObjectGraphCreatorSecurity(Context context, String alias) {
        mEncryptionObjectCreator = new EncryptionObjectCreator(context, alias);
    }

    public static ObjectGraphCreatorSecurity getInstance(Context context, String alias) {
        if (sInstance == null) {
            sInstance = new ObjectGraphCreatorSecurity(context, alias);
        }
        return sInstance;
    }

    public static void destroy() {
        sInstance = null;
    }

    @Provides
    @NonNull
    private EncryptionStrategy provideEncryptionStrategy(Context context) {
        return new EncryptionStrategyAdapter(provideEncryptionManager(context),
                mEncryptionObjectCreator.provideRsaEncryptionManager(),
                mEncryptionObjectCreator.provideAesEncryptionManager(context));
    }

    @Provides
    @NonNull
    private EncryptionManager provideEncryptionManager(Context context) {
        return new Base64EncryptionManager(mEncryptionObjectCreator.provideEncryptionManagerBelowApi23(),
                mEncryptionObjectCreator.provideEncryptionManagerAboveApi23(context));
    }

    private CreationController provideCreationController(Context context) {
        return new CreationController(provideCreationHandler(context));
    }

    private CreationHandler provideCreationHandler(Context context) {
        return new DefaultCreationHandler(provideEncryptionStrategy(context));
    }

    @NonNull
    public SecurityController provideSecurityController(Context context, CoreController coreController) {
        return new SecurityController(context, coreController, provideCreationController(context), provideEncryptionStrategy(context));
    }
}
