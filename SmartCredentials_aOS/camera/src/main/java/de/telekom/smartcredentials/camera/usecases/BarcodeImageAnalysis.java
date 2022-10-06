package de.telekom.smartcredentials.camera.usecases;

import android.media.Image;

import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.UseCase;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import de.telekom.smartcredentials.core.camera.ScannerCallback;

public class BarcodeImageAnalysis implements CameraUseCase {

    private final int mBarcodeFormat;

    public BarcodeImageAnalysis(int barcodeFormat) {
        mBarcodeFormat = barcodeFormat;
    }

    @ExperimentalGetImage
    @Override
    public UseCase create(ScannerCallback callback) {
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), imageProxy -> {
            Image mediaImage = imageProxy.getImage();

            if (mediaImage != null) {
                InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(mBarcodeFormat)
                        .build();
                BarcodeScanner scanner = BarcodeScanning.getClient(options);
                scanner.process(image)
                        .addOnSuccessListener(barcodes -> {
                            if (!barcodes.isEmpty()) {
                                List<String> barcodesList = new ArrayList<>();
                                for (Barcode barcode : barcodes) {
                                    barcodesList.add(barcode.getRawValue());
                                }
                                callback.onDetected(barcodesList);
                            }
                        })
                        .addOnFailureListener(callback::onScanFailed)
                        .addOnCompleteListener(barcodes -> imageProxy.close());
            }
        });
        return imageAnalysis;
    }
}
