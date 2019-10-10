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

package de.telekom.smartcredentials.camera.ocr.di;

import android.content.Context;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextRecognizer;

import de.telekom.smartcredentials.camera.ocr.OcrBlockView;
import de.telekom.smartcredentials.camera.ocr.OcrDetectorProcessor;
import de.telekom.smartcredentials.camera.ocr.presenters.OcrCameraScannerPresenter;

/**
 * Created by Lucian Iacob on November 05, 2018.
 */
public class OcrObjectCreator {

    private final Context mContext;
    private final OcrBlockView mOcrBlockView;
    private final OcrCameraScannerPresenter mPresenter;

    public OcrObjectCreator(Context context, OcrBlockView ocrBlockView) {
        mContext = context;
        mOcrBlockView = ocrBlockView;
        mPresenter = new OcrCameraScannerPresenter();
    }

    public OcrCameraScannerPresenter provideOcrScannerPresenter() {
        return mPresenter;
    }

    public Detector provideDetector() {
        return new TextRecognizer
                .Builder(mContext)
                .build();
    }

    public OcrDetectorProcessor provideProcessor() {
        return new OcrDetectorProcessor(mOcrBlockView);
    }
}
