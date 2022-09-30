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

import android.content.Context;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.camera.BarcodeType;
import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 09, 2018.
 */
public interface CameraApi<V> {

    /**
     * Method used to get a QR Scanner.
     *
     * @param callback    {@link ScannerCallback} for retrieving success or failure events
     * @param barcodeType {@link BarcodeType} type of wanted barcode. If argument is null, all barcodes will be detected
     * @return {@link SmartCredentialsApiResponse} containing a {@link CameraScannerLayout} if response was successful,
     * or {@link RootedThrowable} if device is rooted
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> getBarcodeScannerView(@NonNull Context context,
                                                        CameraScannerLayout<V> cameraScannerLayout,
                                                        @NonNull ScannerCallback callback,
                                                        BarcodeType barcodeType);

    /**
     * Method used to get a OCR Scanner View.
     *
     * @param callback {@link ScannerCallback} for retrieving success or failure events
     * @return {@link SmartCredentialsApiResponse} containing a {@link CameraScannerLayout} if response was successful,
     * or {@link RootedThrowable} if device is rooted
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<CameraScannerLayout<V>> getOcrScannerView(@NonNull Context context,
                                                                          @NonNull ScannerCallback callback);
}
