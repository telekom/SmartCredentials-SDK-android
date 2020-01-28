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

import android.text.TextUtils;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.DomainModelException;
import de.telekom.smartcredentials.core.model.EncryptionAlgorithm;
import de.telekom.smartcredentials.core.model.ModelValidator;
import de.telekom.smartcredentials.core.strategies.EncryptionStrategy;

public class ItemDomainModel {

    public static final String UNIQUE_KEY_EXCEPTION_MESSAGE = "Could not compute unique key; item id was not set";

    private String mUid;
    private ItemDomainMetadata mMetadata;
    private ItemDomainData mData;

    public ItemDomainModel() {
        // required empty constructor
    }

    public ItemDomainModel(ItemDomainModel item) {
        mUid = item.getUid();
        mMetadata = new ItemDomainMetadata(item.getMetadata());
        mData = new ItemDomainData(item.getData());
    }

    public String getUid() {
        return mUid;
    }

    public ItemDomainModel setId(String id) {
        this.mUid = id;
        return this;
    }

    public ItemDomainMetadata getMetadata() {
        return mMetadata;
    }

    public ItemDomainModel setMetadata(ItemDomainMetadata metadata) {
        mMetadata = metadata;
        return this;
    }

    public ItemDomainData getData() {
        return mData;
    }

    public ItemDomainModel setData(ItemDomainData data) {
        mData = data;
        return this;
    }

    public ItemDomainModel encryptData(EncryptionStrategy encryptionStrategy, boolean isSensitive)
            throws EncryptionException {
        mData.encryptWith(encryptionStrategy, isSensitive);
        return this;
    }

    public ItemDomainModel partiallyEncrypt(EncryptionStrategy encryptionStrategy, boolean isSensitive)
            throws EncryptionException {
        mData.partiallyEncryptWith(encryptionStrategy, isSensitive);
        return this;
    }

    public ItemDomainModel decryptData(EncryptionStrategy encryptionStrategy)
            throws EncryptionException {
        mData.decryptWith(encryptionStrategy);
        return this;
    }

    public ItemDomainModel decryptData(EncryptionStrategy encryptionStrategy,
                                       EncryptionAlgorithm algorithm) throws EncryptionException {
        mData.decryptWith(encryptionStrategy, algorithm);
        return this;
    }

    public String getUniqueKey() {
        if (TextUtils.isEmpty(getUid())) {
            throw new DomainModelException(UNIQUE_KEY_EXCEPTION_MESSAGE);
        }
        return ModelValidator.getValidatedUniqueKey(mMetadata.getUniqueKeyPrefix(), getUid());
    }
}
