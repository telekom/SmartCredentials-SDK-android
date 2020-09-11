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

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * Created by Lucian Iacob on December 14, 2018.
 */
class SmartCredentialsJSONParser {

    static final String PREFIX = "Mapping with key <";
    static final String SUFFIX = "> was not found. Cannot build Item Envelope.";
    private static final String TAG = "SmartCredentialsJSONParser";
    private static final String MAPPING_NOT_FOUND = PREFIX + SUFFIX;
    private static final String[] ITEM_ENVELOPE_KEYS = new String[]{
            ItemEnvelope.KEY_ID,
            ItemEnvelope.KEY_TYPE,
            ItemEnvelope.KEY_USER_ID,
            ItemEnvelope.KEY_IDENTIFIER,
            ItemEnvelope.KEY_METADATA
    };
    private static final String[] ITEM_METADATA_KEYS = new String[]{
            ItemEnvelope.KEY_ACTION_LIST,
            ItemEnvelope.KEY_AUTOLOCK,
            ItemEnvelope.KEY_LOCK,
            ItemEnvelope.KEY_PRIVATE_DATA
    };
    private static final String[] SC_ACTION_KEYS = new String[]{
            ItemEnvelope.KEY_ACTION_ID,
            ItemEnvelope.KEY_ACTION_NAME,
            ItemEnvelope.KEY_ACTION_DATA
    };

    private final JSONObject mJSONObject;

    SmartCredentialsJSONParser(JSONObject jsonObject) {
        mJSONObject = jsonObject;
    }

    ItemEnvelope parse() throws JSONException {
        ItemEnvelope itemEnvelope = new ItemEnvelope();
        parseOuterLayer(itemEnvelope);
        parseInnerLayer(itemEnvelope);
        return itemEnvelope;
    }

    private void parseOuterLayer(@NonNull ItemEnvelope itemEnvelope) throws JSONException {
        validateKeys(ITEM_ENVELOPE_KEYS, mJSONObject);

        itemEnvelope.setItemId(mJSONObject.getString(ItemEnvelope.KEY_ID));
        itemEnvelope.setUserId(mJSONObject.getString(ItemEnvelope.KEY_USER_ID));
        itemEnvelope.setItemType(ItemTypeFactory.createSensitiveType(mJSONObject.getString(ItemEnvelope.KEY_TYPE)));
        itemEnvelope.setIdentifier(mJSONObject.getJSONObject(ItemEnvelope.KEY_IDENTIFIER));
    }

    private void parseInnerLayer(@NonNull ItemEnvelope itemEnvelope) throws JSONException {
        ItemMetadata itemMetadata = new ItemMetadata(false);
        JSONObject jsonMetadata = mJSONObject.getJSONObject(ItemEnvelope.KEY_METADATA);

        validateKeys(ITEM_METADATA_KEYS, jsonMetadata);

        itemMetadata.setAutoLockState(jsonMetadata.getBoolean(ItemEnvelope.KEY_AUTOLOCK));
        itemMetadata.setLockState(jsonMetadata.getBoolean(ItemEnvelope.KEY_LOCK));

        JSONArray actionsArray = jsonMetadata.getJSONArray(ItemEnvelope.KEY_ACTION_LIST);
        for (int i = 0; i < actionsArray.length(); i++) {
            JSONObject jsonAction = actionsArray.getJSONObject(i);
            SmartCredentialsAction action = instantiateActionFromJson(jsonAction);
            if (action != null) {
                itemMetadata.addAction(action);
            }
        }

        JSONObject privateData = jsonMetadata.getJSONObject(ItemEnvelope.KEY_PRIVATE_DATA);
        itemMetadata.setPrivateData(new ItemPrivateData(privateData));

        itemEnvelope.setItemMetadata(itemMetadata);
    }

    private SmartCredentialsAction instantiateActionFromJson(JSONObject jsonAction) throws JSONException {
        validateKeys(SC_ACTION_KEYS, jsonAction);

        String className = jsonAction.getString(ItemEnvelope.KEY_ACTION_NAME);

        try {
            Class<?> actionClass = Class.forName(className);
            SmartCredentialsAction smartCredentialsAction = (SmartCredentialsAction) actionClass.newInstance();
            smartCredentialsAction.setId(jsonAction.getString(ItemEnvelope.KEY_ACTION_ID));
            smartCredentialsAction.setName(className);
            smartCredentialsAction.setData(jsonAction.getJSONObject(ItemEnvelope.KEY_ACTION_DATA));
            return smartCredentialsAction;
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            ApiLoggerResolver.logError(TAG, e.getMessage());
            return null;
        }
    }

    private void validateKeys(String[] keysWanted, JSONObject jsonObject) throws JSONException {
        for (String key : keysWanted) {
            if (!jsonObject.has(key)) {
                StringBuilder builder = new StringBuilder(MAPPING_NOT_FOUND);
                builder.insert(PREFIX.length(), key);
                throw new JSONException(builder.toString());
            }
        }
    }

}
