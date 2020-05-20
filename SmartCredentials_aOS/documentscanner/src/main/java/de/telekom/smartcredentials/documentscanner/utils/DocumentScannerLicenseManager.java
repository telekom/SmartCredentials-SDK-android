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

package de.telekom.smartcredentials.documentscanner.utils;

import android.content.Context;

import com.microblink.MicroblinkSDK;
import com.microblink.recognition.InvalidLicenceKeyException;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public class DocumentScannerLicenseManager {

    public static void setLicense(String licenceKey, String licenceName, Context context) {
        MicroblinkSDK.setShowTimeLimitedLicenseWarning(false);
        try {
            MicroblinkSDK.setLicenseKey(licenceKey, licenceName, context);
        } catch (InvalidLicenceKeyException exception) {
            ApiLoggerResolver.logError("LicenseManager", exception.getMessage());
        }
    }

}
