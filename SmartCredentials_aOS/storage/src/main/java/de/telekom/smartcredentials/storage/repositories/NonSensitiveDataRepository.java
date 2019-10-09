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

import android.database.sqlite.SQLiteConstraintException;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.repositories.Repository;
import de.telekom.smartcredentials.storage.database.dao.ItemDao;
import de.telekom.smartcredentials.storage.database.dao.ItemPrivateDataDao;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;
import de.telekom.smartcredentials.storage.database.models.ModelConverter;
import de.telekom.smartcredentials.storage.exceptions.RepositoryException;

import static de.telekom.smartcredentials.core.model.ModelValidator.checkParamNotNull;
import static de.telekom.smartcredentials.core.model.ModelValidator.getValidatedMetadata;

public class NonSensitiveDataRepository extends Repository {

    static final RepositoryType REPO_TYPE = RepositoryType.NONSENSITIVE;
    private static final String TAG = "NonSensitiveDataRepository";

    private final ItemDao mItemsDao;
    private final ItemPrivateDataDao mItemPrivateDataDao;

    NonSensitiveDataRepository(ItemDao itemDao, ItemPrivateDataDao itemPrivateDataDao) {
        mItemsDao = itemDao;
        mItemPrivateDataDao = itemPrivateDataDao;
    }

    @Override
    public int saveData(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        try {
            Item item = ModelConverter.getItemSummary(itemDomainModel);
            ItemPrivateData itemPrivateData = ModelConverter.getItemPrivateData(itemDomainModel);

            if (item != null && itemPrivateData != null) {
                long privateDataRowId = mItemPrivateDataDao.insert(itemPrivateData);
                long itemRowId = mItemsDao.insert(item);
                return privateDataRowId > -1 && itemRowId > -1 ? 1 : 0;
            }
            return 0;
        } catch (SQLiteConstraintException ex) {
            throw new RepositoryException(NonSensitiveDataRepository.class.getSimpleName() + ex.getMessage(), ex);
        }
    }

    @Override
    public int updateItem(ItemDomainModel itemDomainModel) {
        Item item = ModelConverter.getItemSummary(itemDomainModel);
        ItemPrivateData itemPrivateData = ModelConverter.getItemPrivateData(itemDomainModel);

        mItemPrivateDataDao.update(itemPrivateData);
        return mItemsDao.update(item);
    }

    @Override
    public List<ItemDomainModel> retrieveItemsFilteredByType(ItemDomainModel itemDomainModel) {
        ItemDomainMetadata metadata = getValidatedMetadata(itemDomainModel);

        List<Item> items = mItemsDao.getItemsByType(metadata.getUserId(), metadata.getItemType());
        List<ItemDomainModel> domainModels = new ArrayList<>();
        for (Item item : items) {
            domainModels.add(ModelConverter.generateItemDomainModel(item, null, REPO_TYPE));
        }
        return domainModels;
    }

    @Override
    public ItemDomainModel retrieveFilteredItemSummaryByUniqueIdAndType(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        Item itemSummary = mItemsDao.getItemByIdAndType(itemDomainModel.getUid(), itemDomainModel.getMetadata().getItemType(), itemDomainModel.getMetadata().getUserId());
        return ModelConverter.generateItemDomainModel(itemSummary, null, REPO_TYPE);
    }

    @Override
    public ItemDomainModel retrieveFilteredItemDetailsByUniqueIdAndType(ItemDomainModel domainModel) {
        checkParamNotNull(domainModel);

        Item item = mItemsDao.getItemByIdAndType(domainModel.getUid(), domainModel.getMetadata().getItemType(), domainModel.getMetadata().getUserId());
        String privateData = mItemPrivateDataDao.getPrivateData(domainModel.getUid());

        return ModelConverter.generateItemDomainModel(item, privateData, REPO_TYPE);
    }

    @Override
    public int deleteAllData() {
        mItemPrivateDataDao.deleteAllData();
        return mItemsDao.deleteAllData();
    }

    @Override
    public int deleteItem(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        ItemPrivateData privateData = ModelConverter.getItemPrivateData(itemDomainModel);
        if (privateData != null) {
            mItemPrivateDataDao.delete(privateData);
        }

        Item item = ModelConverter.getItemSummary(itemDomainModel);
        if (item != null) {
            return mItemsDao.deleteItemByUidAndType(item.getUid(), item.getType(), item.getUserId());
        }
        return 0;
    }

    @Override
    public int deleteItemsByType(ItemDomainModel itemDomainModel) {
        checkParamNotNull(itemDomainModel);

        String type = itemDomainModel.getMetadata().getItemType();
        if (TextUtils.isEmpty(type)) {
            return 0;
        }

        List<Item> itemList = mItemsDao.getItemsByType(itemDomainModel.getMetadata().getUserId(), type);
        int deletedItemsCount = 0;
        for (Item item : itemList) {
            String uid = item.getUid();
            deletedItemsCount += mItemsDao.deleteItemByUidAndType(uid, item.getType(), item.getUserId());
            mItemPrivateDataDao.delete(new ItemPrivateData(item.getUid()));
        }
        return deletedItemsCount;
    }

    @Override
    public String getTag() {
        return TAG;
    }
}
