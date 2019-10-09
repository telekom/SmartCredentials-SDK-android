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

package de.telekom.smartcredentials.authorization.fingerprint;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.os.CancellationSignal;

import javax.crypto.Cipher;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.authorization.biometric.BiometricAuthCallback;
import de.telekom.smartcredentials.authorization.di.ObjectGraphCreatorAuthorization;
import de.telekom.smartcredentials.core.authorization.AuthorizationPluginError;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.core.authorization.AuthorizationPluginError.FINGERPRINT_NOT_ENROLLED;
import static de.telekom.smartcredentials.core.authorization.AuthorizationPluginError.HARDWARE_NOT_DETECTED;

@RequiresApi(api = Build.VERSION_CODES.M)
public class AuthHandler implements BiometricAuthCallback {

    private static final String TAG = "AuthHandler";
    private static final String FINGERPRINT_MISSING = "Fingerprint permission is missing";

    private AuthorizationListener mAuthorizationListener;
    private AuthorizationManagerWrapper mAuthorizationManagerWrapper;
    private CancellationSignal mCancellationSignal;
    private final AuthorizationCipher mAuthorizationCipher;

    public AuthHandler(CancellationSignal cancellationSignal, AuthorizationCipher authorizationCipher) {
        mCancellationSignal = cancellationSignal;
        mAuthorizationCipher = authorizationCipher;
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        ApiLoggerResolver.logError(TAG, "Authentication error: error code=" + errMsgId + "\nError message: " + errString);

        if (isAuthNotCanceled()) {
            if (mAuthorizationListener != null) {
                mAuthorizationListener.onError(errString);
            }
        }
    }

    @Override
    public void onAuthenticationFailed() {
        if (isAuthNotCanceled() && mAuthorizationListener != null) {
            mAuthorizationListener.onFailed();
        }
    }

    @Override
    public void onAuthenticationCancelled() {
        cancelAuthentication();
        mAuthorizationListener.onError(AuthorizationPluginError.AUTH_CANCELED_BY_USER);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        if (isAuthNotCanceled() && mAuthorizationListener != null) {
            mAuthorizationListener.onHelp(helpString);
        }
    }

    @Override
    public void onAuthenticationSucceeded() {
        if (isAuthNotCanceled()) {
            if (mAuthorizationListener != null) {
                mAuthorizationListener.onAuthenticated();
            }

            cancelAuthentication();
        }
    }

    public void init(AuthorizationManagerWrapper authorizationManagerWrapper) {
        mAuthorizationManagerWrapper = authorizationManagerWrapper;
    }

    public void setListener(AuthorizationListener listener) {
        mAuthorizationListener = listener;
    }

    public boolean isFingerprintAuthAvailable() {
        return mAuthorizationManagerWrapper.isAuthMethodAvailable();
    }

    public void tryAuthenticateUser() {
        try {
            if (!mAuthorizationManagerWrapper.hasPermission()) {
                ApiLoggerResolver.logError(TAG, FINGERPRINT_MISSING);
                if (mAuthorizationListener != null) {
                    mAuthorizationListener.onPermissionMissing();
                }
                return;
            }

            if (!mAuthorizationManagerWrapper.isHardwareDetected()) {
                notifyError(HARDWARE_NOT_DETECTED);
                return;
            }

            if (!mAuthorizationManagerWrapper.hasEnrolledFingerprints()) {
                notifyError(FINGERPRINT_NOT_ENROLLED);
                return;
            }

            tryAuthenticate();
        } catch (CipherException e) {
            cancelAuthentication();
            if (mAuthorizationListener != null) {
                mAuthorizationListener.onError(e.toString());
            }
        }
    }

    public void cancelAuthentication() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
        }
    }

    private void notifyError(AuthorizationPluginError errorMessage) {
        ApiLoggerResolver.logError(TAG, errorMessage.getDesc());
        if (mAuthorizationListener != null) {
            mAuthorizationListener.onError(errorMessage);
        }
    }

    private void tryAuthenticate() throws CipherException, SecurityException {
        Cipher cipher = mAuthorizationCipher.getFingerprintCipher();
        if (mCancellationSignal == null) {
            mCancellationSignal = ObjectGraphCreatorAuthorization.getInstance().provideCancellationSignal();
        }
        if (!mCancellationSignal.isCanceled()) {
            mAuthorizationManagerWrapper.authenticate(cipher, mCancellationSignal, this);
        }
    }

    private boolean isAuthNotCanceled() {
        return mCancellationSignal != null && !mCancellationSignal.isCanceled();
    }

    public interface AuthorizationListener {

        void onAuthenticated();

        void onPermissionMissing();

        void onError(@NonNull AuthorizationPluginError error);

        void onError(CharSequence errString);

        void onFailed();

        void onHelp(CharSequence errString);
    }
}
