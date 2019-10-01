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

package de.telekom.smartcredentials.storage.database.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import static de.telekom.smartcredentials.storage.database.models.TableName.ITEM_PRIVATE_DATA_TABLE_NAME;

@Entity(tableName = ITEM_PRIVATE_DATA_TABLE_NAME)
public class ItemPrivateData {

    @Ignore
    static final String DESCRIPTION = ItemPrivateData.class.getSimpleName();

    @Ignore
    public static final String UID_NAME = "uid";

    @Ignore
    public static final String INFO_NAME = "info";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = UID_NAME)
    @SerializedName(UID_NAME)
    private String mUid;

    @ColumnInfo(name = INFO_NAME)
    @SerializedName(INFO_NAME)
    private String mInfo;

    @NonNull
    public String getUid() {
        return mUid;
    }

    public void setUid(@NonNull String uid) {
        this.mUid = uid;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        this.mInfo = info;
    }

    public ItemPrivateData(@NonNull String uid) {
        this.mUid = uid;
    }
}
