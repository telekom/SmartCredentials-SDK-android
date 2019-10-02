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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.utils.ModelGenerator;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class RepositoryProviderTest {

    private RepositoryProvider mRepositoryProvider;
    private ItemDomainModel mNonSensitiveItemDomainModel;
    private ItemDomainModel mSensitiveItemDomainModel;
    private SensitiveDataRepository mSensitiveRepo;
    private NonSensitiveDataRepository mNonSensitiveRepo;

    @Before
    public void setUp() {
        mSensitiveRepo = Mockito.mock(SensitiveDataRepository.class);
        mNonSensitiveRepo = Mockito.mock(NonSensitiveDataRepository.class);

        mRepositoryProvider = new RepositoryProvider(mSensitiveRepo, mNonSensitiveRepo);

        mNonSensitiveItemDomainModel = ModelGenerator.generateEncryptedNonSensitiveItemDomainModel();
        mSensitiveItemDomainModel = ModelGenerator.generateEncryptedSensitiveItemDomainModel();
    }

    @Test
    public void saveDataCallsSaveDataOnNonSensitiveRepositoryWhenNonSensitiveData() {
        mRepositoryProvider.saveData(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).saveData(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).saveData(mNonSensitiveItemDomainModel);
    }

    @Test
    public void saveDataCallsSaveDataOnSensitiveRepositoryWhenSensitiveData() {
        mRepositoryProvider.saveData(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).saveData(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).saveData(mSensitiveItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemsCallsRetrieveFilteredItemsOnNonSensitiveRepositoryWhenNonSensitiveData() {
        mRepositoryProvider.retrieveItemsFilteredByType(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).retrieveItemsFilteredByType(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).retrieveItemsFilteredByType(mNonSensitiveItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemsCallsRetrieveFilteredItemsOnSensitiveRepositoryWhenSensitiveData() {
        mRepositoryProvider.retrieveItemsFilteredByType(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).retrieveItemsFilteredByType(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).retrieveItemsFilteredByType(mSensitiveItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemSummaryCallsRetrieveFilteredItemSummaryOnNonSensitiveRepositoryWhenNonSensitiveData() {
        mRepositoryProvider.retrieveFilteredItemSummaryByUniqueIdAndType(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).retrieveFilteredItemSummaryByUniqueIdAndType(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).retrieveFilteredItemSummaryByUniqueIdAndType(mNonSensitiveItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemSummaryCallsRetrieveFilteredItemSummaryOnSensitiveRepositoryWhenSensitiveData() {
        mRepositoryProvider.retrieveFilteredItemSummaryByUniqueIdAndType(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).retrieveFilteredItemSummaryByUniqueIdAndType(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).retrieveFilteredItemSummaryByUniqueIdAndType(mSensitiveItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemDetailsCallsRetrieveFilteredItemDetailsOnNonSensitiveRepositoryWhenNonSensitiveData() {
        mRepositoryProvider.retrieveFilteredItemDetailsByUniqueIdAndType(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).retrieveFilteredItemDetailsByUniqueIdAndType(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).retrieveFilteredItemDetailsByUniqueIdAndType(mNonSensitiveItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemDetailsCallsRetrieveFilteredItemDetailsOnSensitiveRepositoryWhenSensitiveData() {
        mRepositoryProvider.retrieveFilteredItemDetailsByUniqueIdAndType(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).retrieveFilteredItemDetailsByUniqueIdAndType(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).retrieveFilteredItemDetailsByUniqueIdAndType(mSensitiveItemDomainModel);
    }

    @Test
    public void deleteAllDataCallsDeleteAllDataOnBothNonSensitiveAndSensitiveRepository() {
        mRepositoryProvider.deleteAllData();
        verify(mNonSensitiveRepo).deleteAllData();
        verify(mSensitiveRepo).deleteAllData();
    }

    @Test
    public void deleteItemCallsDeleteItemOnNonSensitiveRepositoryWhenNonSensitiveData() {
        mRepositoryProvider.deleteItem(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).deleteItem(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).deleteItem(mNonSensitiveItemDomainModel);
    }

    @Test
    public void deleteItemCallsDeleteItemOnSensitiveRepositoryWhenSensitiveData() {
        mRepositoryProvider.deleteItem(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).deleteItem(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).deleteItem(mSensitiveItemDomainModel);
    }

    @Test
    public void deleteItemsByTypeCallsDeleteItemOnNonSensitiveRepositoryWhenNonSensitiveData() {
        mRepositoryProvider.deleteItemsByType(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).deleteItemsByType(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).deleteItemsByType(mNonSensitiveItemDomainModel);
    }

    @Test
    public void deleteItemsByTypeCallsDeleteItemOnSensitiveRepositoryWhenSensitiveData() {
        mRepositoryProvider.deleteItemsByType(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).deleteItemsByType(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).deleteItemsByType(mSensitiveItemDomainModel);
    }

    @Test
    public void updateItemCallsUpdateItemOnNonSensitiveRepositoryWhenNonSensitiveItem() {
        mRepositoryProvider.updateItem(mNonSensitiveItemDomainModel);
        verify(mNonSensitiveRepo).updateItem(mNonSensitiveItemDomainModel);
        verify(mSensitiveRepo, never()).saveData(mNonSensitiveItemDomainModel);
    }

    @Test
    public void updateItemCallsUpdateItemOnSensitiveRepositoryWhenSensitiveItem() {
        mRepositoryProvider.updateItem(mSensitiveItemDomainModel);
        verify(mNonSensitiveRepo, never()).updateItem(mSensitiveItemDomainModel);
        verify(mSensitiveRepo).updateItem(mSensitiveItemDomainModel);
    }
}