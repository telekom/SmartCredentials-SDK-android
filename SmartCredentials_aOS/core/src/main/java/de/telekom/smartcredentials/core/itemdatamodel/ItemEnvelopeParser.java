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

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;

public class ItemEnvelopeParser {

    private final ItemEnvelope mItemEnvelope;

    public ItemEnvelopeParser(ItemEnvelope itemEnvelope) {
        mItemEnvelope = itemEnvelope;
    }

    public JSONObject generateJSON() throws JSONException {
        JSONObject json = new JSONObject();

        generateOuterLayer(json);
        generateInnerLayer(json);

        return json;
    }

    private void generateOuterLayer(@NonNull JSONObject json) throws JSONException {
        json.put(ItemEnvelope.KEY_ID, mItemEnvelope.getItemId());
        json.put(ItemEnvelope.KEY_TYPE, mItemEnvelope.getItemType().getDesc());
        json.put(ItemEnvelope.KEY_USER_ID, mItemEnvelope.getUserId());
        json.put(ItemEnvelope.KEY_IDENTIFIER, mItemEnvelope.getIdentifier());
    }

    private void generateInnerLayer(@NonNull JSONObject json) throws JSONException {
        JSONArray actionArray = new JSONArray();
        List<SmartCredentialsAction> actionList = mItemEnvelope.getItemMetadata().getActionList();
        for (int i = 0; i < actionList.size(); i++) {
            JSONObject action = new JSONObject();
            action.put(ItemEnvelope.KEY_ACTION_ID, actionList.get(i).getId());
            action.put(ItemEnvelope.KEY_ACTION_NAME, actionList.get(i).getName());
            action.put(ItemEnvelope.KEY_ACTION_DATA, actionList.get(i).getData());
            actionArray.put(action);
        }

        JSONObject privateData = mItemEnvelope.getItemMetadata().getDetails();
        if (privateData == null) {
            privateData = new JSONObject();
        }
        JSONObject metadata = new JSONObject();
        metadata.put(ItemEnvelope.KEY_ACTION_LIST, actionArray);
        metadata.put(ItemEnvelope.KEY_AUTOLOCK, mItemEnvelope.getItemMetadata().isAutoLockEnabled());
        metadata.put(ItemEnvelope.KEY_LOCK, mItemEnvelope.getItemMetadata().isLocked());
        metadata.put(ItemEnvelope.KEY_PRIVATE_DATA, privateData);

        json.put(ItemEnvelope.KEY_METADATA, metadata);
    }

}
