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

package de.telekom.smartcredentials.camera.barcode;

import com.google.android.gms.vision.barcode.Barcode;

import de.telekom.smartcredentials.camera.views.DetectedBlock;

import static de.telekom.smartcredentials.camera.barcode.utils.RectComparator.isBarcodeInBounds;

public class BarcodeTextBlock extends DetectedBlock<Barcode> {

    private final Barcode mBarcode;

    public BarcodeTextBlock(BarcodeBlockView overlay, Barcode text) {
        super(overlay);
        mBarcode = text;
    }

    @Override
    public Barcode getBlockBetween(float left, float top, float right, float bottom) {
        Barcode barcode = mBarcode;
        if (barcode != null && isBarcodeInBounds(barcode, left, top, right, bottom, this))
            return mBarcode;

        return null;
    }

}
