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

package de.telekom.smartcredentials.authorization.controllers;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import de.telekom.smartcredentials.authorization.handlers.AuthorizationHandler;
import de.telekom.smartcredentials.authorization.handlers.BiometricsHandler;
import de.telekom.smartcredentials.authorization.handlers.DeviceCredentialsHandler;
import de.telekom.smartcredentials.authorization.security.CipherManager;
import de.telekom.smartcredentials.core.api.AuthorizationApi;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

public class AuthorizationController implements AuthorizationApi {

    private final CoreController mCoreController;
    private final CipherManager mCipherManager;

    public AuthorizationController(CoreController coreController,
                                   CipherManager cipherManager) {
        this.mCoreController = coreController;
        this.mCipherManager = cipherManager;
    }

    @Override
    public SmartCredentialsApiResponse<Void> authorize(@Nullable FragmentActivity activity,
                                                       @NonNull AuthorizationConfiguration configuration,
                                                       @NonNull AuthorizationCallback callback) {
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.AUTHORIZE)) {
            String errorMessage = SmartCredentialsFeatureSet.AUTHORIZE.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        AuthorizationHandler authorizationHandler;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            authorizationHandler = new DeviceCredentialsHandler(activity, configuration, callback, mCipherManager);
        } else {
            authorizationHandler = new BiometricsHandler(activity, configuration, callback, mCipherManager);
        }
        authorizationHandler.prepareAuthorization();
        return new SmartCredentialsResponse<>();
    }
}
