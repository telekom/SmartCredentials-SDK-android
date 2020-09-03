package de.telekom.camerademo.qr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import de.telekom.camerademo.R;
import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory;
import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.camera.BarcodeType;
import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class QrActivity extends AppCompatActivity implements QrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private FrameLayout cameraWrapper;
    private LottieAnimationView qrAnimationView;
    private CameraScannerLayout cameraScannerLayout;
    private boolean isProcessing = true;

    private final ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {
            runOnUiThread(() -> {
                if (isProcessing) {
                    isProcessing = false;
                    qrAnimationView.pauseAnimation();
                    ArrayList<String> qrValues = new ArrayList<>(detectedValues);
                    QrDialogFragment dialogFragment = QrDialogFragment.newInstance(qrValues);
                    dialogFragment.show(getSupportFragmentManager(), QrDialogFragment.TAG);
                }
            });
        }

        @Override
        public void onInitialized() {
            // no implementation
        }

        @Override
        public void onScannerUnavailable(ScannerPluginUnavailable errorMessage) {
            // no implementation
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        cameraWrapper = findViewById(R.id.camera_wrapper);
        qrAnimationView = findViewById(R.id.scan_qr_animation_view);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasCameraPermission()) {
            requestCameraPermission();
        } else {
            getBarcodeScanner();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (cameraScannerLayout != null) {
            cameraScannerLayout.stopScanner();
            cameraScannerLayout.releaseCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_RQ) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getBarcodeScanner();
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

    private void startScanner() {
        cameraWrapper.removeAllViews();
        cameraWrapper.addView(cameraScannerLayout);
        cameraScannerLayout.startScanner();
        qrAnimationView.setVisibility(View.VISIBLE);
        qrAnimationView.playAnimation();
    }

    private void getBarcodeScanner() {
        CameraApi cameraApi = SmartCredentialsCameraFactory.getCameraApi();
        SmartCredentialsApiResponse<CameraScannerLayout> response =
                cameraApi.getBarcodeScannerView(this, scannerCallback, BarcodeType.BARCODE_ALL_FORMATS);
        if (response.isSuccessful()) {
            cameraScannerLayout = response.getData();
            startScanner();
        } else {
            Toast.makeText(this, R.string.camera_scanner_layout_failed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}