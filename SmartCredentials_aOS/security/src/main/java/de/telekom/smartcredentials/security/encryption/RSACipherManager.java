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
import android.security.KeyPairGeneratorSpec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.security.auth.x500.X500Principal;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;
import de.telekom.smartcredentials.security.keystore.KeyStoreManager;
import de.telekom.smartcredentials.security.utils.Constants;

import static de.telekom.smartcredentials.security.encryption.StreamConverter.append;
import static de.telekom.smartcredentials.security.encryption.StreamConverter.getByteArrayInputStream;
import static de.telekom.smartcredentials.security.encryption.StreamConverter.getOutputStreamBytes;
import static de.telekom.smartcredentials.security.utils.Constants.ANDROID_KEY_STORE;
import static de.telekom.smartcredentials.security.utils.Constants.KEY_ALGORITHM_RSA;

public class RSACipherManager extends CipherManager {

    private static final String CIPHER_ALGORITHM_MODE_ECB = "ECB";
    private static final String CIPHER_ALGORITHM_PADDING_PKCS1 = "PKCS1Padding";
    private static final String CIPHER_ALGORITHM = Constants.KEY_ALGORITHM_RSA + "/"
            + CIPHER_ALGORITHM_MODE_ECB + "/" + CIPHER_ALGORITHM_PADDING_PKCS1;
    private static final String X500_NAME = "CN=Sample Name, O=Android Authority";

    private final Context mContext;
    private final KeyStoreManager mKeyStoreManager;

    public RSACipherManager(Context context, String appAlias) {
        super(appAlias);
        mContext = context;
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
            generateEncryptionKeyPairWithRSA(mContext, buildAlias(metaAlias));
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

    private void generateEncryptionKeyPairWithRSA(Context context, String metaAlias) throws EncryptionException {
        try {
            KeyPairGeneratorSpec spec = generateKeyPairGeneratorSpec(context, metaAlias);
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA, ANDROID_KEY_STORE);
            generator.initialize(spec);
            generator.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new EncryptionException(e);
        }
    }

    private KeyPairGeneratorSpec generateKeyPairGeneratorSpec(Context context, String metaAlias) {
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 1);

        return new KeyPairGeneratorSpec.Builder(context)
                .setAlias(metaAlias)
                .setSubject(new X500Principal(X500_NAME))
                .setSerialNumber(BigInteger.ONE)
                .setStartDate(start.getTime())
                .setEndDate(end.getTime())
                .build();
    }

    @Override
    String getAlgorithmAlias() {
        return Constants.KEY_ALGORITHM_RSA;
    }
}
