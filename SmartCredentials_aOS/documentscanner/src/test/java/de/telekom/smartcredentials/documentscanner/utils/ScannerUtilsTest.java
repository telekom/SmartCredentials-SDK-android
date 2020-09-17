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

import android.content.Context;
import android.util.Log;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.util.RecognizerCompatibility;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import de.telekom.smartcredentials.core.documentscanner.CameraType;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@PrepareForTest({RecognizerCompatibility.class, Log.class})
@RunWith(PowerMockRunner.class)
public class ScannerUtilsTest {

    private Context    mContext;
    private Recognizer mRecognizer;

    @Before
    public void setup() {
        PowerMockito.mockStatic(RecognizerCompatibility.class);
        PowerMockito.mockStatic(Log.class);
        mRecognizer = Mockito.mock(Recognizer.class);
        mContext = Mockito.mock(Context.class);
    }

    @Test
    public void checkIfRecognizerIsSupportedReturnsTrueIfRecognizerRequiresAutofocusAndCameraHasAutofocus() {
        Mockito.when(mRecognizer.requiresAutofocus()).thenReturn(true);
        PowerMockito.when(RecognizerCompatibility.cameraHasAutofocus(
                com.microblink.hardware.camera.CameraType.CAMERA_FRONTFACE, mContext))
                .thenReturn(true);

        boolean result = ScannerUtils.checkIfRecognizerIsSupported(mRecognizer, mContext, CameraType.FRONT_CAMERA);
        assertTrue(result);
    }

    @Test
    public void checkIfRecognizerIsSupportedReturnsTrueIfRecognizerDoesNotRequireAutofocusAndCameraHasAutofocus() {
        Mockito.when(mRecognizer.requiresAutofocus()).thenReturn(false);
        PowerMockito.when(RecognizerCompatibility.cameraHasAutofocus(
                com.microblink.hardware.camera.CameraType.CAMERA_BACKFACE, mContext))
                .thenReturn(true);

        boolean result = ScannerUtils.checkIfRecognizerIsSupported(mRecognizer, mContext, CameraType.BACK_CAMERA);
        assertTrue(result);
    }

    @Test
    public void checkIfRecognizerIsSupportedReturnsFalseIfRecognizerRequiresAutofocusAndCameraDoesNotHaveAutofocus() {
        Mockito.when(mRecognizer.requiresAutofocus()).thenReturn(true);
        PowerMockito.when(RecognizerCompatibility.cameraHasAutofocus(
                com.microblink.hardware.camera.CameraType.CAMERA_BACKFACE, mContext))
                .thenReturn(false);

        boolean result = ScannerUtils.checkIfRecognizerIsSupported(mRecognizer, mContext, CameraType.BACK_CAMERA);
        assertFalse(result);
    }

    @Test
    public void checkIfRecognizerIsSupportedReturnsTrueIfRecognizerDoesNotRequireAutofocusAndCameraDoesNotHavAutofocus() {
        Mockito.when(mRecognizer.requiresAutofocus()).thenReturn(false);
        PowerMockito.when(RecognizerCompatibility.cameraHasAutofocus(
                com.microblink.hardware.camera.CameraType.CAMERA_BACKFACE, mContext))
                .thenReturn(false);

        boolean result = ScannerUtils.checkIfRecognizerIsSupported(mRecognizer, mContext, CameraType.BACK_CAMERA);
        assertTrue(result);
    }

    @Test
    public void enableImagesEnableAllImages() {
        MockRecognizer imageRecognizer = new MockRecognizer();

        assertFalse(imageRecognizer.shouldEncodeFaceImage());
        assertFalse(imageRecognizer.shouldEncodeFullDocumentImage());
        assertFalse(imageRecognizer.shouldReturnFullDocumentImage());
        assertFalse(imageRecognizer.shouldReturnFaceImage());

        ScannerUtils.enableImages(imageRecognizer);

        assertTrue(imageRecognizer.shouldEncodeFaceImage());
        assertTrue(imageRecognizer.shouldEncodeFullDocumentImage());
        assertTrue(imageRecognizer.shouldReturnFullDocumentImage());
        assertTrue(imageRecognizer.shouldReturnFaceImage());
    }
}