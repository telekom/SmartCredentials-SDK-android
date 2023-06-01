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
import androidx.lifecycle.LifecycleOwner;

import de.telekom.smartcredentials.core.camera.BarcodeType;
import de.telekom.smartcredentials.core.camera.SurfaceContainerInteractor;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.SurfaceContainer;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 09, 2018.
 */
public interface CameraApi<SH> {

    /**
     * Method used to read the content from the surface holder provided as a parameter and extract the
     * present barcodes. The results are delivered asynchronously on the scanner callback.
     *
     * @param surfaceContainer    {@link SurfaceContainer} is a container for a {@link android.view.TextureView},
     *                                                     {@link android.view.SurfaceView} or any kind of view
     *                                                      that can display the camera feed
     * @param callback  {@link ScannerCallback} used to return the scan results or failure events
     * @param barcodeType {@link BarcodeType} type of wanted barcode. If argument is null, all barcodes will be detected
     * @return {@link SmartCredentialsApiResponse} true if the barcode scanner was initialized successfully,
     * false otherwise
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> getBarcodeScannerView(@NonNull Context context,
                                                               SurfaceContainer<SH> surfaceContainer,
                                                               LifecycleOwner lifecycleOwner,
                                                               @NonNull ScannerCallback callback,
                                                               BarcodeType barcodeType);

    /**
     * Method used to read the content from the surface holder provided as a parameter and extract the present
     * text. The results are delivered asynchronously on the scanner callback.
     *
     * @param surfaceContainer {@link SurfaceContainer} is a container for a {@link android.view.TextureView},
     *                                                  {@link android.view.SurfaceView} or any kind of view
     *                                                  that can display the camera feed
     * @param callback {@link ScannerCallback} used to return the scanner results or failure events
     * @return {@link SmartCredentialsApiResponse} an interactor of type {@link SurfaceContainerInteractor} that
     * can be used to capture the image on which OCR is performed.
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<SurfaceContainerInteractor> getOcrScannerView(@NonNull Context context,
                                                                              SurfaceContainer<SH> surfaceContainer,
                                                                              LifecycleOwner lifecycleOwner,
                                                                              @NonNull ScannerCallback callback);
}
