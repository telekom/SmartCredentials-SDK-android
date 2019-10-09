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

import android.hardware.Camera;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.SURFACE_UNAVAILABLE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CameraSourcePreviewPresenterTest {

    private CameraSourcePreviewPresenter mPresenter;
    private CameraSourceView mCameraSourceView;

    @Before
    public void setUp() {
        mCameraSourceView = Mockito.mock(CameraSourceView.class);

        mPresenter = new CameraSourcePreviewPresenter();
        mPresenter.viewReady(mCameraSourceView);
    }

    @Test
    public void notifyStartRequestedDoesNotCallStartCameraWhenSurfaceNotAvailable() {
        mPresenter.mSurfaceAvailable = false;
        mPresenter.notifyStartRequested();

        assertTrue(mPresenter.mStartRequested);
        assertFalse(mPresenter.mSurfaceAvailable);
        verify(mCameraSourceView, never()).startCameraSource();
    }

    @Test
    public void notifyStartRequestedNotifiesSurfaceUnavailable() {
        CameraSourcePreviewPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        cameraScannerPresenterSpy.mSurfaceAvailable = false;

        cameraScannerPresenterSpy.notifyStartRequested();

        verify(cameraScannerPresenterSpy).onError(SURFACE_UNAVAILABLE);
    }

    @Test
    public void notifyStartRequestedCallsStartCameraSourceOnViewAndResetsStartRequestedProperty() {
        mPresenter.mSurfaceAvailable = true;

        mPresenter.notifyStartRequested();

        assertFalse(mPresenter.mStartRequested);
        assertTrue(mPresenter.mSurfaceAvailable);
        verify(mCameraSourceView).startCameraSource();
    }

    @Test
    public void notifyStartRequestedDoesNotCallErrorMethodWhenSurfaceAvailable() {
        CameraSourcePreviewPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        cameraScannerPresenterSpy.mSurfaceAvailable = true;

        cameraScannerPresenterSpy.notifyStartRequested();

        verify(cameraScannerPresenterSpy, never()).onError(SURFACE_UNAVAILABLE);
    }

    @Test
    public void notifySurfaceCreatedSetsSurfaceAvailablePropertyAndDoesNotCallAnyMethodWhenStartWasNotRequested() {
        mPresenter.mStartRequested = false;

        mPresenter.notifySurfaceCreated();

        assertTrue(mPresenter.mSurfaceAvailable);
        assertFalse(mPresenter.mStartRequested);
        verify(mCameraSourceView, never()).startCameraSource();
    }

    @Test
    public void notifySurfaceCreatedDoeNotCallErrorMethodWhenStartWasNotRequested() {
        CameraSourcePreviewPresenter cameraScannerPresenterSpy = Mockito.spy(mPresenter);
        cameraScannerPresenterSpy.mStartRequested = false;

        cameraScannerPresenterSpy.notifySurfaceCreated();

        verify(cameraScannerPresenterSpy, never()).onError(SURFACE_UNAVAILABLE);
    }

    @Test
    public void notifySurfaceCreatedCallsStartCameraSourceOnViewAndResetsStartRequestedProperty() {
        mPresenter.mStartRequested = true;

        mPresenter.notifySurfaceCreated();

        assertTrue(mPresenter.mSurfaceAvailable);
        assertFalse(mPresenter.mStartRequested);
        verify(mCameraSourceView).startCameraSource();
    }

    @Test
    public void notifySurfaceDestroyedResetsSurfaceAvailableProperty() {
        mPresenter.mSurfaceAvailable = true;

        mPresenter.notifySurfaceDestroyed();

        assertFalse(mPresenter.mSurfaceAvailable);
    }

    @Test
    public void onPrepareCameraRequestedReturnsWithoutCallingOtherMethodWhenSurfaceIsAlreadyAvailable() {
        mPresenter.mSurfaceAvailable = true;

        mPresenter.onPrepareCameraRequested();

        verify(mCameraSourceView, never()).recreateSurface();
    }

    @Test
    public void onPrepareCameraRequestedCallsRecreateSurfaceOnViewWhenSurfaceIsNotAvailable() {
        mPresenter.mSurfaceAvailable = false;

        mPresenter.onPrepareCameraRequested();

        verify(mCameraSourceView).recreateSurface();
    }

    @Test
    public void onCameraStartedAdjustsCameraOrientationAndAppliesManualFocus() {
        Camera camera = Mockito.mock(Camera.class);
        when(mCameraSourceView.getCamera()).thenReturn(camera);

        mPresenter.onCameraStarted();

        verify(mCameraSourceView).adjustCameraOrientation();
        verify(mCameraSourceView).getCamera();
        verify(mCameraSourceView).applyManualFocusCameraOnTouch(camera);
    }

    @Test
    public void onCameraStartedAdjustsCameraOrientationAndDoesNotApplyManualFocusIfCameraObjectIsNull() {
        when(mCameraSourceView.getCamera()).thenReturn(null);

        mPresenter.onCameraStarted();

        verify(mCameraSourceView).adjustCameraOrientation();
        verify(mCameraSourceView).getCamera();
        verify(mCameraSourceView, never()).applyManualFocusCameraOnTouch(null);
    }

    @Test
    public void applyManualFocusSetsMacroMode() {
        Camera.Parameters parameters = Mockito.mock(Camera.Parameters.class);
        Camera camera = Mockito.mock(Camera.class);
        when(camera.getParameters()).thenReturn(parameters);

        mPresenter.applyManualFocus(camera);

        verify(camera).getParameters();
        verify(parameters).setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);
        verify(camera).setParameters(parameters);
        verify(camera).autoFocus(any());
    }

    @Test
    public void stopRequestedStopsCameraSourceAndRemovesManualFocusListener() {
        mPresenter.stopRequested();

        verify(mCameraSourceView).stopCameraSource();
        verify(mCameraSourceView).removeManualFocusListener();
    }

}