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

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.repositories.RepositoryType;

import static org.junit.Assert.assertEquals;

public class ModelComparator {

    public static void compareItemToItemDomainModel(Item item, ItemDomainModel domainModel, RepositoryType repositoryType) {
        assertEquals(item.getUid(), domainModel.getUid());
        assertEquals(item.getIdentifier(), domainModel.getData().getIdentifier());
        assertEquals(item.isSecuredData(), domainModel.getMetadata().isDataEncrypted());
        assertEquals(item.getType(), domainModel.getMetadata().getItemType());
        assertEquals(item.getUserId(), domainModel.getMetadata().getUserId());
        assertEquals(item.getActionList().get(0).getClassName(), domainModel.getMetadata().getActionList().get(0).getClassName());
        assertEquals(repositoryType.getContentType(), domainModel.getMetadata().getContentType());
    }

    public static void compareItemWithPrivateDataToItemDomainModel(Item item, String privateData, ItemDomainModel domainModel, RepositoryType repositoryType) {
        compareItemToItemDomainModel(item, domainModel, repositoryType);
        assertEquals(privateData, domainModel.getData().getPrivateData());
    }

    public static void compareDomainModels(ItemDomainModel m1, ItemDomainModel m2) {
        assertEquals(m1.getUid(), m2.getUid());
        assertEquals(m1.getMetadata().getUserId(), m2.getMetadata().getUserId());
        assertEquals(m1.getMetadata().getItemType(), m2.getMetadata().getItemType());
        assertEquals(m1.getMetadata().getContentType(), m2.getMetadata().getContentType());
        assertEquals(m1.getMetadata().getActionList().get(0).getClassName(), m2.getMetadata().getActionList().get(0).getClassName());
        assertEquals(m1.getData().getPrivateData(), m2.getData().getPrivateData());
        assertEquals(m1.getData().getIdentifier(), m2.getData().getIdentifier());
    }
}
