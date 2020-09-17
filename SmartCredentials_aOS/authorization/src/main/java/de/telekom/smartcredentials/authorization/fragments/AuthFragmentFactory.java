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

package de.telekom.smartcredentials.authorization.fragments;

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import de.telekom.smartcredentials.authorization.fingerprint.FingerprintManagerWrapper;
import de.telekom.smartcredentials.authorization.keyguard.KeyguardManagerWrapper;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.plugins.callbacks.AuthorizationPluginCallback;

public class AuthFragmentFactory {

    private static final String TAG = "AuthFragmentFactory";

    private static AuthFragmentFactory instance;

    private final FingerprintManagerWrapper mFingerprintManagerWrapper;
    private final KeyguardManagerWrapper mKeyguardManagerWrapper;

    private AuthFragmentFactory(FingerprintManagerWrapper fingerprintManagerWrapper, KeyguardManagerWrapper keyguardManagerWrapper) {
        mFingerprintManagerWrapper = fingerprintManagerWrapper;
        mKeyguardManagerWrapper = keyguardManagerWrapper;
    }

    public static AuthFragmentFactory getInstance(FingerprintManagerWrapper fingerprintManagerWrapper, KeyguardManagerWrapper keyguardManagerWrapper) {
        if (instance == null) {
            instance = new AuthFragmentFactory(fingerprintManagerWrapper, keyguardManagerWrapper);
        }
        return instance;
    }

    public Fragment getAuthorizationFragment(@NonNull AuthorizationPluginCallback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return getDeviceCredentialsFragment(callback);
        } else {
            if (mFingerprintManagerWrapper.isAuthMethodAvailable()) {
                return getFingerprintDialogFragment(callback);
            } else {
                return getDeviceCredentialsFragment(callback);
            }
        }
    }

    private Fragment getDeviceCredentialsFragment(@NonNull AuthorizationPluginCallback callback) {
        ApiLoggerResolver.logFactoryMethod(TAG, "getAuthorizationFragment", DeviceCredentialsFragment.class.getSimpleName());
        if (mKeyguardManagerWrapper.checkIsKeyguardSecured()) {
            return DeviceCredentialsFragment.getInstance(callback);
        } else {
            callback.onFailed(AuthorizationPluginError.DEVICE_NOT_SECURED);
            ApiLoggerResolver.logError(TAG, AuthorizationPluginError.DEVICE_NOT_SECURED.getDesc());

            return null;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private Fragment getFingerprintDialogFragment(@NonNull AuthorizationPluginCallback callback) {
        ApiLoggerResolver.logFactoryMethod(TAG, "getAuthorizationFragment", FingerprintDialogFragment.class.getSimpleName());
        return FingerprintDialogFragment.getInstance(callback);
    }
}
