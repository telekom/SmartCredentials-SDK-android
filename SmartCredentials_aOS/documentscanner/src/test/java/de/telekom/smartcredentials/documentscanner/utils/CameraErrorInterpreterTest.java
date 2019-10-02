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

package de.telekom.smartcredentials.documentscanner.utils;

import com.microblink.hardware.camera.CameraResolutionTooSmallException;
import com.microblink.recognition.FeatureNotSupportedException;
import com.microblink.recognition.RecognizerError;
import com.microblink.view.NotSupportedReason;

import org.junit.Test;

import java.io.IOException;

import de.telekom.smartcredentials.core.documentscanner.ScannerPluginUnavailable;

import static org.junit.Assert.assertEquals;

public class CameraErrorInterpreterTest {

    @Test
    public void fromThrowableReturnsAppropriateMessageBasedOnException() {
        ScannerPluginUnavailable resolutionResponse =
                CameraErrorInterpreter.fromThrowable(new CameraResolutionTooSmallException(""));
        assertEquals(resolutionResponse, ScannerPluginUnavailable.CAMERA_RESOLUTION_TOO_SMALL);

        ScannerPluginUnavailable linkErrorResponse =
                CameraErrorInterpreter.fromThrowable(new UnsatisfiedLinkError(""));
        assertEquals(linkErrorResponse, ScannerPluginUnavailable.NATIVE_LIBRARY_NOT_LOADED);

        ScannerPluginUnavailable recognizerErrorResponse =
                CameraErrorInterpreter.fromThrowable(new RecognizerError(""));
        assertEquals(recognizerErrorResponse, ScannerPluginUnavailable.INITIALIZATION_FAILURE);

        ScannerPluginUnavailable featureNotSupportedResponse =
                CameraErrorInterpreter.fromThrowable(new FeatureNotSupportedException(
                        NotSupportedReason.BLACKLISTED_DEVICE));
        assertEquals(featureNotSupportedResponse, ScannerPluginUnavailable.FEATURE_NOT_SUPPORTED);

        ScannerPluginUnavailable defaultResponse =
                CameraErrorInterpreter.fromThrowable(new IOException(""));
        assertEquals(defaultResponse, ScannerPluginUnavailable.SCANNER_EXCEPTION);
    }

}