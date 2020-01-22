/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.storage.database.models.migration.fourfulltwo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;
import de.telekom.smartcredentials.storage.database.models.ModelConverter;
import de.telekom.smartcredentials.storage.repositories.RepositoryType;

/**
 * Created by Alex.Graur@endava.com at 1/22/2020
 */
public class ModelConverterFourTwo {

    private static final String ITEM_DESCRIPTION = "a";
    private static final String ITEM_PRIVATE_DATA_DESCRIPTION = "a";

    public static ItemDomainModel getItemSummaryFromJSONObject(String json, RepositoryType repositoryType) throws JSONException {
        if (json == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(json);
        String aString = jsonObject.getString(ITEM_DESCRIPTION);
        return ModelConverter.generateItemDomainModel(generateItem(aString), null, repositoryType);
    }

    public static ItemDomainModel getItemDomainModelFromJSONObject(String json, RepositoryType repositoryType) throws JSONException {
        if (json == null) {
            return null;
        }

        JSONObject jsonObject = new JSONObject(json);
        String aString = jsonObject.getString(ITEM_DESCRIPTION);
        String bString = jsonObject.getString(ITEM_PRIVATE_DATA_DESCRIPTION);
        return ModelConverter.generateItemDomainModel(generateItem(aString),
                generateItemPrivateData(bString).getInfo(), repositoryType);
    }

    private static Item generateItem(String json) throws JSONException {
        JSONObject jsonObjectA = new JSONObject(json);
        Item item = new Item();
        item.setActionList(new ArrayList<>());
        item.setAutoLockEnabled(false);
        item.setLocked(false);
        item.setSecuredData(true);
        item.setType(jsonObjectA.getString(Item.TYPE_NAME));
        item.setUserId(jsonObjectA.getString(Item.USER_ID_NAME));
        item.setIdentifier(jsonObjectA.getString(Item.IDENTIFIER_NAME));
        item.setUid(jsonObjectA.getString(Item.UID_NAME));
        return item;
    }

    private static ItemPrivateData generateItemPrivateData(String json) throws JSONException {
        JSONObject jsonObjectB = new JSONObject(json);
        ItemPrivateData itemPrivateData = new ItemPrivateData(jsonObjectB.getString(ItemPrivateData.UID_NAME));
        itemPrivateData.setInfo(jsonObjectB.getString(ItemPrivateData.INFO_NAME));
        return itemPrivateData;
    }
}
