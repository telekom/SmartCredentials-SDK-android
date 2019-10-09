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
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Lucian Iacob on December 07, 2018.
 */
public class ItemPrivateData implements Parcelable, Serializable {

    private JSONObject mPrivateData;

    public ItemPrivateData(JSONObject privateData) {
        mPrivateData = privateData;
    }

    private ItemPrivateData(Parcel in) throws JSONException {
        String detailsString = in.readString();
        mPrivateData = detailsString == null ? null : new JSONObject(detailsString);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPrivateData == null ? null : mPrivateData.toString());
    }

    public static final Creator<ItemPrivateData> CREATOR = new Creator<ItemPrivateData>() {
        @Nullable
        @Override
        public ItemPrivateData createFromParcel(Parcel in) {
            try {
                return new ItemPrivateData(in);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public ItemPrivateData[] newArray(int size) {
            return new ItemPrivateData[size];
        }
    };

    public JSONObject getData() {
        return mPrivateData;
    }
}
