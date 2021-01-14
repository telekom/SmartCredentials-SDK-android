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
import androidx.core.os.CancellationSignal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.crypto.Cipher;

import de.telekom.smartcredentials.authorization.biometric.BiometricAuthCallback;
import de.telekom.smartcredentials.core.plugins.fingerprint.BiometricView;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

public class AuthorizationManagerWrapperTest {

    private AuthorizationManagerWrapper mAuthorizationManagerWrapper;

    @Before
    public void setUp() {
        mAuthorizationManagerWrapper = new AuthorizationManagerWrapper(Mockito.mock(Context.class)) {
            @Override
            public boolean hasPermission() {
                return false;
            }

            @Override
            public boolean isHardwareDetected() {
                return false;
            }

            @Override
            public void authenticate(Cipher cipher, CancellationSignal cancel, BiometricAuthCallback callback) {

            }

            @Override
            public void authenticate(Cipher cipher, CancellationSignal cancellationSignal, BiometricAuthCallback callback, BiometricView view) {

            }
        };
    }

    @Test
    public void isAuthMethodAvailableChecksForHardwareAndFingerprints() {
        AuthorizationManagerWrapper spy = spy(mAuthorizationManagerWrapper);
        when(spy.isHardwareDetected()).thenReturn(true);

        spy.isAuthMethodAvailable();

        verify(spy).isHardwareDetected();
        verify(spy).hasEnrolledFingerprints();
    }

    @Test
    public void isUserAuthCapableChecksForPermissionAndHardware() {
        AuthorizationManagerWrapper spy = spy(mAuthorizationManagerWrapper);
        when(spy.hasPermission()).thenReturn(true);

        spy.isUserAuthCapable();

        verify(spy).hasPermission();
        verify(spy).isHardwareDetected();
    }
}