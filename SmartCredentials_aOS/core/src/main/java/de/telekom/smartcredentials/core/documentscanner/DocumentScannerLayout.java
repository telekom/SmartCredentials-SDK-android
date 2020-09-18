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

package de.telekom.smartcredentials.core.documentscanner;

import android.content.Context;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import android.widget.FrameLayout;

@SuppressWarnings({"JavaDoc", "unused"})
public abstract class DocumentScannerLayout<R> extends FrameLayout {

    public DocumentScannerLayout(@NonNull Context context) {
        super(context);
    }

    /**
     * Pauses the scanning loop, but keeps camera active.
     * You can resume scanning by calling {@link #resumeScanning(boolean)}
     * <p>
     * Please be advised that {@link #pauseScanning()} and {@link #resumeScanning(boolean)}
     * are counting calls, so if you call {@link #pauseScanning()} twice, make sure you also call
     * {@link #resumeScanning(boolean)} twice to actually resume scanning process.
     */
    public abstract void pauseScanning();

    /**
     * Resumes the scanning loop that was paused by {@link #pauseScanning()}.
     * Set param shouldResetRecognizerState to true if you want to reset the recognition state. (i.e. delete cached data)
     * <p>
     * Please be advised that {@link #pauseScanning()} and {@link #resumeScanning(boolean)}
     * are counting calls, so if you call {@link #pauseScanning()} twice, make sure you also call
     * {@link #resumeScanning(boolean)} twice to actually resume scanning process.
     *
     * @param shouldResetRecognizerState true if recognizer's internal state should be reset when resuming scanning
     */
    public abstract void resumeScanning(boolean shouldResetRecognizerState);

    /**
     * Use this method to reconfigure actual recognizer without creating another instance of {@link DocumentScannerLayout}
     * This method should be called anywhere after calling {@link #onStart()} and before {@link #onStop()}
     * otherwise a {@link LifecycleFlowException} will be thrown.
     *
     * @param recognizer the new recognizer to be applied to scanning process
     */
    public abstract void swapRecognizer(@NonNull R recognizer);

    /**
     * Sets the torch state.
     *
     * @param torchState a {@link TorchState} specifying the new state of the torch
     * @param callback   a {@link CompletionCallback} that will be triggered when setting torch state has finished.
     *                   Pass null if you don't want completion event
     */
    public abstract void setTorchMode(TorchState torchState, CompletionCallback callback);

    /**
     * Use this method prior to {@link #swapRecognizer(Object)} to be sure that the
     * recognizer is supported on the current device.
     *
     * @param recognizer to be check for compatibility
     * @return <code>true</code> if the scanner recognizer is supported on the device,
     * <code>false</code> otherwise
     */
    public abstract boolean isRecognizerSupported(R recognizer);

    /**
     * Use this method to check whether camera has torch or not.
     * This method is useful if you want to disable torch button if it is not supported.
     *
     * @return true if the torch is supported, false otherwise
     */
    public abstract boolean isCameraTorchSupported();

    /**
     * Use this method to restrict scanning area.
     * You must ensure that scanning area defined here is the same as in the layout.
     * <p>
     * Please be advised that this method works with percentage values relative to {@link DocumentScannerLayout}.
     * For example if your scanning area starts at 10% of the recognizer's width and 30% of the recognizer's height,
     * then set the first two parameters to 0.1f and 0.3f.
     * The next 2 parameters are rectangle's width and height. This means that if the rectangle's width
     * is 80% of {@link DocumentScannerLayout}'s width and the rectangle's height is 40% of {@link DocumentScannerLayout}'s height
     * then set the 3rd parameter to 0.8f and the 4th to 0.4f.
     *
     * @param leftAreaPercentage a value between 0.0 and 1.0 specifying the space between {@link DocumentScannerLayout} left point and the rectangle left point in percentage relative to {@link DocumentScannerLayout}
     * @param topAreaPercentage  a value between 0.0 and 1.0 specifying the space between {@link DocumentScannerLayout} top point and the rectangle top point in percentage relative to {@link DocumentScannerLayout}
     * @param widthPercentage    a value between 0.0 and 1.0 specifying the rectangle's width in percentage relative to {@link DocumentScannerLayout}
     * @param heightPercentage   a value between 0.0 and 1.0 specifying the rectangle's height in percentage relative to {@link DocumentScannerLayout}
     */
    public abstract void setScanningArea(@FloatRange(from = 0.0, to = 1.0) float leftAreaPercentage,
                                         @FloatRange(from = 0.0, to = 1.0) float topAreaPercentage,
                                         @FloatRange(from = 0.0, to = 1.0) float widthPercentage,
                                         @FloatRange(from = 0.0, to = 1.0) float heightPercentage);

    /**
     * This method must be called from activity/fragment's onCreate lifecycle callback.
     * If it's not called the scanning process will not work.
     */
    @UiThread
    public abstract void onCreate();

    /**
     * This method must be called from activity/fragment's onStart lifecycle callback.
     * If it's not called the scanning process will not work.
     */
    @UiThread
    public abstract void onStart();

    /**
     * This method must be called from activity/fragment's onResume lifecycle callback.
     * If it's not called the scanning process will not work.
     */
    @UiThread
    public abstract void onResume();

    /**
     * This method must be called from activity/fragment's onPause lifecycle callback.
     */
    @UiThread
    public abstract void onPause();

    /**
     * This method must be called from activity/fragment's onStop lifecycle callback.
     */
    @UiThread
    public abstract void onStop();

    /**
     * This method must be called from activity/fragment's onDestroy lifecycle callback.
     */
    @UiThread
    public abstract void onDestroy();
}
