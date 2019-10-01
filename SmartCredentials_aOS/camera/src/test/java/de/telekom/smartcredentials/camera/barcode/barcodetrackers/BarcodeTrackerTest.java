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

import com.google.android.gms.vision.barcode.Barcode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import de.telekom.smartcredentials.camera.barcode.BarcodeBlockView;
import de.telekom.smartcredentials.camera.barcode.BarcodeTextBlock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class BarcodeTrackerTest {

    private BarcodeBlockView mBarcodeBlockView;

    private BarcodeTracker.BarcodeUpdateListener mBarcodeUpdateListener;

    private BarcodeTracker mBarcodeTracker;

    @Before
    public void setUp() {
        mBarcodeUpdateListener = Mockito.mock(BarcodeTracker.BarcodeUpdateListener.class);
        mBarcodeBlockView = Mockito.mock(BarcodeBlockView.class);
        mBarcodeTracker = new BarcodeTracker(mBarcodeUpdateListener, mBarcodeBlockView);
    }

    @Test
    public void onNewItemCallsListenerMethod() {
        Barcode item = Mockito.mock(Barcode.class);
        mBarcodeTracker.onNewItem(0, item);

        verify(mBarcodeBlockView).clearBlockView();
        verify(mBarcodeBlockView).addBlockView(any(BarcodeTextBlock.class));
        verify(mBarcodeUpdateListener).onBarcodeDetected(mBarcodeBlockView);
    }

    @Test
    public void onNewItemDoesNothingIfListenerIsMissing() {
        Barcode item = Mockito.mock(Barcode.class);
        mBarcodeTracker.mBarcodeUpdateListener = null;
        mBarcodeTracker.onNewItem(0, item);

        verify(mBarcodeUpdateListener, never()).onBarcodeDetected(mBarcodeBlockView);
    }

}