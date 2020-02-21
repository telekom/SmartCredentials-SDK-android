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

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.telekom.smartcredentials.core.model.DomainModelException;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.models.ModelConverter;
import de.telekom.smartcredentials.storage.utils.ModelComparator;
import de.telekom.smartcredentials.storage.utils.ModelGenerator;

import static de.telekom.smartcredentials.core.model.ModelValidator.NULL_PARAMETER_EXCEPTION_MSG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class SharedPreferencesRepoTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    private Gson mGson;
    private PreferencesManager mPreferencesManager;
    private ItemDomainModel mItemDomainModel;
    private SharedPreferencesRepo mSharedPreferencesRepo;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);

        mGson = new GsonBuilder().create();
        mPreferencesManager = Mockito.mock(PreferencesManager.class);
        mSharedPreferencesRepo = new SharedPreferencesRepoFiveFourteen(mGson, mPreferencesManager);
        mItemDomainModel = ModelGenerator.generateNonEncryptedNonSensitiveItemDomainModel();
    }

    @Test
    public void saveDataThrowsExceptionWhenModelIsNull() throws JSONException {
        thrown.expect(DomainModelException.class);
        thrown.expectMessage(NULL_PARAMETER_EXCEPTION_MSG);

        mSharedPreferencesRepo.saveData(null);
    }

    @Test
    public void saveDataCallsSaveOnPreferencesManager() throws JSONException {
        mSharedPreferencesRepo.saveData(mItemDomainModel);
        String data = Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString();

        when(mPreferencesManager.getItem(mItemDomainModel.getUniqueKey())).thenReturn(null);

        verify(mPreferencesManager).save(mItemDomainModel.getUniqueKey(), data);
    }

    @Test
    public void saveDataReturnsZeroWhenNoItemWasActuallySaved() throws JSONException {
        when(mPreferencesManager.getItem(mItemDomainModel.getUniqueKey())).thenReturn(null);
        int count = mSharedPreferencesRepo.saveData(mItemDomainModel);

        assertEquals(count, 0);
    }

    @Test
    public void saveDataReturnsNumberOfItemsSaved() throws JSONException {
        String data = Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString();
        when(mPreferencesManager.getItem(mItemDomainModel.getUniqueKey())).thenReturn(data);
        int count = mSharedPreferencesRepo.saveData(mItemDomainModel);

        assertEquals(count, 1);
    }

    @PrepareForTest({ModelConverter.class, TextUtils.class})
    @Test
    public void saveDataCallsNoMethodOnPrefsManagerWhenJSONObjectIsNull() throws JSONException {
        PowerMockito.mockStatic(ModelConverter.class);
        when(ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).thenReturn(null);
        mSharedPreferencesRepo.saveData(mItemDomainModel);
        verify(mPreferencesManager, never()).save(anyString(), anyString());
    }

    @Test
    public void retrieveFilteredItemsReturnsEmptyListWhenNoMatchingItem() throws JSONException {
        when(mPreferencesManager.getItemsMatchingType(anyString())).thenReturn(new ArrayList<>());

        List<ItemDomainModel> itemDomainModelList = mSharedPreferencesRepo
                .retrieveItemsFilteredByType(mItemDomainModel.getMetadata());

        verify(mPreferencesManager)
                .getItemsMatchingType(mItemDomainModel.getMetadata().getUniqueKeyPrefix());
        assertNotNull(itemDomainModelList);
        assertEquals(itemDomainModelList.size(), 0);
    }

    @Test
    public void retrieveFilteredItemsReturnsNonEmptyListWhenExistsMatchingItem() throws JSONException {
        List<String> items = new ArrayList<>();
        items.add(Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString());
        when(mPreferencesManager.getItemsMatchingType(anyString())).thenReturn(items);

        List<ItemDomainModel> itemDomainModelList = mSharedPreferencesRepo
                .retrieveItemsFilteredByType(mItemDomainModel.getMetadata());

        assertNotNull(itemDomainModelList);
        assertEquals(itemDomainModelList.size(), 1);
    }

    @Test
    public void retrieveFilteredItemsReturnsDomainModels() throws JSONException {
        List<String> items = new ArrayList<>();
        ItemDomainModel sensitiveItem = ModelGenerator.generateEncryptedSensitiveItemDomainModel();
        items.add(Objects.requireNonNull(ModelConverter.getItemJSONObject(sensitiveItem, mGson)).toString());

        when(mPreferencesManager.getItemsMatchingType(anyString())).thenReturn(items);

        List<ItemDomainModel> itemDomainModelList =
                mSharedPreferencesRepo.retrieveItemsFilteredByType(mItemDomainModel.getMetadata());
        assertEquals(itemDomainModelList.size(), 1);
        ModelComparator.compareDomainModels(sensitiveItem, itemDomainModelList.get(0));
    }

    @Test
    public void retrieveFilteredItemSummaryCallsGetItemOnPrefsManager() throws JSONException {
        mSharedPreferencesRepo.retrieveFilteredItemSummary(mItemDomainModel.getUniqueKey());

        verify(mPreferencesManager).getItem(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void retrieveFilteredItemDetailsCallsGetItemOnPrefsManager() throws JSONException {
        mSharedPreferencesRepo.retrieveFilteredItemDetails(mItemDomainModel.getUniqueKey());

        verify(mPreferencesManager).getItem(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void deleteAllDataCallsClearAllOnPrefsManager() {
        mSharedPreferencesRepo.deleteAllData();

        verify(mPreferencesManager).clearAll();
    }

    @Test
    public void deleteItemCallsRemoveOnPrefsManager() {
        mSharedPreferencesRepo.deleteItem(mItemDomainModel);

        verify(mPreferencesManager).remove(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void deleteItemByTypeCallsMethodOnDaoObjectWhenDomainListNotEmpty() throws JSONException {
        List<String> items = new ArrayList<>();
        items.add(Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString());
        when(mPreferencesManager.getItemsMatchingType(mItemDomainModel.getMetadata().getUniqueKeyPrefix()))
                .thenReturn(items);

        mSharedPreferencesRepo.deleteItemByType(mItemDomainModel);

        verify(mPreferencesManager, times(items.size())).remove(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void deleteItemByTypeCallsNoMethodOnDaoObjectWhenDomainListEmpty() throws JSONException {
        when(mPreferencesManager.getItemsMatchingType(mItemDomainModel.getMetadata().getUniqueKeyPrefix()))
                .thenReturn(new ArrayList<>());

        mSharedPreferencesRepo.deleteItemByType(mItemDomainModel);

        verify(mPreferencesManager, never()).remove(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void updateItemCallsUpdateOnPreferencesManager() throws JSONException {
        mSharedPreferencesRepo.updateItem(mItemDomainModel);
        String data = Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString();

        when(mPreferencesManager.getItem(mItemDomainModel.getUniqueKey())).thenReturn(null);

        verify(mPreferencesManager).update(mItemDomainModel.getUniqueKey(), data);
    }

    @Test
    public void updateItemReturnsZeroWhenNoItemWasActuallyUpdated() throws JSONException {
        when(mPreferencesManager.getItem(mItemDomainModel.getUniqueKey())).thenReturn(null);

        int count = mSharedPreferencesRepo.updateItem(mItemDomainModel);

        assertEquals(count, 0);
    }

    @Test
    public void updateItemReturnsNumberOfItemsUpdated() throws JSONException {
        String data = Objects.requireNonNull(
                ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).toString();
        when(mPreferencesManager.getItem(mItemDomainModel.getUniqueKey())).thenReturn(data);

        int count = mSharedPreferencesRepo.updateItem(mItemDomainModel);

        assertEquals(count, 1);
    }

    @PrepareForTest({ModelConverter.class, TextUtils.class})
    @Test
    public void updateItemCallsNoMethodOnPrefsManagerWhenJSONObjectIsNull() throws JSONException {
        PowerMockito.mockStatic(ModelConverter.class);
        when(ModelConverter.getItemJSONObject(mItemDomainModel, mGson)).thenReturn(null);

        mSharedPreferencesRepo.updateItem(mItemDomainModel);

        verify(mPreferencesManager, never()).save(anyString(), anyString());
    }
}