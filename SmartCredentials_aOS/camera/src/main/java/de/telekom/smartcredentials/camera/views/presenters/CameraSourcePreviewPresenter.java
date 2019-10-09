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

import java.lang.ref.WeakReference;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.SURFACE_UNAVAILABLE;

public class CameraSourcePreviewPresenter extends CameraViewPresenter {

    private static final String TAG = "CameraSourcePreviewPresenter";
    private static final String CAMERA_FOCUSED = "Camera successfully focused.";
    private static final String NULL_CAMERA = "Could not get camera reference to set manual focus listener.";

    boolean mStartRequested;
    boolean mSurfaceAvailable;
    private WeakReference<CameraSourceView> mView;

    @Override
    public String getTAG() {
        return TAG;
    }

    @Override
    public void onCameraStarted() {
        super.onCameraStarted();
        new WeakRefClassResolver<CameraSourceView>() {
            @Override
            public void onWeakRefResolved(CameraSourceView ref) {
                ref.adjustCameraOrientation();
                Camera camera = ref.getCamera();
                if (camera != null) {
                    ref.applyManualFocusCameraOnTouch(camera);
                } else {
                    ApiLoggerResolver.logError(TAG, NULL_CAMERA);
                }
            }
        }.execute(mView);
    }

    public void viewReady(CameraSourceView view) {
        mView = new WeakReference<>(view);
    }

    public void notifyStartRequested() {
        mStartRequested = true;
        tryStartCameraSource();
    }

    public void notifySurfaceCreated() {
        mSurfaceAvailable = true;
        tryStartCameraSource();
    }

    public void notifySurfaceDestroyed() {
        mSurfaceAvailable = false;
    }

    public void onPrepareCameraRequested() {
        if (mSurfaceAvailable) {
            return;
        }

        new WeakRefClassResolver<CameraSourceView>() {
            @Override
            public void onWeakRefResolved(CameraSourceView ref) {
                ref.recreateSurface();
            }
        }.execute(mView);
    }

    public void applyManualFocus(Camera camera) {
        camera.cancelAutoFocus();
        Camera.Parameters parameters = camera.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_MACRO);

        try {
            camera.setParameters(parameters);
        } catch (RuntimeException ignored) {
        }
        camera.startPreview();
        camera.autoFocus(getAutoFocusCallback());
    }

    public void stopRequested() {
        new WeakRefClassResolver<CameraSourceView>() {
            @Override
            public void onWeakRefResolved(CameraSourceView ref) {
                ref.stopCameraSource();
                ref.removeManualFocusListener();
            }
        }.execute(mView);
    }

    private void tryStartCameraSource() {
        if (mStartRequested) {
            if (mSurfaceAvailable) {
                startCameraSource();
            } else {
                onError(SURFACE_UNAVAILABLE);
            }
        }
    }

    private void startCameraSource() {
        new WeakRefClassResolver<CameraSourceView>() {
            @Override
            public void onWeakRefResolved(CameraSourceView ref) {
                ref.startCameraSource();
            }
        }.execute(mView);
        mStartRequested = false;
    }

    private Camera.AutoFocusCallback getAutoFocusCallback() {
        return (success, camera) -> {
            if (!camera.getParameters().getFocusMode().equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                camera.setParameters(parameters);
                camera.startPreview();
            }
            ApiLoggerResolver.logInfo(CAMERA_FOCUSED);
        };
    }
}
