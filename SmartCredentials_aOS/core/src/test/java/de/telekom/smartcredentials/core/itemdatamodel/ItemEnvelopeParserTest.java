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
import org.junit.Test;

import de.telekom.smartcredentials.core.ModelGenerator;

import static junit.framework.Assert.assertNotNull;

public class ItemEnvelopeParserTest {

    @Test
    public void checkJsonFromItemIsNotNull() throws JSONException {
        ItemEnvelope itemEnvelope = ModelGenerator.generateItemEnvelope();
        ItemEnvelopeParser parser = new ItemEnvelopeParser(itemEnvelope);
        JSONObject jsonItem = parser.generateJSON();

        assertNotNull(jsonItem);
    }

}