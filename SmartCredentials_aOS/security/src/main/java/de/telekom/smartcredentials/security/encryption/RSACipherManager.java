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

import android.content.Context;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.security.keystore.KeyStoreManager;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;
import de.telekom.smartcredentials.security.utils.Constants;

import static de.telekom.smartcredentials.security.encryption.StreamConverter.append;
import static de.telekom.smartcredentials.security.encryption.StreamConverter.getByteArrayInputStream;
import static de.telekom.smartcredentials.security.encryption.StreamConverter.getOutputStreamBytes;

public class RSACipherManager {

    private static final String CIPHER_ALGORITHM_MODE_ECB = "ECB";
    private static final String CIPHER_ALGORITHM_PADDING_PKCS1 = "PKCS1Padding";
    private static final String CIPHER_ALGORITHM = Constants.KEY_ALGORITHM_RSA + "/"
            + CIPHER_ALGORITHM_MODE_ECB + "/" + CIPHER_ALGORITHM_PADDING_PKCS1;

    private final Context mContext;
    private final KeyPairGeneratorWrapper mKeyPairGeneratorWrapper;
    private final KeyStoreManager mKeyStoreManager;

    public RSACipherManager(Context context, KeyPairGeneratorWrapper keyPairGeneratorWrapper) {
        mContext = context;
        mKeyPairGeneratorWrapper = keyPairGeneratorWrapper;
        mKeyStoreManager = new KeyStoreManager();
    }

    SmartCredentialsCipherWrapper getEncryptionCipherWrapper(String metaAlias) throws KeyStoreManagerException, EncryptionException {
        return new SmartCredentialsCipherWrapper(
                CIPHER_ALGORITHM,
                Cipher.ENCRYPT_MODE,
                getKeyStorePrivateEntryPublicKey(metaAlias));
    }

    PublicKey getKeyStorePrivateEntryPublicKey(String metaAlias) throws KeyStoreManagerException, EncryptionException {
        if (!mKeyStoreManager.checkKeyStoreContainsAlias(buildAlias(metaAlias))) {
            mKeyPairGeneratorWrapper.generateEncryptionKeyPairWithRSA(mContext, buildAlias(metaAlias));
        }

        KeyStore.PrivateKeyEntry privateKeyEntry = getKeyStorePrivateEntry(buildAlias(metaAlias));
        if (privateKeyEntry != null && privateKeyEntry.getCertificate() != null && privateKeyEntry.getCertificate().getPublicKey() != null) {
            return privateKeyEntry.getCertificate().getPublicKey();
        }
        throw new KeyStoreManagerException("Could not get public key.");
    }

    byte[] getMultiBlockBytes(byte[] initialBytes, SmartCredentialsCipherWrapper smartCredentialsCipherWrapper, int length)
            throws IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        byte[] multiBlockBytes = new byte[0];
        int fromByteIndex = 0;
        int toByteIndex = Math.min(length, initialBytes.length);

        while (fromByteIndex < initialBytes.length) {
            multiBlockBytes = getBlockWithAppendedBytes(multiBlockBytes, initialBytes, fromByteIndex, toByteIndex, smartCredentialsCipherWrapper);

            fromByteIndex = toByteIndex;
            toByteIndex = Math.min(toByteIndex + length, initialBytes.length);
        }

        return multiBlockBytes;
    }

    private String buildAlias(String metaAlias) {
        return Constants.KEY_ALGORITHM_RSA + mKeyPairGeneratorWrapper.getAlias() + metaAlias;
    }

    private KeyStore.PrivateKeyEntry getKeyStorePrivateEntry(String alias) throws KeyStoreManagerException {
        return mKeyStoreManager.getPrivateKeyEntry(alias);
    }

    private byte[] getBlockWithAppendedBytes(byte[] blockBytesToAppendTo, byte[] initialBytes,
                                             int fromInitialBytesIndex, int toInitialBytesIndex,
                                             SmartCredentialsCipherWrapper smartCredentialsCipherWrapper) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, IOException {

        byte[] split = java.util.Arrays.copyOfRange(initialBytes, fromInitialBytesIndex, toInitialBytesIndex);
        byte[] toAppendBytes = smartCredentialsCipherWrapper.getOpMode() == Cipher.ENCRYPT_MODE
                ? getOutputStreamBytes(split, smartCredentialsCipherWrapper.getCipher())
                : getByteArrayInputStream(split, smartCredentialsCipherWrapper.getCipher());

        return append(blockBytesToAppendTo, toAppendBytes);
    }

    SmartCredentialsCipherWrapper getDecryptionCipherWrapper(String metaAlias) throws KeyStoreManagerException {
        return new SmartCredentialsCipherWrapper(
                CIPHER_ALGORITHM,
                Cipher.DECRYPT_MODE,
                getKeyStorePrivateEntryPrivateKey(metaAlias));
    }

    private PrivateKey getKeyStorePrivateEntryPrivateKey(String metaAlias) throws KeyStoreManagerException {
        KeyStore.PrivateKeyEntry privateKeyEntry = getKeyStorePrivateEntry(buildAlias(metaAlias));
        if (privateKeyEntry != null && privateKeyEntry.getPrivateKey() != null) {
            return privateKeyEntry.getPrivateKey();
        }
        throw new KeyStoreManagerException("Could not get private key.");
    }
}
