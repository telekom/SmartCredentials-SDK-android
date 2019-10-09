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
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.crypto.NoSuchPaddingException;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;

import static de.telekom.smartcredentials.security.encryption.AESCipherManager.BASE64_FLAG;
import static de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerRSA.MAX_BYTES_LENGTH_TO_ENCRYPT;
import static de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerRSA.RSA_KEY_LENGTH;
import static de.telekom.smartcredentials.core.security.EncryptionError.DECRYPTION_EXCEPTION_TEXT;
import static de.telekom.smartcredentials.core.security.EncryptionError.ENCRYPTION_EXCEPTION_TEXT;
import static de.telekom.smartcredentials.core.security.EncryptionError.PUBLIC_KEY_EXCEPTION_TEXT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, Base64.class, StreamConverter.class})
public class Base64EncryptionManagerRSATest {

    private static final String NOT_ENCRYPTED_TEXT = "text_to_encrypt";
    private static final String ENCRYPTED_TEXT = "encrypted_text";

    private SmartCredentialsCipherWrapper mSmartCredentialsCipherWrapper;
    private Base64EncryptionManagerRSA mEncryptionManagerRSA;

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();
    private final String mAlias = "someAlias";
    private RSACipherManager mRSACipherManager;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
        PowerMockito.mockStatic(Base64.class);

        mSmartCredentialsCipherWrapper = Mockito.mock(SmartCredentialsCipherWrapper.class);
        mRSACipherManager = Mockito.mock(RSACipherManager.class);
        mEncryptionManagerRSA = new Base64EncryptionManagerRSA(mRSACipherManager);
    }

    @Test
    public void testEncryptShouldReturnNullWhenParameterIsNull() throws EncryptionException {
        PowerMockito.when(TextUtils.isEmpty(null)).thenReturn(true);
        String result = mEncryptionManagerRSA.encrypt(null, mAlias);
        assertNull(result);
    }

    @Test
    public void testEncryptReturnsEncryptedTextWhenOnlyOneBlockNeedsToBeEncrypted() throws Exception {
        when(mRSACipherManager.getEncryptionCipherWrapper(mAlias)).thenReturn(mSmartCredentialsCipherWrapper);
        when(mRSACipherManager.getMultiBlockBytes(NOT_ENCRYPTED_TEXT.getBytes(),
                mSmartCredentialsCipherWrapper, MAX_BYTES_LENGTH_TO_ENCRYPT))
                .thenReturn(ENCRYPTED_TEXT.getBytes());
        PowerMockito.when(Base64.encodeToString(ENCRYPTED_TEXT.getBytes(), BASE64_FLAG))
                .thenReturn(ENCRYPTED_TEXT);

        String encrypted = mEncryptionManagerRSA.encrypt(NOT_ENCRYPTED_TEXT, mAlias);

        assertNotNull(encrypted);
        assertEquals(ENCRYPTED_TEXT, encrypted);
    }

    @Test
    public void testEncryptThrowsEncryptionExceptionWhenSomethingWentWrong() throws Exception {
        when(mRSACipherManager.getEncryptionCipherWrapper(mAlias)).thenReturn(mSmartCredentialsCipherWrapper);
        when(mRSACipherManager.getMultiBlockBytes(NOT_ENCRYPTED_TEXT.getBytes(), mSmartCredentialsCipherWrapper,
                MAX_BYTES_LENGTH_TO_ENCRYPT))
                .thenThrow(new NoSuchPaddingException());

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(ENCRYPTION_EXCEPTION_TEXT);

        mEncryptionManagerRSA.encrypt(NOT_ENCRYPTED_TEXT, mAlias);
    }

    @Test
    public void testDecryptShouldReturnNullWhenParameterIsNull() throws EncryptionException {
        PowerMockito.when(TextUtils.isEmpty(null)).thenReturn(true);
        String result = mEncryptionManagerRSA.decrypt(null, mAlias);
        assertNull(result);
    }

    @Test
    public void testDecryptReturnsDecryptedText() throws Exception {
        when(mRSACipherManager.getDecryptionCipherWrapper(mAlias)).thenReturn(mSmartCredentialsCipherWrapper);
        PowerMockito.when(Base64.decode(ENCRYPTED_TEXT, BASE64_FLAG))
                .thenReturn(ENCRYPTED_TEXT.getBytes());
        when(mRSACipherManager.getMultiBlockBytes(ENCRYPTED_TEXT.getBytes(), mSmartCredentialsCipherWrapper, RSA_KEY_LENGTH))
                .thenReturn(NOT_ENCRYPTED_TEXT.getBytes());

        String decryptedText = mEncryptionManagerRSA.decrypt(ENCRYPTED_TEXT, mAlias);

        assertNotNull(decryptedText);
        assertEquals(NOT_ENCRYPTED_TEXT, decryptedText);
    }

    @Test
    public void testDecryptThrowsEncryptionExceptionWhenSomethingWentWrong() throws Exception {
        when(mRSACipherManager.getDecryptionCipherWrapper(mAlias)).thenReturn(mSmartCredentialsCipherWrapper);
        PowerMockito.when(Base64.decode(ENCRYPTED_TEXT, BASE64_FLAG))
                .thenReturn(ENCRYPTED_TEXT.getBytes());
        when(mRSACipherManager.getMultiBlockBytes(ENCRYPTED_TEXT.getBytes(), mSmartCredentialsCipherWrapper, RSA_KEY_LENGTH))
                .thenThrow(new NoSuchPaddingException());

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(DECRYPTION_EXCEPTION_TEXT);

        mEncryptionManagerRSA.decrypt(ENCRYPTED_TEXT, mAlias);
    }

    @Test
    public void getPublicKeyCallsMethodOnRsaCipherManager() throws Exception {
        mEncryptionManagerRSA.getPublicKey(mAlias);

        verify(mRSACipherManager, times(1)).getKeyStorePrivateEntryPublicKey(mAlias);
    }

    @Test
    public void getPublicKeyThrowsEncryptionExceptionWhenSomethingWentWrong() throws Exception {
        String errMessage = "error";
        when(mRSACipherManager.getKeyStorePrivateEntryPublicKey(mAlias))
                .thenThrow(new KeyStoreManagerException(errMessage));

        mExpectedException.expect(EncryptionException.class);
        mExpectedException.expectMessage(PUBLIC_KEY_EXCEPTION_TEXT + errMessage);

        mEncryptionManagerRSA.getPublicKey(mAlias);
    }
}