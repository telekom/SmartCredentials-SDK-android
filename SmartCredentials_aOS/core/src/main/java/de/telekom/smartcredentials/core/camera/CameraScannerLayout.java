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

package de.telekom.smartcredentials.core.camera;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class CameraScannerLayout extends LinearLayout {

    public CameraScannerLayout(Context context) {
        super(context);
    }

    /**
     * Call this when the scanner - along with the camera - needs to be started
     */
    public abstract void startScanner();

    /**
     * Call this in onPause
     */
    public abstract void stopScanner();

    /**
     * Call this in onDestroy
     */
    public abstract void releaseCamera();
}
