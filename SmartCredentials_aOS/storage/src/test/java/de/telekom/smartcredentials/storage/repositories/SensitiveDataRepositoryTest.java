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

import android.text.TextUtils;

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

import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.exceptions.RepositoryException;
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepoFiveFourteen;
import de.telekom.smartcredentials.storage.prefs.SharedPreferencesRepoFourTwo;
import de.telekom.smartcredentials.storage.utils.ModelGenerator;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class SensitiveDataRepositoryTest {

    @Rule
    public final ExpectedException mExpectedException = ExpectedException.none();

    private SensitiveDataRepository mSensitiveDataRepository;
    private ItemDomainModel mItemDomainModel;
    private SharedPreferencesRepoFiveFourteen mSharedPrefsRepoFiveFourteen;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);

        mSharedPrefsRepoFiveFourteen = Mockito.mock(SharedPreferencesRepoFiveFourteen.class);
        SharedPreferencesRepoFourTwo mSharedPreferencesRepoFourTwo = Mockito.mock(SharedPreferencesRepoFourTwo.class);
        mSensitiveDataRepository = new SensitiveDataRepository(mSharedPrefsRepoFiveFourteen,
                mSharedPreferencesRepoFourTwo);

        mItemDomainModel = ModelGenerator.generateEncryptedSensitiveItemDomainModel();
    }

    @Test
    public void saveDataCallsSharedPreferencesRepoMethod() throws JSONException {
        mSensitiveDataRepository.saveData(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).saveData(mItemDomainModel);
    }

    @Test
    public void saveDataThrowsRepoException() throws JSONException {
        doThrow(new JSONException("")).when(mSharedPrefsRepoFiveFourteen).saveData(mItemDomainModel);
        mExpectedException.expect(RepositoryException.class);
        mSensitiveDataRepository.saveData(mItemDomainModel);
    }

    @Test
    public void retrieveFilteredItemsCallsSharedPreferencesRepoMethod() throws JSONException {
        mSensitiveDataRepository.retrieveItemsFilteredByType(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).retrieveItemsFilteredByType(mItemDomainModel.getMetadata());
    }

    @Test
    public void retrieveFilteredItemSummaryCallsSharedPreferencesRepoMethod() throws JSONException {
        mSensitiveDataRepository.retrieveFilteredItemSummaryByUniqueIdAndType(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).retrieveFilteredItemSummary(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void retrieveFilteredItemDetailsCallsSharedPreferencesRepoMethod() throws JSONException {
        mSensitiveDataRepository.retrieveFilteredItemDetailsByUniqueIdAndType(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).retrieveFilteredItemDetails(mItemDomainModel.getUniqueKey());
    }

    @Test
    public void deleteAllDataCallsSharedPreferencesRepoMethod() {
        mSensitiveDataRepository.deleteAllData();
        verify(mSharedPrefsRepoFiveFourteen).deleteAllData();
    }

    @Test
    public void deleteItemCallsSharedPreferencesRepoMethod() {
        mSensitiveDataRepository.deleteItem(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).deleteItem(mItemDomainModel);
    }

    @Test
    public void deleteItemByTypeCallsSharedPreferencesRepoMethod() throws JSONException {
        mSensitiveDataRepository.deleteItemsByType(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).deleteItemByType(mItemDomainModel);
    }

    @Test
    public void updateItemCallsSharedPreferencesRepoMethod() throws JSONException {
        mSensitiveDataRepository.updateItem(mItemDomainModel);
        verify(mSharedPrefsRepoFiveFourteen).updateItem(mItemDomainModel);
    }

    @Test
    public void updateItemThrowsRepoException() throws JSONException {
        doThrow(new JSONException("")).when(mSharedPrefsRepoFiveFourteen).updateItem(mItemDomainModel);
        mExpectedException.expect(RepositoryException.class);
        mSensitiveDataRepository.updateItem(mItemDomainModel);
    }
}