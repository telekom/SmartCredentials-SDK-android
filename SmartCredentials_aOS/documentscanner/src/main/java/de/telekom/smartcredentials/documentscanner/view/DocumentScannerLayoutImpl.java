/*
 * Copyright (c) 2019 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Created by Lucian Iacob on 6/29/18 2:05 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.view;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import android.widget.LinearLayout;

import com.microblink.entities.Entity;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.RecognizerBundle;
import com.microblink.geometry.Rectangle;
import com.microblink.metadata.MetadataCallbacks;
import com.microblink.view.CameraEventsListener;
import com.microblink.view.recognition.RecognizerRunnerView;
import com.microblink.view.recognition.ScanResultListener;

import de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout;
import de.telekom.smartcredentials.core.documentscanner.CompletionCallback;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.core.documentscanner.TorchState;
import de.telekom.smartcredentials.documentscanner.presenter.DocumentScannerPresenter;
import de.telekom.smartcredentials.documentscanner.presenter.DocumentScannerPresenterImpl;
import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;


@SuppressLint("ViewConstructor")
public class DocumentScannerLayoutImpl extends DocumentScannerLayout implements DocumentScannerView {

    private final DocumentScannerPresenter mPresenter;
    private RecognizerRunnerView mRecognizerView;
    private Recognizer mRecognizer;

    public static DocumentScannerLayoutImpl getNewInstance(SmartCredentialsDocumentScanConfiguration configuration, DocumentScannerPluginCallback pluginCallback) {
        return new DocumentScannerLayoutImpl(configuration, pluginCallback);
    }

    private DocumentScannerLayoutImpl(SmartCredentialsDocumentScanConfiguration configuration, DocumentScannerPluginCallback pluginCallback) {
        super(configuration.getContext());
        mPresenter = new DocumentScannerPresenterImpl();
        mPresenter.init(this, configuration, pluginCallback);
    }

    @Override
    public void createRecognizerView(Context context) {
        mRecognizerView = new RecognizerRunnerView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mRecognizerView.setLayoutParams(params);
        addView(mRecognizerView);
    }

    @Override
    public void setRecognizerSettings(SmartCredentialsDocumentScanConfiguration configuration) {
        mRecognizer = configuration.getScannerRecognizer().getRecognizer();
        mPresenter.validateRecognizer(mRecognizer);
        RecognizerBundle recognizerBundle = new RecognizerBundle(mRecognizer);
        mRecognizerView.setRecognizerBundle(recognizerBundle);
        mRecognizerView.setCameraType(ModelConverter.convertCameraTypeEnum(configuration.getCameraType()));
        mRecognizerView.setAspectMode(ModelConverter.convertAspectMode(configuration.getAspectMode()));
        mRecognizerView.setPinchToZoomAllowed(configuration.isPinchToZoomAllowed());
        mRecognizerView.setRequestAutofocusOnShakingStopInContinousAutofocusMode(configuration.shouldFocusOnShakingStop());
        mRecognizerView.setOptimizeCameraForNearScan(configuration.shouldOptimizeCameraForNearScan());
        mRecognizerView.setTapToFocusAllowed(configuration.shouldAllowTapToFocus());
        mRecognizerView.setVideoResolutionPreset(ModelConverter.convertVideoPreset(configuration.getVideoResolution()));
        mRecognizerView.setZoomLevel(configuration.getZoomLevel());
    }

    @Override
    public void setListeners(ScanResultListener scanResultListener, CameraEventsListener cameraEventsListener) {
        mRecognizerView.setScanResultListener(scanResultListener);
        mRecognizerView.setCameraEventsListener(cameraEventsListener);
    }

    @Override
    public Entity.Result getRecognizerResult() {
        return mRecognizer.getResult().clone();
    }

    @Override
    public void pauseScanning() {
        mRecognizerView.pauseScanning();
    }

    @Override
    public void resumeScanning(boolean shouldResetRecognizerState) {
        mRecognizerView.resumeScanning(shouldResetRecognizerState);
    }

    @Override
    public void swapRecognizer(@NonNull Object recognizer) {
        mPresenter.changeRecognizer((ScannerRecognizer) recognizer);
    }

    @Override
    public void reconfigureRecognizer(ScannerRecognizer scannerRecognizer) {
        mRecognizer = scannerRecognizer.getRecognizer();
        mPresenter.validateRecognizer(mRecognizer);
        RecognizerBundle recognizerBundle = new RecognizerBundle(mRecognizer);
        mRecognizerView.reconfigureRecognizers(recognizerBundle);
    }

    @Override
    public void setTorchMode(TorchState torchState, CompletionCallback callback) {
        mPresenter.changeTorchMode(torchState, callback);
    }

    @Override
    public boolean isRecognizerSupported(Object recognizer) {
        return mPresenter.isRecognizerSupported((ScannerRecognizer) recognizer);
    }

    @Override
    public void setTorch(boolean value, CompletionCallback callback) {
        mRecognizerView.setTorchState(value, b -> {
            if (callback != null) {
                callback.onOperationDone(b);
            }
        });
    }

    @Override
    public boolean isCameraTorchSupported() {
        return mRecognizerView.isCameraTorchSupported();
    }

    @Override
    public void setScanningArea(@FloatRange(from = 0.0, to = 1.0) float leftAreaPercentage,
                                @FloatRange(from = 0.0, to = 1.0) float topAreaPercentage,
                                @FloatRange(from = 0.0, to = 1.0) float widthPercentage,
                                @FloatRange(from = 0.0, to = 1.0) float heightPercentage) {
        mPresenter.setScanningArea(leftAreaPercentage, topAreaPercentage, widthPercentage, heightPercentage);
    }

    @Override
    public void setScanningRegion(Rectangle rectangle, boolean rotateRegionWithDevice) {
        mRecognizerView.setScanningRegion(rectangle, false);
    }

    @Override
    public void setMetadataCallbacks(MetadataCallbacks metadataCallbacks) {
        mRecognizerView.setMetadataCallbacks(metadataCallbacks);
    }

    //   <editor-fold name="Activity/Fragment's lifecycle callbacks">
    @Override
    public void onCreate() {
        mPresenter.onCreate();
    }

    @Override
    public void createRecognizer() {
        mRecognizerView.create();
    }

    @Override
    public void onStart() {
        mPresenter.onStart();
    }

    @Override
    public void startRecognizer() {
        mRecognizerView.start();
    }

    @Override
    public void onResume() {
        mPresenter.onResume();
    }

    @Override
    public void resumeRecognizer() {
        mRecognizerView.resume();
    }

    @Override
    public void onPause() {
        mPresenter.onPause();
    }

    @Override
    public void pauseRecognizer() {
        mRecognizerView.pause();
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
    }

    @Override
    public void stopRecognizer() {
        mRecognizerView.stop();
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
    }

    @Override
    public void destroyRecognizer() {
        mRecognizerView.destroy();
    }
    //    </editor-fold>
}
