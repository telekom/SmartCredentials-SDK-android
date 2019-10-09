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

package de.telekom.smartcredentials.camera.barcode.presenters;

import android.graphics.Rect;

import com.google.android.gms.vision.barcode.Barcode;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;

import de.telekom.smartcredentials.camera.barcode.BarcodeBlockView;
import de.telekom.smartcredentials.camera.barcode.BarcodeCameraScannerLayoutImpl;
import de.telekom.smartcredentials.camera.barcode.BarcodeTextBlock;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class BarcodeCameraScannerPresenterTest {

    private final int mLeft = 100;
    private final int mTop = 100;
    private final int mRight = 200;
    private final int mBottom = 200;
    private BarcodeCameraScannerLayoutImpl mView;
    private ScannerPluginCallback mPluginCallback;
    private BarcodeCameraScannerPresenter mPresenter;
    private BarcodeBlockView mBarcodeBlockView;

    @Before
    public void setUp() {
        mView = Mockito.mock(BarcodeCameraScannerLayoutImpl.class);
        mPluginCallback = Mockito.mock(ScannerPluginCallback.class);

        mPresenter = new BarcodeCameraScannerPresenter();
        mPresenter.viewReady(mView);
        mPresenter.setPluginCallback(mPluginCallback);
        mBarcodeBlockView = Mockito.mock(BarcodeBlockView.class);
    }

    @Test
    public void onBarcodeDetectedCallsMethodOnPluginCallbackIfIsInBounds() {
        Barcode barcode = Mockito.mock(Barcode.class);
        String rawValue = "1234";
        barcode.rawValue = rawValue;
        when(mView.getRect()).thenReturn(new Rect(0, 0, 0, 0));
        when(mBarcodeBlockView.getBlocksAtLocation(0, 0, 0, 0))
                .thenReturn(Collections.singletonList(barcode));

        mPresenter.onBarcodeDetected(mBarcodeBlockView);

        verify(mPluginCallback).onScanned(Collections.singletonList(rawValue));
    }

    @Test
    public void onBarcodeDetectedDoesNotCallMethodOnPluginCallbackIfNoBarcodeInBounds() {
        Barcode barcode = Mockito.mock(Barcode.class);
        String rawValue = "1234";
        barcode.rawValue = rawValue;
        when(mView.getRect()).thenReturn(new Rect(mLeft, mTop, mRight, mBottom));
        when(mBarcodeBlockView.getBlocksAtLocation(mLeft, mTop, mRight, mBottom))
                .thenReturn(new ArrayList<>());

        mPresenter.onBarcodeDetected(mBarcodeBlockView);

        verify(mPluginCallback, never()).onScanned(Collections.singletonList(rawValue));
    }

    @Test
    public void onBarcodeDetectedDoesNothingIfPluginCallbackIsReset() {
        mPresenter.setPluginCallback(null);
        Barcode barcode = Mockito.mock(Barcode.class);
        String rawValue = "1234";
        barcode.rawValue = rawValue;
        BarcodeTextBlock barcodeTextBlock = new BarcodeTextBlock(mBarcodeBlockView, barcode);
        mBarcodeBlockView.addBlockView(barcodeTextBlock);
        when(mView.getRect()).thenReturn(new Rect(mLeft, mTop, mRight, mBottom));

        mPresenter.onBarcodeDetected(mBarcodeBlockView);

        verify(mPluginCallback, never()).onScanned(Collections.singletonList(rawValue));
    }

    @Test
    public void startCameraRequestedCallsMethodOnView() {
        mPresenter.startCameraRequested();

        verify(mView).setBarcodeTrackerFactoryListener();
    }
}