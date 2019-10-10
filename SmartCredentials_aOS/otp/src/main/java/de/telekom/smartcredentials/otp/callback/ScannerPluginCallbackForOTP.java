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

package de.telekom.smartcredentials.otp.callback;


import android.net.Uri;

import java.util.List;
import java.util.Locale;

import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
import de.telekom.smartcredentials.otp.converters.UriToItemDomainModelConverter;

public class ScannerPluginCallbackForOTP extends ScannerCallback {

    private String mItemId;
    private String mUserId;
    private OTPScannerPluginCallback mOTPScannerPluginCallback;
    private final StorageApi mStorageApi;
    private final UriToItemDomainModelConverter mUriToItemDomainModelConverter;

    public ScannerPluginCallbackForOTP(StorageApi storageApi, UriToItemDomainModelConverter uriToItemDomainModelConverter) {
        mStorageApi = storageApi;
        mUriToItemDomainModelConverter = uriToItemDomainModelConverter;
    }

    public void init(String itemId, String userId, OTPScannerPluginCallback otpScannerPluginCallback) {
        mItemId = itemId;
        mUserId = userId;
        mOTPScannerPluginCallback = otpScannerPluginCallback;
    }

    private ItemDomainModel getParsedModelFromScannerResults(List<String> result) {
        for (String scannedResult : result) {
            ItemDomainModel itemDomainModel = parseUri(Uri.parse(scannedResult));
            if (itemDomainModel != null) {
                return itemDomainModel;
            }
        }
        return null;
    }

    private ItemDomainModel parseUri(Uri uri) {
        return mUriToItemDomainModelConverter.parseOTPUri(mItemId, mUserId, uri);
    }

    private void saveParsedUri(ItemDomainModel itemDomainModel) throws EncryptionException {
        ApiLoggerResolver.logCallbackMethod(TAG, ScannerPluginCallbackForOTP.TAG, "saving OTP item...");

        int savedItemsCount = mStorageApi.putItem(itemDomainModel).getData();
        if (savedItemsCount > 0) {
            mOTPScannerPluginCallback.onScanned(itemDomainModel);
        } else {
            mOTPScannerPluginCallback.onSaveFailed();
        }
    }

    @Override
    public void onDetected(List<String> detectedValues) {
        int resultCount = detectedValues != null ? detectedValues.size() : 0;
        ApiLoggerResolver.logCallbackMethod(TAG, ScannerPluginCallbackForOTP.TAG,
                String.format("QROTPScanner: scanned; %s", String.format(Locale.US, "got %d results", resultCount)));

        ItemDomainModel itemDomainModel = null;
        if (resultCount > 0) {
            itemDomainModel = getParsedModelFromScannerResults(detectedValues);
            if (itemDomainModel != null) {
                try {
                    saveParsedUri(itemDomainModel);
                } catch (EncryptionException e) {
                    mOTPScannerPluginCallback.onEncryptionFailed();
                }
            }
        }
        if (itemDomainModel == null) {
            mOTPScannerPluginCallback.onParseFailed();
        }
    }

    @Override
    public void onInitialized() {
        ApiLoggerResolver.logCallbackMethod(TAG, OTPScannerPluginCallback.TAG, "QROTPScanner: onScannerStarted");
        mOTPScannerPluginCallback.onScannerStarted();
    }

    @Override
    public void onScannerUnavailable(ScannerPluginUnavailable errorMessage) {
        ApiLoggerResolver.logCallbackMethod(TAG, OTPScannerPluginCallback.TAG, "QROTPScanner: onPluginUnavailable");
        mOTPScannerPluginCallback.onPluginUnavailable(errorMessage);
    }
}
