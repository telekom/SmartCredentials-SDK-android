package de.telekom.camerademo.ocr;

import android.Manifest;
import android.content.pm.PackageManager;
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

import de.telekom.camerademo.R;

public class OcrActivity extends AppCompatActivity implements OcrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private FrameLayout cameraWrapper;
    private LottieAnimationView qrAnimationView;
    private View topLeftView;
    private View bottomRightView;
    private boolean isProcessing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        qrAnimationView = findViewById(R.id.scan_qr_animation_view);
        topLeftView = findViewById(R.id.top_left_joint);
        bottomRightView = findViewById(R.id.bottom_right_joint);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasCameraPermission()) {
            requestCameraPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
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
        qrAnimationView.setVisibility(View.VISIBLE);
        qrAnimationView.playAnimation();
    }

    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}