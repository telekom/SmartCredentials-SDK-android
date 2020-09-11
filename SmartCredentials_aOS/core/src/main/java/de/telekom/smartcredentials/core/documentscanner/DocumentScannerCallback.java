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

package de.telekom.smartcredentials.core.documentscanner;

import android.graphics.Rect;
import androidx.annotation.WorkerThread;

import de.telekom.smartcredentials.core.callback.BaseScannerCallback;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public abstract class DocumentScannerCallback extends BaseScannerCallback<ScannerPluginUnavailable> {

    public static final String TAG = "DocumentScannerCallback";

    /**
     * Callback used to get the scanner result.
     * Please be advised that you need to check the concrete instance of {@link DocumentScannerResult}
     * in order to get all relevant info
     *
     * @param scannerResult object containing scanned info
     */
    @WorkerThread
    public abstract void onDetected(DocumentScannerResult scannerResult);

    /**
     * Callback that will be triggered when the first side of an ID card was scanned.
     * This will be invoked only in an combined recognizer setup.
     */
    public void onFirstSideRecognitionFinished() {
        // no implementation
    }

    /**
     * Callback invoked when the recognizer failed to detect anything on the image
     */
    public void onScannedFailed() {
        // no implementation
    }

    /**
     * Method that will be triggered just after camera has been stopped.
     * This means that frames will no longer arrive to the recognizer.
     */
    public void onScannerStopped() {
        // no implementation
    }

    /**
     * Callback that will be triggered when autofocus has started.
     *
     * @param focusAreas areas where focus is being measured or null on devices that do not support fine-grained camera control
     */
    public void onAutoFocusStarted(Rect[] focusAreas) {
        // no implementation
    }

    /**
     * Callback that will be called when camera focusing has stopped
     *
     * @param focusAreas areas where focus is being measured
     */
    public void onAutoFocusStopped(Rect[] focusAreas) {
        // no implementation
    }

    /**
     * Callback called when camera focusing has failed after all camera focusing strategies was tried.
     */
    public void onAutofocusFailed() {
        // no implementation
    }
}
