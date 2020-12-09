package de.telekom.scdocumentscannerdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import de.telekom.scdocumentscannerdemo.recognizer.RecognizerData;
import de.telekom.scdocumentscannerdemo.recognizer.RecognizerFactory;
import de.telekom.scdocumentscannerdemo.recognizer.RecognizerTypeActivity;
import de.telekom.scdocumentscannerdemo.result.ResultActivity;
import de.telekom.smartcredentials.core.api.DocumentScannerApi;
import de.telekom.smartcredentials.core.documentscanner.AspectMode;
import de.telekom.smartcredentials.core.documentscanner.CameraType;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerCallback;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout;
import de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.documentscanner.VideoResolution;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.factory.SmartCredentialsDocumentScannerFactory;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_RQ = 1234;

    private FrameLayout recognizerLayout;
    private DocumentScannerLayout<ScannerRecognizer> mDocumentScannerView;
    private PreferenceManager preferenceManager;
    private RecognizerData recognizerData;
    private DocumentScannerApi<SmartCredentialsDocumentScanConfiguration, ScannerRecognizer> api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = SmartCredentialsDocumentScannerFactory.getDocumentScannerApi();
        recognizerLayout = findViewById(R.id.recognizer_view_container);
        preferenceManager = new PreferenceManager(this);
        recognizerData = new RecognizerFactory(preferenceManager.getRecognizerType());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter) {
            startActivity(new Intent(this, RecognizerTypeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!hasCameraPermission()) {
            requestCameraPermission();
        } else {
            recognizerData.setRecognizer(preferenceManager.getRecognizerType());
            startDocumentScanner();
        }
    }

    private boolean hasCameraPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_RQ);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_RQ) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startDocumentScanner();
            } else if (grantResults.length != 0) {
                Toast.makeText(this, R.string.camera_permission_not_granted, Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTitle();
        if (mDocumentScannerView != null) {
            mDocumentScannerView.onResume();
        }
    }

    private void setTitle() {
        setTitle(preferenceManager.getRecognizerType().getDesc());
    }

    private void startDocumentScanner() {
        ScannerRecognizer recognizer = recognizerData.getRecognizer();
        SmartCredentialsDocumentScanConfiguration config = createScannerConfiguration(recognizer);

        SmartCredentialsApiResponse<DocumentScannerLayout<ScannerRecognizer>> response =
                api.getDocumentScannerView(config, mScannerCallback);
        if (response.isSuccessful()) {
            mDocumentScannerView = response.getData();
            if (mDocumentScannerView != null) {
                mDocumentScannerView.onCreate();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                mDocumentScannerView.setLayoutParams(params);
                recognizerLayout.addView(mDocumentScannerView);
                mDocumentScannerView.onStart();
            }
        } else {
            Toast.makeText(this, R.string.document_scanner_layout_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private SmartCredentialsDocumentScanConfiguration createScannerConfiguration(ScannerRecognizer recognizer) {
        return new SmartCredentialsDocumentScanConfiguration.Builder(this)
                .setScannerRecognizer(recognizer)
                .setCameraType(CameraType.BACK_CAMERA)
                .setZoomLevel(0f)
                .setAspectMode(AspectMode.FILL)
                .setPinchToZoomAllowed(true)
                .setVideoResolution(VideoResolution.DEFAULT)
                .shouldAllowTapToFocus(true)
                .shouldOptimizeCameraForNearScan(false)
                .build();
    }

    protected final DocumentScannerCallback mScannerCallback = new DocumentScannerCallback() {
        @Override
        public void onDetected(DocumentScannerResult result) {
            Timber.tag(DemoApplication.TAG).d("DocumentScannerCallback:onDetected");
            mDocumentScannerView.pauseScanning();
            recognizerData.saveScanningResult(result);
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFirstSideRecognitionFinished() {
            Timber.tag(DemoApplication.TAG).d("DocumentScannerCallback:onFirstSideRecognitionFinished");
            Toast.makeText(MainActivity.this, getString(R.string.turn_card), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onInitialized() {
            Timber.tag(DemoApplication.TAG).d("DocumentScannerCallback:onInitialized");
        }

        @Override
        public void onScannerUnavailable(ScannerPluginUnavailable errorMessage) {
            Timber.tag(DemoApplication.TAG).e(errorMessage.getDesc());
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (mDocumentScannerView != null) {
            mDocumentScannerView.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mDocumentScannerView != null) {
            mDocumentScannerView.onStop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDocumentScannerView != null) {
            mDocumentScannerView.onDestroy();
        }
    }
}