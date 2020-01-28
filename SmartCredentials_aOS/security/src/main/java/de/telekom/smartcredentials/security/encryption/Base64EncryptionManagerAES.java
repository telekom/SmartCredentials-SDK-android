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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.exceptions.InvalidAlgorithmException;
import de.telekom.smartcredentials.core.security.KeyStoreManagerException;
import de.telekom.smartcredentials.core.security.KeyStoreProviderException;
import de.telekom.smartcredentials.security.repositories.RepositoryAliasNative;

import static de.telekom.smartcredentials.core.security.EncryptionError.DECRYPTION_EXCEPTION_TEXT;
import static de.telekom.smartcredentials.core.security.EncryptionError.ENCRYPTION_EXCEPTION_TEXT;
import static de.telekom.smartcredentials.security.encryption.AESCipherManager.BASE64_FLAG;

public class Base64EncryptionManagerAES implements EncryptionManager {

    static final String BASE64_CHAR_SET = "UTF-8";

    static final String IV_SEPARATOR = "cipherIV=";

    private static final String MISSING_IV_MESSAGE = "IV not set in encrypted text";

    private final AESCipherManager mAESCipherManager;

    public Base64EncryptionManagerAES(AESCipherManager aesCipherManager) {
        mAESCipherManager = aesCipherManager;
    }

    @Override
    public String encrypt(String toEncrypt) throws EncryptionException {
        if (TextUtils.isEmpty(toEncrypt)) {
            return toEncrypt;
        }

        try {
            return encrypt(toEncrypt, RepositoryAliasNative.getAliasSensitive());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IOException | KeyStoreManagerException | KeyStoreProviderException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new EncryptionException(ENCRYPTION_EXCEPTION_TEXT + e.getMessage(), e);
        }
    }

    @Override
    public String encrypt(String toEncrypt, boolean isSensitive) throws EncryptionException {
        if (TextUtils.isEmpty(toEncrypt)) {
            return toEncrypt;
        }

        try {
            return encrypt(toEncrypt, isSensitive ? RepositoryAliasNative.getAliasSensitive() :
                    RepositoryAliasNative.getAliasNonSensitive());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                | IOException | KeyStoreManagerException | KeyStoreProviderException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new EncryptionException(ENCRYPTION_EXCEPTION_TEXT + e.getMessage(), e);
        }
    }

    private String encrypt(String toEncrypt, String repositoryAlias) throws NoSuchAlgorithmException, NoSuchPaddingException, KeyStoreProviderException, KeyStoreManagerException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        AESCipherManager.AESCipher aesCipher = mAESCipherManager.obtainEncryptionCipher(repositoryAlias);

        String encodedIV = new String(Base64.encode(aesCipher.getIV(), BASE64_FLAG), Charset.defaultCharset());
        byte[] cipherTextFinalBytes = aesCipher.getFinalBytes(toEncrypt.getBytes(BASE64_CHAR_SET));

        return new String(Base64.encode(cipherTextFinalBytes, BASE64_FLAG), Charset.defaultCharset())
                + IV_SEPARATOR
                + encodedIV;
    }

    @Override
    public String decrypt(String encryptedText) throws EncryptionException {
        if (TextUtils.isEmpty(encryptedText)) {
            return encryptedText;
        }

        try {
            return decrypt(encryptedText, RepositoryAliasNative.getAliasSensitive());
        } catch (NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | InvalidAlgorithmParameterException | KeyStoreProviderException | InvalidKeyException | KeyStoreManagerException | NoSuchPaddingException | UnsupportedEncodingException e) {
            try {
                return decrypt(encryptedText, RepositoryAliasNative.getAliasNonSensitive());
            } catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | KeyStoreManagerException | InvalidKeyException | KeyStoreProviderException ex) {
                throw new EncryptionException(DECRYPTION_EXCEPTION_TEXT + e.getMessage(), e);
            }
        }
    }

    private String decrypt(String encryptedText, String repositoryAlias) throws InvalidAlgorithmException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, KeyStoreManagerException, InvalidKeyException, KeyStoreProviderException {
        String[] textWithIV = encryptedText.split(IV_SEPARATOR);
        if (textWithIV.length < 2) {
            throw new InvalidAlgorithmException(DECRYPTION_EXCEPTION_TEXT + "\n " + MISSING_IV_MESSAGE);
        }

        // text to be decrypted process
        String textToDecrypt = textWithIV[0]
                .replace("\n", "");
        byte[] bytesToDecrypt = textToDecrypt.getBytes(BASE64_CHAR_SET);

        // IV process
        String iv = textWithIV[1].replace("\n", "");

        AESCipherManager.AESCipher aesCipher = mAESCipherManager.obtainDecryptionCypher(iv, repositoryAlias);
        byte[] cipherText = Base64.decode(bytesToDecrypt, BASE64_FLAG);
        byte[] bytesDecrypted = aesCipher.getFinalBytes(cipherText);
        return new String(bytesDecrypted, BASE64_CHAR_SET);
    }
}