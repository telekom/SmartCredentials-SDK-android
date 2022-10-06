package de.telekom.scotpdemo.add;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import com.airbnb.lottie.LottieAnimationView;

import de.telekom.scotpdemo.BaseUpActivity;
import de.telekom.scotpdemo.R;
import de.telekom.smartcredentials.core.api.OtpApi;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.camera.SurfaceContainer;
import de.telekom.smartcredentials.core.otp.OTPImportFailed;
import de.telekom.smartcredentials.core.otp.OTPImportItem;
import de.telekom.smartcredentials.core.otp.OTPImporterCallback;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.otp.factory.SmartCredentialsOtpFactory;

/**
 * Created by gabriel.blaj@endava.com at 9/2/2020
 */
public class AddQrOtpActivity extends BaseUpActivity {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private LottieAnimationView qrAnimationView;
    private PreviewView previewView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_qr_layout);

        previewView = findViewById(R.id.preview_view);
        qrAnimationView = findViewById(R.id.scan_qr_animation_view);
    }

    private final OTPImporterCallback otpImporterCallback = new OTPImporterCallback() {
        @Override
        public void onOTPItemImported(OTPImportItem otpImportItem) {
            runOnUiThread(() -> {
                Toast.makeText(AddQrOtpActivity.this, getResources().getString(R.string.qr_otp_success), Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        @Override
        public void onOTPItemImportFailed(OTPImportFailed otpHandlerFailed) {
            runOnUiThread(() -> {
                Toast.makeText(AddQrOtpActivity.this, otpHandlerFailed.getDesc(), Toast.LENGTH_SHORT).show();
                finish();
            });
        }

        @Override
        public void onInitialized() {
        }

        @Override
        public void onScannerUnavailable(ScannerPluginUnavailable scannerPluginUnavailable) {
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasCameraPermission()) {
            requestCameraPermission();
        } else {
            getScanner();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_RQ) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getScanner();
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
        qrAnimationView.setVisibility(View.VISIBLE);
        qrAnimationView.playAnimation();
    }

    private void getScanner() {
        OtpApi<PreviewView> otpApi = SmartCredentialsOtpFactory.getOtpApi();
        SmartCredentialsApiResponse<Boolean> response =
                otpApi.importOTPItemViaQRForId(this, new SurfaceContainer<>(previewView),
                        AddQrOtpActivity.this,
                        String.valueOf(System.currentTimeMillis()),
                        otpImporterCallback);

        if (response.isSuccessful()) {
            startScanner();
        } else {
            Toast.makeText(this, R.string.camera_scanner_layout_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
