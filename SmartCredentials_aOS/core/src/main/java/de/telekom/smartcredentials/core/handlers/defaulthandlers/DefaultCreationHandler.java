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

package de.telekom.smartcredentials.core.handlers.defaulthandlers;

import java.security.PublicKey;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.handlers.CreationHandler;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;


public class DefaultCreationHandler implements CreationHandler {

    private final EncryptionStrategy mEncryptionStrategy;

    public DefaultCreationHandler(EncryptionStrategy encryptionStrategy) {
        mEncryptionStrategy = encryptionStrategy;
    }

    @Override
    public PublicKey createPublicKey(String alias) throws EncryptionException {
        return mEncryptionStrategy.getPublicKey(alias);
    }

    @Override
    public String encrypt(String toEncrypt, EncryptionAlgorithm algorithm) throws EncryptionException {
        return mEncryptionStrategy.encrypt(toEncrypt, algorithm);
    }

    @Override
    public String encrypt(String toEncrypt, boolean isSensitive, EncryptionAlgorithm algorithm) throws EncryptionException {
        return mEncryptionStrategy.encrypt(toEncrypt, isSensitive, algorithm);
    }
}
