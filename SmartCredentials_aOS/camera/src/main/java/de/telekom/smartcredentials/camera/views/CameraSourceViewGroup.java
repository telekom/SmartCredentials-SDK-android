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

package de.telekom.smartcredentials.camera.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

import java.io.IOException;
import java.lang.reflect.Field;

import de.telekom.smartcredentials.camera.views.presenters.CameraSourcePreviewPresenter;
import de.telekom.smartcredentials.camera.views.presenters.CameraSourceView;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.CAMERA_SECURITY_EXCEPTION;
import static de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable.CAMERA_START_EXCEPTION;

public class CameraSourceViewGroup extends CameraBaseViewGroup implements CameraSourceView {

    CameraSourcePreviewPresenter mPresenter;
    private SurfaceView mSurfaceView;
    private CameraSource mCameraSource;
    private DetectedBlockView mOverlay;

    public CameraSourceViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public Size getPreviewSize() {
        if (mCameraSource != null) {
            return mCameraSource.getPreviewSize();
        }
        return null;
    }

    @RequiresPermission(Manifest.permission.CAMERA)
    @Override
    public void startCameraSource() {
        try {
            mCameraSource.start(mSurfaceView.getHolder());
            mPresenter.onCameraStarted();
        } catch (SecurityException se) {
            mPresenter.onError(CAMERA_SECURITY_EXCEPTION);
            releaseCamera();
        } catch (IOException e) {
            mPresenter.onError(CAMERA_START_EXCEPTION);
        }
    }

    @Override
    public Camera getCamera() {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    return (Camera) field.get(mCameraSource);
                } catch (IllegalAccessException ignored) {
                }
            }
        }
        return null;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void applyManualFocusCameraOnTouch(Camera camera) {
        setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                mPresenter.applyManualFocus(camera);
            }
            return true;
        });
    }

    @Override
    public void removeManualFocusListener() {
        setOnTouchListener(null);
    }

    @Override
    public void stopCameraSource() {
        if (mCameraSource != null) {
            mCameraSource.stop();
            mCameraSource = null;
        }
    }

    @Override
    public void adjustCameraOrientation() {
        if (mOverlay != null) {
            Size size = mCameraSource.getPreviewSize();
            int min = Math.min(size.getWidth(), size.getHeight());
            int max = Math.max(size.getWidth(), size.getHeight());
            if (isPortraitMode()) {
                mOverlay.setCameraInfo(min, max, mCameraSource.getCameraFacing());
            } else {
                mOverlay.setCameraInfo(max, min, mCameraSource.getCameraFacing());
            }
            mOverlay.clearBlockView();
        }
    }

    @Override
    public void init() {
        mPresenter = new CameraSourcePreviewPresenter();
        informPresenterViewIsReady();
        recreateSurface();
    }

    @Override
    public void recreateSurface() {
        removeView(mSurfaceView);
        mSurfaceView = new SurfaceView(mContext);
        mSurfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(mSurfaceView);
    }

    public void start(CameraSource cameraSource, DetectedBlockView overlay) {
        mOverlay = overlay;
        if (cameraSource == null) {
            stop();
            return;
        }

        mCameraSource = cameraSource;
        mPresenter.notifyStartRequested();
    }

    public void stop() {
        mPresenter.stopRequested();
    }

    public void prepareCameraStart() {
        mPresenter.onPrepareCameraRequested();
    }

    public void setPluginCallback(ScannerPluginCallback<ScannerPluginUnavailable> pluginCallback) {
        mPresenter.setPluginCallback(pluginCallback);
    }

    public void releaseCamera() {
        if (mCameraSource != null) {
            try {
                mCameraSource.release();
            } catch (NullPointerException ignored) {
            }
            mCameraSource = null;
        }
    }

    private void informPresenterViewIsReady() {
        mPresenter.viewReady(this);
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surface) {
            mPresenter.notifySurfaceCreated();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surface) {
            mPresenter.notifySurfaceDestroyed();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
    }
}
