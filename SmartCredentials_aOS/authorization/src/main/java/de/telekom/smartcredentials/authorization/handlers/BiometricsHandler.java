/*
 * Copyright (c) 2021 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.authorization.handlers;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

import de.telekom.smartcredentials.authorization.security.CipherException;
import de.telekom.smartcredentials.authorization.security.CipherManager;
import de.telekom.smartcredentials.core.authorization.AuthorizationCallback;
import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;
import de.telekom.smartcredentials.core.authorization.AuthorizationError;

public class BiometricsHandler extends AuthorizationHandler {

    private final Executor mExecutor;
    private BiometricPrompt mBiometricPrompt;
    private BiometricPrompt.PromptInfo mPromptInfo;

    public BiometricsHandler(FragmentActivity activity,
                             AuthorizationConfiguration configuration,
                             AuthorizationCallback callback,
                             CipherManager cipherManager) {
        super(activity, configuration, callback, cipherManager);
        this.mExecutor = ContextCompat.getMainExecutor(activity);
    }

    @Override
    public void prepareAuthorization() {
        setupBiometricPrompt();
        setupPromptInfo();
        authorize();
    }

    private void setupBiometricPrompt() {
        mBiometricPrompt = new BiometricPrompt(mActivity,
                mExecutor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence error) {
                super.onAuthenticationError(errorCode, error);
                if (error.equals(mConfiguration.getAuthorizationView().getNegativeButtonText())) {
                    mCallback.onAuthorizationError(AuthorizationError.AUTHORIZATION_CANCELED.getMessage());
                } else {
                    mCallback.onAuthorizationError(error.toString());
                }
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                mCallback.onAuthorizationSucceeded();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                mCallback.onAuthorizationFailed(AuthorizationError.AUTHORIZATION_FAILED.getMessage());
            }
        });
    }

    private void setupPromptInfo() {
        if (mConfiguration.getAuthorizationView() == null) {
            mCallback.onAuthorizationFailed(AuthorizationError.INVALID_AUTHORIZATION_VIEW.getMessage());
        }

        BiometricPrompt.PromptInfo.Builder builder = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(mConfiguration.getAuthorizationView().getTitle())
                .setConfirmationRequired(mConfiguration.isFaceRecognitionConfirmationRequired());

        if (mConfiguration.shouldAllowDeviceCredentialsFallback()) {
            builder.setDeviceCredentialAllowed(true);
        } else {
            builder.setNegativeButtonText(mConfiguration.getAuthorizationView().getNegativeButtonText());
        }

        if (mConfiguration.getAuthorizationView().getSubtitle() != null) {
            builder.setSubtitle(mConfiguration.getAuthorizationView().getSubtitle());
        }

        if (mConfiguration.getAuthorizationView().getDescription() != null) {
            builder.setDescription(mConfiguration.getAuthorizationView().getDescription());
        }

        mPromptInfo = builder.build();
    }

    public void authorize() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            mBiometricPrompt.authenticate(mPromptInfo);
        } else {
            try {
                mBiometricPrompt.authenticate(mPromptInfo, new BiometricPrompt.CryptoObject(mCipherManager.getCipher()));
            } catch (CipherException e) {
                mCallback.onAuthorizationFailed(AuthorizationError.COULD_NOT_RETRIEVE_CIPHER.getMessage());
            }
        }
    }
}