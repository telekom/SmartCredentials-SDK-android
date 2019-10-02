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

import java.io.Serializable;

import de.telekom.smartcredentials.core.model.item.ContentType;

public class ItemType implements Parcelable, Serializable {

    private final String mDesc;
    private final ContentType mContentType;

    ItemType(String typeDesc, ContentType contentType) {
        mDesc = typeDesc;
        mContentType = contentType;
    }

    private ItemType(Parcel in) {
        mDesc = in.readString();
        int contentTypeOrdinal = in.readInt();
        mContentType = ContentType.values()[contentTypeOrdinal];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDesc);
        int contentTypeOrdinal = mContentType == null ? -1 : mContentType.ordinal();
        dest.writeInt(contentTypeOrdinal);
    }

    public static final Creator<ItemType> CREATOR = new Creator<ItemType>() {
        @Override
        public ItemType createFromParcel(Parcel in) {
            return new ItemType(in);
        }

        @Override
        public ItemType[] newArray(int size) {
            return new ItemType[size];
        }
    };

    public String getDesc() {
        return mDesc;
    }

    public ContentType getContentType() {
        return mContentType;
    }

    boolean isSensitive() {
        return mContentType != null && mContentType.isSensitive();
    }
}
