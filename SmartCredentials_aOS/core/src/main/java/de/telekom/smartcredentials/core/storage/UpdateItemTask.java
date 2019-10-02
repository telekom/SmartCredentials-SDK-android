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

package de.telekom.smartcredentials.core.storage;

import android.os.AsyncTask;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.context.ItemContext;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class UpdateItemTask extends AsyncTask<Void, Void, SmartCredentialsApiResponse<Integer>> {

    private final StorageApi mApi;
    private final ItemContext mItemContext;
    private final ItemEnvelope mItemEnvelope;

    public UpdateItemTask(StorageApi api, ItemContext itemContext, ItemEnvelope itemEnvelope) {
        mApi = api;
        mItemContext = itemContext;
        mItemEnvelope = itemEnvelope;
    }

    @Override
    protected SmartCredentialsApiResponse<Integer> doInBackground(Void... voids) {
        return mApi.updateItem(mItemEnvelope, mItemContext);
    }

}