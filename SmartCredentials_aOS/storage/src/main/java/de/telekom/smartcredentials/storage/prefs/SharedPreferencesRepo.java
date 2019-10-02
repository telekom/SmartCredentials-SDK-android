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

package de.telekom.smartcredentials.storage.prefs;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.storage.database.models.ModelConverter;
import de.telekom.smartcredentials.storage.repositories.RepositoryType;

import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;
import static de.telekom.smartcredentials.core.model.ModelValidator.getValidatedMetadata;

public class SharedPreferencesRepo {

    private static final String TAG = "SharedPreferencesRepo";
    private static final String ERR_CONVERTING_TO_JSON = "converting item domain model to JSON Object returns null";
    private static final RepositoryType REPO_TYPE = RepositoryType.SENSITIVE;

    private final Gson mGson;
    private final PreferencesManager mPreferencesManager;

    public SharedPreferencesRepo(Gson gson, PreferencesManager preferencesManager) {
        mGson = gson;
        mPreferencesManager = preferencesManager;
    }

    public int saveData(ItemDomainModel itemDomainModel) throws JSONException {
        checkParamNotNull(itemDomainModel);

        JSONObject jsonObject = ModelConverter.getItemJSONObject(itemDomainModel, mGson);
        if (jsonObject == null) {
            ApiLoggerResolver.logError(TAG, ERR_CONVERTING_TO_JSON);
            return 0;
        }

        String data = jsonObject.toString();
        mPreferencesManager.save(itemDomainModel.getUniqueKey(), data);

        return Objects.equals(mPreferencesManager.getItem(itemDomainModel.getUniqueKey()), data) ? 1 : 0;
    }

    public int updateItem(ItemDomainModel itemDomainModel) throws JSONException {
        JSONObject jsonObject = ModelConverter.getItemJSONObject(itemDomainModel, mGson);
        if (jsonObject == null) {
            ApiLoggerResolver.logError(TAG, ERR_CONVERTING_TO_JSON);
            return 0;
        }
        String data = jsonObject.toString();
        mPreferencesManager.update(itemDomainModel.getUniqueKey(), data);

        return Objects.equals(mPreferencesManager.getItem(itemDomainModel.getUniqueKey()), data) ? 1 : 0;
    }

    public List<ItemDomainModel> retrieveItemsFilteredByType(ItemDomainMetadata metadata) throws JSONException {
        List<ItemDomainModel> itemDomainModelList = new ArrayList<>();
        for (String item : mPreferencesManager.getItemsMatchingType(metadata.getUniqueKeyPrefix())) {
            itemDomainModelList.add(ModelConverter.getItemDomainModelFromJSONObject(item, mGson, REPO_TYPE));
        }
        return itemDomainModelList;
    }

    public ItemDomainModel retrieveFilteredItemSummary(String uniqueKey) throws JSONException {
        return ModelConverter.getItemSummaryFromJSONObject(mPreferencesManager.getItem(uniqueKey), mGson, REPO_TYPE);
    }

    public ItemDomainModel retrieveFilteredItemDetails(String uniqueKey) throws JSONException {
        return ModelConverter.getItemDomainModelFromJSONObject(mPreferencesManager.getItem(uniqueKey), mGson, REPO_TYPE);
    }

    public int deleteAllData() {
        int itemsCountBeforeDelete = mPreferencesManager.getItemsCount();
        mPreferencesManager.clearAll();
        return itemsCountBeforeDelete - mPreferencesManager.getItemsCount();
    }

    public int deleteItem(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        int itemsCountBeforeDelete = mPreferencesManager.getItemsCount();
        mPreferencesManager.remove(itemDomainModel.getUniqueKey());
        return itemsCountBeforeDelete - mPreferencesManager.getItemsCount();
    }

    public int deleteItemByType(ItemDomainModel itemDomainModel) throws JSONException {
        ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);

        int itemsCountBeforeDelete = mPreferencesManager.getItemsCount();
        List<ItemDomainModel> itemDomainModelList = retrieveItemsFilteredByType(metadata);
        for (ItemDomainModel itemDomainModel1 : itemDomainModelList) {
            deleteItem(itemDomainModel1);
        }
        return itemsCountBeforeDelete - mPreferencesManager.getItemsCount();
    }
}
