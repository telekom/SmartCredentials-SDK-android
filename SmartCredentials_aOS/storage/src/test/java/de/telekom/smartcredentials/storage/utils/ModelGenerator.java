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

package de.telekom.smartcredentials.storage.utils;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.repositories.RepositoryType;

public class ModelGenerator {

    public static final String ID = "12345";
    public static final String USER_ID = "89798";
    public static final String IDENTIFIER = "IDENTIFIER";
    public static final String TYPE = "ITEM_TYPE";
    public static final String PRIVATE = "PRIVATE_DATA";
    public static final boolean DATA_IS_NOT_ENCRYPTED = false;
    public static final boolean DATA_IS_ENCRYPTED = true;
    public static final boolean AUTO_LOCK_ENABLED = true;
    public static final boolean AUTO_LOCK_NOT_ENABLED = false;
    public static final boolean LOCKED = true;
    public static final boolean NOT_LOCKED = false;
    public static final RepositoryType NON_SENSITIVE_REPO_TYPE = RepositoryType.NONSENSITIVE;
    public static final RepositoryType SENSITIVE_REPO_TYPE = RepositoryType.SENSITIVE;
    public static final List<ItemDomainAction> mActionList;

    public static Item generateItem() {
        Item item = new Item();
        item.setUid(ID);
        item.setActionList(mActionList);
        item.setIdentifier(IDENTIFIER);
        item.setType(TYPE);
        item.setSecuredData(DATA_IS_NOT_ENCRYPTED);
        item.setAutoLockEnabled(AUTO_LOCK_NOT_ENABLED);
        item.setLocked(NOT_LOCKED);
        return item;
    }

    public static Item generateOtherItem() {
        Item item = new Item();
        String id2 = "98765";
        item.setUid(id2);
        item.setActionList(mActionList);
        String identifier2 = "IDENTIFIER2";
        item.setIdentifier(identifier2);
        String type2 = "TYPE2";
        item.setType(type2);
        item.setSecuredData(DATA_IS_ENCRYPTED);
        item.setAutoLockEnabled(AUTO_LOCK_NOT_ENABLED);
        item.setLocked(NOT_LOCKED);
        return item;
    }

    public static ItemDomainModel generateNonEncryptedNonSensitiveItemDomainModel() {
        return generateItemDomainModel(DATA_IS_NOT_ENCRYPTED, AUTO_LOCK_NOT_ENABLED, NOT_LOCKED,
                NON_SENSITIVE_REPO_TYPE);
    }

    public static ItemDomainModel generateEncryptedNonSensitiveItemDomainModel() {
        return generateItemDomainModel(DATA_IS_ENCRYPTED, AUTO_LOCK_NOT_ENABLED, NOT_LOCKED,
                NON_SENSITIVE_REPO_TYPE);
    }

    public static ItemDomainModel generateEncryptedSensitiveItemDomainModel() {
        return generateItemDomainModel(DATA_IS_ENCRYPTED, AUTO_LOCK_NOT_ENABLED, NOT_LOCKED,
                SENSITIVE_REPO_TYPE);
    }

    static {
        mActionList = new ArrayList<>();
        ItemDomainAction itemDomainAction = new ItemDomainAction().setClassName(TYPE).setActionId(ID);
        mActionList.add(itemDomainAction);
    }

    private static ItemDomainModel generateItemDomainModel(boolean encrypted, boolean autoLockEnabled,
                                                           boolean isLocked, RepositoryType repositoryType) {
        ItemDomainData data = new ItemDomainData();
        data.setIdentifier(IDENTIFIER)
                .setPrivateData(PRIVATE);

        ItemDomainMetadata metadata = new ItemDomainMetadata(encrypted, autoLockEnabled, isLocked);
        metadata.setActionList(mActionList)
                .setUserId(USER_ID)
                .setItemType(TYPE)
                .setContentType(repositoryType.getContentType());

        return new ItemDomainModel().setId(ID)
                .setData(data)
                .setMetadata(metadata);
    }
}
