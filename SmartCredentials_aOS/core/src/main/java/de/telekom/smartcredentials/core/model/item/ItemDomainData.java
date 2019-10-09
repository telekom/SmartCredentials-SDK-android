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

package de.telekom.smartcredentials.core.model.item;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;

public class ItemDomainData {

    private String mIdentifier;
    private String mPrivateData;

    public ItemDomainData() {
        // required empty constructor
    }

    public ItemDomainData(ItemDomainData data) {
        mIdentifier = data.getIdentifier();
        mPrivateData = data.getPrivateData();
    }

    public ItemDomainData setIdentifier(String identifier) {
        mIdentifier = identifier;
        return this;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public ItemDomainData setPrivateData(String privateData) {
        mPrivateData = privateData;
        return this;
    }

    public String getPrivateData() {
        return mPrivateData;
    }

    void encryptWith(EncryptionStrategy encryptionStrategy, String metaAlias) throws EncryptionException {
        mIdentifier = encryptionStrategy.encrypt(mIdentifier, metaAlias);
        mPrivateData = encryptionStrategy.encrypt(mPrivateData, metaAlias);
    }

    void partiallyEncryptWith(EncryptionStrategy encryptionStrategy, String metaAlias) throws EncryptionException {
        mIdentifier = encryptionStrategy.encrypt(mIdentifier, metaAlias);
    }

    void decryptWith(EncryptionStrategy encryptionStrategy, String metaAlias) throws EncryptionException {
        mIdentifier = encryptionStrategy.decrypt(mIdentifier, metaAlias);
        mPrivateData = encryptionStrategy.decrypt(mPrivateData, metaAlias);
    }

    void decryptWith(EncryptionStrategy encryptionStrategy, String alias, EncryptionAlgorithm algorithm) throws EncryptionException {
        mIdentifier = encryptionStrategy.decrypt(mIdentifier, alias, algorithm);
        mPrivateData = encryptionStrategy.decrypt(mPrivateData, alias, algorithm);
    }
}
