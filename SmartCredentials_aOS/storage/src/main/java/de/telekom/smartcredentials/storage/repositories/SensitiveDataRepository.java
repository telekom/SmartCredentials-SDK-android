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

package de.telekom.smartcredentials.storage.repositories;

import org.json.JSONException;

import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.repositories.Repository;
import de.telekom.smartcredentials.storage.exceptions.RepositoryException;
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepo;

import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;
import static de.telekom.smartcredentials.core.model.ModelValidator.getValidatedMetadata;

class SensitiveDataRepository extends Repository {

    private static final String TAG = "SensitiveDataRepository";

    private final SharedPreferencesRepo mSharedPreferencesRepo;

    SensitiveDataRepository(SharedPreferencesRepo sharedPreferencesRepo) {
        mSharedPreferencesRepo = sharedPreferencesRepo;
    }

    @Override
    public int saveData(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<Integer>() {
            @Override
            Integer throwableMethod() throws JSONException {
                return mSharedPreferencesRepo.saveData(itemDomainModel);
            }
        }.transformException();
    }

    @Override
    public int updateItem(ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<Integer>() {
            @Override
            Integer throwableMethod() throws JSONException {
                return mSharedPreferencesRepo.updateItem(itemDomainModel);
            }
        }.transformException();
    }

    @Override
    public List<ItemDomainModel> retrieveItemsFilteredByType(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<List<ItemDomainModel>>() {
            @Override
            List<ItemDomainModel> throwableMethod() throws JSONException {
                ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);

                return mSharedPreferencesRepo.retrieveItemsFilteredByType(metadata);
            }
        }.transformException();
    }

    @Override
    public ItemDomainModel retrieveFilteredItemSummaryByUniqueIdAndType(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<ItemDomainModel>() {
            @Override
            ItemDomainModel throwableMethod() throws JSONException {
                checkParamNotNull(itemDomainModel);
                return mSharedPreferencesRepo.retrieveFilteredItemSummary(itemDomainModel.getUniqueKey());
            }
        }.transformException();
    }

    @Override
    public ItemDomainModel retrieveFilteredItemDetailsByUniqueIdAndType(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<ItemDomainModel>() {
            @Override
            ItemDomainModel throwableMethod() throws JSONException {
                checkParamNotNull(itemDomainModel);
                return mSharedPreferencesRepo.retrieveFilteredItemDetails(itemDomainModel.getUniqueKey());
            }
        }.transformException();
    }

    @Override
    public int deleteAllData() {
        return mSharedPreferencesRepo.deleteAllData();
    }

    @Override
    public int deleteItem(ItemDomainModel itemDomainModel) {
        return mSharedPreferencesRepo.deleteItem(itemDomainModel);
    }

    @Override
    public int deleteItemsByType(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<Integer>() {
            @Override
            Integer throwableMethod() throws JSONException {
                checkParamNotNull(itemDomainModel);
                return mSharedPreferencesRepo.deleteItemByType(itemDomainModel);
            }
        }.transformException();
    }

    @Override
    public String getTag() {
        return TAG;
    }

    private abstract class JSONExceptionResolver<T> {

        abstract T throwableMethod() throws JSONException;

        T transformException() {
            try {
                return throwableMethod();
            } catch (JSONException e) {
                throw new RepositoryException(e);
            }
        }
    }
}
