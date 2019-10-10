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

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.geometry.Rectangle;
import com.microblink.hardware.camera.CameraResolutionTooSmallException;
import com.microblink.metadata.MetadataCallbacks;
import com.microblink.metadata.detection.FailedDetectionCallback;
import com.microblink.metadata.recognition.FirstSideRecognitionCallback;
import com.microblink.recognition.RecognitionSuccessType;
import com.microblink.view.CameraEventsListener;
import com.microblink.view.recognition.ScanResultListener;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.documentscanner.CompletionCallback;
import de.telekom.smartcredentials.documentscanner.config.SmartCredentialsDocumentScanConfiguration;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.core.documentscanner.TorchState;
import de.telekom.smartcredentials.core.documentscanner.LifecycleFlowException;
import de.telekom.smartcredentials.documentscanner.exception.WrongThreadException;
import de.telekom.smartcredentials.documentscanner.model.romania.RomaniaFrontSideResult;
import de.telekom.smartcredentials.core.documentscanner.RecognizerState;
import de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable;
import de.telekom.smartcredentials.documentscanner.utils.ScannerResultConverter;
import de.telekom.smartcredentials.documentscanner.utils.ScannerUtils;
import de.telekom.smartcredentials.documentscanner.view.DocumentScannerView;
import de.telekom.smartcredentials.core.plugins.callbacks.DocumentScannerPluginCallback;

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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
@PrepareForTest(Looper.class)
@RunWith(PowerMockRunner.class)
public class DocumentScannerPresenterImplTest {

    private Context mContext;
    private DocumentScannerView mView;
    private SmartCredentialsDocumentScanConfiguration mConfiguration;
    private DocumentScannerPluginCallback mCallback;
    private Looper mFakeLooper;
    private DocumentScannerPresenterImpl mPresenter;
    private CameraEventsListener mCameraEventsListener;
    private ScanResultListener mScanResultListener;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        PowerMockito.mockStatic(Looper.class);

        mContext = Mockito.mock(Context.class);
        mView = Mockito.mock(DocumentScannerView.class);
        mConfiguration = new SmartCredentialsDocumentScanConfiguration.Builder(mContext).build();
        mCallback = Mockito.mock(DocumentScannerPluginCallback.class);
        mFakeLooper = Mockito.mock(Looper.class);

        mPresenter = new DocumentScannerPresenterImpl();

        mPresenter.init(mView, mConfiguration, mCallback);
        mScanResultListener = mPresenter.mScanResultListener;
        mCameraEventsListener = mPresenter.mCameraEventsListener;
    }

    @Test
    public void initRecognizerCallsMethodsOnView() {
        verify(mView, times(1)).createRecognizerView(mContext);
        verify(mView, times(1)).setRecognizerSettings(mConfiguration);
        verify(mView, times(1)).setListeners(
                mPresenter.mScanResultListener, mPresenter.mCameraEventsListener);
    }

    @PrepareForTest({ScannerUtils.class, Looper.class})
    @Test
    public void validateRecognizerCallsPluginUnavailableWhenRecognizerNotSupported() {
        PowerMockito.mockStatic(ScannerUtils.class);
        Recognizer recognizer = Mockito.mock(Recognizer.class);
        PowerMockito.when(ScannerUtils.checkIfRecognizerIsSupported(recognizer,
                mContext, mConfiguration.getCameraType())).thenReturn(false);

        mPresenter.validateRecognizer(recognizer);

        verify(mCallback, times(1))
                .onPluginUnavailable(ScannerPluginUnavailable.RECOGNIZER_NOT_SUPPORTED);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void validateRecognizerThrowsNullPointerExceptionWhenRecognizerIsNull() {
        thrown.expect(NullPointerException.class);
        mPresenter.validateRecognizer(null);
    }

    @PrepareForTest({ScannerUtils.class, Looper.class})
    @Test
    public void validateRecognizerDoNotCallPluginUnavailableWhenRecognizerIsSupported() {
        PowerMockito.mockStatic(ScannerUtils.class);
        Recognizer recognizer = Mockito.mock(Recognizer.class);
        PowerMockito.when(ScannerUtils.checkIfRecognizerIsSupported(recognizer,
                mContext, mConfiguration.getCameraType())).thenReturn(true);

        mPresenter.validateRecognizer(recognizer);

        verify(mCallback, never()).onPluginUnavailable(any());
    }

    @PrepareForTest({ScannerResultConverter.class, Looper.class})
    @Test
    public void onScanningDoneCallsMethodOnPluginCallbackWhenResponseIsPartial() {
        PowerMockito.mockStatic(ScannerResultConverter.class);
        RomaniaFrontSideResult romaniaResult = new RomaniaFrontSideResult(Recognizer.Result.State.Uncertain);
        PowerMockito.when(ScannerResultConverter.convertToInternalModel(any())).thenReturn(romaniaResult);
        when(mView.getRecognizerResult()).thenReturn(null);

        mScanResultListener.onScanningDone(RecognitionSuccessType.PARTIAL);

        verify(mCallback, times(1)).onScanned(romaniaResult);
    }

    @PrepareForTest({ScannerResultConverter.class, Looper.class})
    @Test
    public void onScanningDoneCallsMethodOnPluginCallbackWhenResponseIsSuccessful() {
        PowerMockito.mockStatic(ScannerResultConverter.class);
        RomaniaFrontSideResult romaniaResult = new RomaniaFrontSideResult(Recognizer.Result.State.Uncertain);
        PowerMockito.when(ScannerResultConverter.convertToInternalModel(any())).thenReturn(romaniaResult);
        when(mView.getRecognizerResult()).thenReturn(null);

        mScanResultListener.onScanningDone(RecognitionSuccessType.SUCCESSFUL);

        verify(mCallback, times(1)).onScanned(romaniaResult);
    }

    @Test
    public void onScanningDoneCallsNoMethodOnPluginCallbackWhenResponseIsUnsuccessful() {
        mScanResultListener.onScanningDone(RecognitionSuccessType.UNSUCCESSFUL);

        verify(mCallback, never()).onScanned(any());
    }

    @Test
    public void onCameraPermissionDeniedCallsMethodOnPluginCallback() {
        mCameraEventsListener.onCameraPermissionDenied();

        verify(mCallback, times(1))
                .onPluginUnavailable(ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED);
    }

    @Test
    public void onCameraPreviewStartedCallsMethodOnPluginCallback() {
        mCameraEventsListener.onCameraPreviewStarted();

        verify(mCallback, times(1)).onScannerStarted();
    }

    @Test
    public void onCameraPreviewStoppedCallsMethodOnPluginCallback() {
        mCameraEventsListener.onCameraPreviewStopped();

        verify(mCallback, times(1)).onScannerStopped();
    }

    @Test
    public void onErrorCallsMethodOnPluginCallback() {
        mCameraEventsListener.onError(new CameraResolutionTooSmallException(""));

        verify(mCallback, times(1))
                .onPluginUnavailable(ScannerPluginUnavailable.CAMERA_RESOLUTION_TOO_SMALL);
    }

    @Test
    public void onAutofocusFailedCallsMethodOnPluginCallback() {
        mCameraEventsListener.onAutofocusFailed();

        verify(mCallback, times(1)).onAutoFocusFailed();
    }

    @Test
    public void onAutofocusStartedCallsMethodOnPluginCallback() {
        mCameraEventsListener.onAutofocusStarted(null);

        verify(mCallback, times(1)).onAutoFocusStarted(null);
    }

    @Test
    public void onAutofocusStoppedCallsMethodOnPluginCallback() {
        mCameraEventsListener.onAutofocusStopped(null);

        verify(mCallback, times(1)).onAutoFocusStopped(null);
    }

    @Test
    public void changeRecognizerThrowsLifecycleExceptionWhenRecognizerStateIsDestroyed() {
        mPresenter.mRecognizerState = RecognizerState.DESTROYED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(LifecycleFlowException.ON_START_NOT_CALLED_SWAP_RECOGNIZERS +
                mPresenter.mRecognizerState);

        mPresenter.changeRecognizer(ScannerRecognizer.COLOMBIA_ID_CARD_FRONT_SIDE);
    }

    @Test
    public void changeRecognizerThrowsLifecycleExceptionWhenRecognizerStateIsCreated() {
        mPresenter.mRecognizerState = RecognizerState.CREATED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(LifecycleFlowException.ON_START_NOT_CALLED_SWAP_RECOGNIZERS +
                mPresenter.mRecognizerState);

        mPresenter.changeRecognizer(ScannerRecognizer.UAE_ID_CARD_BACK_SIDE);
    }

    @Test
    public void changeRecognizerCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.RESUMED;

        mPresenter.changeRecognizer(ScannerRecognizer.EGYPT_ID_CARD_FRONT_SIDE);

        verify(mView, times(1))
                .reconfigureRecognizer(ScannerRecognizer.EGYPT_ID_CARD_FRONT_SIDE);
    }

    @Test
    public void changeTorchModeCallsNoMethodOnViewWhenTorchStateIsNull() {
        CompletionCallback callback = Mockito.mock(CompletionCallback.class);

        mPresenter.changeTorchMode(null, callback);

        verify(mView, never()).setTorch(eq(false), any());
        verify(mView, never()).setTorch(eq(true), any());
        verify(callback, times(1)).onOperationDone(false);
    }

    @Test
    public void changeTorchModeCallsNoMethodOnViewWhenTorchStateIsNullAndCallbackIsNull() {
        mPresenter.changeTorchMode(null, null);

        verify(mView, never()).setTorch(eq(false), any());
        verify(mView, never()).setTorch(eq(true), any());
    }

    @Test
    public void changeTorchModeCallsMethodOnViewAndCallbackIsNull() {
        mPresenter.changeTorchMode(TorchState.OFF, null);

        verify(mView, times(1)).setTorch(false, null);
    }

    @Test
    public void changeTorchModeCallsMethodOnViewAndCallbackIsNotNull() {
        CompletionCallback callback = Mockito.mock(CompletionCallback.class);
        mPresenter.changeTorchMode(TorchState.ON, callback);

        verify(mView, times(1)).setTorch(true, callback);
    }

    @PrepareForTest(value = {ScannerUtils.class, Looper.class, Log.class})
    @Test
    public void isRecognizerSupportedReturnsTrueWhenRecognizerIsSupported() {
        ScannerRecognizer scannerRecognizer = Mockito.mock(ScannerRecognizer.class);
        PowerMockito.mockStatic(ScannerUtils.class);
        PowerMockito.mockStatic(Log.class);
        PowerMockito.when(ScannerUtils.checkIfRecognizerIsSupported(scannerRecognizer.getRecognizer(),
                mContext, mConfiguration.getCameraType())).thenReturn(true);

        boolean actual = mPresenter.isRecognizerSupported(scannerRecognizer);

        assertTrue(actual);
    }

    @PrepareForTest(value = {ScannerUtils.class, Looper.class, Log.class})
    @Test
    public void isRecognizerSupportedReturnsTrueWhenRecognizerIsNotSupported() {
        ScannerRecognizer scannerRecognizer = Mockito.mock(ScannerRecognizer.class);
        PowerMockito.mockStatic(ScannerUtils.class);
        PowerMockito.mockStatic(Log.class);
        PowerMockito.when(ScannerUtils.checkIfRecognizerIsSupported(scannerRecognizer.getRecognizer(),
                mContext, mConfiguration.getCameraType())).thenReturn(false);

        boolean actual = mPresenter.isRecognizerSupported(scannerRecognizer);

        assertFalse(actual);
    }

    @Test
    public void isRecognizerSupportedReturnsFalseWhenRecognizerIsNull() {
        boolean actual = mPresenter.isRecognizerSupported(null);

        assertFalse(actual);
    }

    @Test
    public void setScanningAreaSetsRectangleOnView() {
        mPresenter.setScanningArea(10, 10, 10, 10);

        verify(mView, times(1)).setScanningRegion(any(Rectangle.class), eq(false));
    }

    @Test
    public void onCreateThrowsLifecycleExceptionWhenRecognizerStateDoNotHaveDesiredState() {
        mPresenter.mRecognizerState = RecognizerState.RESUMED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(ON_CREATE_ALREADY_CALLED + RecognizerState.RESUMED);

        mPresenter.onCreate();
    }

    @Test
    public void onStartThrowsLifecycleExceptionWhenRecognizerStateDoNotHaveDesiredState() {
        mPresenter.mRecognizerState = RecognizerState.RESUMED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(ON_CREATE_NOT_CALLED + RecognizerState.RESUMED);

        mPresenter.onStart();
    }

    @Test
    public void onResumeThrowsLifecycleExceptionWhenRecognizerStateDoNotHaveDesiredState() {
        mPresenter.mRecognizerState = RecognizerState.CREATED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(ON_START_NOT_CALLED + RecognizerState.CREATED);

        mPresenter.onResume();
    }

    @Test
    public void onPauseThrowsLifecycleExceptionWhenRecognizerStateDoNotHaveDesiredState() {
        mPresenter.mRecognizerState = RecognizerState.STARTED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(ON_RESUME_NOT_CALLED + RecognizerState.STARTED);

        mPresenter.onPause();
    }

    @Test
    public void onStopThrowsLifecycleExceptionWhenRecognizerStateDoNotHaveDesiredState() {
        mPresenter.mRecognizerState = RecognizerState.RESUMED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(ON_PAUSE_NOT_CALLED + RecognizerState.RESUMED);

        mPresenter.onStop();
    }

    @Test
    public void onDestroyThrowsLifecycleExceptionWhenRecognizerStateDoNotHaveDesiredState() {
        mPresenter.mRecognizerState = RecognizerState.STARTED;
        thrown.expect(LifecycleFlowException.class);
        thrown.expectMessage(ON_STOP_NOT_CALLED + RecognizerState.STARTED);

        mPresenter.onDestroy();
    }

    @Test
    public void onCreateThrowsWrongThreadExceptionWhenMethodIsNotCalledOnMainThread() {
        mPresenter.mRecognizerState = RecognizerState.DESTROYED;
        when(Looper.myLooper()).thenReturn(mFakeLooper);
        thrown.expect(WrongThreadException.class);
        thrown.expectMessage(ON_CREATE);

        mPresenter.onCreate();
    }

    @Test
    public void onStartThrowsWrongThreadExceptionWhenMethodIsNotCalledOnMainThread() {
        mPresenter.mRecognizerState = RecognizerState.CREATED;
        when(Looper.myLooper()).thenReturn(mFakeLooper);
        thrown.expect(WrongThreadException.class);
        thrown.expectMessage(ON_START);

        mPresenter.onStart();
    }

    @Test
    public void onResumeThrowsWrongThreadExceptionWhenMethodIsNotCalledOnMainThread() {
        mPresenter.mRecognizerState = RecognizerState.STARTED;
        when(Looper.myLooper()).thenReturn(mFakeLooper);
        thrown.expect(WrongThreadException.class);
        thrown.expectMessage(ON_RESUME);

        mPresenter.onResume();
    }

    @Test
    public void onPauseThrowsWrongThreadExceptionWhenMethodIsNotCalledOnMainThread() {
        mPresenter.mRecognizerState = RecognizerState.RESUMED;
        when(Looper.myLooper()).thenReturn(mFakeLooper);
        thrown.expect(WrongThreadException.class);
        thrown.expectMessage(ON_PAUSE);

        mPresenter.onPause();
    }

    @Test
    public void onStopThrowsWrongThreadExceptionWhenMethodIsNotCalledOnMainThread() {
        mPresenter.mRecognizerState = RecognizerState.STARTED;
        when(Looper.myLooper()).thenReturn(mFakeLooper);
        thrown.expect(WrongThreadException.class);
        thrown.expectMessage(ON_STOP);

        mPresenter.onStop();
    }

    @Test
    public void onDestroyThrowsWrongThreadExceptionWhenMethodIsNotCalledOnMainThread() {
        mPresenter.mRecognizerState = RecognizerState.CREATED;
        when(Looper.myLooper()).thenReturn(mFakeLooper);
        thrown.expect(WrongThreadException.class);
        thrown.expectMessage(ON_DESTROY);

        mPresenter.onDestroy();
    }

    @Test
    public void onCreateCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.DESTROYED;
        mPresenter.onCreate();

        verify(mView, times(1)).createRecognizer();
        assertEquals(RecognizerState.CREATED, mPresenter.mRecognizerState);
    }

    @Test
    public void onStartCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.CREATED;

        mPresenter.onStart();

        verify(mView, times(1)).startRecognizer();
        assertEquals(RecognizerState.STARTED, mPresenter.mRecognizerState);
    }

    @Test
    public void onResumeCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.STARTED;

        mPresenter.onResume();

        verify(mView, times(1)).resumeRecognizer();
        verify(mView, times(1)).setMetadataCallbacks(any(MetadataCallbacks.class));
        assertEquals(RecognizerState.RESUMED, mPresenter.mRecognizerState);
    }

    @Test
    public void onPauseCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.RESUMED;

        mPresenter.onPause();

        verify(mView, times(1)).pauseRecognizer();
        verify(mView, times(1)).setMetadataCallbacks(null);
        assertEquals(RecognizerState.STARTED, mPresenter.mRecognizerState);
    }

    @Test
    public void onStopCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.STARTED;

        mPresenter.onStop();

        verify(mView, times(1)).stopRecognizer();
        assertEquals(RecognizerState.CREATED, mPresenter.mRecognizerState);
    }

    @Test
    public void onDestroyCallsMethodOnView() {
        mPresenter.mRecognizerState = RecognizerState.CREATED;

        mPresenter.onDestroy();

        verify(mView, times(1)).destroyRecognizer();
        assertEquals(RecognizerState.DESTROYED, mPresenter.mRecognizerState);
    }

    @Test
    public void createMetadataCallbacksCreatesMetadataWith2Listeners() {
        MetadataCallbacks callbacks = mPresenter.createMetadataCallbacks();

        assertNotNull(callbacks.getFirstSideRecognitionCallback());
        assertNotNull(callbacks.getFailedDetectionCallback());
        assertNull(callbacks.getDebugImageCallback());
        assertNull(callbacks.getDebugTextCallback());
        assertNull(callbacks.getQuadDetectionCallback());
        assertNull(callbacks.getOcrCallback());
        assertNull(callbacks.getGlareCallback());
        assertNull(callbacks.getPointsDetectionCallback());
    }

    @Test
    public void firstSideRecognitionFinishedCallbackCallsMethodOnPluginCallback() {
        MetadataCallbacks callbacks = mPresenter.createMetadataCallbacks();
        FirstSideRecognitionCallback firstSideRecognitionCallback = callbacks.getFirstSideRecognitionCallback();
        assertNotNull(firstSideRecognitionCallback);

        firstSideRecognitionCallback.onFirstSideRecognitionFinished();

        verify(mCallback, times(1)).onFirstSideRecognitionFinished();
    }

    @Test
    public void failedDetectionCallbackCallsMethodOnPluginCallback() {
        MetadataCallbacks callbacks = mPresenter.createMetadataCallbacks();
        FailedDetectionCallback failedDetectionCallback = callbacks.getFailedDetectionCallback();
        assertNotNull(failedDetectionCallback);

        failedDetectionCallback.onDetectionFailed();

        verify(mCallback, times(1)).onScannedFailed();
    }
}