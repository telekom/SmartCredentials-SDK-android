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

package de.telekom.smartcredentials.core.api;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.documentscanner.DocumentScanConfiguration;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerCallback;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public interface DocumentScannerApi<T extends DocumentScanConfiguration> {

    /**
     * Method used to scan Document (ID or Payment) cards.
     *
     * @param configuration {@link DocumentScanConfiguration} Configuration object that contains
     *                      all necessary camera and scanning configuration
     * @param callback      {@link DocumentScannerCallback} callback used for retrieving scanning events
     * @return {@link SmartCredentialsApiResponse} containing a {@link DocumentScannerLayout} if response was successful
     * or {@link RootedThrowable} if the device is rooted
     * or {@link FeatureNotSupportedThrowable} if the scanning Document Cards feature is not supported
     * on device (get reason by calling getError().getMessage() on response object)
     * @throws NullPointerException if configuration is null
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<DocumentScannerLayout> getDocumentScannerView(@NonNull T configuration,
                                                                              DocumentScannerCallback callback);

}
