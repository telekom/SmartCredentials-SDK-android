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

import android.text.TextUtils;
import android.util.Base64;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.security.InvalidKeyException;

import javax.crypto.NoSuchPaddingException;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.exceptions.InvalidAlgorithmException;

import static de.telekom.smartcredentials.security.encryption.AESCipherManager.BASE64_FLAG;
import static de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerAES.BASE4_CHAR_SET;
import static de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerAES.IV_SEPARATOR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, Base64.class})
public class Base64EncryptionManagerAESTest {

    private static final String TO_ENCRYPT_TEXT = "test";

    private final AESCipherManager.AESCipher mAESCipher = PowerMockito.mock(AESCipherManager.AESCipher.class);
    private final String mEncodedCipherIV = "mEncodedCipherIV";
    private final String mCipherTextFinal = "mCipherTextFinal";
    private final String mEncodedCipherTextFinal = "mEncodedCipherTextFinal";

    private final byte[] mEncodedCipherIVBytes = mEncodedCipherIV.getBytes();
    private final byte[] mCipherTextFinalBytes = mCipherTextFinal.getBytes();
    private final byte[] mEncodedCipherTextFinalBytes = mEncodedCipherTextFinal.getBytes();
    private final String mEncryptedText = new String(mEncodedCipherTextFinalBytes) + IV_SEPARATOR + new String(mEncodedCipherIVBytes);
    private Base64EncryptionManagerAES mEncryptionManagerAES;

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();
    private final String mAlias = "someAlias";
    private AESCipherManager mAESCipherManager;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(Base64.class);
        mAESCipherManager = PowerMockito.mock(AESCipherManager.class);
        mEncryptionManagerAES = new Base64EncryptionManagerAES(mAESCipherManager);
    }

    @Test
    public void testEncryptShouldReturnNullWhenParameterIsNull() throws EncryptionException {
        PowerMockito.when(TextUtils.isEmpty(null)).thenReturn(true);
        String result = mEncryptionManagerAES.encrypt(null, mAlias);
        assertNull(result);
    }

    @Test
    public void testEncryptReturnsEncryptedText() throws Exception {
        PowerMockito.when(TextUtils.isEmpty(TO_ENCRYPT_TEXT))
                .thenReturn(false);
        when(mAESCipherManager.obtainEncryptionCipher(mAlias))
                .thenReturn(mAESCipher);
        PowerMockito.when(Base64.encode(mAESCipher.getIV(), BASE64_FLAG))
                .thenReturn(mEncodedCipherIVBytes);
        when(mAESCipher.getFinalBytes(TO_ENCRYPT_TEXT.getBytes(BASE4_CHAR_SET)))
                .thenReturn(mCipherTextFinalBytes);
        PowerMockito.when(Base64.encode(mCipherTextFinalBytes, BASE64_FLAG))
                .thenReturn(mEncodedCipherTextFinalBytes);

        String result = mEncryptionManagerAES.encrypt(TO_ENCRYPT_TEXT, mAlias);
        assertNotNull(result);
        assertEquals(mEncryptedText, result);
    }

    @Test
    public void testEncryptThrowsEncryptionExceptionWhenSomethingWentWrong() throws Exception {
        PowerMockito.when(TextUtils.isEmpty(TO_ENCRYPT_TEXT))
                .thenReturn(false);
        when(mAESCipherManager.obtainEncryptionCipher(mAlias))
                .thenThrow(new InvalidKeyException());

        mExpectedException.expect(EncryptionException.class);
        mEncryptionManagerAES.encrypt(TO_ENCRYPT_TEXT, mAlias);
    }

    @Test
    public void testDecryptShouldReturnNullWhenParameterIsNull() throws EncryptionException {
        PowerMockito.when(TextUtils.isEmpty(null)).thenReturn(true);
        String result = mEncryptionManagerAES.decrypt(null, mAlias);
        assertNull(result);
    }

    @Test
    public void testDecryptReturnsDecryptedText() throws Exception {
        PowerMockito.when(TextUtils.isEmpty(mEncryptedText)).thenReturn(false);
        when(mAESCipherManager.obtainDecryptionCypher(mEncodedCipherIV, mAlias))
                .thenReturn(mAESCipher);
        PowerMockito.when(Base64.decode(mEncodedCipherTextFinalBytes, BASE64_FLAG))
                .thenReturn(mEncodedCipherTextFinalBytes);
        when(mAESCipher.getFinalBytes(mEncodedCipherTextFinalBytes))
                .thenReturn(TO_ENCRYPT_TEXT.getBytes());

        String result = mEncryptionManagerAES.decrypt(mEncryptedText, mAlias);
        assertNotNull(result);
        assertEquals(TO_ENCRYPT_TEXT, result);
    }

    @Test
    public void testDecryptThrowsEncryptionExceptionWhenSomethingWentWrong() throws Exception {
        PowerMockito.when(TextUtils.isEmpty(mEncryptedText)).thenReturn(false);

        when(mAESCipherManager.obtainDecryptionCypher(mEncodedCipherIV, mAlias))
                .thenThrow(new NoSuchPaddingException());

        mExpectedException.expect(EncryptionException.class);
        mEncryptionManagerAES.decrypt(mEncryptedText, mAlias);
    }

    @Test
    public void testDecryptThrowsEncryptionExceptionWhenIVIsMissing() throws Exception {
        PowerMockito.when(TextUtils.isEmpty(mEncryptedText)).thenReturn(false);

        mExpectedException.expect(InvalidAlgorithmException.class);
        mEncryptionManagerAES.decrypt(new String(mEncodedCipherTextFinalBytes), mAlias);
    }

}