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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import de.telekom.smartcredentials.core.actions.SmartCredentialsAction;
import de.telekom.smartcredentials.core.model.item.ItemDomainData;
import de.telekom.smartcredentials.core.model.item.ItemDomainMetadata;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.responses.EnvelopeException;
import de.telekom.smartcredentials.core.responses.EnvelopeExceptionReason;

public class ItemEnvelope implements Parcelable, Serializable {

    @SuppressWarnings("WeakerAccess") public static final String KEY_ID = "id";
    @SuppressWarnings("WeakerAccess") public static final String KEY_TYPE = "type";
    @SuppressWarnings("WeakerAccess") public static final String KEY_USER_ID = "user_id";
    @SuppressWarnings("WeakerAccess") public static final String KEY_IDENTIFIER = "identifier";
    @SuppressWarnings("WeakerAccess") public static final String KEY_METADATA = "metadata";
    @SuppressWarnings("WeakerAccess") public static final String KEY_AUTOLOCK = "auto_lock";
    @SuppressWarnings("WeakerAccess") public static final String KEY_LOCK = "lock";
    @SuppressWarnings("WeakerAccess") public static final String KEY_ACTION_LIST = "action_list";
    @SuppressWarnings("WeakerAccess") public static final String KEY_PRIVATE_DATA = "private_data";
    @SuppressWarnings("WeakerAccess") public static final String KEY_ACTION_ID = "action_id";
    @SuppressWarnings("WeakerAccess") public static final String KEY_ACTION_NAME = "module_name";
    @SuppressWarnings("WeakerAccess") public static final String KEY_ACTION_DATA = "action_data";
    @SuppressWarnings("unused") public static final String KEY_DETAILS = "details";

    private String mItemId;
    private String mUserId;
    private ItemType mItemType;
    private JSONObject mIdentifier;
    private ItemMetadata mItemMetadata;

    public ItemEnvelope() {
        // required empty constructor
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mItemId);
        dest.writeString(mUserId);
        dest.writeString(mIdentifier == null ? null : mIdentifier.toString());
        dest.writeParcelable(mItemType, flags);
        dest.writeParcelable(mItemMetadata, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @NonNull
        @Override
        public ItemEnvelope createFromParcel(Parcel source) {
            try {
                return new ItemEnvelope(source);
            } catch (JSONException e) {
                throw new EnvelopeException(EnvelopeExceptionReason.JSON_EXCEPTION);
            }
        }

        @Override
        public ItemEnvelope[] newArray(int size) {
            return new ItemEnvelope[size];
        }
    };

    public String getItemId() {
        return mItemId;
    }

    public ItemEnvelope setItemId(String itemId) {
        mItemId = itemId;
        return this;
    }

    public String getUserId() {
        return mUserId;
    }

    public ItemEnvelope setUserId(String userId) {
        mUserId = userId;
        return this;
    }

    public JSONObject getIdentifier() {
        return mIdentifier;
    }

    public ItemEnvelope setIdentifier(JSONObject identifier) {
        mIdentifier = identifier;
        return this;
    }

    public JSONObject getDetails() {
        checkMetadataNonNull(mItemMetadata);

        return mItemMetadata.getDetails();
    }

    public boolean isSensitiveItem() throws EnvelopeException {
        checkItemTypeNotNull(mItemType);
        return mItemType.isSensitive();
    }

    public ItemEnvelope setItemMetadata(ItemMetadata itemMetadata) throws EnvelopeException {
        checkMetadataNonNull(itemMetadata);

        mItemMetadata = itemMetadata;
        return this;
    }

    public ItemMetadata getItemMetadata() {
        return mItemMetadata;
    }

    public ItemType getItemType() {
        return mItemType;
    }

    public ItemEnvelope setItemType(ItemType itemType) throws EnvelopeException {
        checkItemTypeNotNull(itemType);

        mItemType = itemType;
        return this;
    }

    public ItemDomainModel toItemDomainModel(String userId) {
        ItemDomainModel domainModel = new ItemDomainModel();

        return domainModel.setId(mItemId)
                .setMetadata(toItemDomainMetadata(userId))
                .setData(getItemDomainData());
    }

    public List<SmartCredentialsAction> getActionList() {
        checkMetadataNonNull(mItemMetadata);

        return mItemMetadata.getActionList();
    }

    public boolean isAutoLockEnabled() {
        checkMetadataNonNull(mItemMetadata);

        return mItemMetadata.isAutoLockEnabled();
    }

    public boolean isLocked() {
        checkMetadataNonNull(mItemMetadata);

        return mItemMetadata.isLocked();
    }

    private ItemEnvelope(Parcel in) throws JSONException {
        mItemId = in.readString();
        mUserId = in.readString();
        String identifierString = in.readString();
        mIdentifier = identifierString == null ? null : new JSONObject(identifierString);
        mItemType = in.readParcelable(ItemType.class.getClassLoader());
        mItemMetadata = in.readParcelable(ItemMetadata.class.getClassLoader());
    }

    private ItemDomainData getItemDomainData() throws EnvelopeException {
        checkMetadataNonNull(mItemMetadata);

        return new ItemDomainData()
                .setIdentifier(mIdentifier != null ? mIdentifier.toString() : null)
                .setPrivateData(mItemMetadata.getDetails() != null ? mItemMetadata.getDetails().toString() : null);
    }

    private ItemDomainMetadata toItemDomainMetadata(String userId) throws EnvelopeException {
        checkMetadataNonNull(mItemMetadata);

        ItemDomainMetadata itemDomainMetadata = new ItemDomainMetadata(mItemMetadata.isDataEncrypted(),
                mItemMetadata.isAutoLockEnabled(), mItemMetadata.isLocked());
        checkItemTypeNotNull(mItemType);

        return itemDomainMetadata
                .setItemType(mItemType.getDesc())
                .setUserId(userId)
                .setContentType(mItemType.getContentType());
    }

    private void checkMetadataNonNull(ItemMetadata metadata) throws EnvelopeException {
        checkNotNull(metadata, EnvelopeExceptionReason.NO_METADATA_EXCEPTION_MSG);
    }

    private void checkItemTypeNotNull(ItemType itemType) throws EnvelopeException {
        checkNotNull(itemType, EnvelopeExceptionReason.NO_ITEM_TYPE_EXCEPTION_MSG);
    }

    private void checkNotNull(Object o, EnvelopeExceptionReason exceptionMessage) throws EnvelopeException {
        if (o == null) {
            throw new EnvelopeException(exceptionMessage);
        }
    }

}
