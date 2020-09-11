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

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import de.telekom.smartcredentials.storage.database.models.ItemPrivateData;

import static de.telekom.smartcredentials.storage.database.models.TableName.ITEM_PRIVATE_DATA_TABLE_NAME;

@Dao
public interface ItemPrivateDataDao extends BaseDao<ItemPrivateData> {

    @Query("SELECT " + ItemPrivateData.INFO_NAME + " FROM " + ITEM_PRIVATE_DATA_TABLE_NAME
            + " WHERE " + ItemPrivateData.UID_NAME + " = :uid")
    String getPrivateData(String uid);

    @Query("SELECT * FROM " + ITEM_PRIVATE_DATA_TABLE_NAME)
    List<ItemPrivateData> getAll();

    @Query("DELETE FROM " + ITEM_PRIVATE_DATA_TABLE_NAME)
    int deleteAllData();

}
