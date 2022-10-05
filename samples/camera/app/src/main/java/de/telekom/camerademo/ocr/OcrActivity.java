package de.telekom.camerademo.ocr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.SurfaceContainer;
import de.telekom.smartcredentials.core.camera.SurfaceContainerInteractor;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class OcrActivity extends AppCompatActivity implements OcrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private LottieAnimationView qrAnimationView;
    private PreviewView previewView;
    private boolean isProcessing = true;
    private SmartCredentialsApiResponse<SurfaceContainerInteractor> response;

    private final ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {
            if (isProcessing) {
                isProcessing = false;
                qrAnimationView.pauseAnimation();

                OcrDialogFragment dialogFragment = OcrDialogFragment.newInstance((ArrayList<String>) detectedValues);
                dialogFragment.show(getSupportFragmentManager(), OcrDialogFragment.TAG);
            }
        }

        @Override
        public void onScanFailed(Exception e) {
            Toast.makeText(OcrActivity.this, R.string.ocr_scan_failed, Toast.LENGTH_SHORT).show();
        }
    };

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ocr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.take_picture_item) {
            captureFrame();

            return true;
        }
        return super.onOptionsItemSelected(item);
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
        CameraApi<PreviewView> cameraApi = SmartCredentialsCameraFactory.getCameraApi();
        SurfaceContainer<PreviewView> surfaceContainer = new SurfaceContainer<>(previewView);
        response = cameraApi.getOcrScannerView(this, surfaceContainer, this,
                scannerCallback);

        qrAnimationView.setVisibility(View.VISIBLE);
        qrAnimationView.playAnimation();
    }

    private void captureFrame() {
        if (response.isSuccessful()) {
            response.getData().takePicture();
        }
    }


    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}