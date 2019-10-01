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

import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BarcodeTrackerFactoryTest {
    private BarcodeTrackerFactory mBarcodeTrackerFactory;

    @Before
    public void setUp() {
        mBarcodeTrackerFactory = new BarcodeTrackerFactory(null);
    }

    @Test
    public void createReturnsAnInstanceOfBarcodeTracker() {
        BarcodeTracker.BarcodeUpdateListener barcodeUpdateListener = barcodeBlockView -> {
        };
        mBarcodeTrackerFactory.setListener(barcodeUpdateListener);
        Barcode barcode = Mockito.mock(Barcode.class);
        Tracker tracker = mBarcodeTrackerFactory.create(barcode);

        assertTrue(tracker instanceof BarcodeTracker);
        assertEquals(((BarcodeTracker) tracker).mBarcodeUpdateListener, barcodeUpdateListener);
    }

}