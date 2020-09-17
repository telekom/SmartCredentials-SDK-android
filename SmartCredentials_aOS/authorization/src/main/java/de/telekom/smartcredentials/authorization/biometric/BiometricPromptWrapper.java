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

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.os.CancellationSignal;

import javax.crypto.Cipher;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.authorization.R;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView;

import static de.telekom.smartcredentials.authorization.biometric.BiometricConverter.getBiometricPromptAuthenticationCallback;

@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricPromptWrapper extends AuthorizationManagerWrapper {

    private BiometricAuthCallback mBiometricAuthCallback;

    public BiometricPromptWrapper(Context context) {
        super(context);
    }

    @Override
    public boolean isHardwareDetected() {
        return mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
    }

    @Override
    public boolean hasPermission() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_BIOMETRIC)
                == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void authenticate(Cipher cipher, CancellationSignal cancel, BiometricAuthCallback authenticationCallback) {
        mBiometricAuthCallback = authenticationCallback;

        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(mContext)
                .setTitle(mContext.getString(R.string.sc_plugin_string_fingerprint_sign_in))
                .setNegativeButton(mContext.getString(R.string.sc_plugin_string_cancel),
                        mContext.getMainExecutor(), (dialogInterface, i) -> {
                            if (mBiometricAuthCallback != null) {
                                mBiometricAuthCallback.onAuthenticationCancelled();
                            }
                        })
                .build();

        biometricPrompt.authenticate(new BiometricPrompt.CryptoObject(cipher),
                (android.os.CancellationSignal) cancel.getCancellationSignalObject(),
                mContext.getMainExecutor(),
                getBiometricPromptAuthenticationCallback(authenticationCallback));
    }

    @Override
    public void authenticate(Cipher cipher, CancellationSignal cancellationSignal,
                             BiometricAuthCallback callback, BiometricView view) {
        mBiometricAuthCallback = callback;
        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(mContext)
                .setTitle(view.getTitle())
                .setSubtitle(view.getSubtitle())
                .setDescription(view.getDescription())
                .setNegativeButton(view.getNegativeButtonDescription(), mContext.getMainExecutor(), view.getBiometricViewClickListener())
                .build();
        biometricPrompt.authenticate(new BiometricPrompt.CryptoObject(cipher),
                (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject(),
                mContext.getMainExecutor(),
                getBiometricPromptAuthenticationCallback(callback));
    }
}
