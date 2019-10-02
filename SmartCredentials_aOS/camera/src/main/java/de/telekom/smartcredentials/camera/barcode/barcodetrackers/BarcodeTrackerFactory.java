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

import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import de.telekom.smartcredentials.camera.barcode.BarcodeBlockView;

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {

    private final BarcodeBlockView mBarcodeBlockView;
    private BarcodeTracker.BarcodeUpdateListener mListener;

    public BarcodeTrackerFactory(BarcodeBlockView barcodeBlockView) {
        mBarcodeBlockView = barcodeBlockView;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        return new BarcodeTracker(mListener, mBarcodeBlockView);
    }

    public void setListener(BarcodeTracker.BarcodeUpdateListener listener) {
        mListener = listener;
    }
}
