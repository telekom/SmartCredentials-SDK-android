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
import de.telekom.smartcredentials.storage.database.models.Item;

import static de.telekom.smartcredentials.storage.database.dao.ItemGenerator.mRandomId;
import static de.telekom.smartcredentials.storage.database.dao.ItemGenerator.mRandomType;
import static de.telekom.smartcredentials.storage.database.dao.ItemGenerator.mUserId1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ItemDaoTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private AppDatabase mAppDatabase;
    private ItemDao mItemDao;
    private Item mItem;
    private Item mOtherItem;

    @Before
    public void setUp() {
        mAppDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase.class).build();
        mItemDao = mAppDatabase.getItemsDao();
        mItem = ItemGenerator.generateItem();
        mOtherItem = ItemGenerator.generateOtherItem();
    }

    @After
    public void closeDb() {
        mAppDatabase.close();
    }

    @Test
    public void insertAllSavesData() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        List<Item> all = mItemDao.getAll();
        assertNotNull(all);
    }

    @Test
    public void getItemByIdRetrievesDataById() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        Item item = mItemDao.getItemByIdAndType(mItem.getUid(), mItem.getType(), mItem.getUserId());
        assertNotNull(item);
        assertEquals(item.getUid(), mItem.getUid());
        assertEquals(item.getIdentifier(), mItem.getIdentifier());
        assertEquals(item.getType(), mItem.getType());
        assertEquals(item.isSecuredData(), mItem.isSecuredData());
        assertTrue(item.getActionList().size() > 0);
        assertEquals(item.getActionList().get(0).getClassName(), mItem.getActionList().get(0).getClassName());
    }

    @Test
    public void getItemByIdReturnsNullIfNoItemWithIdInDb() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        Item item = mItemDao.getItemByIdAndType(mRandomId, mRandomType, mUserId1);
        assertNull(item);
    }

    @Test
    public void getAllReturnsAllData() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        List<Item> all = mItemDao.getAll();
        assertNotNull(all);
        assertEquals(2, all.size());

        List<String> ids = new ArrayList<>();
        for (Item item : all) {
            ids.add(item.getUid());
        }
        assertTrue(ids.contains(mItem.getUid()));
        assertTrue(ids.contains(mOtherItem.getUid()));
    }

    @Test
    public void getItemsByTypeReturnsDataByType() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        List<Item> all = mItemDao.getItemsByType(mItem.getUserId(), mItem.getType());
        assertNotNull(all);
        assertEquals(1, all.size());

        Item item = all.get(0);
        assertEquals(item.getUid(), mItem.getUid());
        assertEquals(item.getIdentifier(), mItem.getIdentifier());
        assertTrue(item.getActionList().size() > 0);
        assertEquals(item.getActionList().get(0).getClassName(), mItem.getActionList().get(0).getClassName());
        assertEquals(item.getType(), mItem.getType());
        assertEquals(item.isSecuredData(), mItem.isSecuredData());
    }

    @Test
    public void getItemsByTypeReturnsEmptyListIfNoItemWithTypeInDb() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        List<Item> all = mItemDao.getItemsByType(mUserId1, mRandomType);
        assertNotNull(all);
        assertEquals(all.size(), 0);
    }

    @Test
    public void deleteAllDataRemovesAllData() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        mItemDao.deleteAllData();
        assertNotNull(mItemDao.getAll());
        assertEquals(0, mItemDao.getAll().size());
    }

    @Test
    public void deleteItemRemovesItem() {
        Item[] itemsToInsert = new Item[]{mItem, mOtherItem};
        mItemDao.insertAll(itemsToInsert);

        List<Item> itemList = mItemDao.getAll();
        assertNotNull(itemList);
        assertEquals(2, itemList.size());

        mItemDao.deleteItemByUidAndType(mOtherItem.getUid(), mOtherItem.getType(), mOtherItem.getUserId());

        itemList = mItemDao.getAll();
        assertNotNull(itemList);
        assertEquals(1, itemList.size());
        assertEquals(itemList.get(0).getUid(), mItem.getUid());
    }

    @Test
    public void deleteItemDoesNotRemoveItemIfNotPresentInDb() {
        mItemDao.insert(mItem);

        List<Item> itemList = mItemDao.getAll();
        assertNotNull(itemList);
        assertEquals(1, itemList.size());

        mItemDao.deleteItemByUidAndType(mOtherItem.getUid(), mOtherItem.getType(), mOtherItem.getUserId());

        itemList = mItemDao.getAll();
        assertNotNull(itemList);
        assertEquals(1, itemList.size());
        assertEquals(itemList.get(0).getUid(), mItem.getUid());
    }

}