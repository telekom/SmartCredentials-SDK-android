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

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import de.telekom.smartcredentials.core.model.item.ItemDomainAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.storage.database.converters.ActionListTypeConverter;

import static de.telekom.smartcredentials.storage.database.models.TableName.ITEM_TABLE_NAME;

@Entity(tableName = ITEM_TABLE_NAME)
public class Item {

    @Ignore static final String DESCRIPTION = Item.class.getSimpleName();
    @Ignore public static final String UID_NAME = "uid";
    @Ignore public static final String IDENTIFIER_NAME = "identifier";
    @Ignore public static final String TYPE_NAME = "type";
    @Ignore public static final String SECURED_NAME = "secured";
    @Ignore public static final String AUTO_LOCK_NAME = "auto_lock";
    @Ignore public static final String LOCKED_NAME = "locked";
    @Ignore public static final String USER_ID_NAME = "user_id";
    @Ignore public static final String ACTION_LIST = "action_list";

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = UID_NAME, index = true)
    @SerializedName(UID_NAME)
    private String mUid;

    @ColumnInfo(name = IDENTIFIER_NAME)
    @SerializedName(IDENTIFIER_NAME)
    private String mIdentifier;

    @ColumnInfo(name = TYPE_NAME)
    @SerializedName(TYPE_NAME)
    private String mType;

    @ColumnInfo(name = SECURED_NAME)
    @SerializedName(SECURED_NAME)
    private boolean mSecuredData;

    @ColumnInfo(name = AUTO_LOCK_NAME)
    @SerializedName(AUTO_LOCK_NAME)
    private boolean mAutoLockEnabled;

    @ColumnInfo(name = LOCKED_NAME)
    @SerializedName(LOCKED_NAME)
    private boolean mLocked;

    @ColumnInfo(name = USER_ID_NAME)
    @SerializedName(USER_ID_NAME)
    private String mUserId;

    @TypeConverters(ActionListTypeConverter.class)
    @ColumnInfo(name = ACTION_LIST)
    @SerializedName(ACTION_LIST)
    private List<ItemDomainAction> mActionList;

    @NonNull
    public String getUid() {
        return mUid;
    }

    public void setUid(@NonNull String uid) {
        this.mUid = uid;
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(String identifier) {
        this.mIdentifier = identifier;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public boolean isSecuredData() {
        return mSecuredData;
    }

    public void setSecuredData(boolean securedData) {
        this.mSecuredData = securedData;
    }

    public boolean isAutoLockEnabled() {
        return mAutoLockEnabled;
    }

    public void setAutoLockEnabled(boolean isAutoLockEnabled) {
        this.mAutoLockEnabled = isAutoLockEnabled;
    }

    public boolean isLocked() {
        return mLocked;
    }

    public void setLocked(boolean isLocked) {
        this.mLocked = isLocked;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        this.mUserId = userId;
    }

    public List<ItemDomainAction> getActionList() {
        return mActionList;
    }

    public void setActionList(List<ItemDomainAction> actionList) {
        mActionList = actionList;
    }

    @Ignore
    ItemDomainModel toDomainModel() {
        ItemDomainModel domainModel = new ItemDomainModel();
        return domainModel
                .setId(mUid)
                .setData(toItemDomainData())
                .setMetadata(toDomainMetadata());
    }

    @Ignore
    private ItemDomainData toItemDomainData() {
        return new ItemDomainData()
                .setIdentifier(mIdentifier);
    }

    @Ignore
    private ItemDomainMetadata toDomainMetadata() {
        return new ItemDomainMetadata(mSecuredData, mAutoLockEnabled, mLocked)
                .setItemType(mType)
                .setUserId(mUserId)
                .setActionList(mActionList);
    }
}
