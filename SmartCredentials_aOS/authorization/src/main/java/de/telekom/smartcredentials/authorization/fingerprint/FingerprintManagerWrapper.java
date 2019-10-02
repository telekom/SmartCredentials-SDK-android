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

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

import javax.crypto.Cipher;

import de.telekom.smartcredentials.authorization.AuthorizationManagerWrapper;
import de.telekom.smartcredentials.authorization.biometric.BiometricAuthCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView;

import static de.telekom.smartcredentials.authorization.biometric.BiometricConverter.getFingerprintManagerCompatAuthenticationCallback;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintManagerWrapper extends AuthorizationManagerWrapper {

    public FingerprintManagerWrapper(Context context) {
        super(context);
    }

    @SuppressLint("MissingPermission")
    @Override
    public boolean isHardwareDetected() {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
        return fingerprintManagerCompat.isHardwareDetected();
    }

    @Override
    public boolean hasPermission() {
        return ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT)
                == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void authenticate(Cipher cipher, CancellationSignal cancel, BiometricAuthCallback callback) {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
        fingerprintManagerCompat.authenticate(new FingerprintManagerCompat.CryptoObject(cipher), 0, cancel,
                getFingerprintManagerCompatAuthenticationCallback(callback), null);
    }

    @Override
    public void authenticate(Cipher cipher, CancellationSignal cancellationSignal, BiometricAuthCallback callback, @NonNull BiometricView view) {
        authenticate(cipher, cancellationSignal, callback);
    }
}
