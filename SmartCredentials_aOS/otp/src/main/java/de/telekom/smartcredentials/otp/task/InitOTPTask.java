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

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.otp.OTPHandlerCallback;
import de.telekom.smartcredentials.otp.otp.OTPUpdateCallback;

public class InitOTPTask extends AsyncTask<Void, Void, Void> {

    private final OTPHandlerResolver mOTPHandlerResolver;
    private final SecurityApi mSecurityApi;
    private final StorageApi mStorageApi;
    private final OTPHandlerCallback mOtpHandlerCallback;
    private final ItemDomainModel mItemDomainModel;

    InitOTPTask(SecurityApi securityApi, StorageApi storageApi,
                OTPHandlerCallback otpHandlerCallback, ItemDomainModel itemDomainModel) {
        mSecurityApi = securityApi;
        mStorageApi = storageApi;
        mOtpHandlerCallback = otpHandlerCallback;
        mItemDomainModel = itemDomainModel;
        mOTPHandlerResolver = new OTPHandlerResolver(getOTPUpdateCallback());
    }

    @Override
    protected Void doInBackground(Void... voids) {
        mOTPHandlerResolver.resolveHandler(mSecurityApi, mStorageApi,
                mOtpHandlerCallback, mItemDomainModel);
        return null;
    }

    private OTPUpdateCallback getOTPUpdateCallback() {
        return updatedTokenRequest -> new UpdateOTPTask(mStorageApi, updatedTokenRequest, mItemDomainModel).execute();
    }

    public void run() {
        execute();
    }

    public OTPHandlerResolver getOTPHandlerResolver() {
        return mOTPHandlerResolver;
    }
}
