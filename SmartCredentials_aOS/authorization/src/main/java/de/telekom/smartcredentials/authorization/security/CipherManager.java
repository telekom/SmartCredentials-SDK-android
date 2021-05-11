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

package de.telekom.smartcredentials.authorization.security;

import android.annotation.TargetApi;
import android.os.Build;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;

import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.security.CipherWrapper;
import de.telekom.smartcredentials.core.security.KeyStoreKeyProvider;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;
import de.telekom.smartcredentials.core.security.KeyStoreProviderException;

@TargetApi(Build.VERSION_CODES.M)
public class CipherManager {

    private static final String FINGERPRINT_KEY_ALIAS = "FingerprintKeyAlias";
    private static final String CIPHER_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
            + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7;

    private final SecurityApi mSecurityApi;
    private final KeyStoreKeyProvider mKeyStoreKeyProvider;

    public CipherManager(SecurityApi securityApi) {
        mSecurityApi = securityApi;
        mKeyStoreKeyProvider = mSecurityApi.getKeyStoreKeyProvider();
    }

    public Cipher getCipher() throws CipherException {
        try {
            CipherWrapper cipherWrapper = getCipherWrapper();
            return cipherWrapper.getCipher();
        } catch (KeyPermanentlyInvalidatedException e) {
            try {
                mKeyStoreKeyProvider.deleteEntry(FINGERPRINT_KEY_ALIAS);
                CipherWrapper cipherWrapper = getCipherWrapper();
                return cipherWrapper.getCipher();
            } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                    | KeyStoreManagerException | KeyStoreProviderException e1) {
                throw new CipherException(CipherException.CYPHER_INIT_EXCEPTION_TEXT, e1);
            }
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
                | KeyStoreManagerException | KeyStoreProviderException e) {
            throw new CipherException(CipherException.CYPHER_INIT_EXCEPTION_TEXT, e);
        }
    }

    private CipherWrapper getCipherWrapper() throws KeyStoreManagerException, KeyStoreProviderException {
        return mSecurityApi.getCipherWrapper(
                CIPHER_ALGORITHM,
                Cipher.ENCRYPT_MODE,
                mKeyStoreKeyProvider.getKeyStoreSecretKey(FINGERPRINT_KEY_ALIAS));
    }
}
