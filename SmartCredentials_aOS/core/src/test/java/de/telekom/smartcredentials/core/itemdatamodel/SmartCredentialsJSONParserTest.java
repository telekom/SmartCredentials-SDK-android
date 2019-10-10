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
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.ModelGenerator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Lucian Iacob on December 17, 2018.
 */
@RunWith(PowerMockRunner.class)
public class SmartCredentialsJSONParserTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {

    }

    @Test
    public void parseConvertsJSONToItemEnvelope() throws JSONException {
        JSONObject jsonObject = new JSONObject(ModelGenerator.ITEM_ENVELOPE_JSON);
        SmartCredentialsJSONParser scJSONParser = new SmartCredentialsJSONParser(jsonObject);

        ItemEnvelope itemEnvelope = scJSONParser.parse();

        assertEquals("12345", itemEnvelope.getItemId());
        assertEquals("OCR", itemEnvelope.getItemType().getDesc());
        assertEquals("55555", itemEnvelope.getUserId());
        assertEquals(0, itemEnvelope.getIdentifier().length());
        assertEquals(2, itemEnvelope.getActionList().size());
        assertEquals(1, itemEnvelope.getDetails().length());
    }

    @Test
    public void parseThrowsJSONExceptionWhenOneItemEnvelopeKeyNotFound() throws JSONException {
        JSONObject jsonObject = new JSONObject(ModelGenerator.ITEM_ENVELOPE_JSON_NO_TYPE);
        SmartCredentialsJSONParser scJSONParser = new SmartCredentialsJSONParser(jsonObject);

        thrown.expect(JSONException.class);
        thrown.expectMessage(SmartCredentialsJSONParser.PREFIX + ItemEnvelope.KEY_TYPE +
                SmartCredentialsJSONParser.SUFFIX);

        scJSONParser.parse();
    }

    @Test
    public void parseThrowsJSONExceptionWhenOneItemMetadataKeyNotFound() throws JSONException {
        JSONObject jsonObject = new JSONObject(ModelGenerator.ITEM_ENVELOPE_JSON_NO_ACTIONS);
        SmartCredentialsJSONParser scJSONParser = new SmartCredentialsJSONParser(jsonObject);

        thrown.expect(JSONException.class);
        thrown.expectMessage(SmartCredentialsJSONParser.PREFIX + ItemEnvelope.KEY_ACTION_LIST +
                SmartCredentialsJSONParser.SUFFIX);

        scJSONParser.parse();
    }
}