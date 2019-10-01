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

package de.telekom.smartcredentials.storage.database.models;

import org.junit.Before;
import org.junit.Test;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.utils.ModelGenerator;

import static de.telekom.smartcredentials.storage.utils.ModelGenerator.ID;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.IDENTIFIER;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ItemTest {
    private Item mItem;

    @Before
    public void setUp() {
        mItem = ModelGenerator.generateItem();
    }

    @Test
    public void toDomainModelConvertsItem() {
        ItemDomainModel itemDomainModel = mItem.toDomainModel();
        assertNotNull(itemDomainModel);
        assertEquals(ID, itemDomainModel.getUid());

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(ID, itemDomainModel.getMetadata().getActionList().get(0).getId());
        assertEquals(TYPE, itemDomainModel.getMetadata().getItemType());

        assertNotNull(itemDomainModel.getData());
        assertEquals(IDENTIFIER, itemDomainModel.getData().getIdentifier());
    }

}