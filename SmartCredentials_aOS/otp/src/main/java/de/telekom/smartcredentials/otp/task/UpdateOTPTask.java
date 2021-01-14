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

package de.telekom.smartcredentials.otp.task;

import android.os.AsyncTask;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.storage.TokenRequest;

public class UpdateOTPTask extends AsyncTask<Void, Void, Boolean> {

    private final StorageApi mStorageApi;
    private final ItemDomainModel mItemDomainModel;
    private final TokenRequest mUpdatedTokenRequest;

    public UpdateOTPTask(StorageApi storageApi, TokenRequest updatedTokenRequest,
                         ItemDomainModel itemDomainModel) {
        mStorageApi = storageApi;
        mUpdatedTokenRequest = updatedTokenRequest;
        mItemDomainModel = itemDomainModel;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        ItemDomainModel retrievedItem = mStorageApi.retrieveItemSummaryByUniqueIdAndType(mItemDomainModel).getData();
        return mStorageApi.putItem(retrievedItem, mUpdatedTokenRequest).isSuccessful();
    }
}
