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
 * Created by Lucian Iacob on 6/29/18 2:06 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.config;

import android.content.Context;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;

import java.util.Objects;

import de.telekom.smartcredentials.core.documentscanner.AspectMode;
import de.telekom.smartcredentials.core.documentscanner.CameraType;
import de.telekom.smartcredentials.core.documentscanner.DocumentScanConfiguration;
import de.telekom.smartcredentials.core.documentscanner.DocumentScannerLayout;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.core.documentscanner.VideoResolution;


public class SmartCredentialsDocumentScanConfiguration extends DocumentScanConfiguration {

    private final Context mContext;
    private final CameraType mCameraType;
    private final AspectMode mAspectMode;
    private final VideoResolution mVideoResolution;
    private final ScannerRecognizer mScannerRecognizer;
    private final boolean mPinchToZoomAllowed;
    private final boolean mShouldFocusOnShakingStop;
    private final boolean mShouldOptimizeCameraForNearScan;
    private final boolean mShouldAllowTapToFocus;
    private final float mZoomLevel;

    private SmartCredentialsDocumentScanConfiguration(final Builder builder) {
        mContext = builder.context;
        mScannerRecognizer = builder.scannerRecognizer;
        mCameraType = builder.cameraType;
        mAspectMode = builder.aspectMode;
        mPinchToZoomAllowed = builder.pinchToZoomAllowed;
        mShouldFocusOnShakingStop = builder.shouldFocusOnShakingStop;
        mShouldOptimizeCameraForNearScan = builder.shouldOptimizeCamera;
        mShouldAllowTapToFocus = builder.shouldAllowTapToFocus;
        mVideoResolution = builder.videoResolution;
        mZoomLevel = builder.zoomLevel;
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    public ScannerRecognizer getScannerRecognizer() {
        return mScannerRecognizer;
    }

    public CameraType getCameraType() {
        return mCameraType;
    }

    public AspectMode getAspectMode() {
        return mAspectMode;
    }

    public boolean isPinchToZoomAllowed() {
        return mPinchToZoomAllowed;
    }

    public boolean shouldFocusOnShakingStop() {
        return mShouldFocusOnShakingStop;
    }

    public boolean shouldOptimizeCameraForNearScan() {
        return mShouldOptimizeCameraForNearScan;
    }

    public boolean shouldAllowTapToFocus() {
        return mShouldAllowTapToFocus;
    }

    public VideoResolution getVideoResolution() {
        return mVideoResolution;
    }

    public float getZoomLevel() {
        return mZoomLevel;
    }

    @SuppressWarnings("WeakerAccess")
    public static class Builder {

        private final Context context;

        private ScannerRecognizer scannerRecognizer = ScannerRecognizer.MACHINE_READABLE_ZONE;
        private VideoResolution videoResolution = VideoResolution.DEFAULT;
        private CameraType cameraType = CameraType.DEFAULT;
        private AspectMode aspectMode = AspectMode.FILL;
        private boolean pinchToZoomAllowed = false;
        private boolean shouldFocusOnShakingStop = false;
        private boolean shouldOptimizeCamera = false;
        private boolean shouldAllowTapToFocus = true;
        private float zoomLevel = 0f;

        /**
         * Creates a builder for a {@link SmartCredentialsDocumentScanConfiguration} that uses the default settings.
         *
         * @param context {@link android.app.Activity}'s context in order to create {@link DocumentScannerLayout}
         * @throws NullPointerException if context is null
         */
        public Builder(@NonNull final Context context) {
            Objects.requireNonNull(context);
            this.context = context;
        }

        /**
         * Sets a {@link ScannerRecognizer} that will be used by {@link DocumentScannerLayout}.
         * <p>
         * Default is {@link ScannerRecognizer#MACHINE_READABLE_ZONE}
         *
         * @param scannerRecognizer recognizer that will be set in order to scan specific country's ID
         *                          or a payment card's side
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         * @throws NullPointerException if scannerRecognizer is null
         */
        public Builder setScannerRecognizer(@NonNull final ScannerRecognizer scannerRecognizer) {
            Objects.requireNonNull(scannerRecognizer);
            this.scannerRecognizer = scannerRecognizer;
            return this;
        }

        /**
         * Sets the camera type that will be used by scanner.
         * <p>
         * Default is {@link CameraType#DEFAULT}.
         *
         * @param cameraType enum specifying camera type
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setCameraType(CameraType cameraType) {
            if (cameraType != null) {
                this.cameraType = cameraType;
            }
            return this;
        }

        /**
         * Sets the aspect mode for the scanner.
         * <p>
         * Default is {@link AspectMode#FILL}.
         *
         * @param aspectMode enum specifying the aspect mode
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setAspectMode(AspectMode aspectMode) {
            if (aspectMode != null) {
                this.aspectMode = aspectMode;
            }
            return this;
        }

        /**
         * Sets whether pinch to zoom should be allowed or not.
         * <p>
         * Default is false.
         *
         * @param pinchToZoomAllowed true if pinch to zoom should be allowed, false otherwise
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setPinchToZoomAllowed(boolean pinchToZoomAllowed) {
            this.pinchToZoomAllowed = pinchToZoomAllowed;
            return this;
        }

        /**
         * Sets whether camera should autofocus after device shaking has stopped.
         * <p>
         * Default is false.
         *
         * @param setFocusOnShakingStop true if should request autofocus, false otherwise
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder shouldRequestAutofocusOnShakingStopInContinuousAutofocusMode(boolean setFocusOnShakingStop) {
            this.shouldFocusOnShakingStop = setFocusOnShakingStop;
            return this;
        }

        /**
         * Sets whether scanner should optimize the camera parameters for near object scan.
         * This means that macro focus will be applied instead of autofocus mode.
         * <p>
         * Default is false.
         *
         * @param shouldOptimizeCamera true if should optimize for near scan, false otherwise
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder shouldOptimizeCameraForNearScan(boolean shouldOptimizeCamera) {
            this.shouldOptimizeCamera = shouldOptimizeCamera;
            return this;
        }

        /**
         * Flag which indicates if camera should focus on tap.
         * <p>
         * Default is true.
         *
         * @param shouldAllowTapToFocus true if tap to focus is allowed, false otherwise
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder shouldAllowTapToFocus(boolean shouldAllowTapToFocus) {
            this.shouldAllowTapToFocus = shouldAllowTapToFocus;
            return this;
        }

        /**
         * Sets the camera resolution.
         * <p>
         * Default is {@link VideoResolution#DEFAULT}
         *
         * @param videoResolution enum defining the video resolution
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setVideoResolution(VideoResolution videoResolution) {
            if (videoResolution != null) {
                this.videoResolution = videoResolution;
            }
            return this;
        }

        /**
         * Sets the default camera zoom level.
         * <p>
         * Default is 0.0f.
         *
         * @param zoomLevel float between 0.0f and 1.0f specifying the default zoom level
         * @return this {@link Builder} object to allow for chaining of calls to set methods
         */
        public Builder setZoomLevel(@FloatRange(from = 0.0, to = 1.0) float zoomLevel) {
            this.zoomLevel = zoomLevel;
            return this;
        }

        /**
         * Creates a {@link SmartCredentialsDocumentScanConfiguration} with the arguments supplied to this builder.
         *
         * @return {@link SmartCredentialsDocumentScanConfiguration} - configuration object
         */
        public SmartCredentialsDocumentScanConfiguration build() {
            return new SmartCredentialsDocumentScanConfiguration(this);
        }
    }
}
