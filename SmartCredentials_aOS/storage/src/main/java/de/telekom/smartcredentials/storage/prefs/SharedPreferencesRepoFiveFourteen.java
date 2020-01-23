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

package de.telekom.smartcredentials.storage.prefs;

import com.google.gson.Gson;

import org.json.JSONException;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.models.ModelConverter;

/**
 * Created by Alex.Graur@endava.com at 1/23/2020
 */
public class SharedPreferencesRepoFiveFourteen extends SharedPreferencesRepo {

    public SharedPreferencesRepoFiveFourteen(Gson gson, PreferencesManager preferencesManager) {
        super(gson, preferencesManager);
    }

    @Override
    public ItemDomainModel retrieveFilteredItemSummary(String uniqueKey) throws JSONException {
        return ModelConverter.getItemSummaryFromJSONObject(mPreferencesManager.getItem(uniqueKey),
                mGson, REPO_TYPE);
    }

    @Override
    public ItemDomainModel retrieveFilteredItemDetails(String uniqueKey) throws JSONException {
        return ModelConverter.getItemDomainModelFromJSONObject(mPreferencesManager.getItem(uniqueKey),
                mGson, REPO_TYPE);
    }
}
