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

package de.telekom.smartcredentials.documentscanner.presenter;

import android.graphics.Rect;
import android.os.Looper;
import androidx.annotation.NonNull;

import com.microblink.entities.Entity;
import com.microblink.entities.recognizers.Recognizer;
import com.microblink.geometry.Rectangle;
import com.microblink.metadata.MetadataCallbacks;
import com.microblink.recognition.RecognitionSuccessType;
import com.microblink.view.CameraEventsListener;
import com.microblink.view.recognition.ScanResultListener;

import java.lang.ref.WeakReference;
import java.util.Objects;

import de.telekom.smartcredentials.core.documentscanner.CompletionCallback;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.core.documentscanner.TorchState;
import de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException;
import de.telekom.smartcredentials.documentscanner.exception.WrongThreadException;
import de.telekom.smartcredentials.documentscanner.utils.CameraErrorInterpreter;
import de.telekom.smartcredentials.core.documentscanner.RecognizerState;
import de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable;
import de.telekom.smartcredentials.documentscanner.utils.ScannerResultConverter;
import de.telekom.smartcredentials.documentscanner.utils.ScannerUtils;
import de.telekom.smartcredentials.documentscanner.view.DocumentScannerView;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException.ON_CREATE_ALREADY_CALLED;
import static de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException.ON_CREATE_NOT_CALLED;
import static de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException.ON_PAUSE_NOT_CALLED;
import static de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException.ON_RESUME_NOT_CALLED;
import static de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException.ON_START_NOT_CALLED;
import static de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException.ON_STOP_NOT_CALLED;
import static de.telekom.smartcredentials.documentscanner.exception.WrongThreadException.ON_CREATE;
import static de.telekom.smartcredentials.documentscanner.exception.WrongThreadException.ON_DESTROY;
import static de.telekom.smartcredentials.documentscanner.exception.WrongThreadException.ON_PAUSE;
import static de.telekom.smartcredentials.documentscanner.exception.WrongThreadException.ON_RESUME;
import static de.telekom.smartcredentials.documentscanner.exception.WrongThreadException.ON_START;
import static de.telekom.smartcredentials.documentscanner.exception.WrongThreadException.ON_STOP;

@SuppressWarnings("unchecked")
public class DocumentScannerPresenterImpl implements DocumentScannerPresenter {

    private static final String TAG = "DocumentScannerPresenterImpl";

    RecognizerState mRecognizerState = RecognizerState.DESTROYED;
    private DocumentScannerPluginCallback mPluginCallback;
    private SmartCredentialsDocumentScanConfiguration mScannerConfiguration;
    private WeakReference<DocumentScannerView> mView;

    public DocumentScannerPresenterImpl() {
        // required empty constructor
    }

    MetadataCallbacks createMetadataCallbacks() {
        MetadataCallbacks callbacks = new MetadataCallbacks();
        callbacks.setFirstSideRecognitionCallback(mPluginCallback::onFirstSideRecognitionFinished);
        callbacks.setFailedDetectionCallback(mPluginCallback::onScannedFailed);
        return callbacks;
    }

    @Override
    public void onCreate() {
        validateLifecycleCallback(RecognizerState.DESTROYED, ON_CREATE_ALREADY_CALLED, ON_CREATE);
        mView.get().createRecognizer();
        mRecognizerState = RecognizerState.CREATED;
    }

    @Override
    public void onStart() {
        validateLifecycleCallback(RecognizerState.CREATED, ON_CREATE_NOT_CALLED, ON_START);
        mView.get().startRecognizer();
        mRecognizerState = RecognizerState.STARTED;
    }

    @Override
    public void onResume() {
        validateLifecycleCallback(RecognizerState.STARTED, ON_START_NOT_CALLED, ON_RESUME);
        mView.get().resumeRecognizer();
        mView.get().setMetadataCallbacks(createMetadataCallbacks());
        mRecognizerState = RecognizerState.RESUMED;
    }

    @Override
    public void onPause() {
        validateLifecycleCallback(RecognizerState.RESUMED, ON_RESUME_NOT_CALLED, ON_PAUSE);
        mView.get().pauseRecognizer();
        mView.get().setMetadataCallbacks(null);
        mRecognizerState = RecognizerState.STARTED;
    }

    @Override
    public void onStop() {
        validateLifecycleCallback(RecognizerState.STARTED, ON_PAUSE_NOT_CALLED, ON_STOP);
        mView.get().stopRecognizer();
        mRecognizerState = RecognizerState.CREATED;
    }

    @Override
    public void onDestroy() {
        validateLifecycleCallback(RecognizerState.CREATED, ON_STOP_NOT_CALLED, ON_DESTROY);
        mView.get().destroyRecognizer();
        mRecognizerState = RecognizerState.DESTROYED;
    }

    @Override
    public void validateRecognizer(@NonNull Recognizer recognizer) {
        Objects.requireNonNull(recognizer);
        boolean recognizerSupported = ScannerUtils.checkIfRecognizerIsSupported(recognizer,
                mScannerConfiguration.getContext(),
                mScannerConfiguration.getCameraType());
        if (!recognizerSupported) {
            mPluginCallback.onPluginUnavailable(ScannerPluginUnavailable.RECOGNIZER_NOT_SUPPORTED);
        }
    }

    @Override
    public void changeRecognizer(ScannerRecognizer recognizer) {
        if (mRecognizerState == RecognizerState.DESTROYED || mRecognizerState == RecognizerState.CREATED) {
            throw new LifecycleFlowException(LifecycleFlowException.ON_START_NOT_CALLED_SWAP_RECOGNIZERS, mRecognizerState);
        }
        mView.get().reconfigureRecognizer(recognizer);
    }

    @Override
    public void changeTorchMode(TorchState torchState, CompletionCallback callback) {
        if (torchState == null) {
            if (callback != null) {
                callback.onOperationDone(false);
            }
        } else {
            mView.get().setTorch(torchState.getValue(), callback);
        }
    }

    @Override
    public boolean isRecognizerSupported(ScannerRecognizer recognizer) {
        return recognizer != null && ScannerUtils.checkIfRecognizerIsSupported(recognizer.getRecognizer(),
                mScannerConfiguration.getContext(),
                mScannerConfiguration.getCameraType());
    }

    @Override
    public void setScanningArea(float leftAreaPercentage, float topAreaPercentage, float widthPercentage, float heightPercentage) {
        Rectangle rectangle = new Rectangle(leftAreaPercentage, topAreaPercentage, widthPercentage, heightPercentage);
        mView.get().setScanningRegion(rectangle, false);
    }

    public void init(DocumentScannerView documentScannerView,
                     SmartCredentialsDocumentScanConfiguration configuration,
                     DocumentScannerPluginCallback pluginCallback) {
        mView = new WeakReference<>(documentScannerView);
        mScannerConfiguration = configuration;
        mPluginCallback = pluginCallback;
        initRecognizer();
    }

    final ScanResultListener mScanResultListener = new ScanResultListener() {
        @Override
        public void onScanningDone(@NonNull RecognitionSuccessType recognitionSuccessType) {
            if (recognitionSuccessType != RecognitionSuccessType.UNSUCCESSFUL) {
                Entity.Result result = mView.get().getRecognizerResult();
                DocumentScannerResult documentResult = ScannerResultConverter.convertToInternalModel(result);
                mPluginCallback.onScanned(documentResult);
            }
        }
    };

    final CameraEventsListener mCameraEventsListener = new CameraEventsListener() {
        @Override
        public void onCameraPermissionDenied() {
            mPluginCallback.onPluginUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
        }

        @Override
        public void onCameraPreviewStarted() {
            mPluginCallback.onScannerStarted();
        }

        @Override
        public void onCameraPreviewStopped() {
            mPluginCallback.onScannerStopped();
        }

        @Override
        public void onError(Throwable throwable) {
            ApiLoggerResolver.logError(TAG, throwable.getMessage());
            mPluginCallback.onPluginUnavailable(CameraErrorInterpreter.fromThrowable(throwable));
        }

        @Override
        public void onAutofocusFailed() {
            mPluginCallback.onAutoFocusFailed();
        }

        @Override
        public void onAutofocusStarted(Rect[] rects) {
            mPluginCallback.onAutoFocusStarted(rects);
        }

        @Override
        public void onAutofocusStopped(Rect[] rects) {
            mPluginCallback.onAutoFocusStopped(rects);
        }
    };

    private void initRecognizer() {
        mView.get().createRecognizerView(mScannerConfiguration.getContext());
        mView.get().setRecognizerSettings(mScannerConfiguration);
        mView.get().setListeners(mScanResultListener, mCameraEventsListener);
    }

    private void validateLifecycleCallback(RecognizerState desiredState,
                                           String lifecycleExceptionMessage,
                                           String threadExceptionMessage) {
        if (mRecognizerState != desiredState) {
            throw new LifecycleFlowException(lifecycleExceptionMessage, mRecognizerState);
        } else if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new WrongThreadException(threadExceptionMessage);
        }
    }
}
