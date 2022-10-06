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

import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.plugins.callbacks.OTPScannerPluginCallback;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.otp.OTPImportFailed;
import de.telekom.smartcredentials.core.otp.OTPImporterCallback;
import de.telekom.smartcredentials.core.otp.OTPImportItem;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public class PluginCallbackOtpConverter {

    public static OTPScannerPluginCallback convertToDomainPluginCallback(final OTPImporterCallback callback, String tag) {
        return new OTPScannerPluginCallback<ScannerPluginUnavailable>() {
            @Override
            public void onScannerStarted() {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onInitialized");
                if (callback == null) {
                    return;
                }
                callback.onInitialized();
            }

            @Override
            public void onScanned(ItemDomainModel result) {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onOTPItemImported");
                if (callback == null) {
                    return;
                }
                callback.onOTPItemImported(new OTPImportItem(result.getUid(), OTPType.getOTP(result.getMetadata().getItemType())));
            }

            @Override
            public void onPluginUnavailable(ScannerPluginUnavailable errorMessage) {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onPluginUnavailable");
                if (callback == null) {
                    return;
                }
                callback.onScannerUnavailable(errorMessage);
            }

            @Override
            public void onFailed(Exception e) {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onFailed");
                if (callback == null) {
                    return;
                }
                callback.onScannerUnavailable(ScannerPluginUnavailable.SURFACE_UNAVAILABLE);
            }

            @Override
            public void onSaveFailed() {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onSaveFailed; see logs for more info");
                if (callback == null) {
                    return;
                }
                callback.onOTPItemImportFailed(OTPImportFailed.OTP_ITEM_NOT_SAVED);
            }

            @Override
            public void onEncryptionFailed() {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onFailed; see logs for more info");
                if (callback == null) {
                    return;
                }
                callback.onOTPItemImportFailed(OTPImportFailed.OTP_ITEM_NOT_ENCRYPTED);
            }

            @Override
            public void onParseFailed() {
                ApiLoggerResolver.logCallbackMethod(tag, OTPImporterCallback.TAG, "onFailed; see logs for more info");
                if (callback == null) {
                    return;
                }
                callback.onOTPItemImportFailed(OTPImportFailed.OTP_ITEM_NOT_FOUND);
            }
        };
    }

}
