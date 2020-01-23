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
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepoFiveFourteen;
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepoFourTwo;

import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;
import static de.telekom.smartcredentials.core.model.ModelValidator.getValidatedMetadata;

class SensitiveDataRepository implements Repository {

    private static final String TAG = "SensitiveDataRepository";

    private final SharedPreferencesRepoFiveFourteen mSharedPreferencesRepoFiveFourteen;
    private final SharedPreferencesRepoFourTwo mSharedPreferencesRepoFourTwo;

    SensitiveDataRepository(SharedPreferencesRepoFiveFourteen sharedPreferencesRepoFiveFourteen,
                            SharedPreferencesRepoFourTwo sharedPreferencesRepoFourTwo) {
        mSharedPreferencesRepoFiveFourteen = sharedPreferencesRepoFiveFourteen;
        mSharedPreferencesRepoFourTwo = sharedPreferencesRepoFourTwo;
    }

    @Override
    public int saveData(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<Integer>() {
            @Override
            Integer throwableMethod() throws JSONException {
                return mSharedPreferencesRepoFiveFourteen.saveData(itemDomainModel);
            }
        }.transformException();
    }

    @Override
    public int updateItem(ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<Integer>() {
            @Override
            Integer throwableMethod() throws JSONException {
                return mSharedPreferencesRepoFiveFourteen.updateItem(itemDomainModel);
            }
        }.transformException();
    }

    @Override
    public List<ItemDomainModel> retrieveItemsFilteredByType(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<List<ItemDomainModel>>() {
            @Override
            List<ItemDomainModel> throwableMethod() throws JSONException {
                ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);

                return mSharedPreferencesRepoFiveFourteen.retrieveItemsFilteredByType(metadata);
            }
        }.transformException();
    }

    @Override
    public ItemDomainModel retrieveFilteredItemSummaryByUniqueIdAndType(final ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);
        try {
            return mSharedPreferencesRepoFiveFourteen.retrieveFilteredItemSummary(itemDomainModel.getUniqueKey());
        } catch (JSONException e) {
            try {
                return mSharedPreferencesRepoFourTwo.retrieveFilteredItemSummary(itemDomainModel.getUniqueKey());
            } catch (JSONException ex) {
                throw new RepositoryException(e);
            }
        }
    }

    @Override
    public ItemDomainModel retrieveFilteredItemDetailsByUniqueIdAndType(final ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);
        try {
            return mSharedPreferencesRepoFiveFourteen.retrieveFilteredItemDetails(itemDomainModel.getUniqueKey());
        } catch (JSONException e) {
            try {
                return mSharedPreferencesRepoFourTwo.retrieveFilteredItemDetails(itemDomainModel.getUniqueKey());
            } catch (JSONException ex) {
                throw new RepositoryException(e);
            }
        }
    }

    @Override
    public int deleteAllData() {
        return mSharedPreferencesRepoFiveFourteen.deleteAllData();
    }

    @Override
    public int deleteItem(ItemDomainModel itemDomainModel) {
        return mSharedPreferencesRepoFiveFourteen.deleteItem(itemDomainModel);
    }

    @Override
    public int deleteItemsByType(final ItemDomainModel itemDomainModel) {
        return new JSONExceptionResolver<Integer>() {
            @Override
            Integer throwableMethod() throws JSONException {
                checkParamNotNull(itemDomainModel);
                return mSharedPreferencesRepoFiveFourteen.deleteItemByType(itemDomainModel);
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
