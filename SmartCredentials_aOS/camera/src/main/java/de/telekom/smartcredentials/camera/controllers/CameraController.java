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

package de.telekom.smartcredentials.camera.controllers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;

import de.telekom.smartcredentials.camera.barcode.BarcodeCameraScannerLayoutImpl;
import de.telekom.smartcredentials.camera.ocr.OcrCameraScannerLayoutImpl;
import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.blacklisting.SmartCredentialsFeatureSet;
import de.telekom.smartcredentials.core.camera.BarcodeType;
import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.controllers.CoreController;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.responses.FeatureNotSupportedThrowable;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.core.responses.SmartCredentialsResponse;

public class CameraController implements CameraApi<PreviewView> {

    private final CoreController mCoreController;

    public CameraController(CoreController coreController) {
        mCoreController = coreController;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<CameraScannerLayout<PreviewView>> getBarcodeScannerView(@NonNull Context context,
                                                                                               @NonNull ScannerCallback callback,
                                                                                               BarcodeType barcodeType) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getBarcodeScannerView");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.QR)) {
            String errorMessage = SmartCredentialsFeatureSet.QR.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        int barcodeFormat = barcodeType == null
                ? BarcodeType.BARCODE_ALL_FORMATS.getFormat()
                : barcodeType.getFormat();
        CameraScannerLayout<PreviewView> cameraScannerLayout = new BarcodeCameraScannerLayoutImpl(context, callback);
        return new SmartCredentialsResponse<>(cameraScannerLayout);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SmartCredentialsApiResponse<CameraScannerLayout<PreviewView>> getOcrScannerView(@NonNull Context context,
                                                                                           @NonNull ScannerCallback callback) {
        ApiLoggerResolver.logMethodAccess(getClass().getSimpleName(), "getBarcodeScannerView");
        if (mCoreController.isSecurityCompromised()) {
            mCoreController.handleSecurityCompromised();
            return new SmartCredentialsResponse<>(new RootedThrowable());
        }

        if (mCoreController.isDeviceRestricted(SmartCredentialsFeatureSet.OCR)) {
            String errorMessage = SmartCredentialsFeatureSet.OCR.getNotSupportedDesc();
            return new SmartCredentialsResponse<>(new FeatureNotSupportedThrowable(errorMessage));
        }

        CameraScannerLayout<PreviewView> ocrCameraScannerLayout = new OcrCameraScannerLayoutImpl(context, callback);
        return new SmartCredentialsResponse<>(ocrCameraScannerLayout);
    }
}
