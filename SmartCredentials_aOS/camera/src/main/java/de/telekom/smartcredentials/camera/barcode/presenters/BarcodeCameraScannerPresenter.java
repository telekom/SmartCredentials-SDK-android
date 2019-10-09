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

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

import de.telekom.smartcredentials.camera.barcode.BarcodeBlockView;
import de.telekom.smartcredentials.camera.barcode.barcodetrackers.BarcodeTracker;
import de.telekom.smartcredentials.camera.views.presenters.CameraScannerPresenter;
import de.telekom.smartcredentials.camera.views.presenters.WeakRefClassResolver;

public class BarcodeCameraScannerPresenter extends CameraScannerPresenter implements BarcodeTracker.BarcodeUpdateListener {

    private WeakReference<BarcodeCameraScannerView> mView;

    public void viewReady(BarcodeCameraScannerView cameraScanner) {
        super.viewReady(cameraScanner);
        mView = new WeakReference<>(cameraScanner);
    }

    @Override
    public void onBarcodeDetected(BarcodeBlockView barcodeBlockView) {
        new WeakRefClassResolver<BarcodeCameraScannerView>() {
            @Override
            public void onWeakRefResolved(BarcodeCameraScannerView ref) {
                Rect rect = ref.getRect();
                List<Barcode> barcodeList = barcodeBlockView.getBlocksAtLocation(rect.left, rect.top, rect.right, rect.bottom);
                if (!barcodeList.isEmpty() && mPluginCallback != null) {
                    mPluginCallback.onScanned(Collections.singletonList(barcodeList.get(0).rawValue));
                }
            }
        }.execute(mView);
    }

    @Override
    public void startCameraRequested() {
        super.startCameraRequested();

        new WeakRefClassResolver<BarcodeCameraScannerView>() {
            @Override
            public void onWeakRefResolved(BarcodeCameraScannerView ref) {
                ref.setBarcodeTrackerFactoryListener();
            }
        }.execute(mView);
    }
}
