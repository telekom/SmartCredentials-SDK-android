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

package de.telekom.smartcredentials.authorization;

import android.content.Context;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

import javax.crypto.Cipher;

import de.telekom.smartcredentials.authorization.biometric.BiometricAuthCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView;

public abstract class AuthorizationManagerWrapper {

    protected final Context mContext;

    public AuthorizationManagerWrapper(Context context) {
        mContext = context;
    }

    public boolean isAuthMethodAvailable() {
        return isHardwareDetected() && hasEnrolledFingerprints();
    }

    public boolean isUserAuthCapable() {
        return hasPermission() && isHardwareDetected();
    }

    public boolean hasEnrolledFingerprints() {
        FingerprintManagerCompat fingerprintManagerCompat = FingerprintManagerCompat.from(mContext);
        return fingerprintManagerCompat.hasEnrolledFingerprints();
    }

    public abstract boolean hasPermission();

    public abstract boolean isHardwareDetected();

    /**
     * Method used to authenticate use with the SDKs default biometric view.
     *
     * @param cipher             used for authentication
     * @param cancellationSignal used in the authentication process
     * @param callback           used for delivering the authentication results
     */
    public abstract void authenticate(Cipher cipher, CancellationSignal cancellationSignal,
                                      BiometricAuthCallback callback);

    /**
     * Method used to authenticate the user with a provided {@link BiometricView}.
     *
     * @param cipher             used for authentication
     * @param cancellationSignal used in the authentication process
     * @param callback           used for delivering the authentication results
     * @param view               used for the authentication process
     */
    @SuppressWarnings("unused")
    public abstract void authenticate(Cipher cipher, CancellationSignal cancellationSignal,
                                      BiometricAuthCallback callback, BiometricView view);
}
