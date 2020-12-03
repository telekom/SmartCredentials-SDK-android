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

package de.telekom.smartcredentials.documentscanner.factory;

import android.content.Context;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.DocumentScannerApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsModuleSet;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.exceptions.InvalidCoreApiException;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.controllers.DocumentScannerController;
import de.telekom.smartcredentials.documentscanner.di.ObjectGraphCreatorDocumentScanner;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.documentscanner.utils.DocumentScannerLicenseManager;

@SuppressWarnings("unused")
public class SmartCredentialsDocumentScannerFactory {

    private static final String MODULE_NOT_INITIALIZED_EXCEPTION = "SmartCredentials DocumentScanner Module have not been initialized";

    private static DocumentScannerController sDocumentScannerController;

    private SmartCredentialsDocumentScannerFactory() {
        // required empty constructor
    }

    @NonNull
    public static synchronized DocumentScannerApi<SmartCredentialsDocumentScanConfiguration, ScannerRecognizer> initSmartCredentialsDocumentScannerModule(@NonNull final CoreApi coreApi) {
        CoreController coreController;

        if (coreApi instanceof CoreController) {
            coreController = (CoreController) coreApi;
        } else {
            throw new InvalidCoreApiException(SmartCredentialsModuleSet.DOCUMENT_SCANNER_MODULE.getModuleName());
        }
        sDocumentScannerController = ObjectGraphCreatorDocumentScanner.getInstance()
                .provideApiControllerDocumentScanner(coreController);
        return sDocumentScannerController;
    }

    public static void setLicense(@NonNull final String licenceKey, @NonNull final Context context) {
        DocumentScannerLicenseManager.setLicense(licenceKey, context);
    }

    @NonNull
    public static synchronized DocumentScannerApi<SmartCredentialsDocumentScanConfiguration, ScannerRecognizer> getDocumentScannerApi() {
        if (sDocumentScannerController == null) {
            throw new RuntimeException(MODULE_NOT_INITIALIZED_EXCEPTION);
        }
        return sDocumentScannerController;
    }

    public static void clear() {
        ObjectGraphCreatorDocumentScanner.destroy();
        sDocumentScannerController = null;
    }
}
