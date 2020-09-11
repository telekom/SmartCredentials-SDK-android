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
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import android.content.Context;
import androidx.annotation.NonNull;

import de.telekom.smartcredentials.storage.database.dao.ItemDao;
import de.telekom.smartcredentials.storage.database.dao.ItemPrivateDataDao;
import de.telekom.smartcredentials.storage.database.models.Item;
import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;
import de.telekom.smartcredentials.storage.database.models.TableName;

@Database(entities = {Item.class, ItemPrivateData.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_FILE_NAME = "items-database";

    private static AppDatabase appDatabaseInstance;

    public static AppDatabase getDatabase(Context context) {
        if (appDatabaseInstance == null) {
            appDatabaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_FILE_NAME)
                    .addMigrations(MIGRATION_1_TO_2, MIGRATION_1_TO_3, MIGRATION_2_TO_3)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabaseInstance;
    }

    static final Migration MIGRATION_1_TO_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            final String BACKUP_TABLE_NAME = "item_backup";

            database.execSQL("CREATE TABLE " + BACKUP_TABLE_NAME + " ("
                    + Item.UID_NAME + " TEXT NOT NULL, "
                    + Item.IDENTIFIER_NAME + " TEXT, "
                    + Item.TYPE_NAME + " TEXT, "
                    + Item.SECURED_NAME + " INTEGER NOT NULL, "
                    + Item.USER_ID_NAME + " TEXT, "
                    + Item.ACTION_LIST + " TEXT, "
                    + "PRIMARY KEY(" + Item.UID_NAME + "))");

            database.execSQL(
                    "INSERT INTO " + BACKUP_TABLE_NAME + " ("
                            + Item.UID_NAME + ", "
                            + Item.IDENTIFIER_NAME + ", "
                            + Item.TYPE_NAME + ", "
                            + Item.SECURED_NAME + ", "
                            + Item.USER_ID_NAME + ") "
                            + "SELECT "
                            + Item.UID_NAME + ", "
                            + Item.IDENTIFIER_NAME + ", "
                            + Item.TYPE_NAME + ", "
                            + Item.SECURED_NAME + ", "
                            + Item.USER_ID_NAME + " "
                            + "FROM " + TableName.ITEM_TABLE_NAME);


            database.execSQL("DROP TABLE " + TableName.ITEM_TABLE_NAME);
            database.execSQL("ALTER TABLE " + BACKUP_TABLE_NAME + " RENAME TO " + TableName.ITEM_TABLE_NAME);
            database.execSQL("CREATE INDEX index_item_uid ON " + TableName.ITEM_TABLE_NAME + "(" + Item.UID_NAME + ")");
        }
    };

    private static final Migration MIGRATION_1_TO_3 = new Migration(1, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            final String BACKUP_TABLE_NAME = "item_backup";

            database.execSQL("CREATE TABLE " + BACKUP_TABLE_NAME + " ("
                    + Item.UID_NAME + " TEXT NOT NULL, "
                    + Item.IDENTIFIER_NAME + " TEXT, "
                    + Item.TYPE_NAME + " TEXT, "
                    + Item.SECURED_NAME + " INTEGER NOT NULL, "
                    + Item.AUTO_LOCK_NAME + " INTEGER NOT NULL DEFAULT 0, "
                    + Item.LOCKED_NAME + " INTEGER NOT NULL DEFAULT 0, "
                    + Item.USER_ID_NAME + " TEXT, "
                    + Item.ACTION_LIST + " TEXT, "
                    + "PRIMARY KEY(" + Item.UID_NAME + "))");

            database.execSQL(
                    "INSERT INTO " + BACKUP_TABLE_NAME + " ("
                            + Item.UID_NAME + ", "
                            + Item.IDENTIFIER_NAME + ", "
                            + Item.TYPE_NAME + ", "
                            + Item.SECURED_NAME + ", "
                            + Item.USER_ID_NAME + ") "
                            + "SELECT "
                            + Item.UID_NAME + ", "
                            + Item.IDENTIFIER_NAME + ", "
                            + Item.TYPE_NAME + ", "
                            + Item.SECURED_NAME + ", "
                            + Item.USER_ID_NAME + " "
                            + "FROM " + TableName.ITEM_TABLE_NAME);


            database.execSQL("DROP TABLE " + TableName.ITEM_TABLE_NAME);
            database.execSQL("ALTER TABLE " + BACKUP_TABLE_NAME + " RENAME TO " + TableName.ITEM_TABLE_NAME);
            database.execSQL("CREATE INDEX index_item_uid ON " + TableName.ITEM_TABLE_NAME + "(" + Item.UID_NAME + ")");
        }
    };

    private static final Migration MIGRATION_2_TO_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

            final String BACKUP_TABLE_NAME = "item_backup";

            database.execSQL("CREATE TABLE " + BACKUP_TABLE_NAME + " ("
                    + Item.UID_NAME + " TEXT NOT NULL, "
                    + Item.IDENTIFIER_NAME + " TEXT, "
                    + Item.TYPE_NAME + " TEXT, "
                    + Item.SECURED_NAME + " INTEGER NOT NULL, "
                    + Item.AUTO_LOCK_NAME + " INTEGER NOT NULL DEFAULT 0, "
                    + Item.LOCKED_NAME + " INTEGER NOT NULL DEFAULT 0, "
                    + Item.USER_ID_NAME + " TEXT, "
                    + Item.ACTION_LIST + " TEXT, "
                    + "PRIMARY KEY(" + Item.UID_NAME + "))");

            database.execSQL(
                    "INSERT INTO " + BACKUP_TABLE_NAME + " ("
                            + Item.UID_NAME + ", "
                            + Item.IDENTIFIER_NAME + ", "
                            + Item.TYPE_NAME + ", "
                            + Item.SECURED_NAME + ", "
                            + Item.USER_ID_NAME + ", "
                            + Item.ACTION_LIST + ") "
                            + "SELECT "
                            + Item.UID_NAME + ", "
                            + Item.IDENTIFIER_NAME + ", "
                            + Item.TYPE_NAME + ", "
                            + Item.SECURED_NAME + ", "
                            + Item.USER_ID_NAME + ", "
                            + Item.ACTION_LIST + " "
                            + "FROM " + TableName.ITEM_TABLE_NAME);


            database.execSQL("DROP TABLE " + TableName.ITEM_TABLE_NAME);
            database.execSQL("ALTER TABLE " + BACKUP_TABLE_NAME + " RENAME TO " + TableName.ITEM_TABLE_NAME);
            database.execSQL("CREATE INDEX index_item_uid ON " + TableName.ITEM_TABLE_NAME + "(" + Item.UID_NAME + ")");
        }
    };

    public abstract ItemDao getItemsDao();

    public abstract ItemPrivateDataDao getPrivateDataDao();

}
