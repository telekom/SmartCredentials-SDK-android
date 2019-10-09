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

package de.telekom.smartcredentials.core.filter;

import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemType;

public class SmartCredentialsFilter {

    private String mId;
    private ItemType mItemType;
    private ItemEnvelope mItemEnvelope;

    SmartCredentialsFilter(ItemType itemType) {
        this(null, itemType);
    }

    SmartCredentialsFilter(String id, ItemType itemType) {
        mId = id;
        mItemType = itemType;
    }

    public SmartCredentialsFilter(ItemEnvelope itemEnvelope) {
        mItemEnvelope = itemEnvelope;
    }

    public String getId() {
        return mId;
    }

    public ItemDomainModel toItemDomainModel(String userId) {
        if (mItemEnvelope != null) {
            return mItemEnvelope.toItemDomainModel(userId);
        }

        return new ItemDomainModel()
                .setId(mId)
                .setMetadata(toItemDomainMetadata(userId))
                .setData(new ItemDomainData());
    }

    ItemType getItemType() {
        return mItemType;
    }

    private ItemDomainMetadata toItemDomainMetadata(String userId) {
        ItemDomainMetadata itemDomainMetadata = new ItemDomainMetadata(true, false, false)
                .setUserId(userId);
        if (mItemType != null) {
            return itemDomainMetadata
                    .setItemType(mItemType.getDesc())
                    .setContentType(mItemType.getContentType());
        }
        return itemDomainMetadata;
    }

}
