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

import com.google.android.gms.vision.CameraSource;

import de.telekom.smartcredentials.camera.views.utils.RectAdjuster;

public abstract class DetectedBlock<T> implements RectAdjuster {

    private final DetectedBlockView<T> mDetectedBlockView;

    public DetectedBlock(DetectedBlockView<T> overlay) {
        mDetectedBlockView = overlay;
        postInvalidate();
    }

    @Override
    public float translateOnVertical(float y) {
        return y * mDetectedBlockView.getHeightScaleFactor();
    }

    @Override
    public float translateOnHorizontal(float x) {
        if (mDetectedBlockView.getCameraFacing() == CameraSource.CAMERA_FACING_FRONT) {
            return mDetectedBlockView.getWidth() - x * mDetectedBlockView.getWidthScaleFactor();
        } else {
            return x * mDetectedBlockView.getWidthScaleFactor();
        }
    }

    public abstract T getBlockBetween(float left, float top, float right, float bottom);

    private void postInvalidate() {
        mDetectedBlockView.postInvalidate();
    }
}
