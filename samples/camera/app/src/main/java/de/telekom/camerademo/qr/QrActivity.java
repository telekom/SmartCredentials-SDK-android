package de.telekom.camerademo.qr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import de.telekom.camerademo.R;

public class QrActivity extends AppCompatActivity implements QrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private PreviewView previewView;
    private LottieAnimationView qrAnimationView;
    private boolean isProcessing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        previewView = findViewById(R.id.preview_view);
        qrAnimationView = findViewById(R.id.scan_qr_animation_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasCameraPermission()) {
            requestCameraPermission();
        } else {
            startCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_RQ) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else if (grantResults.length != 0) {
                Toast.makeText(this, R.string.camera_permission_not_granted, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasCameraPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_RQ);
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(QrActivity.this, R.string.failed_camera_provider, Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
        qrAnimationView.setVisibility(View.VISIBLE);
        qrAnimationView.playAnimation();
    }

    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build();
        imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), imageProxy -> {
            Image mediaImage = imageProxy.getImage();
            InputImage image =
                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
            BarcodeScanner scanner = BarcodeScanning.getClient();
            scanner.process(image)
                    .addOnSuccessListener(barcodes -> {
                        if (!barcodes.isEmpty()) {
                            if (isProcessing) {
                                isProcessing = false;
                                ArrayList<String> qrValues = new ArrayList<>();
                                for (Barcode barcode : barcodes) {
                                    qrValues.add(barcode.getDisplayValue());
                                }
                                runOnUiThread(() -> {
                                    qrAnimationView.pauseAnimation();
                                    QrDialogFragment dialogFragment = QrDialogFragment.newInstance(qrValues);
                                    dialogFragment.show(getSupportFragmentManager(), QrDialogFragment.TAG);
                                });
                            }
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(QrActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show())
                    .addOnCompleteListener(barcodes -> imageProxy.close());
        });
        final ImageCapture imageCapture = new ImageCapture.Builder()
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(this, cameraSelector, preview,
                imageAnalysis, imageCapture);
    }

    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}