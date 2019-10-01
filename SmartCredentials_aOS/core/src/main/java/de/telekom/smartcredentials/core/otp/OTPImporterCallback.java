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

package de.telekom.smartcredentials.core.otp;

import de.telekom.smartcredentials.core.callback.BaseScannerCallback;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;

public abstract class OTPImporterCallback extends BaseScannerCallback<ScannerPluginUnavailable> {

    public static final String TAG = "OTPImporterCallback";

    /**
     * Used to return the OTP item that was imported.
     *
     * @param otpImportItem returned item
     */
    public abstract void onOTPItemImported(OTPImportItem otpImportItem);

    /**
     * Used to signal that the OTP item import has failed.
     *
     * @param otpHandlerFailed returned error message
     */
    public abstract void onOTPItemImportFailed(OTPImportFailed otpHandlerFailed);
}
