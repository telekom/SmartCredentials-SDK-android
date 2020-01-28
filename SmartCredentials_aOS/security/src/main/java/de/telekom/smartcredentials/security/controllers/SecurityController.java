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

package de.telekom.smartcredentials.security.controllers;

import android.content.Context;

import java.security.Key;
import java.security.PublicKey;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.controllers.CreationController;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;
import de.telekom.smartcredentials.core.security.CipherWrapper;
import de.telekom.smartcredentials.core.security.KeyStoreKeyProvider;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;
import de.telekom.smartcredentials.security.encryption.ChecksumGenerator;
import de.telekom.smartcredentials.security.encryption.HmacManager;
import de.telekom.smartcredentials.security.encryption.SmartCredentialsCipherWrapper;
import de.telekom.smartcredentials.security.keystore.SmartCredentialsKeyStoreKeyProvider;

public class SecurityController implements SecurityApi {

    private final Context mContext;
    private final CoreController mCoreController;
    private final CreationController mCreationController;
    private final EncryptionStrategy mEncryptionStrategy;

    public SecurityController(Context context, CoreController coreController, CreationController creationController, EncryptionStrategy encryptionStrategy) {
        mContext = context;
        mCoreController = coreController;
        mCreationController = creationController;
        mEncryptionStrategy = encryptionStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<PublicKey> getPublicKey(String publicKeyAlias) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getPublicKey");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.PUBLIC_KEY_GENERATION)) {
            String errorMessage = SmartCredentialsFeatureSet.PUBLIC_KEY_GENERATION.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            PublicKey publicKey = mCreationController.createPublicKey(publicKeyAlias);
            return new SmartCredentialsResponse<>(publicKey);
        } catch (EncryptionException e) {
            return new SmartCredentialsResponse<>(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<String> encrypt(String toEncrypt, EncryptionAlgorithm algorithm) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "encrypt");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.ENCRYPT)) {
            String errorMessage = SmartCredentialsFeatureSet.ENCRYPT.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        try {
            String encryptedText = mCreationController.encrypt(toEncrypt, algorithm);
            return new SmartCredentialsResponse<>(encryptedText);
        } catch (EncryptionException e) {
            return new SmartCredentialsResponse<>(e);
        }
    }

    @Override
    public CipherWrapper getCipherWrapper(String transformation, int opMode, Key key) {
        return new SmartCredentialsCipherWrapper(transformation, opMode, key);
    }

    @Override
    public KeyStoreKeyProvider getKeyStoreKeyProvider() {
        return new SmartCredentialsKeyStoreKeyProvider(mContext);
    }

    @Override
    public int calculateChecksum(long otp, int digits) {
        return ChecksumGenerator.calcChecksum(otp, digits);
    }

    @Override
    public byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) throws EncryptionException {
        return HmacManager.hmac_sha(crypto, keyBytes, text);
    }

    @Override
    public EncryptionStrategy getEncryptionStrategy() {
        return mEncryptionStrategy;
    }
}
