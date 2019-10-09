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

package de.telekom.smartcredentials.camera.ocr.presenters;

import java.lang.ref.WeakReference;
import java.util.List;

import de.telekom.smartcredentials.camera.ocr.OcrDetectorProcessor;
import de.telekom.smartcredentials.camera.ocr.TextParser;
import de.telekom.smartcredentials.camera.views.presenters.CameraScannerPresenter;
import de.telekom.smartcredentials.camera.views.presenters.WeakRefClassResolver;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.SCANNER_NOT_STARTED;

public class OcrCameraScannerPresenter extends CameraScannerPresenter implements OcrDetectorProcessor.OnDetectedTextListener {

    ParserAsyncTask mParserAsyncTask;
    TextParser mTextParser;
    private WeakReference<OcrCameraScannerView> mView;

    @Override
    public void onDetected(List<String> detectedValues) {
        if (mTextParser != null) {
            mParserAsyncTask = ParserAsyncTask.getInstance(mPluginCallback, mTextParser);
            mParserAsyncTask.execute(detectedValues.toArray(new String[0]));
        } else {
            ParserAsyncTask.notifyScannedResultsReady(mPluginCallback, detectedValues);
        }
    }

    public void viewReady(OcrCameraScannerView cameraScanner) {
        super.viewReady(cameraScanner);
        mView = new WeakReference<>(cameraScanner);
    }

    public void prepareDetection(TextParser textParser, boolean capturePicture) {
        mTextParser = textParser;
        if (!mIsCameraStarted) {
            onError(SCANNER_NOT_STARTED);
            return;
        }
        if (capturePicture) {
            new WeakRefClassResolver<OcrCameraScannerView>() {
                @Override
                public void onWeakRefResolved(OcrCameraScannerView ref) {
                    ref.takeCurrentPicture();
                }
            }.execute(mView);
        } else {
            onShutter();
        }
    }

    public void onShutter() {
        new WeakRefClassResolver<OcrCameraScannerView>() {
            @Override
            public void onWeakRefResolved(OcrCameraScannerView ref) {
                ref.processDetectedTextBlocks(OcrCameraScannerPresenter.this);
            }
        }.execute(mView);
    }

    public void onPictureTaken(byte[] bytes) {
        new WeakRefClassResolver<OcrCameraScannerView>() {
            @Override
            public void onWeakRefResolved(OcrCameraScannerView ref) {
                ref.loadPicture(bytes);
            }
        }.execute(mView);
    }

    public void onStopRequested() {
        if (mParserAsyncTask != null && !mParserAsyncTask.isCancelled()) {
            mParserAsyncTask.cancel(true);
        }
    }
}
