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

package de.telekom.smartcredentials.camera.barcode.barcodetrackers;

import android.support.annotation.UiThread;

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import de.telekom.smartcredentials.camera.barcode.BarcodeBlockView;
import de.telekom.smartcredentials.camera.barcode.BarcodeTextBlock;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

public class BarcodeTracker extends Tracker<Barcode> {

    private final BarcodeBlockView mBarcodeBlockView;
    BarcodeUpdateListener mBarcodeUpdateListener;

    BarcodeTracker(BarcodeUpdateListener listener, BarcodeBlockView barcodeBlockView) {
        mBarcodeUpdateListener = listener;
        mBarcodeBlockView = barcodeBlockView;
    }

    @Override
    public void onNewItem(int id, Barcode item) {
        if (item == null)
            return;

        ApiLoggerResolver.logInfo("Detected barcode: " + item.rawValue);
        mBarcodeBlockView.clearBlockView();
        mBarcodeBlockView.addBlockView(new BarcodeTextBlock(mBarcodeBlockView, item));
        if (mBarcodeUpdateListener != null) {
            mBarcodeUpdateListener.onBarcodeDetected(mBarcodeBlockView);
        }
    }

    public interface BarcodeUpdateListener {
        @UiThread
        void onBarcodeDetected(BarcodeBlockView barcodeBlockView);
    }
}
