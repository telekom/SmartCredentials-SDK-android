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

package de.telekom.smartcredentials.camera.ocr;

import android.graphics.Rect;

public interface OcrCameraScannerLayout {

    /**
     * Called when detection needs to start.
     * The detection is performed taking in consideration the whole camera surface.
     */
    void detect();


    /**
     * Called when detection needs to start.
     * The detection is performed taking in consideration the whole camera surface.
     *
     * @param onBitmapLoadCallback Callback that will be called once the bitmap image is created.
     */
    void detect(OnBitmapLoadedCallback onBitmapLoadCallback);


    /**
     * Called when detection needs to start.
     * The detection is performed taking in consideration the whole camera surface.
     *
     * @param textParser TextParser will be applied on the detected strings.
     */
    void detect(TextParser textParser);


    /**
     * Called when detection needs to start.
     * The detection is performed taking in consideration the whole camera surface.
     *
     * @param onBitmapLoadCallback Callback that will be called once the bitmap image is created.
     * @param textParser           TextParser will be applied on the detected strings.
     */
    void detect(TextParser textParser, OnBitmapLoadedCallback onBitmapLoadCallback);

    /**
     * Called when detection needs to start.
     * The detection is performed inside the defined rectangle.
     */
    void detect(Rect rect);

    /**
     * Called when detection needs to start.
     * The detection is performed inside the defined rectangle.
     *
     * @param onBitmapLoadCallback Callback that will be called once the bitmap image is created.
     */
    void detect(OnBitmapLoadedCallback onBitmapLoadCallback, Rect rect);


    /**
     * Called when detection needs to start.
     * The detection is performed inside the defined rectangle.
     *
     * @param textParser TextParser will be applied on the detected strings.
     */
    void detect(TextParser textParser, Rect rect);


    /**
     * Called when detection needs to start.
     * The detection is performed inside the defined rectangle.
     *
     * @param onBitmapLoadCallback Callback that will be called once the bitmap image is created.
     * @param textParser           TextParser will be applied on the detected strings.
     */
    void detect(TextParser textParser, OnBitmapLoadedCallback onBitmapLoadCallback, Rect rect);
}
