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

package de.telekom.smartcredentials.storage.database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.room.Room;
import androidx.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.telekom.smartcredentials.storage.database.dao.ItemGenerator;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.database.models.TableName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Lucian Iacob on December 14, 2018.
 */
@RunWith(AndroidJUnit4.class)
public class MigrationsTest {

    private static final String TEST_DB_NAME = "test_db";
    private static final String APP_ID       = "09878";
    private static final Item   ITEM         = ItemGenerator.generateItem();

    @Rule
    public final MigrationTestHelper mMigrationTestHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                                    Objects.requireNonNull(AppDatabase.class.getCanonicalName()),
                                    new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void testMigration1To2() throws IOException {
        SupportSQLiteDatabase databaseV1 = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 1);
        insertUser(databaseV1);

        databaseV1.close();

        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME,
                                                      2,
                                                      true,
                                                      AppDatabase.MIGRATION_1_TO_2);

        AppDatabase databaseV2 = getMigratedDatabase();
        List<Item> items = databaseV2.getItemsDao().getAll();
        assertTrue(items.size() > 0);

        Item item = items.get(0);

        assertEquals(ItemGenerator.mId1, item.getUid());
        assertEquals(ItemGenerator.mUserId1, item.getUserId());
        assertTrue(item.getActionList().isEmpty());
    }

    private AppDatabase getMigratedDatabase() {
        AppDatabase database = Room
                .databaseBuilder(InstrumentationRegistry.getContext(),
                                 AppDatabase.class,
                                 TEST_DB_NAME)
                .build();
        mMigrationTestHelper.closeWhenFinished(database);
        return database;
    }

    private void insertUser(SupportSQLiteDatabase database) {
        ContentValues value = new ContentValues();
        value.put(Item.UID_NAME, MigrationsTest.ITEM.getUid());
        value.put("app_id", APP_ID);
        value.put(Item.USER_ID_NAME, MigrationsTest.ITEM.getUserId());
        value.put(Item.SECURED_NAME, MigrationsTest.ITEM.isSecuredData() ? 1 : 0);

        database.insert(TableName.ITEM_TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, value);
    }

}
