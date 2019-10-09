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

package de.telekom.smartcredentials.camera.ocr;

import android.os.AsyncTask;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;

public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private AsyncTask<Void, Void, List<String>> mAsyncTask;
    private final OcrBlockView mOcrBlockView;

    public OcrDetectorProcessor(OcrBlockView ocrOcrBlockView) {
        mOcrBlockView = ocrOcrBlockView;
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mOcrBlockView.clearBlockView();
        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            TextBlock item = items.valueAt(i);
            OcrTextBlock graphic = new OcrTextBlock(mOcrBlockView, item);
            mOcrBlockView.addBlockView(graphic);
        }
    }

    @Override
    public void release() {
        mOcrBlockView.clearBlockView();
        if (mAsyncTask != null && !mAsyncTask.isCancelled()) {
            mAsyncTask.cancel(true);
        }
    }

    void filterDetectedTexts(OnDetectedTextListener onDetectedTextListener, float left, float top, float right, float bottom) {
        mOcrBlockView.blockDetection();
        mAsyncTask = new DetectTextAsyncTask(onDetectedTextListener, mOcrBlockView, left, top, right, bottom)
                .execute();
    }

    public interface OnDetectedTextListener {

        void onDetected(List<String> detectedValues);
    }
}
