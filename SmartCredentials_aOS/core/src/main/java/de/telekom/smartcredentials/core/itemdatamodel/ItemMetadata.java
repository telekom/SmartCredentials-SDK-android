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

package de.telekom.smartcredentials.core.itemdatamodel;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;

public class ItemMetadata implements Parcelable {

    private final boolean mWithDataEncrypted;
    private boolean mIsAutoLockEnabled;
    private boolean mIsLocked;
    private List<SmartCredentialsAction> mSmartCredentialsActionList;
    private ItemPrivateData mItemPrivateData;

    public ItemMetadata(boolean withDataEncrypted, boolean isAutoLockEnabled, boolean isLocked) {
        mWithDataEncrypted = withDataEncrypted;
        mIsAutoLockEnabled = isAutoLockEnabled;
        mIsLocked = isLocked;
        mSmartCredentialsActionList = new ArrayList<>();
    }

    ItemMetadata(boolean isAutoLockEnabled, boolean isLocked) {
        mWithDataEncrypted = true;
        mIsAutoLockEnabled = isAutoLockEnabled;
        mIsLocked = isLocked;
        mSmartCredentialsActionList = new ArrayList<>();
    }

    public ItemMetadata(boolean withDataEncrypted) {
        mWithDataEncrypted = withDataEncrypted;
        mIsAutoLockEnabled = false;
        mIsLocked = false;
        mSmartCredentialsActionList = new ArrayList<>();
    }

    ItemMetadata() {
        mWithDataEncrypted = true;
        mIsAutoLockEnabled = false;
        mIsLocked = false;
        mSmartCredentialsActionList = new ArrayList<>();
    }

    private ItemMetadata(Parcel in) {
        mWithDataEncrypted = in.readByte() != 0;
        mIsAutoLockEnabled = in.readByte() != 0;
        mIsLocked = in.readByte() != 0;
        mItemPrivateData = in.readParcelable(ItemPrivateData.class.getClassLoader());
        mSmartCredentialsActionList = new ArrayList<>();
        in.readList(mSmartCredentialsActionList, SmartCredentialsAction.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mWithDataEncrypted ? 1 : 0));
        dest.writeByte((byte) (mIsAutoLockEnabled ? 1 : 0));
        dest.writeByte((byte) (mIsLocked ? 1 : 0));
        dest.writeParcelable(mItemPrivateData, flags);
        dest.writeList(mSmartCredentialsActionList);
    }

    public static final Creator<ItemMetadata> CREATOR = new Creator<ItemMetadata>() {
        @NonNull
        @Override
        public ItemMetadata createFromParcel(Parcel in) {
            return new ItemMetadata(in);
        }

        @Override
        public ItemMetadata[] newArray(int size) {
            return new ItemMetadata[size];
        }
    };

    public boolean isDataEncrypted() {
        return mWithDataEncrypted;
    }

    public ItemMetadata setAutoLockState(boolean state){
        mIsAutoLockEnabled = state;
        return this;
    }

    public ItemMetadata setLockState(boolean state){
        mIsLocked = state;
        return this;
    }

    public ItemMetadata setActionList(List<SmartCredentialsAction> smartCredentialsActions) {
        mSmartCredentialsActionList = smartCredentialsActions;
        return this;
    }

    public ItemMetadata setPrivateData(ItemPrivateData privateData) {
        mItemPrivateData = privateData;
        return this;
    }

    public void addAction(SmartCredentialsAction smartCredentialsAction) {
        if (mSmartCredentialsActionList == null) {
            mSmartCredentialsActionList = new ArrayList<>();
        }
        mSmartCredentialsActionList.add(smartCredentialsAction);
    }

    public JSONObject getDetails() {
        return mItemPrivateData != null ? mItemPrivateData.getData() : null;
    }

    List<SmartCredentialsAction> getActionList() {
        return mSmartCredentialsActionList;
    }

    boolean isAutoLockEnabled() {
        return mIsAutoLockEnabled;
    }

    boolean isLocked() {
        return mIsLocked;
    }
}
