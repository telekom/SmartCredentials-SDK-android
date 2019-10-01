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

package de.telekom.smartcredentials.storage.database.models;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.repositories.RepositoryType;

public class ModelConverter {

    private ModelConverter() {
        // required empty constructor
    }

    public static ItemDomainModel generateItemDomainModel(Item item, String privateData, RepositoryType repositoryType) {
        if (item == null) {
            return null;
        }

        ItemDomainModel itemDomainModel = item.toDomainModel();

        ItemDomainMetadata itemDomainMetadata = itemDomainModel.getMetadata();
        if (repositoryType != null) {
            itemDomainMetadata.setContentType(repositoryType.getContentType());
        }

        ItemDomainData data = itemDomainModel.getData();
        data.setPrivateData(privateData);

        return itemDomainModel;
    }

    public static Item getItemSummary(ItemDomainModel itemDomainModel) {
        if (itemDomainModel == null) {
            return null;
        }

        Item item = new Item();
        item.setUid(itemDomainModel.getUid());
        if (itemDomainModel.getData() != null) {
            item.setIdentifier(itemDomainModel.getData().getIdentifier());
        }
        if (itemDomainModel.getMetadata() != null) {
            ItemDomainMetadata metadata = itemDomainModel.getMetadata();
            item.setType(metadata.getItemType());
            item.setUserId(metadata.getUserId());
            item.setSecuredData(metadata.isDataEncrypted());
            item.setAutoLockEnabled(metadata.isAutoLockEnabled());
            item.setLocked(metadata.isLocked());
            item.setActionList(metadata.getActionList());
        }
        return item;
    }

    public static ItemPrivateData getItemPrivateData(ItemDomainModel itemDomainModel) {
        if (itemDomainModel == null) {
            return null;
        }

        ItemPrivateData itemPrivateData = new ItemPrivateData(itemDomainModel.getUid());
        if (itemDomainModel.getData() != null) {
            itemPrivateData.setInfo(itemDomainModel.getData().getPrivateData());
        }
        return itemPrivateData;
    }

    public static JSONObject getItemJSONObject(ItemDomainModel itemDomainModel, Gson gson) throws JSONException {
        Item item = ModelConverter.getItemSummary(itemDomainModel);
        if (item == null) {
            return null;
        }

        String itemJson = gson.toJson(item, item.getClass());

        ItemPrivateData itemPrivateData = ModelConverter.getItemPrivateData(itemDomainModel);
        String itemPrivateDataJson = gson.toJson(itemPrivateData, itemPrivateData.getClass());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Item.DESCRIPTION, itemJson);
        jsonObject.put(ItemPrivateData.DESCRIPTION, itemPrivateDataJson);

        return jsonObject;
    }

    public static ItemDomainModel getItemDomainModelFromJSONObject(String json, Gson gson, RepositoryType repositoryType) throws JSONException {
        if (json == null) {
            return null;
        }

        JSONObject jsonObject = new JSONObject(json);

        Item item = gson.fromJson(jsonObject.getString(Item.DESCRIPTION), Item.class);
        ItemPrivateData itemPrivateData = gson.fromJson(jsonObject.getString(ItemPrivateData.DESCRIPTION), ItemPrivateData.class);

        return generateItemDomainModel(item, itemPrivateData.getInfo(), repositoryType);
    }

    public static ItemDomainModel getItemSummaryFromJSONObject(String json, Gson gson, RepositoryType repositoryType) throws JSONException {
        if (json == null) {
            return null;
        }

        JSONObject jsonObject = new JSONObject(json);
        Item item = gson.fromJson(jsonObject.getString(Item.DESCRIPTION), Item.class);

        return generateItemDomainModel(item, null, repositoryType);
    }

}
