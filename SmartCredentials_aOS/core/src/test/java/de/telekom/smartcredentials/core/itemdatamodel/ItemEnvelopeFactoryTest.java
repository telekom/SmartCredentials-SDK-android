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

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static de.telekom.smartcredentials.core.ModelGenerator.ITEM_ENVELOPE_JSON;
import static de.telekom.smartcredentials.core.ModelGenerator.ITEM_ENVELOPE_JSON_BROKEN_USER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ItemEnvelopeFactoryTest {

    private static final String KEY_IDENTIFIER = "key_identifier";
    private static final String KEY_DETAILS    = "key_details";

    private final String       mItemId         = "123";
    private final String       mIdentifierText = "identifier";
    private final String       mDetailsText    = "details";
    private final JSONObject   mIdentifier     = new JSONObject();
    private final JSONObject   mDetails        = new JSONObject();
    private ItemEnvelope mItemEnvelope;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws JSONException {
        mIdentifier.put(KEY_IDENTIFIER, mIdentifierText);
        mDetails.put(KEY_DETAILS, mDetailsText);
    }

    @Test
    public void createItemEnvelopeCreatesItemEnvelope() throws Exception {
        mItemEnvelope = ItemEnvelopeFactory.createItemEnvelope(mItemId, mIdentifier, mDetails);
        assertNotNull(mItemEnvelope.getItemMetadata());
        assertEquals(mItemEnvelope.getItemMetadata().getDetails(), mDetails);
        assertEquals(mItemEnvelope.getItemId(), mItemId);
        assertEquals(mItemEnvelope.getIdentifier(), mIdentifier);
        assertEquals(mItemEnvelope.getIdentifier().get(KEY_IDENTIFIER), mIdentifierText);
        assertEquals(mItemEnvelope.getDetails().get(KEY_DETAILS), mDetailsText);
    }

    @Test
    public void createItemEnvelopeCreatesItemEnvelopeWithoutDetails() throws Exception {
        mItemEnvelope = ItemEnvelopeFactory.createItemEnvelope(mItemId, mIdentifier);
        assertNotNull(mItemEnvelope.getItemMetadata());
        assertNull(mItemEnvelope.getItemMetadata().getDetails());
        assertEquals(mItemEnvelope.getItemId(), mItemId);
        assertEquals(mItemEnvelope.getIdentifier(), mIdentifier);
        assertEquals(mItemEnvelope.getIdentifier().get(KEY_IDENTIFIER), mIdentifierText);
    }

    @Test
    public void createItemEnvelopeCreatesItemEnvelopeWithoutDetailsAndWithoutId() throws Exception {
        mItemEnvelope = ItemEnvelopeFactory.createItemEnvelope(mIdentifier);
        assertNotNull(mItemEnvelope.getItemMetadata());
        assertNull(mItemEnvelope.getItemMetadata().getDetails());
        assertNull(mItemEnvelope.getItemId());
        assertEquals(mItemEnvelope.getIdentifier(), mIdentifier);
        assertEquals(mItemEnvelope.getIdentifier().get(KEY_IDENTIFIER), mIdentifierText);
    }

    @Test
    public void fromJSONFailsWhenMappingBrokenOrNotExists() throws JSONException {
        thrown.expect(JSONException.class);
        thrown.expectMessage(SmartCredentialsJSONParser.PREFIX + ItemEnvelope.KEY_USER_ID +
                SmartCredentialsJSONParser.SUFFIX);

        ItemEnvelopeFactory.from(ITEM_ENVELOPE_JSON_BROKEN_USER_ID);
    }

    @Test
    public void fromJSONConvertsStringToItemEnvelope() throws JSONException {
        ItemEnvelope itemEnvelope = ItemEnvelopeFactory.from(ITEM_ENVELOPE_JSON);

        assertEquals("12345", itemEnvelope.getItemId());
        assertEquals("OCR", itemEnvelope.getItemType().getDesc());
        assertEquals("55555", itemEnvelope.getUserId());
        assertEquals(0, itemEnvelope.getIdentifier().length());
        assertEquals(2, itemEnvelope.getActionList().size());
        assertEquals(1, itemEnvelope.getDetails().length());
    }
}