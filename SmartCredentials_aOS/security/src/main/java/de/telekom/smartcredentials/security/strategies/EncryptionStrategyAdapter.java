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

package de.telekom.smartcredentials.security.strategies;

import java.security.PublicKey;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.exceptions.InvalidAlgorithmException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.security.EncryptionError;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerAES;
import de.telekom.smartcredentials.security.encryption.Base64EncryptionManagerRSA;
import de.telekom.smartcredentials.security.encryption.EncryptionManager;

public class EncryptionStrategyAdapter implements EncryptionStrategy {

    private final EncryptionManager mBase64EncryptionManager;
    private final Base64EncryptionManagerRSA mBase64EncryptionManagerRSA;
    private final Base64EncryptionManagerAES mBase64EncryptionManagerAES;

    public EncryptionStrategyAdapter(EncryptionManager encryptionManager,
                                     Base64EncryptionManagerRSA base64EncryptionManagerRSA,
                                     Base64EncryptionManagerAES base64EncryptionManagerAES) {
        mBase64EncryptionManager = encryptionManager;
        mBase64EncryptionManagerRSA = base64EncryptionManagerRSA;
        mBase64EncryptionManagerAES = base64EncryptionManagerAES;
    }

    @Override
    public String encrypt(String toEncrypt) throws EncryptionException {
        return mBase64EncryptionManager.encrypt(toEncrypt);
    }

    @Override
    public String encrypt(String toEncrypt, EncryptionAlgorithm algorithm) throws EncryptionException {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M && algorithm == EncryptionAlgorithm.AES_256) {
            throw new InvalidAlgorithmException(EncryptionError.ALGORITHM_NOT_SUPPORTED);
        }

        switch (algorithm) {
            case RSA_2048:
                return mBase64EncryptionManagerRSA.encrypt(toEncrypt);
            case AES_256:
                return mBase64EncryptionManagerAES.encrypt(toEncrypt);
            case DEFAULT:
            default:
                return mBase64EncryptionManager.encrypt(toEncrypt);
        }
    }

    @Override
    public String encrypt(String toEncrypt, boolean isSensitive) throws EncryptionException {
        return mBase64EncryptionManager.encrypt(toEncrypt, isSensitive);
    }

    @Override
    public String encrypt(String toEncrypt, boolean isSensitive, EncryptionAlgorithm algorithm) throws EncryptionException {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M && algorithm == EncryptionAlgorithm.AES_256) {
            throw new InvalidAlgorithmException(EncryptionError.ALGORITHM_NOT_SUPPORTED);
        }

        switch (algorithm) {
            case RSA_2048:
                return mBase64EncryptionManagerRSA.encrypt(toEncrypt, isSensitive);
            case AES_256:
                return mBase64EncryptionManagerAES.encrypt(toEncrypt, isSensitive);
            case DEFAULT:
            default:
                return mBase64EncryptionManager.encrypt(toEncrypt, isSensitive);
        }
    }

    @Override
    public String decrypt(String encryptedText) throws EncryptionException {
        return mBase64EncryptionManager.decrypt(encryptedText);
    }

    @Override
    public String decrypt(String encryptedText, EncryptionAlgorithm algorithm) throws EncryptionException {
        if (algorithm == EncryptionAlgorithm.RSA_2048) {
            return mBase64EncryptionManagerRSA.decrypt(encryptedText);
        }
        return mBase64EncryptionManager.decrypt(encryptedText);
    }

    @Override
    public PublicKey getPublicKey(String alias) throws EncryptionException {
        return mBase64EncryptionManagerRSA.getPublicKey(alias);
    }
}
