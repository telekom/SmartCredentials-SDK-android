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

package de.telekom.smartcredentials.camera.views.presenters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.CAMERA_PERMISSION_NEEDED;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.DETECTOR_NOT_OPERATIONAL;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.GOOGLE_PLAY_SERVICES_UNAVAILABLE;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.LOW_STORAGE;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CameraScannerPresenterTest {

    private CameraScannerView mCameraScannerView;

    private CameraScannerPresenter mPresenter;

    @Before
    public void setUp() {
        mCameraScannerView = Mockito.mock(CameraScannerView.class);
        mPresenter = new CameraScannerPresenter();
        mPresenter.viewReady(mCameraScannerView);
    }

    @Test
    public void startCameraRequestedDoesNotInitCameraSourceWhenNoCameraPermission() {
        when(mCameraScannerView.hasCameraPermission()).thenReturn(false);

        mPresenter.startCameraRequested();

        verify(mCameraScannerView).stopCamera();
        verify(mCameraScannerView).prepareCameraStart();
        verify(mCameraScannerView).hasCameraPermission();
        assertFalse(mPresenter.mIsCameraStarted);
        verify(mCameraScannerView, never()).initCameraSource();
    }

    @Test
    public void startCameraRequestedCallsOnErrorWhenNoCameraPermission() {
        CameraScannerPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        cameraScannerPresenterSpy.viewReady(mCameraScannerView);
        when(mCameraScannerView.hasCameraPermission()).thenReturn(false);

        cameraScannerPresenterSpy.startCameraRequested();

        verify(cameraScannerPresenterSpy).onError(CAMERA_PERMISSION_NEEDED);
    }

    @Test
    public void startCameraRequestedDoesNotStartCameraSourceWhenNoCameraPermission() {
        when(mCameraScannerView.hasCameraPermission()).thenReturn(true);
        when(mCameraScannerView.isGooglePlayServicesAvailable()).thenReturn(false);

        mPresenter.startCameraRequested();

        verify(mCameraScannerView).stopCamera();
        verify(mCameraScannerView).prepareCameraStart();
        verify(mCameraScannerView).hasCameraPermission();
        assertTrue(mPresenter.mIsCameraStarted);
        verify(mCameraScannerView).initCameraSource();
        verify(mCameraScannerView, never()).startCameraSource();
    }

    @Test
    public void startCameraRequestedCallsOnErrorWhenGooglePlayServicesUnavailable() {
        CameraScannerPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        cameraScannerPresenterSpy.viewReady(mCameraScannerView);
        when(mCameraScannerView.hasCameraPermission()).thenReturn(true);
        when(mCameraScannerView.isGooglePlayServicesAvailable()).thenReturn(false);

        cameraScannerPresenterSpy.startCameraRequested();

        verify(cameraScannerPresenterSpy, never()).onError(CAMERA_PERMISSION_NEEDED);
        verify(cameraScannerPresenterSpy).onError(GOOGLE_PLAY_SERVICES_UNAVAILABLE);
    }

    @Test
    public void startCameraRequestedCallsStartCameraSourceOnView() {
        when(mCameraScannerView.hasCameraPermission()).thenReturn(true);
        when(mCameraScannerView.isGooglePlayServicesAvailable()).thenReturn(true);

        mPresenter.startCameraRequested();

        verify(mCameraScannerView).stopCamera();
        verify(mCameraScannerView).prepareCameraStart();
        verify(mCameraScannerView).hasCameraPermission();
        assertTrue(mPresenter.mIsCameraStarted);
        verify(mCameraScannerView).initCameraSource();
        verify(mCameraScannerView).startCameraSource();
    }

    @Test
    public void startCameraRequestedDoesNotCallErrorMethodWhenHasCameraPermissionAndGooglePlayServicesAvailable() {
        CameraScannerPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        cameraScannerPresenterSpy.viewReady(mCameraScannerView);
        when(mCameraScannerView.hasCameraPermission()).thenReturn(true);
        when(mCameraScannerView.isGooglePlayServicesAvailable()).thenReturn(true);

        cameraScannerPresenterSpy.startCameraRequested();

        verify(cameraScannerPresenterSpy, never()).onError(CAMERA_PERMISSION_NEEDED);
        verify(cameraScannerPresenterSpy, never()).onError(GOOGLE_PLAY_SERVICES_UNAVAILABLE);
    }

    @Test
    public void onDetectorNotOperationalNotifiesLowStorage() {
        CameraScannerPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        doNothing().when(cameraScannerPresenterSpy).onError(LOW_STORAGE);
        when(mCameraScannerView.hasLowStorage()).thenReturn(true);

        cameraScannerPresenterSpy.onDetectorNotOperational();

        verify(cameraScannerPresenterSpy).onError(LOW_STORAGE);
    }

    @Test
    public void onDetectorNotOperationalNotifiesDetectorNotOperational() {
        CameraScannerPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        doNothing().when(cameraScannerPresenterSpy).onError(DETECTOR_NOT_OPERATIONAL);
        when(mCameraScannerView.hasLowStorage()).thenReturn(false);

        cameraScannerPresenterSpy.onDetectorNotOperational();

        verify(cameraScannerPresenterSpy).onError(DETECTOR_NOT_OPERATIONAL);
    }

}