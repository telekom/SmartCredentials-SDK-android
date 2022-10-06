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

package de.telekom.smartcredentials.documentscanner.callback;

import android.graphics.Rect;

import de.telekom.smartcredentials.core.documentscanner.DocumentScannerCallback;
import de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

public class PluginCallbackDocScannerConverter {

    public static DocumentScannerPluginCallback convertToDomainPluginCallback(final DocumentScannerCallback callback, String tag) {
        return new DocumentScannerPluginCallback<ScannerPluginUnavailable>() {
            @Override
            public void onFirstSideRecognitionFinished() {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onFirstSideRecognitionFinished");
                if (callback == null) {
                    return;
                }
                callback.onFirstSideRecognitionFinished();
            }

            @Override
            public void onScannerStarted() {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onInitialized");
                if (callback == null) {
                    return;
                }
                callback.onInitialized();
            }

            @Override
            public void onScanned(DocumentScannerResult result) {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onScanned");
                if (callback == null) {
                    return;
                }
                callback.onDetected(result);
            }

            @Override
            public void onPluginUnavailable(ScannerPluginUnavailable errorMessage) {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onPluginUnavailable");
                if (callback == null) {
                    return;
                }
                callback.onScannerUnavailable(errorMessage);
            }

            @Override
            public void onFailed(Exception e) {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onFailed");
                if (callback == null) {
                    return;
                }
                callback.onScannedFailed();
            }

            @Override
            public void onScannedFailed() {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onScannedFailed");
                if (callback == null) {
                    return;
                }
                callback.onScannedFailed();
            }

            @Override
            public void onScannerStopped() {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onScannerStopped");
                if (callback == null) {
                    return;
                }
                callback.onScannerStopped();
            }

            @Override
            public void onAutoFocusStarted(Rect[] rects) {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onAutoFocusStarted");
                if (callback == null) {
                    return;
                }
                callback.onAutoFocusStarted(rects);
            }

            @Override
            public void onAutoFocusStopped(Rect[] rects) {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onAutoFocusStopped");
                if (callback == null) {
                    return;
                }
                callback.onAutoFocusStopped(rects);
            }

            @Override
            public void onAutoFocusFailed() {
                ApiLoggerResolver.logCallbackMethod(tag, DocumentScannerCallback.TAG, "onAutoFocusFailed");
                if (callback == null) {
                    return;
                }
                callback.onAutofocusFailed();
            }
        };
    }
}
