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

package de.telekom.smartcredentials.camera.barcode.di;

import android.content.Context;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import de.telekom.smartcredentials.camera.barcode.BarcodeBlockView;
import de.telekom.smartcredentials.camera.barcode.barcodetrackers.BarcodeTrackerFactory;
import de.telekom.smartcredentials.camera.barcode.presenters.BarcodeCameraScannerPresenter;

/**
 * Created by Lucian Iacob on November 05, 2018.
 */
public class BarcodeObjectCreator {

    private final Context mContext;
    private final int mBarcodeFormat;
    private final BarcodeTrackerFactory mBarcodeTrackerFactory;

    public BarcodeObjectCreator(Context context, int barcodeFormat, BarcodeBlockView barcodeBlockView) {
        mContext = context;
        mBarcodeFormat = barcodeFormat;
        mBarcodeTrackerFactory = new BarcodeTrackerFactory(barcodeBlockView);
    }

    public BarcodeTrackerFactory provideBarcodeTrackerFactory() {
        return mBarcodeTrackerFactory;
    }

    public BarcodeCameraScannerPresenter provideBarcodeScannerPresenter() {
        return new BarcodeCameraScannerPresenter();
    }

    public Detector provideDetector() {
        return new BarcodeDetector.Builder(mContext)
                .setBarcodeFormats(mBarcodeFormat)
                .build();
    }

    public Detector.Processor provideProcessor() {
        return new MultiProcessor.Builder<>(mBarcodeTrackerFactory)
                .build();
    }
}
