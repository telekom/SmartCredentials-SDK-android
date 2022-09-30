package de.telekom.camerademo.qr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;

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

    private ConstraintLayout rootView;
    private LottieAnimationView qrAnimationView;
    private boolean isProcessing = true;

    private ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {

        }

        @Override
        public void onSomethingHappened(Exception e) {

        }

        @Override
        public void onInitialized() {

        }

        @Override
        public void onScannerUnavailable(ScannerPluginUnavailable errorMessage) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        rootView = findViewById(R.id.root_view);
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

    public void startCamera() {
        CameraApi<PreviewView> cameraApi = SmartCredentialsCameraFactory.getCameraApi();
        SmartCredentialsApiResponse<CameraScannerLayout<PreviewView>> response =
                cameraApi.getBarcodeScannerView(this, scannerCallback, BarcodeType.BARCODE_2D_QR_CODE);

        if (response.isSuccessful()) {
            CameraScannerLayout<PreviewView> cameraScannerLayout = response.getData();
            PreviewView previewView = cameraScannerLayout.getView();
            previewView.setLayoutParams(new ConstraintLayout.LayoutParams());

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.connect();

            rootView.addView(previewView);
        }
    }

    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}