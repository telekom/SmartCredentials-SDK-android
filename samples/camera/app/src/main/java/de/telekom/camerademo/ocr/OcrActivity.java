package de.telekom.camerademo.ocr;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Size;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import de.telekom.camerademo.R;

public class OcrActivity extends AppCompatActivity implements OcrDialogInteractionListener {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private LottieAnimationView qrAnimationView;
    private PreviewView previewView;
    private ImageCapture imageCapture;
    private boolean isProcessing = true;
    View scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        previewView = findViewById(R.id.preview_view);
        qrAnimationView = findViewById(R.id.scan_qr_animation_view);
        scannerView = findViewById(R.id.scanner_view);
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

    private void startCamera(){
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(OcrActivity.this, R.string.failed_camera_provider, Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
        qrAnimationView.setVisibility(View.VISIBLE);
        qrAnimationView.playAnimation();
    }


    private void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();


        imageCapture = new ImageCapture.Builder()
                .setTargetResolution(new Size(scannerView.getWidth(), scannerView.getHeight()))
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        cameraProvider.unbindAll();
        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    private void captureFrame(){
        imageCapture.takePicture(Executors.newSingleThreadExecutor(), new ImageCapture.OnImageCapturedCallback() {
            @Override
            public void onCaptureSuccess(@NonNull ImageProxy imageProxy) {
                super.onCaptureSuccess(imageProxy);

                if (isProcessing) {
                    isProcessing = false;
                    TextRecognizer textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                    Image imageMedia = imageProxy.getImage();

                    if(imageMedia != null){
                        InputImage inputImage = InputImage.fromMediaImage(imageMedia, imageProxy.getImageInfo().getRotationDegrees());
                        textRecognizer.process(inputImage)
                                .addOnSuccessListener(new OnSuccessListener<Text>() {
                                    @Override
                                    public void onSuccess(Text visionText) {
                                        qrAnimationView.pauseAnimation();
                                        processExtractedText(visionText);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        runOnUiThread(() -> {
                                            Toast.makeText(OcrActivity.this, "Text recognition failed", Toast.LENGTH_SHORT).show();
                                        });
                                    }
                                });
                    }
                    imageProxy.close();
                }
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                super.onError(exception);
                runOnUiThread(() -> {
                        Toast.makeText(OcrActivity.this, "Couldn't capture image", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void processExtractedText(Text text){
        ArrayList<String> ocrValues = new ArrayList<>();
        for (Text.TextBlock textBlock: text.getTextBlocks()) {
            for (Text.Line line: textBlock.getLines()) {
                ocrValues.add(line.getText());
            }
        }
        OcrDialogFragment dialogFragment = OcrDialogFragment.newInstance(ocrValues);
        dialogFragment.show(getSupportFragmentManager(), OcrDialogFragment.TAG);
    }

    @Override
    public void onOkButtonClicked() {
        qrAnimationView.resumeAnimation();
        isProcessing = true;
    }
}