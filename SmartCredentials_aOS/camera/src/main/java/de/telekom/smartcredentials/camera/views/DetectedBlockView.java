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

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.vision.CameraSource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DetectedBlockView<T> extends View {

    public final Set<DetectedBlock<T>> mBlockHandlerSet = new HashSet<>();
    private final Object mLock = new Object();
    private float mWidthScaleFactor = 1.0f;
    private float mHeightScaleFactor = 1.0f;
    private int mPreviewWidth;
    private int mPreviewHeight;
    private int mCameraFacing = CameraSource.CAMERA_FACING_BACK;
    private boolean mBlockedDetection;

    public DetectedBlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (mLock) {
            if ((mPreviewWidth != 0) && (mPreviewHeight != 0)) {
                mWidthScaleFactor = (float) canvas.getWidth() / (float) mPreviewWidth;
                mHeightScaleFactor = (float) canvas.getHeight() / (float) mPreviewHeight;
            }
        }
    }

    public void blockDetection() {
        mBlockedDetection = true;
    }

    public void clearBlockView() {
        if (mBlockedDetection) {
            return;
        }
        synchronized (mLock) {
            mBlockHandlerSet.clear();
        }
        postInvalidate();
    }

    public void addBlockView(DetectedBlock<T> detectedBlockHandler) {
        if (mBlockedDetection) {
            return;
        }
        synchronized (mLock) {
            mBlockHandlerSet.add(detectedBlockHandler);
        }
        postInvalidate();
    }

    public List<T> getBlocksAtLocation(float left, float top, float right, float bottom) {
        synchronized (mLock) {
            int[] location = new int[2];
            this.getLocationOnScreen(location);
            List<T> graphics = new ArrayList<>();

            for (DetectedBlock<T> detectedBlock : mBlockHandlerSet) {
                float adjustedLeft = left - location[0];
                float adjustedTop = top - location[1];
                float adjustedRight = right - location[0];
                float adjustedBottom = bottom - location[1];
                T block = detectedBlock.getBlockBetween(adjustedLeft, adjustedTop, adjustedRight, adjustedBottom);
                if (block != null) {
                    graphics.add(block);
                }
            }
            allowDetection();
            return graphics;
        }
    }

    public void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (mLock) {
            mPreviewWidth = previewWidth;
            mPreviewHeight = previewHeight;
            mCameraFacing = facing;
        }
        postInvalidate();
    }

    public float getWidthScaleFactor() {
        return mWidthScaleFactor;
    }

    public float getHeightScaleFactor() {
        return mHeightScaleFactor;
    }

    public int getCameraFacing() {
        return mCameraFacing;
    }

    void allowDetection() {
        mBlockedDetection = false;
    }
}
