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

package de.telekom.smartcredentials.storage.database.dao;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.storage.database.AppDatabase;
import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ItemPrivateDataDaoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private AppDatabase mAppDatabase;
    private ItemPrivateDataDao mPrivateDataDao;
    private ItemPrivateData mPrivateItem;
    private ItemPrivateData mOtherPrivateItem;

    @Before
    public void setUp() {
        mAppDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class).build();
        mPrivateDataDao = mAppDatabase.getPrivateDataDao();
        mPrivateItem = ItemGenerator.generateItemPrivateData();
        mOtherPrivateItem = ItemGenerator.generateOtherItemPrivateData();
    }

    @After
    public void closeDb() {
        mAppDatabase.close();
    }

    @Test
    public void insertAllSavesData() {
        ItemPrivateData[] itemsToInsert = new ItemPrivateData[]{mPrivateItem, mOtherPrivateItem};
        mPrivateDataDao.insertAll(itemsToInsert);

        List<ItemPrivateData> all = mPrivateDataDao.getAll();
        assertNotNull(all);
    }

    @Test
    public void getPrivateDataReturnsPrivateDataById() {
        ItemPrivateData[] itemsToInsert = new ItemPrivateData[]{mPrivateItem, mOtherPrivateItem};
        mPrivateDataDao.insertAll(itemsToInsert);

        String itemPrivateData = mPrivateDataDao.getPrivateData(mPrivateItem.getUid());
        assertNotNull(itemPrivateData);
        assertEquals(itemPrivateData, mPrivateItem.getInfo());
    }

    @Test
    public void getPrivateDataReturnsNullWhenNoDataWithIdPresentInDb() {
        mPrivateDataDao.insert(mPrivateItem);

        String itemPrivateData = mPrivateDataDao.getPrivateData(mOtherPrivateItem.getUid());
        assertNull(itemPrivateData);
    }

    @Test
    public void getAllReturnsAllData() {
        ItemPrivateData[] itemsToInsert = new ItemPrivateData[]{mPrivateItem, mOtherPrivateItem};
        mPrivateDataDao.insertAll(itemsToInsert);

        List<ItemPrivateData> all = mPrivateDataDao.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        List<String> ids = new ArrayList<>();
        for (ItemPrivateData item : all) {
            ids.add(item.getUid());
        }
        assertTrue(ids.contains(mPrivateItem.getUid()));
        assertTrue(ids.contains(mOtherPrivateItem.getUid()));
    }

    @Test
    public void deleteAllDataRemovesAllData() {
        ItemPrivateData[] itemsToInsert = new ItemPrivateData[]{mPrivateItem, mOtherPrivateItem};
        mPrivateDataDao.insertAll(itemsToInsert);

        mPrivateDataDao.deleteAllData();
        assertNotNull(mPrivateDataDao.getAll());
        assertEquals(0, mPrivateDataDao.getAll().size());
    }

    @Test
    public void deleteItemRemovesItem() {
        ItemPrivateData[] itemsToInsert = new ItemPrivateData[]{mPrivateItem, mOtherPrivateItem};
        mPrivateDataDao.insertAll(itemsToInsert);

        List<ItemPrivateData> itemList = mPrivateDataDao.getAll();
        assertNotNull(itemList);
        assertEquals(2, itemList.size());

        mPrivateDataDao.delete(mOtherPrivateItem);

        itemList = mPrivateDataDao.getAll();
        assertNotNull(itemList);
        assertEquals(1, itemList.size());
        assertEquals(itemList.get(0).getUid(), mPrivateItem.getUid());
    }

    @Test
    public void deleteItemDoesNotRemoveItemIfNotPresentInDb() {
        mPrivateDataDao.insert(mPrivateItem);

        List<ItemPrivateData> itemList = mPrivateDataDao.getAll();
        assertNotNull(itemList);
        assertEquals(1, itemList.size());

        mPrivateDataDao.delete(mOtherPrivateItem);

        itemList = mPrivateDataDao.getAll();
        assertNotNull(itemList);
        assertEquals(1, itemList.size());
        assertEquals(itemList.get(0).getUid(), mPrivateItem.getUid());
    }

}