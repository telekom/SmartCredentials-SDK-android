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
 * Created by Lucian Iacob on 6/29/18 2:02 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.utils;

import androidx.annotation.NonNull;

import com.microblink.hardware.camera.CameraResolutionTooSmallException;
import com.microblink.recognition.FeatureNotSupportedException;
import com.microblink.recognition.RecognizerError;

import de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable;

public class CameraErrorInterpreter {

    private CameraErrorInterpreter() {
        // required empty constructor
    }

    @NonNull
    public static ScannerPluginUnavailable fromThrowable(Throwable throwable) {
        if (throwable instanceof CameraResolutionTooSmallException) {
            return ScannerPluginUnavailable.CAMERA_RESOLUTION_TOO_SMALL;
        } else if (throwable instanceof UnsatisfiedLinkError) {
            return ScannerPluginUnavailable.NATIVE_LIBRARY_NOT_LOADED;
        } else if (throwable instanceof RecognizerError) {
            return ScannerPluginUnavailable.INITIALIZATION_FAILURE;
        } else if (throwable instanceof FeatureNotSupportedException) {
            return ScannerPluginUnavailable.FEATURE_NOT_SUPPORTED;
        } else {
            return ScannerPluginUnavailable.SCANNER_EXCEPTION;
        }
    }

}
