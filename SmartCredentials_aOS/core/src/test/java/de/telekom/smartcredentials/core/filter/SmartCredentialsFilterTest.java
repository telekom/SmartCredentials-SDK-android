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

import android.text.TextUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.ModelGenerator;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemType;
import de.telekom.smartcredentials.core.itemdatamodel.ItemTypeFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class SmartCredentialsFilterTest {

    private final String mUserId = "1234";
    private final ItemType mItemType = ItemTypeFactory.createNonSensitiveType("voucher");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
    }

    @Test
    public void toItemDomainModelCreatesItemFromFilterWithItemType() {
        SmartCredentialsFilter smartCredentialsFilter = new SmartCredentialsFilter(mItemType);
        ItemDomainModel itemDomainModel = smartCredentialsFilter.toItemDomainModel(mUserId);

        assertNull(itemDomainModel.getUid());
        assertEquals(mItemType.getDesc(), itemDomainModel.getMetadata().getItemType());
        assertEquals(itemDomainModel.getMetadata().getUserId(), mUserId);
        assertTrue(itemDomainModel.getMetadata().isDataEncrypted());
    }

    @Test
    public void toItemDomainModelCreatesItemFromFilterWithoutItemType() {
        String id = "444";

        SmartCredentialsFilter smartCredentialsFilter = new SmartCredentialsFilter(id, null);
        ItemDomainModel itemDomainModel = smartCredentialsFilter.toItemDomainModel(mUserId);

        assertEquals(itemDomainModel.getUid(), id);
        assertNull(itemDomainModel.getMetadata().getItemType());
        assertEquals(itemDomainModel.getMetadata().getUserId(), mUserId);
        assertTrue(itemDomainModel.getMetadata().isDataEncrypted());
    }

    @Test
    public void toItemDomainModelCreatesItemFromItemEnvelope() {
        ItemEnvelope itemEnvelope = ModelGenerator.generateItemEnvelope();

        SmartCredentialsFilter smartCredentialsFilter = new SmartCredentialsFilter(itemEnvelope);
        ItemDomainModel itemDomainModel = smartCredentialsFilter.toItemDomainModel(mUserId);

        assertEquals(itemDomainModel.getUid(), itemEnvelope.getItemId());

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getItemType(), itemEnvelope.getItemType().getDesc());
        assertEquals(itemDomainModel.getMetadata().isDataEncrypted(), itemEnvelope.getItemMetadata().isDataEncrypted());

        assertNotNull(itemDomainModel.getData());
        assertEquals(itemDomainModel.getData().getPrivateData(), itemEnvelope.getDetails().toString());
        assertEquals(itemDomainModel.getData().getIdentifier(), itemEnvelope.getIdentifier().toString());
    }

}