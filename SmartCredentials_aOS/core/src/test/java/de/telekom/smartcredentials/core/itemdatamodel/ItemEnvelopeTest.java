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

package de.telekom.smartcredentials.core.itemdatamodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import de.telekom.smartcredentials.core.ModelGenerator;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ItemEnvelopeTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private final String mUserId = "1234";

    @Test
    public void isSensitiveItemThrowsExceptionWhenTargetIsNull() {
        thrown.expect(EnvelopeException.class);
        thrown.expectMessage(EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG.getReason());

        ModelGenerator
                .generateItemEnvelopeWithoutItemType()
                .isSensitiveItem();
    }

    @Test
    public void toItemDomainModelConvertsModel() {
        ItemEnvelope itemEnvelope = ModelGenerator.generateItemEnvelope();
        assertEquals(itemEnvelope.isSensitiveItem(), ModelGenerator.mItmType.isSensitive());

        ItemDomainModel itemDomainModel = itemEnvelope.toItemDomainModel(mUserId);

        assertEquals(itemDomainModel.getUid(), itemEnvelope.getItemId());

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getItemType(), itemEnvelope.getItemType().getDesc());
        assertEquals(itemDomainModel.getMetadata().isDataEncrypted(), itemEnvelope.getItemMetadata().isDataEncrypted());

        assertNotNull(itemDomainModel.getData());
        assertEquals(itemDomainModel.getData().getPrivateData(), itemEnvelope.getItemMetadata().getDetails().toString());
        assertEquals(itemDomainModel.getData().getIdentifier(), itemEnvelope.getIdentifier().toString());
    }

    @Test
    public void toItemDomainModelConvertsModelWithEmptyData() {
        ItemEnvelope itemEnvelope = ModelGenerator.generateItemEnvelopeWithEmptyDetailsAndIdentifier();

        ItemDomainModel itemDomainModel = itemEnvelope.toItemDomainModel(mUserId);

        assertEquals(itemDomainModel.getUid(), itemEnvelope.getItemId());

        assertNotNull(itemDomainModel.getMetadata());
        assertEquals(itemDomainModel.getMetadata().getItemType(), itemEnvelope.getItemType().getDesc());
        assertEquals(itemDomainModel.getMetadata().isDataEncrypted(), itemEnvelope.getItemMetadata().isDataEncrypted());

        assertNotNull(itemDomainModel.getData());
        assertNull(itemDomainModel.getData().getPrivateData());
        assertNull(itemDomainModel.getData().getIdentifier());
    }

    @Test
    public void toItemDomainModelThrowsExceptionWhenMetadataIsNull() {
        thrown.expect(EnvelopeException.class);
        thrown.expectMessage(EnvelopeExceptionReason.NO_METADATA_EXCEPTION_MSG.getReason());

        ModelGenerator
                .generateItemEnvelopeWithNoMetadata()
                .toItemDomainModel(mUserId);
    }

    @Test
    public void toItemDomainModelThrowsExceptionWhenTargetIsNull() {
        thrown.expect(EnvelopeException.class);
        thrown.expectMessage(EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG.getReason());

        ModelGenerator
                .generateItemEnvelopeWithoutItemType()
                .toItemDomainModel(mUserId);
    }

    @Test
    public void setItemTypeThrowsExceptionWhenItemTypeIsNull() {
        thrown.expect(EnvelopeException.class);
        thrown.expectMessage(EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG.getReason());

        new ItemEnvelope().setItemType(null);
    }

    @Test
    public void setItemMetadataThrowsExceptionWhenItemTypeIsNull() {
        thrown.expect(EnvelopeException.class);
        thrown.expectMessage(EnvelopeExceptionReason.NO_METADATA_EXCEPTION_MSG.getReason());

        new ItemEnvelope().setItemMetadata(null);
    }
}