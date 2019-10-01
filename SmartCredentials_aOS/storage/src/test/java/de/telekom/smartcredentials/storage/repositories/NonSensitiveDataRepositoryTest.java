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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.dao.ItemDao;
import de.telekom.smartcredentials.storage.database.dao.ItemPrivateDataDao;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;
import de.telekom.smartcredentials.storage.database.models.ModelConverter;
import de.telekom.smartcredentials.storage.exceptions.RepositoryException;
import de.telekom.smartcredentials.storage.utils.ModelComparator;
import de.telekom.smartcredentials.storage.utils.ModelGenerator;

import static de.telekom.smartcredentials.core.model.ModelValidator.getValidatedMetadata;
import static de.telekom.smartcredentials.storage.utils.ModelGenerator.PRIVATE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({TextUtils.class, ModelConverter.class})
public class NonSensitiveDataRepositoryTest {

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();
    private static final RepositoryType REPO_TYPE = NonSensitiveDataRepository.REPO_TYPE;
    private ItemDao mItemDao;
    private ItemPrivateDataDao mItemPrivateDataDao;
    private NonSensitiveDataRepository mNonSensitiveDataRepository;
    private ItemDomainModel mItemDomainModel;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
        mItemDao = Mockito.mock(ItemDao.class);
        mItemPrivateDataDao = Mockito.mock(ItemPrivateDataDao.class);
        mItemDomainModel = ModelGenerator.generateNonEncryptedNonSensitiveItemDomainModel();

        mNonSensitiveDataRepository = new NonSensitiveDataRepository(mItemDao, mItemPrivateDataDao);
    }

    @Test
    public void saveDataCallsMethodsOnDaoObjects() {
        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        ArgumentCaptor<ItemPrivateData> itemPrivateDataArgumentCaptor =
                ArgumentCaptor.forClass(ItemPrivateData.class);

        mNonSensitiveDataRepository.saveData(mItemDomainModel);

        verify(mItemDao).insert(itemArgumentCaptor.capture());
        verify(mItemPrivateDataDao).insert(itemPrivateDataArgumentCaptor.capture());
    }

    @Test
    public void saveDataThrowsRepoExceptionWhenConstraintException() {
        doThrow(new SQLiteConstraintException()).when(mItemDao).insert(any());
        mExpectedException.expect(RepositoryException.class);
        mNonSensitiveDataRepository.saveData(mItemDomainModel);
    }

    @Test
    public void saveDataCallsNoMethodOnDaoObjectsWhenItemOrPrivateDataAreNull() {
        PowerMockito.mockStatic(ModelConverter.class);

        mNonSensitiveDataRepository.saveData(mItemDomainModel);

        verify(mItemDao, never()).insert(any());
        verify(mItemPrivateDataDao, never()).insert(any());
    }

    @Test
    public void saveDataReturnsZeroWhenItemCountNotBeConverted() {
        PowerMockito.mockStatic(ModelConverter.class);
        when(ModelConverter.getItemSummary(mItemDomainModel)).thenReturn(null);

        int count = mNonSensitiveDataRepository.saveData(mItemDomainModel);

        assertEquals(count, 0);
    }

    @Test
    public void saveDataReturnsZeroWhenItemPrivateDataCountNotBeConverted() {
        PowerMockito.mockStatic(ModelConverter.class);
        when(ModelConverter.getItemSummary(mItemDomainModel)).thenReturn(new Item());
        when(ModelConverter.getItemPrivateData(mItemDomainModel)).thenReturn(null);

        int count = mNonSensitiveDataRepository.saveData(mItemDomainModel);

        assertEquals(count, 0);
    }

    @Test
    public void saveDataReturnsZeroWhenItemIsNotSaved() {
        PowerMockito.mockStatic(ModelConverter.class);
        Item item = new Item();
        when(ModelConverter.getItemSummary(mItemDomainModel)).thenReturn(item);
        when(ModelConverter.getItemPrivateData(mItemDomainModel)).thenReturn(new ItemPrivateData(item.getUid()));
        when(mItemDao.insert(item)).thenReturn((long) -1);

        int count = mNonSensitiveDataRepository.saveData(mItemDomainModel);

        assertEquals(count, 0);
    }

    @Test
    public void saveDataReturnsNumberOfItemsInserted() {
        PowerMockito.mockStatic(ModelConverter.class);
        Item item = new Item();
        when(ModelConverter.getItemSummary(mItemDomainModel)).thenReturn(item);
        when(ModelConverter.getItemPrivateData(mItemDomainModel)).thenReturn(new ItemPrivateData(item.getUid()));
        long ids = 12345;
        when(mItemDao.insert(item)).thenReturn(ids);

        int count = mNonSensitiveDataRepository.saveData(mItemDomainModel);

        assertEquals(count, 1);
    }

    @Test
    public void retrieveFilteredItemsCallsMethodsOnDaoObjects() {
        mNonSensitiveDataRepository.retrieveItemsFilteredByType(mItemDomainModel);
        verify(mItemDao).getItemsByType(getValidatedMetadata(
                mItemDomainModel).getUserId(), getValidatedMetadata(mItemDomainModel).getItemType());
    }

    @Test
    public void retrieveFilteredItemsByTypeReturnsListOfDomainModels() {
        List<Item> itemsList = new ArrayList<>();
        itemsList.add(new Item());
        itemsList.add(new Item());
        when(mItemDao.getItemsByType(anyString(), anyString())).thenReturn(itemsList);
        List<ItemDomainModel> result = mNonSensitiveDataRepository.retrieveItemsFilteredByType(mItemDomainModel);
        assertEquals(result.size(), itemsList.size());
    }

    @Test
    public void retrieveFilteredItemsByTypeReturnsListOfConvertedDomainModels() {
        final Item first = ModelGenerator.generateItem();
        final Item second = ModelGenerator.generateOtherItem();
        List<Item> items = new ArrayList<Item>() {{
            add(first);
            add(second);
        }};

        when(mItemDao.getItemsByType(anyString(), anyString())).thenReturn(items);

        List<ItemDomainModel> result = mNonSensitiveDataRepository.retrieveItemsFilteredByType(mItemDomainModel);

        assertEquals(result.size(), items.size());
        ModelComparator.compareItemToItemDomainModel(first, result.get(0), REPO_TYPE);
        ModelComparator.compareItemToItemDomainModel(second, result.get(1), REPO_TYPE);
    }

    @Test
    public void retrieveFilteredItemSummaryCallsMethodsOnDaoObjects() {
        mNonSensitiveDataRepository.retrieveFilteredItemSummaryByUniqueIdAndType(mItemDomainModel);

        verify(mItemDao).getItemByIdAndType(mItemDomainModel.getUid(),
                mItemDomainModel.getMetadata().getItemType(), mItemDomainModel.getMetadata().getUserId());
    }

    @Test
    public void retrieveFilteredItemSummaryReturnsItemDomainModel() {
        Item item = ModelGenerator.generateItem();
        when(mItemDao.getItemByIdAndType(anyString(), anyString(), anyString())).thenReturn(item);

        ItemDomainModel domainModel =
                mNonSensitiveDataRepository.retrieveFilteredItemSummaryByUniqueIdAndType(mItemDomainModel);

        assertNotNull(domainModel);
        ModelComparator.compareItemToItemDomainModel(item, domainModel, REPO_TYPE);
    }

    @Test
    public void retrieveFilteredItemDetailsCallsMethodsOnDaoObjects() {
        mNonSensitiveDataRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mItemDomainModel);

        verify(mItemDao).getItemByIdAndType(mItemDomainModel.getUid(),
                mItemDomainModel.getMetadata().getItemType(), mItemDomainModel.getMetadata().getUserId());
        verify(mItemPrivateDataDao).getPrivateData(mItemDomainModel.getUid());
    }

    @Test
    public void retrieveFilteredItemDetailsReturnsItemDomainModel() {
        Item item = ModelGenerator.generateItem();
        when(mItemDao.getItemByIdAndType(mItemDomainModel.getUid(),
                mItemDomainModel.getMetadata().getItemType(), mItemDomainModel.getMetadata().getUserId()))
                .thenReturn(item);
        when(mItemPrivateDataDao.getPrivateData(mItemDomainModel.getUid()))
                .thenReturn(PRIVATE);

        ItemDomainModel itemDetails =
                mNonSensitiveDataRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mItemDomainModel);
        ModelComparator.compareItemWithPrivateDataToItemDomainModel(item, PRIVATE, itemDetails, REPO_TYPE);
    }

    @Test
    public void deleteAllDataCallsMethodsOnDaoObjects() {
        mNonSensitiveDataRepository.deleteAllData();

        verify(mItemDao).deleteAllData();
        verify(mItemPrivateDataDao).deleteAllData();
    }

    @Test
    public void deleteItemCallsMethodsOnDaoObjects() {
        ArgumentCaptor<String> itemArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ItemPrivateData> itemPrivateDataArgumentCaptor =
                ArgumentCaptor.forClass(ItemPrivateData.class);

        mNonSensitiveDataRepository.deleteItem(mItemDomainModel);

        verify(mItemDao).deleteItemByUidAndType(itemArgumentCaptor.capture(),
                itemArgumentCaptor.capture(), itemArgumentCaptor.capture());
        verify(mItemPrivateDataDao).delete(itemPrivateDataArgumentCaptor.capture());
    }

    @Test
    public void deleteItemCallsNoMethodOnDaoObjectsWhenItemAndPrivateDataAreNull() {
        PowerMockito.mockStatic(ModelConverter.class);

        mNonSensitiveDataRepository.deleteItem(mItemDomainModel);

        verify(mItemDao, never()).deleteItemByUidAndType(anyString(), anyString(), anyString());
        verify(mItemPrivateDataDao, never()).delete(any());
    }

    @Test
    public void deleteItemsByTypeCallsMethodsOnDaoObjectsWhenItemExistsInRepo() {
        ArgumentCaptor<String> itemArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ItemPrivateData> itemPrivateDataArgumentCaptor =
                ArgumentCaptor.forClass(ItemPrivateData.class);

        String type = mItemDomainModel.getMetadata().getItemType();
        String userId = mItemDomainModel.getMetadata().getUserId();

        List<Item> itemList = new ArrayList<>();
        itemList.add(ModelGenerator.generateItem());
        when(mItemDao.getItemsByType(userId, type)).thenReturn(itemList);

        mNonSensitiveDataRepository.deleteItemsByType(mItemDomainModel);

        verify(mItemDao).getItemsByType(userId, type);
        verify(mItemDao).deleteItemByUidAndType(itemArgumentCaptor.capture(),
                itemArgumentCaptor.capture(), itemArgumentCaptor.capture());
        verify(mItemPrivateDataDao).delete(itemPrivateDataArgumentCaptor.capture());
    }

    @Test
    public void deleteItemsByTypeCallsMethodsOnDaoObjectsWhenItemDoesNotExistInRepo() {
        ArgumentCaptor<String> itemArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<ItemPrivateData> itemPrivateDataArgumentCaptor =
                ArgumentCaptor.forClass(ItemPrivateData.class);

        String type = mItemDomainModel.getMetadata().getItemType();
        String user = mItemDomainModel.getMetadata().getUserId();

        List<Item> itemList = new ArrayList<>();
        when(mItemDao.getItemsByType(user, type)).thenReturn(itemList);

        mNonSensitiveDataRepository.deleteItemsByType(mItemDomainModel);

        verify(mItemDao).getItemsByType(user, type);
        verify(mItemDao, never()).deleteItemByUidAndType(itemArgumentCaptor.capture(),
                itemArgumentCaptor.capture(), itemArgumentCaptor.capture());
        verify(mItemPrivateDataDao, never()).delete(itemPrivateDataArgumentCaptor.capture());
    }

    @Test
    public void deleteItemsByTypeCallsNoMethodOnDaoObjectsWhenTypeIsEmpty() {
        ArgumentCaptor<ItemPrivateData> itemPrivateDataArgumentCaptor =
                ArgumentCaptor.forClass(ItemPrivateData.class);

        String emptyString = "";
        PowerMockito.when(TextUtils.isEmpty(emptyString)).thenReturn(true);
        mItemDomainModel.getMetadata().setItemType(emptyString);
        mNonSensitiveDataRepository.deleteItemsByType(mItemDomainModel);

        verify(mItemDao, never()).getItemsByType(anyString(), anyString());
        verify(mItemDao, never()).deleteItemByUidAndType(anyString(), anyString(), anyString());
        verify(mItemPrivateDataDao, never()).delete(itemPrivateDataArgumentCaptor.capture());
    }

    @Test
    public void updateItemCallsMethodsOnDaoObjects() {
        ArgumentCaptor<Item> itemArgumentCaptor = ArgumentCaptor.forClass(Item.class);
        ArgumentCaptor<ItemPrivateData> itemPrivateDataArgumentCaptor =
                ArgumentCaptor.forClass(ItemPrivateData.class);

        mNonSensitiveDataRepository.updateItem(mItemDomainModel);

        verify(mItemDao).update(itemArgumentCaptor.capture());
        verify(mItemPrivateDataDao).update(itemPrivateDataArgumentCaptor.capture());
    }

    @Test
    public void updateItemReturnsNumberOfItemsUpdated() {
        PowerMockito.mockStatic(ModelConverter.class);
        Item item = new Item();
        when(ModelConverter.getItemSummary(mItemDomainModel)).thenReturn(item);
        when(ModelConverter.getItemPrivateData(mItemDomainModel))
                .thenReturn(new ItemPrivateData(item.getUid()));
        int affectedRows = 1;
        when(mItemDao.update(item)).thenReturn(affectedRows);

        int count = mNonSensitiveDataRepository.updateItem(mItemDomainModel);

        assertEquals(count, affectedRows);
    }
}