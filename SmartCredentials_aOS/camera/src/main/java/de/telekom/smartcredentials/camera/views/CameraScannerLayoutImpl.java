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
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewStub;
import android.view.ViewTreeObserver;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;

import java.util.Random;

import de.telekom.smartcredentials.camera.R;
import de.telekom.smartcredentials.camera.views.presenters.CameraScannerPresenter;
import de.telekom.smartcredentials.camera.views.presenters.CameraScannerView;
import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.camera.ScannerPluginUnavailable;
import de.telekom.smartcredentials.core.plugins.callbacks.ScannerPluginCallback;

public abstract class CameraScannerLayoutImpl extends CameraScannerLayout implements CameraScannerView {

    private static final float CAMERA_FPS = 15.0f;

    protected int mWidth;
    protected int mHeight;
    protected boolean mStartCameraRequested;
    protected View mCustomDetectedBlockView;
    protected Context mContext;
    protected CameraSource mCameraSource;
    protected CameraSourceViewGroup mPreview;
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutOnGlobalLayoutListener;
    private Rect mRect;

    public CameraScannerLayoutImpl(Context context, ScannerPluginCallback<ScannerPluginUnavailable> scannerPluginCallback) {
        super(context);
        init(context);
        setPluginCallback(scannerPluginCallback);
    }

    @Override
    public void startScanner() {
        startScanner(null);
    }

    @Override
    public void releaseCamera() {
        if (mPreview != null) {
            mPreview.releaseCamera();
        }
        mStartCameraRequested = false;
        getViewTreeObserver().removeOnGlobalLayoutListener(mLayoutOnGlobalLayoutListener);
        mCameraSource = null;
    }

    @Override
    public void stopCamera() {
        if (mPreview != null) {
            mPreview.stop();
        }
        mStartCameraRequested = false;
        mCameraSource = null;
    }

    @Override
    public void prepareCameraStart() {
        mPreview.prepareCameraStart();
    }

    @Override
    public boolean hasCameraPermission() {
        int result = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void initCameraSource() {
        Detector detector = getDetector();
        detector.setProcessor(getProcessor());

        if (!detector.isOperational()) {
            getPresenter().onDetectorNotOperational();
            return;
        }

        mCameraSource = new CameraSource.Builder(mContext, detector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(mWidth, mHeight)
                .setRequestedFps(CAMERA_FPS)
                .setAutoFocusEnabled(true)
                .build();
    }

    @Override
    public boolean hasLowStorage() {
        IntentFilter lowStorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
        return mContext.registerReceiver(null, lowStorageFilter) != null;
    }

    @Override
    public boolean isGooglePlayServicesAvailable() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(mContext) == ConnectionResult.SUCCESS;
    }

    @Override
    public void startCameraSource() {
        if (mCameraSource != null) {
            mPreview.start(mCameraSource, getDetectedBlockView());
        }
    }

    protected abstract DetectedBlockView getDetectedBlockView();

    @Override
    public void stopScanner() {
        if (mPreview != null) {
            mPreview.stop();
        }
        mCameraSource = null;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPreview = null;
    }

    public void setRect(Rect rect) {
        mRect = rect;
    }

    public Rect getRect() {
        return mRect;
    }

    public abstract int getCustomLayoutResource();

    public abstract void setupCustomDetectedBlockView();

    public abstract void assembleView();

    public abstract void informPresenterViewIsReady();

    public abstract CameraScannerPresenter getPresenter();

    public abstract Detector getDetector();

    public abstract Detector.Processor getProcessor();

    protected void startScanner(Rect rect) {
        mStartCameraRequested = true;
        setRect(rect);
        tryStartScanner();
    }

    protected void init(Context context) {
        mContext = (context != null && context.getApplicationContext() != null) ? context.getApplicationContext() : context;
        View view = inflate(context, R.layout.sc_layout_camera_scanner, this);
        mPreview = view.findViewById(R.id.sc_view_group_camera);
        ViewStub viewStub = view.findViewById(R.id.sc_view_stub_camera);
        viewStub.setLayoutResource(getCustomLayoutResource());

        mCustomDetectedBlockView = viewStub.inflate();
        setupCustomDetectedBlockView();
        setId(new Random().nextInt());
        assembleView();
        informPresenterViewIsReady();
        initGlobalLayoutListener();
    }

    private void initGlobalLayoutListener() {
        mLayoutOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mWidth == 0 && mHeight == 0 && mWidth != getWidth() && mHeight != getHeight()) {
                    setDimensions(getWidth(), getHeight());
                    tryStartScanner();
                }

                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(mLayoutOnGlobalLayoutListener);
    }

    private void setPluginCallback(ScannerPluginCallback<ScannerPluginUnavailable> pluginCallback) {
        mPreview.setPluginCallback(pluginCallback);
        getPresenter().setPluginCallback(pluginCallback);
    }

    private void tryStartScanner() {
        if (mStartCameraRequested && mWidth > 0 && mHeight > 0) {
            if (getRect() == null) {
                setRect(new Rect(0, 0, mWidth, mHeight));
            }
            getPresenter().startCameraRequested();
        }
    }

    private void setDimensions(int cameraWidth, int cameraHeight) {
        mWidth = cameraWidth;
        mHeight = cameraHeight;
    }
}
