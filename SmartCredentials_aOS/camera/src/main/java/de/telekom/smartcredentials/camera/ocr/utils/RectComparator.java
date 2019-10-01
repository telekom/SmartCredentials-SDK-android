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

package de.telekom.smartcredentials.camera.ocr.utils;

import android.graphics.RectF;

import com.google.android.gms.vision.text.Text;

import de.telekom.smartcredentials.camera.views.utils.RectAdjuster;

public class RectComparator {

    public static boolean isTextInBounds(Text element, float left, float top, float right, float bottom, RectAdjuster rectAdjuster) {
        RectF rect = new RectF(element.getBoundingBox());

        if (rectAdjuster != null) {
            rect.left = rectAdjuster.translateOnHorizontal(rect.left);
            rect.top = rectAdjuster.translateOnVertical(rect.top);
            rect.right = rectAdjuster.translateOnHorizontal(rect.right);
            rect.bottom = rectAdjuster.translateOnVertical(rect.bottom);
        }

        return (rect.left > left && rect.right < right && rect.top > top && rect.bottom < bottom);
    }
}
