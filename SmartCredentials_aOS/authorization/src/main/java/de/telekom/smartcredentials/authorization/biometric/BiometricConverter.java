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

import android.annotation.TargetApi;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;

public class BiometricConverter {

    public static FingerprintManagerCompat.AuthenticationCallback getFingerprintManagerCompatAuthenticationCallback(BiometricAuthCallback authenticationCallback) {
        return new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                authenticationCallback.onAuthenticationError(errMsgId, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                authenticationCallback.onAuthenticationHelp(helpMsgId, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                authenticationCallback.onAuthenticationSucceeded();
            }

            @Override
            public void onAuthenticationFailed() {
                authenticationCallback.onAuthenticationFailed();
            }
        };
    }

    @TargetApi(Build.VERSION_CODES.P)
    static BiometricPrompt.AuthenticationCallback getBiometricPromptAuthenticationCallback(BiometricAuthCallback authenticationCallback) {
        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                AuthorizationPluginError error = AuthorizationPluginError.map(errString.toString());
                if (error == AuthorizationPluginError.AUTH_CANCELED_BY_USER) {
                    authenticationCallback.onAuthenticationCancelled();
                    return;
                }
                authenticationCallback.onAuthenticationError(errorCode, errString);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                authenticationCallback.onAuthenticationHelp(helpCode, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                authenticationCallback.onAuthenticationSucceeded();
            }

            @Override
            public void onAuthenticationFailed() {
                authenticationCallback.onAuthenticationFailed();
            }
        };
    }
}
