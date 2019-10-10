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

import android.util.Base64;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import de.telekom.smartcredentials.security.keystore.SmartCredentialsKeyStoreKeyProvider;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;
import de.telekom.smartcredentials.core.security.KeyStoreProviderException;
import de.telekom.smartcredentials.security.utils.Constants;

public class AESCipherManager {

    static final int BASE64_FLAG = Base64.DEFAULT;
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS7Padding";

    private final SmartCredentialsKeyStoreKeyProvider mSmartCredentialsKeyStoreKeyProvider;
    private final KeyPairGeneratorWrapper mKeyPairGeneratorWrapper;

    public AESCipherManager(SmartCredentialsKeyStoreKeyProvider smartCredentialsKeyStoreKeyProvider, KeyPairGeneratorWrapper keyPairGeneratorWrapper) {
        mSmartCredentialsKeyStoreKeyProvider = smartCredentialsKeyStoreKeyProvider;
        mKeyPairGeneratorWrapper = keyPairGeneratorWrapper;
    }

    AESCipher obtainEncryptionCipher(String metaAlias) throws InvalidKeyException, KeyStoreProviderException,
            KeyStoreManagerException, NoSuchPaddingException, NoSuchAlgorithmException {
        SecretKey secretKey = mSmartCredentialsKeyStoreKeyProvider.getKeyStoreSecretKey(buildAlias(metaAlias));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return new AESCipher(cipher, cipher.getIV());
    }

    AESCipher obtainDecryptionCypher(String iv, String metaAlias) throws NoSuchPaddingException, NoSuchAlgorithmException,
            KeyStoreManagerException, InvalidKeyException, KeyStoreProviderException, InvalidAlgorithmParameterException {
        SecretKey secretKey = mSmartCredentialsKeyStoreKeyProvider.getKeyStoreSecretKey(buildAlias(metaAlias));

        byte[] ivBytes = Base64.decode(iv, BASE64_FLAG);

        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(ivBytes));

        return new AESCipher(cipher, iv.getBytes(Charset.defaultCharset()));
    }

    private String buildAlias(String metaAlias) {
        return Constants.KEY_ALGORITHM_AES + mKeyPairGeneratorWrapper.getAlias() + metaAlias;
    }

    static class AESCipher {
        private final Cipher mCipher;
        private final byte[] mIV;

        AESCipher(Cipher cipher, byte[] IV) {
            mCipher = cipher;
            mIV = IV;
        }

        byte[] getIV() {
            return mIV;
        }

        byte[] getFinalBytes(byte[] toEncryptBytes) throws BadPaddingException, IllegalBlockSizeException {
            return mCipher.doFinal(toEncryptBytes);
        }
    }
}
