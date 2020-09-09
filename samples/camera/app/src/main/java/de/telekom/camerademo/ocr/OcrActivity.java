package de.telekom.camerademo.ocr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import de.telekom.smartcredentials.camera.ocr.OcrCameraScannerLayout;
import de.telekom.smartcredentials.core.api.CameraApi;
import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerCallback;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public class OcrActivity extends AppCompatActivity implements OcrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private FrameLayout cameraWrapper;
    private LottieAnimationView qrAnimationView;
    private View topLeftView;
    private View bottomRightView;
    private CameraScannerLayout cameraScannerLayout;
    private boolean isProcessing = true;

    private final ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onDetected(List<String> detectedValues) {
            if (isProcessing) {
                isProcessing = false;
                qrAnimationView.pauseAnimation();
                ArrayList<String> ocrValues = new ArrayList<>(detectedValues);
                OcrDialogFragment dialogFragment = OcrDialogFragment.newInstance(ocrValues);
                dialogFragment.show(getSupportFragmentManager(), OcrDialogFragment.TAG);
            }
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
        topLeftView = findViewById(R.id.top_left_joint);
        bottomRightView = findViewById(R.id.bottom_right_joint);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasCameraPermission()) {
            requestCameraPermission();
        } else {
            getOcrScanner();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ocr, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.take_picture_item) {
            int left = topLeftView.getRight();
            int top = topLeftView.getBottom();
            int right = bottomRightView.getLeft();
            int bottom = bottomRightView.getTop();
            Rect rect = new Rect(left, top, right, bottom);
            OcrCameraScannerLayout ocrScanner = (OcrCameraScannerLayout) cameraScannerLayout;
            ocrScanner.detect(bitmap -> {
                qrAnimationView.pauseAnimation();
            }, rect);
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
                getOcrScanner();
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

    private void getOcrScanner() {
        CameraApi cameraApi = SmartCredentialsCameraFactory.getCameraApi();
        SmartCredentialsApiResponse<CameraScannerLayout> response = cameraApi
                .getOcrScannerView(this, scannerCallback);
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