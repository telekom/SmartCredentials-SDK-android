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

package de.telekom.smartcredentials.core;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemMetadata;
import de.telekom.smartcredentials.core.itemdatamodel.ItemPrivateData;
import de.telekom.smartcredentials.core.itemdatamodel.ItemType;
import de.telekom.smartcredentials.core.itemdatamodel.ItemTypeFactory;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

public class ModelGenerator {

    public static final String ITEM_ENVELOPE_JSON_BROKEN_USER_ID = "{" +
            "\"id\":\"12345\"," +
            "\"type\":\"OCR\"," +
            "\"userId\":\"55555\"," +
            "\"identifier\":{}," +
            "\"metadata\":{  " +
            "\"auto_lock\":false," +
            "\"lock\":false," +
            "\"action_list\":[  " +
            "         {  " +
            "         \"action_id\":\"1\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }," +
            "         {  " +
            "         \"action_id\":\"2\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }" +
            "      ]," +
            "   \"private_data\":{\"key\":\"value\"}" +
            "   }" +
            "}";
    public static final String ITEM_ENVELOPE_JSON = "{" +
            "\"id\":\"12345\"," +
            "\"type\":\"OCR\"," +
            "\"user_id\":\"55555\"," +
            "\"identifier\":{}," +
            "\"metadata\":{  " +
            "\"auto_lock\":false," +
            "\"lock\":false," +
            "\"action_list\":[  " +
            "         {  " +
            "         \"action_id\":\"1\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }," +
            "         {  " +
            "         \"action_id\":\"2\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }" +
            "      ]," +
            "   \"private_data\":{\"key\":\"value\"}" +
            "   }" +
            "}";
    public static final String ITEM_ENVELOPE_JSON_NO_TYPE = "{" +
            "\"id\":\"12345\"," +
            "\"userId\":\"55555\"," +
            "\"identifier\":{}," +
            "\"metadata\":{  " +
            "\"auto_lock\":false," +
            "\"lock\":false," +
            "\"action_list\":[  " +
            "         {  " +
            "         \"action_id\":\"1\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }," +
            "         {  " +
            "         \"action_id\":\"2\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }" +
            "      ]," +
            "   \"private_data\":{\"key\":\"value\"}" +
            "   }" +
            "}";
    public static final String ITEM_ENVELOPE_JSON_NO_ACTIONS = "{" +
            "\"id\":\"12345\"," +
            "\"user_id\":\"55555\"," +
            "\"type\":\"OCR\"," +
            "\"identifier\":{}," +
            "\"metadata\":{  " +
            "\"auto_lock\":false," +
            "\"lock\":false," +
            "\"private_data\":{\"key\":\"value\"}" +
            "   }" +
            "}";
    public static final String ITEM_ENVELOPE_JSON_ACTION_BROKEN = "{" +
            "\"id\":\"12345\"," +
            "\"type\":\"OCR\"," +
            "\"user_id\":\"55555\"," +
            "\"identifier\":{}," +
            "\"metadata\":{  " +
            "\"auto_lock\":false," +
            "\"lock\":false," +
            "\"action_list\":[  " +
            "         {  " +
            "         \"action_id\":\"1\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.Brokeeen\"," +
            "         \"action_data\":{}" +
            "         }," +
            "         {  " +
            "         \"action_id\":\"2\"," +
            "         \"module_name\":\"de.telekom.smartcredentials.core.itemdatamodel.DummyAction\"," +
            "         \"action_data\":{}" +
            "         }" +
            "      ]," +
            "   \"private_data\":{\"key\":\"value\"}" +
            "   }" +
            "}";
    private static final String mId = "12345";
    private static final String mUserId = "1234";
    private static final String mType = "voucher";
    public static final ItemType mItmType = ItemTypeFactory.createNonSensitiveType(mType);

    public static ItemEnvelope generateItemEnvelope() {
        return new ItemEnvelope()
                .setItemId(mId)
                .setUserId(mUserId)
                .setIdentifier(getJsonObjectSummary())
                .setItemType(mItmType)
                .setItemMetadata(new ItemMetadata(true).setPrivateData(getItemPrivateData()));
    }

    public static ItemEnvelope generateItemEnvelopeWithNoMetadata() {
        return new ItemEnvelope()
                .setItemId(mId)
                .setUserId(mUserId)
                .setIdentifier(getJsonObjectSummary())
                .setItemType(mItmType);
    }

    public static ItemEnvelope generateItemEnvelopeWithoutItemType() {
        ItemMetadata metadata = new ItemMetadata(true)
                .setPrivateData(getItemPrivateData());
        return new ItemEnvelope()
                .setItemId(mId)
                .setUserId(mUserId)
                .setIdentifier(getJsonObjectSummary())
                .setItemMetadata(metadata);
    }

    public static ItemEnvelope generateItemEnvelopeWithEmptyDetailsAndIdentifier() {
        return new ItemEnvelope()
                .setItemId(mId)
                .setUserId(mUserId)
                .setItemType(mItmType)
                .setItemMetadata(new ItemMetadata(true));
    }

    public static SmartCredentialsResponse getSuccessfulResponse(String data) {
        return new SmartCredentialsResponse<>(data);
    }

    public static SmartCredentialsResponse getUnsuccessfulResponse() {
        return new SmartCredentialsResponse<>(new RuntimeException());
    }

    private static JSONObject getJsonObjectSummary() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("testKey", "testObject");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private static ItemPrivateData getItemPrivateData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("testKeyDetails", "testObjectDetails");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ItemPrivateData(jsonObject);
    }
}
