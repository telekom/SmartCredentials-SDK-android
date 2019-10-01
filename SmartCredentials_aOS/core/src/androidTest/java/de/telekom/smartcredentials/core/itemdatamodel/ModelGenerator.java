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

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;

class ModelGenerator {

    static final         String   KEY_IDENTIFIER = "testKey";
    static final         String   KEY_DETAILS    = "testKeyDetails";
    private static final String   mId            = "12345";
    private static final String   mUserId        = "1234";
    private static final String   mType          = "voucher";
    private static final ItemType mItmType       = ItemTypeFactory.createNonSensitiveType(mType);

    static ItemEnvelope generateItemEnvelope() {
        return new ItemEnvelope()
                .setItemId(mId)
                .setUserId(mUserId)
                .setIdentifier(getJsonObjectSummary())
                .setItemType(mItmType)
                .setItemMetadata(new ItemMetadata(true)
                                         .setPrivateData(getPrivateData())
                                         .setActionList(getActionList()));
    }

    private static List<SmartCredentialsAction> getActionList() {
        List<SmartCredentialsAction> actions = new ArrayList<>();

        actions.add(new TestAction("1", "2", new JSONObject()));
        return actions;
    }

    private static JSONObject getJsonObjectSummary() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_IDENTIFIER, "testObject");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static ItemPrivateData getPrivateData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(KEY_DETAILS, "testObjectDetails");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ItemPrivateData(jsonObject);
    }

}
