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

import android.content.Context;
import android.graphics.Rect;

import com.google.android.gms.vision.Detector;

import de.telekom.smartcredentials.camera.R;
import de.telekom.smartcredentials.camera.barcode.barcodetrackers.BarcodeTrackerFactory;
import de.telekom.smartcredentials.camera.barcode.di.BarcodeObjectCreator;
import de.telekom.smartcredentials.camera.barcode.presenters.BarcodeCameraScannerPresenter;
import de.telekom.smartcredentials.camera.barcode.presenters.BarcodeCameraScannerView;
import de.telekom.smartcredentials.camera.views.CameraScannerLayoutImpl;
import de.telekom.smartcredentials.camera.views.DetectedBlockView;
import de.telekom.smartcredentials.camera.views.presenters.CameraScannerPresenter;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

public class BarcodeCameraScannerLayoutImpl extends CameraScannerLayoutImpl implements BarcodeCameraScannerView, BarcodeCameraScannerLayout {

    BarcodeTrackerFactory mBarcodeTrackerFactory;
    BarcodeCameraScannerPresenter mBarcodeCameraScannerPresenter;
    Detector mDetector;
    Detector.Processor mProcessor;
    private int mBarcodeFormat;
    private BarcodeBlockView mBarcodeBlockView;

    public static BarcodeCameraScannerLayoutImpl getNewInstance(Context context, ScannerPluginCallback<ScannerPluginUnavailable> pluginCallback, int barcodeFormat) {
        return new BarcodeCameraScannerLayoutImpl(context, pluginCallback, barcodeFormat);
    }

    public BarcodeCameraScannerLayoutImpl(Context context) {
        super(context, null);
    }

    private BarcodeCameraScannerLayoutImpl(Context context, ScannerPluginCallback<ScannerPluginUnavailable> pluginCallback, int barcodeFormat) {
        super(context, pluginCallback);
        mBarcodeFormat = barcodeFormat;
    }

    @Override
    public int getCustomLayoutResource() {
        return R.layout.sc_layout_barcode_scanner;
    }

    @Override
    public void setupCustomDetectedBlockView() {
        mBarcodeBlockView = mCustomDetectedBlockView.findViewById(R.id.sc_view_barcode_block);
    }

    @Override
    public void assembleView() {
        BarcodeObjectCreator objectCreator = new BarcodeObjectCreator(mContext, mBarcodeFormat, mBarcodeBlockView);
        mBarcodeTrackerFactory = objectCreator.provideBarcodeTrackerFactory();
        mBarcodeCameraScannerPresenter = objectCreator.provideBarcodeScannerPresenter();
        mDetector = objectCreator.provideDetector();
        mProcessor = objectCreator.provideProcessor();
    }

    @Override
    public void informPresenterViewIsReady() {
        mBarcodeCameraScannerPresenter.viewReady(this);
    }

    @Override
    public CameraScannerPresenter getPresenter() {
        return mBarcodeCameraScannerPresenter;
    }

    @Override
    public Detector getDetector() {
        return mDetector;
    }

    @Override
    public Detector.Processor getProcessor() {
        return mProcessor;
    }

    @Override
    protected DetectedBlockView getDetectedBlockView() {
        return mBarcodeBlockView;
    }

    @Override
    public void setBarcodeTrackerFactoryListener() {
        mBarcodeTrackerFactory.setListener(mBarcodeCameraScannerPresenter);
    }

    @Override
    public void startScanner(Rect rect) {
        super.startScanner(rect);
    }

    @Override
    public void stopScanner() {
        super.stopScanner();

        if (mBarcodeTrackerFactory != null) {
            mBarcodeTrackerFactory.setListener(null);
        }
    }
}
