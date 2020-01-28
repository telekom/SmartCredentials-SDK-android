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

package de.telekom.smartcredentials.core.controllers;

import java.security.PublicKey;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.handlers.CreationHandler;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;

public class CreationController {

    private final CreationHandler mDefaultCreationHandler;

    public CreationController(CreationHandler creationHandler) {
        mDefaultCreationHandler = creationHandler;
    }

    public PublicKey createPublicKey(String alias) throws EncryptionException {
        return mDefaultCreationHandler.createPublicKey(alias);
    }

    public String encrypt(String toEncrypt, EncryptionAlgorithm algorithm) throws EncryptionException {
        return mDefaultCreationHandler.encrypt(toEncrypt, algorithm);
    }

    public String encrypt(String toEncrypt, boolean isSensitive, EncryptionAlgorithm algorithm) throws EncryptionException {
        return mDefaultCreationHandler.encrypt(toEncrypt, isSensitive, algorithm);
    }
}
