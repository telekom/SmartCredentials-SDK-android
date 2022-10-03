package de.telekom.camerademo.qr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import de.telekom.camerademo.R;
import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory;
import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.camera.BarcodeType;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.SurfaceContainer;

public class QrActivity extends AppCompatActivity implements QrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private LottieAnimationView qrAnimationView;
    private PreviewView previewView;
    private boolean isProcessing = true;

    private final ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {
            if (isProcessing) {
                isProcessing = false;

                QrDialogFragment dialogFragment = QrDialogFragment.newInstance((ArrayList<String>) detectedValues);
                dialogFragment.show(getSupportFragmentManager(), QrDialogFragment.TAG);
            }
        }

        @Override
        public void onScanFailed(Exception e) {
            //TODO: handle error case
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        qrAnimationView = findViewById(R.id.scan_qr_animation_view);
        previewView = findViewById(R.id.preview_view);
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

    public void startCamera() {
        CameraApi<PreviewView> cameraApi = SmartCredentialsCameraFactory.getCameraApi();
        SurfaceContainer<PreviewView> surfaceContainer = new SurfaceContainer<>(previewView);
        cameraApi.getBarcodeScannerView(this, surfaceContainer, this,
                scannerCallback, BarcodeType.BARCODE_2D_QR_CODE);
    }

    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}