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

import java.util.List;

import de.telekom.smartcredentials.core.model.item.ContentType;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.repositories.Repository;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;

public class RepositoryProvider extends Repository {

    private static final String TAG = "RepositoryProvider";

    private final SensitiveDataRepository mSensitiveDataRepository;
    private final NonSensitiveDataRepository mNonSensitiveDataRepository;

    public RepositoryProvider(SensitiveDataRepository sensitiveDataRepository, NonSensitiveDataRepository nonSensitiveDataRepository) {
        mSensitiveDataRepository = sensitiveDataRepository;
        mNonSensitiveDataRepository = nonSensitiveDataRepository;
    }

    @Override
    public int saveData(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepository = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepository.getTag(), "putItem");

        return selectedRepository.saveData(itemDomainModel);
    }

    @Override
    public int updateItem(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepo = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepo.getTag(), "updateItem");

        return selectedRepo.updateItem(itemDomainModel);
    }

    @Override
    public List<ItemDomainModel> retrieveItemsFilteredByType(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepository = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepository.getTag(), "retrieveItemsFilteredByType");

        return selectedRepository.retrieveItemsFilteredByType(itemDomainModel);
    }

    @Override
    public ItemDomainModel retrieveFilteredItemSummaryByUniqueIdAndType(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepository = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepository.getTag(), "retrieveFilteredItemSummaryByUniqueIdAndType");

        return selectedRepository.retrieveFilteredItemSummaryByUniqueIdAndType(itemDomainModel);
    }

    @Override
    public ItemDomainModel retrieveFilteredItemDetailsByUniqueIdAndType(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepository = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepository.getTag(), "retrieveFilteredItemDetailsByUniqueIdAndType");

        return selectedRepository.retrieveFilteredItemDetailsByUniqueIdAndType(itemDomainModel);
    }

    @Override
    public int deleteAllData() {
        ApiLoggerResolver.logMethodAccess(mNonSensitiveDataRepository.getTag(), "deleteAllData");
        ApiLoggerResolver.logMethodAccess(mSensitiveDataRepository.getTag(), "deleteAllData");

        return mNonSensitiveDataRepository.deleteAllData()
                + mSensitiveDataRepository.deleteAllData();
    }

    @Override
    public int deleteItem(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepository = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepository.getTag(), "deleteItem");

        return selectedRepository.deleteItem(itemDomainModel);
    }

    @Override
    public int deleteItemsByType(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Repository selectedRepository = selectRepository(itemDomainModel.getMetadata());
        ApiLoggerResolver.logMethodAccess(selectedRepository.getTag(), "deleteItemsByType");

        return selectedRepository.deleteItemsByType(itemDomainModel);
    }

    @Override
    public String getTag() {
        return TAG;
    }

    private Repository selectRepository(ItemDomainMetadata metadata) {
        checkParamNotNull(metadata);

        ContentType contentType = metadata.getContentType();
        return contentType == ContentType.NON_SENSITIVE ? mNonSensitiveDataRepository : mSensitiveDataRepository;
    }
}
