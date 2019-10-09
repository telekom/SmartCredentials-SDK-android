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

package de.telekom.smartcredentials.core.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.RuleChain;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.DomainModelGenerator;
import de.telekom.smartcredentials.core.actions.ExecutionCallback;
import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.context.ItemContextFactory;
import de.telekom.smartcredentials.core.converters.ModelConverter;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.responses.EnvelopeException;

import static de.telekom.smartcredentials.core.DomainModelGenerator.ID;
import static de.telekom.smartcredentials.core.DomainModelGenerator.IDENTIFIER;
import static de.telekom.smartcredentials.core.DomainModelGenerator.IS_ENCRYPTED;
import static de.telekom.smartcredentials.core.DomainModelGenerator.PRIVATE_DATA;
import static de.telekom.smartcredentials.core.DomainModelGenerator.USER_ITEM_TYPE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ModelConverterTest {

    private ItemDomainModel mItemDomainModel;

    private List<ItemDomainModel> mItemDomainModels;

    private final ExpectedException mExpectedExceptionRule = ExpectedException.none();

    private final InstantTaskExecutorRule mInstantTaskExecutorRule = new InstantTaskExecutorRule();

    @Rule
    public RuleChain mRuleChain = RuleChain
            .outerRule(mInstantTaskExecutorRule)
            .around(mExpectedExceptionRule);

    @Before
    public void setUp() {
        mItemDomainModel = DomainModelGenerator.generateModel();
        mItemDomainModels = new ArrayList<ItemDomainModel>() {{
            add(mItemDomainModel);
        }};
    }

    @Test
    public void toItemEnvelopeShouldConvertFromDomainModelToItemEnvelope() throws JSONException {
        ItemEnvelope itemEnvelope = ModelConverter.toItemEnvelope(mItemDomainModel);
        assertEquals(itemEnvelope.getIdentifier().toString(), IDENTIFIER);
        assertEquals(itemEnvelope.getItemId(), ID);
        assertEquals(itemEnvelope.getDetails().toString(), "{\"random_key\":\"private data\"}");
        assertNotNull(itemEnvelope.getItemMetadata());
        assertEquals(itemEnvelope.getItemMetadata().isDataEncrypted(), IS_ENCRYPTED);
        assertNotNull(itemEnvelope.getItemType());
        assertNotNull(itemEnvelope.getActionList());
        assertEquals(itemEnvelope.getItemType().getDesc(), USER_ITEM_TYPE);
        assertEquals(itemEnvelope.getItemType().getContentType(), ContentType.SENSITIVE);
        assertNull(itemEnvelope.getUserId());
    }

    @Test
    public void toItemEnvelopeShouldConvertFromDomainModelToItemEnvelopeWithEmptyMetadata() throws JSONException {
        ItemEnvelope itemEnvelope = ModelConverter.toItemEnvelope(DomainModelGenerator.generateEmptyModel());
        assertNull(itemEnvelope.getItemId());
        assertNull(itemEnvelope.getItemMetadata());

        assertNull(itemEnvelope.getIdentifier());
        assertNull(itemEnvelope.getItemId());

        assertNull(itemEnvelope.getUserId());

        mExpectedExceptionRule.expect(EnvelopeException.class);
        itemEnvelope.getDetails();
    }

    @Test
    public void toItemEnvelopeShouldConvertFromDomainModelToItemEnvelopeWithEmptyData() throws JSONException {
        ItemEnvelope itemEnvelope = ModelConverter.toItemEnvelope(DomainModelGenerator.generateModelWithEmptyData());
        assertNotNull(itemEnvelope.getItemMetadata());
        assertNull(itemEnvelope.getDetails());
        assertNull(itemEnvelope.getItemId());

        assertNull(itemEnvelope.getIdentifier());
        assertNotNull(itemEnvelope.getItemMetadata());
        assertEquals(itemEnvelope.getItemMetadata().isDataEncrypted(), IS_ENCRYPTED);
        assertNotNull(itemEnvelope.getItemType());
        assertNull(itemEnvelope.getItemType().getDesc());
        assertEquals(itemEnvelope.getItemType().getContentType(), ContentType.SENSITIVE);
        assertNull(itemEnvelope.getUserId());
    }

    @Test
    public void toItemEnvelopeShouldConvertFromDomainModelToItemEnvelopeWithEmptyIdentifier() throws JSONException {
        boolean isEncrypted = false;
        ItemDomainModel model = DomainModelGenerator.generateModelWithEmptyIdentifier(isEncrypted);
        ItemEnvelope itemEnvelope = ModelConverter.toItemEnvelope(model);
        assertNotNull(itemEnvelope.getItemMetadata());
        assertNull(itemEnvelope.getDetails());
        assertNull(itemEnvelope.getItemId());

        assertNull(itemEnvelope.getIdentifier());
        assertNull(itemEnvelope.getItemId());
        assertNull(itemEnvelope.getDetails());
        assertNotNull(itemEnvelope.getItemMetadata());
        assertEquals(itemEnvelope.getItemMetadata().isDataEncrypted(), isEncrypted);
        assertNotNull(itemEnvelope.getItemType());
        assertNull(itemEnvelope.getItemType().getDesc());
        assertEquals(itemEnvelope.getItemType().getContentType(), ContentType.SENSITIVE);
        assertNull(itemEnvelope.getUserId());
    }

    @Test
    public void toItemEnvelopeShouldReturnNullWhenItemDomainModelIsNull() throws JSONException {
        assertNull(ModelConverter.toItemEnvelope(null));
    }

    @Test
    public void toItemEnvelopeListShouldConvertFromDomainModelListToItemEnvelopeList() throws JSONException {
        List<ItemEnvelope> itemEnvelopes = ModelConverter.toItemEnvelopeList(mItemDomainModels);
        assertThat(itemEnvelopes.size(), is(1));
        for (ItemEnvelope itemEnvelope : itemEnvelopes) {
            assertEquals(itemEnvelope.getItemType().getDesc(), USER_ITEM_TYPE);
            assertEquals(itemEnvelope.getIdentifier().toString(), IDENTIFIER);
            assertEquals(itemEnvelope.getActionList().size(), 0);
            assertEquals(itemEnvelope.getItemId(), ID);
        }
        assertNull(ModelConverter.toItemEnvelopeList(null));
    }

    @Test
    public void toItemDomainModelShouldConvertFromItemEnvelopeAndItemContextToItemDomainModel() throws JSONException {
        String userId = "555";
        String type = "type";

        List<SmartCredentialsAction> actions = new ArrayList<>();
        actions.add(new SmartCredentialsAction("1", "2", new JSONObject()) {
            @Override
            public void execute(Context context, ItemDomainModel itemDomainModel, ExecutionCallback callback) {

            }
        });
        ItemEnvelope itemEnvelope = ItemEnvelopeFactory.createItemEnvelope(ID, new JSONObject(IDENTIFIER), new JSONObject(PRIVATE_DATA), actions);
        ItemContext itemContext = ItemContextFactory.createEncryptedSensitiveItemContext(type);

        ItemDomainModel itemDomainModel = ModelConverter.toItemDomainModel(itemEnvelope, itemContext, userId);

        assertTrue(itemDomainModel.getMetadata().isDataEncrypted());
        assertSame(itemDomainModel.getMetadata().getContentType(), ContentType.SENSITIVE);
        assertEquals(itemDomainModel.getUid(), ID);
        assertEquals(itemDomainModel.getData().getIdentifier(), IDENTIFIER);
        assertEquals(itemDomainModel.getMetadata().getActionList().get(0).getName(), "2");
        assertEquals(itemDomainModel.getData().getPrivateData(), PRIVATE_DATA);
        assertEquals(itemDomainModel.getMetadata().getItemType(), type);
        assertEquals(itemDomainModel.getMetadata().getUserId(), userId);
        assertEquals(itemDomainModel.getMetadata().getUserId(), userId);
    }

}
