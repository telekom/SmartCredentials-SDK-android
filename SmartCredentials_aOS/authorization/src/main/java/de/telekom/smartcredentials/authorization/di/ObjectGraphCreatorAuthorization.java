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

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.os.CancellationSignal;

import de.telekom.smartcredentials.authorization.biometric.BiometricPromptWrapper;
import de.telekom.smartcredentials.authorization.controllers.AuthorizationController;
import de.telekom.smartcredentials.authorization.fingerprint.AuthHandler;
import de.telekom.smartcredentials.authorization.fingerprint.AuthorizationCipher;
import de.telekom.smartcredentials.authorization.fingerprint.FingerprintManagerWrapper;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.di.Provides;

/**
 * Created by Lucian Iacob on November 05, 2018.
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

    public void init(SecurityApi securityApi, StorageApi storageApi) {
        mSecurityApi = securityApi;
        mStorageApi = storageApi;
    }

    @NonNull
    public AuthorizationController provideAuthorizationController(@NonNull final Context context,
                                                                  @NonNull final CoreController coreController) {
        return new AuthorizationController(context, coreController, provideAuthHandler(getSecurityApi()),
                provideFingerprintManagerWrapper(context), provideBiometricPromptWrapper(context),
                provideKeyguardManagerWrapper(context));
    }

    @Provides
    @NonNull
    public CancellationSignal provideCancellationSignal() {
        return new CancellationSignal();
    }

    @Provides
    @NonNull
    @TargetApi(Build.VERSION_CODES.P)
    public BiometricPromptWrapper provideBiometricPromptWrapper(Context context) {
        return new BiometricPromptWrapper(context);
    }

    @Provides
    @NonNull
    @TargetApi(Build.VERSION_CODES.M)
    public AuthHandler provideAuthHandler(SecurityApi securityApi) {
        return new AuthHandler(provideCancellationSignal(), provideAuthorizationCipher(securityApi));
    }

    @Provides
    @NonNull
    private AuthorizationCipher provideAuthorizationCipher(SecurityApi securityApi) {
        return new AuthorizationCipher(securityApi);
    }

    @Provides
    @NonNull
    private FingerprintManagerWrapper provideFingerprintManagerWrapper(Context context) {
        return new FingerprintManagerWrapper(context);
    }

    private KeyguardManagerWrapper provideKeyguardManagerWrapper(Context context) {
        return new KeyguardManagerWrapper(context);
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

    public static void destroy() {
        sInstance = null;
    }
}
