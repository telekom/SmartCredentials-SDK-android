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
import android.media.Image;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import de.telekom.smartcredentials.camera.camera.CameraScannerLayoutImpl;
import de.telekom.smartcredentials.core.camera.ScannerCallback;


public class BarcodeCameraScannerLayoutImpl extends CameraScannerLayoutImpl {

    public BarcodeCameraScannerLayoutImpl(Context context, ScannerCallback scannerCallback) {
        super(context, scannerCallback);
    }

    @ExperimentalGetImage
    @Override
    public void startCamera(LifecycleOwner lifecycleOwner) {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(mContext);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                bindPreview(cameraProvider, lifecycleOwner);
            } catch (ExecutionException | InterruptedException e) {
                //TODO: handle use-case
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(mContext));
    }

    @ExperimentalGetImage
    private void bindPreview(ProcessCameraProvider cameraProvider, LifecycleOwner lifecycleOwner) {
        Preview preview = new Preview.Builder()
                .build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), imageProxy -> {
            Image mediaImage = imageProxy.getImage();

            if (mediaImage != null) {
                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                BarcodeScanner scanner = BarcodeScanning.getClient();
                scanner.process(image)
                        .addOnSuccessListener(barcodes -> {
                            if (!barcodes.isEmpty()) {
                                List<String> barcodesList = new ArrayList<>();
                                for (Barcode barcode : barcodes) {
                                    barcodesList.add(barcode.getDisplayValue());
                                }
                                mScannerCallback.onDetected(barcodesList);
                            }
                        })
                        .addOnFailureListener(mScannerCallback::onSomethingHappened)
                        .addOnCompleteListener(barcodes -> imageProxy.close());
            }
        });
        preview.setSurfaceProvider(mPreviewView.getSurfaceProvider());
        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis);
    }
}
