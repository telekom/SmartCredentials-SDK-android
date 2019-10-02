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

package de.telekom.smartcredentials.core.model.item;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.model.DomainModelException;

public class ItemDomainMetadata {

    public static final String UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE = "Could not " +
            "compute unique key prefix; type is null";
    public static final String UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE = "Could not " +
            "compute unique key prefix; user id is null";
    public static final String KEY_SEPARATOR = "@$@";
    static final String USER_ID = "user_id=";
    static final String ITEM_TYPE = "item_type=";

    private String mItemType;
    private String mUserId;
    private ContentType mContentType;
    private final boolean mIsDataEncrypted;
    private boolean mIsAutoLockEnabled;
    private boolean mIsLocked;
    private List<ItemDomainAction> mActionList;

    public ItemDomainMetadata(ItemDomainMetadata metadata) {
        mItemType = metadata.getItemType();
        mUserId = metadata.getUserId();
        mContentType = metadata.getContentType();
        mIsDataEncrypted = metadata.isDataEncrypted();
        mIsAutoLockEnabled = metadata.isAutoLockEnabled();
        mIsLocked = metadata.isLocked();
        mActionList = metadata.getActionList();
    }

    public ItemDomainMetadata(boolean isDataEncrypted, boolean isAutoLockEnabled, boolean isLocked) {
        mIsDataEncrypted = isDataEncrypted;
        mIsAutoLockEnabled = isAutoLockEnabled;
        mIsLocked = isLocked;
        mActionList = new ArrayList<>();
    }

    public ItemDomainMetadata(boolean isDataEncrypted) {
        mIsDataEncrypted = isDataEncrypted;
        mIsAutoLockEnabled = false;
        mIsLocked = false;
        mActionList = new ArrayList<>();
    }

    public String getItemType() {
        return mItemType;
    }

    public ItemDomainMetadata setItemType(String itemType) {
        mItemType = itemType;
        return this;
    }

    public List<ItemDomainAction> getActionList() {
        return mActionList;
    }

    public ItemDomainMetadata setActionList(List<ItemDomainAction> actionList) {
        mActionList = actionList;
        return this;
    }

    public String getUserId() {
        return mUserId;
    }

    public ItemDomainMetadata setUserId(String userId) {
        mUserId = userId;
        return this;
    }

    public ContentType getContentType() {
        return mContentType;
    }

    public ItemDomainMetadata setContentType(ContentType contentType) {
        mContentType = contentType;
        return this;
    }

    public boolean isDataEncrypted() {
        return mIsDataEncrypted;
    }

    public boolean isAutoLockEnabled() {
        return mIsAutoLockEnabled;
    }

    public ItemDomainMetadata setAutoLockState(boolean state){
        mIsAutoLockEnabled = state;
        return this;
    }

    public boolean isLocked() {
        return mIsLocked;
    }

    public ItemDomainMetadata setLockState(boolean state){
        mIsLocked = state;
        return this;
    }

    public String getUniqueKeyPrefix() {
        if (TextUtils.isEmpty(getItemType())) {
            throw new DomainModelException(UNIQUE_KEY_TYPE_PREFIX_EXCEPTION_MESSAGE);
        }

        if (TextUtils.isEmpty(getUserId())) {
            throw new DomainModelException(UNIQUE_KEY_USER_ID_PREFIX_EXCEPTION_MESSAGE);
        }

        return ITEM_TYPE + getItemType() + KEY_SEPARATOR + USER_ID + getUserId();
    }
}
