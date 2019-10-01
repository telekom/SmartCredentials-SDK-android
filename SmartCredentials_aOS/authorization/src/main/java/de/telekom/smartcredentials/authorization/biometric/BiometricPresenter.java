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

package de.telekom.smartcredentials.authorization.biometric;

import android.os.Build;
import android.support.annotation.RequiresApi;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.authorization.fingerprint.AuthHandler;
import de.telekom.smartcredentials.authorization.fingerprint.presenters.AuthPresenter;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricAuthorizationPresenter;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView;

@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricPresenter extends AuthPresenter<BiometricView> implements AuthHandler.AuthorizationListener, BiometricAuthorizationPresenter {

    private final BiometricPromptWrapper mBiometricPromptWrapper;

    public BiometricPresenter(AuthHandler authHandler, BiometricPromptWrapper biometricPromptWrapper) {
        mBiometricPromptWrapper = biometricPromptWrapper;
        mAuthHandler = authHandler;
        init();
    }

    @Override
    public void viewReady(BiometricView view) {
        // empty method
    }

    @Override
    public AuthorizationManagerWrapper getAuthorizationManagerWrapper() {
        return mBiometricPromptWrapper;
    }
}
