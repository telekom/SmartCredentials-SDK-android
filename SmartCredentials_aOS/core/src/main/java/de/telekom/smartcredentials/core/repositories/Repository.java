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

package de.telekom.smartcredentials.core.repositories;

import java.util.List;

import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;

import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;

public abstract class Repository {

    private static final String REPO_ALIAS_NOT_AVAILABLE = "Repo alias native library not yet loaded.";

    public abstract int saveData(ItemDomainModel itemDomainModel);

    public abstract int updateItem(ItemDomainModel itemDomainModel);

    public abstract List<ItemDomainModel> retrieveItemsFilteredByType(ItemDomainModel itemDomainModel);

    public abstract ItemDomainModel retrieveFilteredItemSummaryByUniqueIdAndType(ItemDomainModel itemDomainModel);

    public abstract ItemDomainModel retrieveFilteredItemDetailsByUniqueIdAndType(ItemDomainModel itemDomainModel);

    public abstract int deleteAllData();

    public abstract int deleteItem(ItemDomainModel itemDomainModel);

    public abstract int deleteItemsByType(ItemDomainModel itemDomainModel);

    public abstract String getTag();

    public String getAlias(ItemDomainMetadata metadata) throws EncryptionException {
        if (!AliasNative.isLibraryLoaded()) {
            throw new EncryptionException(REPO_ALIAS_NOT_AVAILABLE);
        }

        checkParamNotNull(metadata);

        ContentType contentType = metadata.getContentType();
        return AliasNative.getAlias(contentType == ContentType.SENSITIVE);
    }
}
