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

package de.telekom.smartcredentials.security.encryption;

import android.os.Build;
import android.text.TextUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class Base64EncryptionManagerTest {

    private static final String STRING_FOR_ENCRYPTION = "message";
    private static final String SDK_INT = "SDK_INT";
    private static final int API_20 = 20;
    private static final int API_29 = 29;

    private Base64EncryptionManager mEncryptionManager;

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();
    private Base64EncryptionManagerRSA mBase64EncryptionManagerBelowAPI23;
    private Base64EncryptionManagerAES mBase64EncryptionManagerAboveAPI23;

    private static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        field.set(null, newValue);
    }

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
        mBase64EncryptionManagerBelowAPI23 = Mockito.mock(Base64EncryptionManagerRSA.class);
        mBase64EncryptionManagerAboveAPI23 = Mockito.mock(Base64EncryptionManagerAES.class);
        mEncryptionManager = new Base64EncryptionManager(
                mBase64EncryptionManagerBelowAPI23, mBase64EncryptionManagerAboveAPI23);
    }

    @Test
    public void encryptStringShouldCallManagerBelowApi23WhenSdkIntBelow23() throws Exception {
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_20);
        mEncryptionManager.encrypt(STRING_FOR_ENCRYPTION, "");
        verify(mBase64EncryptionManagerBelowAPI23).encrypt(STRING_FOR_ENCRYPTION, "");
        verify(mBase64EncryptionManagerAboveAPI23, never()).encrypt(anyString(), anyString());
    }

    @Test
    public void encryptStringShouldCallManagerAboveApi23WhenSdkIntAbove23() throws Exception {
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_29);
        mEncryptionManager.encrypt(STRING_FOR_ENCRYPTION, "");
        verify(mBase64EncryptionManagerBelowAPI23, never()).encrypt(anyString(), anyString());
        verify(mBase64EncryptionManagerAboveAPI23).encrypt(STRING_FOR_ENCRYPTION, "");
    }

    @Test
    public void encryptThrowsEncryptionExceptionWhenSdkIntBelow23() throws Exception {
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_20);
        String expectedMessage = "could not encrypt text";
        when(mBase64EncryptionManagerBelowAPI23.encrypt(STRING_FOR_ENCRYPTION, ""))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mEncryptionManager.encrypt(STRING_FOR_ENCRYPTION, "");
    }

    @Test
    public void encryptThrowsEncryptionExceptionWhenSdkIntAbove23() throws Exception {
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_29);
        String expectedMessage = "could not encrypt text";
        when(mBase64EncryptionManagerAboveAPI23.encrypt(STRING_FOR_ENCRYPTION, ""))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mEncryptionManager.encrypt(STRING_FOR_ENCRYPTION, "");
    }

    @Test
    public void decryptStringShouldCallManagerBelowApi23WhenSdkIntBelow23() throws Exception {
        when(TextUtils.isEmpty(STRING_FOR_ENCRYPTION)).thenReturn(false);
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_20);
        mEncryptionManager.decrypt(STRING_FOR_ENCRYPTION, "");
        verify(mBase64EncryptionManagerBelowAPI23).decrypt(STRING_FOR_ENCRYPTION, "");
        verify(mBase64EncryptionManagerAboveAPI23, never()).decrypt(anyString(), anyString());
    }

    @Test
    public void decryptStringShouldCallManagerAboveApi23WhenSdkIntAbove23() throws Exception {
        when(TextUtils.isEmpty(STRING_FOR_ENCRYPTION)).thenReturn(false);
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_29);
        mEncryptionManager.decrypt(STRING_FOR_ENCRYPTION, "");
        verify(mBase64EncryptionManagerBelowAPI23, never()).decrypt(anyString(), anyString());
        verify(mBase64EncryptionManagerAboveAPI23).decrypt(STRING_FOR_ENCRYPTION, "");
    }

    @Test
    public void decryptThrowsEncryptionExceptionWhenSdkIntBelow23() throws Exception {
        when(TextUtils.isEmpty(STRING_FOR_ENCRYPTION)).thenReturn(false);
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_20);
        String expectedMessage = "could not decrypt text";
        when(mBase64EncryptionManagerBelowAPI23.decrypt(STRING_FOR_ENCRYPTION, ""))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mEncryptionManager.decrypt(STRING_FOR_ENCRYPTION, "");
    }

    @Test
    public void decryptThrowsEncryptionExceptionWhenSdkIntAbove23() throws Exception {
        when(TextUtils.isEmpty(STRING_FOR_ENCRYPTION)).thenReturn(false);
        setFinalStatic(Build.VERSION.class.getField(SDK_INT), API_29);
        String expectedMessage = "could not decrypt text";
        when(mBase64EncryptionManagerAboveAPI23.decrypt(STRING_FOR_ENCRYPTION, ""))
                .thenThrow(new EncryptionException(expectedMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(expectedMessage);

        mEncryptionManager.decrypt(STRING_FOR_ENCRYPTION, "");
    }
}