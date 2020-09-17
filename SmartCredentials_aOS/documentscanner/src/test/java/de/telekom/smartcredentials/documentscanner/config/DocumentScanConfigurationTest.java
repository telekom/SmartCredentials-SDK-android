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

package de.telekom.smartcredentials.documentscanner.config;

import android.content.Context;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import de.telekom.smartcredentials.core.documentscanner.AspectMode;
import de.telekom.smartcredentials.core.documentscanner.CameraType;
import de.telekom.smartcredentials.documentscanner.model.ScannerRecognizer;
import de.telekom.smartcredentials.core.documentscanner.VideoResolution;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class DocumentScanConfigurationTest {

    private Context mContext;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        mContext = Mockito.mock(Context.class);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void creatingScanConfigurationWithNullContextThrowsException() {
        thrown.expect(NullPointerException.class);
        new SmartCredentialsDocumentScanConfiguration.Builder(null).build();
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void creatingScanConfigurationWithNullRecognizerThrowsException() {
        thrown.expect(NullPointerException.class);
        new SmartCredentialsDocumentScanConfiguration.Builder(mContext)
                .setScannerRecognizer(null)
                .build();
    }

    @Test
    public void creatingScanConfigurationSetParametersAsProvided() {
        SmartCredentialsDocumentScanConfiguration configuration = new SmartCredentialsDocumentScanConfiguration.Builder(mContext)
                .setScannerRecognizer(ScannerRecognizer.ID_SIMPLE_RECOGNIZER)
                .setVideoResolution(VideoResolution.RESOLUTION_2160P)
                .setZoomLevel(0.4f)
                .setAspectMode(AspectMode.FIT)
                .setCameraType(CameraType.FRONT_CAMERA)
                .setPinchToZoomAllowed(false)
                .shouldRequestAutofocusOnShakingStopInContinuousAutofocusMode(true)
                .shouldOptimizeCameraForNearScan(true)
                .shouldAllowTapToFocus(false)
                .build();

        assertNotNull(configuration);
        assertEquals(ScannerRecognizer.ID_SIMPLE_RECOGNIZER, configuration.getScannerRecognizer());
        assertEquals(configuration.getContext(), mContext);
        assertEquals(configuration.getZoomLevel(), 0.4f);
        assertEquals(configuration.getVideoResolution(), VideoResolution.RESOLUTION_2160P);
        assertEquals(configuration.getAspectMode(), AspectMode.FIT);
        assertEquals(configuration.getCameraType(), CameraType.FRONT_CAMERA);
        assertFalse(configuration.isPinchToZoomAllowed());
        assertTrue(configuration.shouldFocusOnShakingStop());
        assertTrue(configuration.shouldOptimizeCameraForNearScan());
        assertFalse(configuration.shouldAllowTapToFocus());
    }

    @Test
    public void creatingScanConfigurationWithoutParamsCreatesConfigurationWithDefaultParameters() {
        SmartCredentialsDocumentScanConfiguration configuration = new SmartCredentialsDocumentScanConfiguration.Builder(mContext).build();

        assertNotNull(configuration);
        assertEquals(configuration.getContext(), mContext);
        assertEquals(ScannerRecognizer.ID_COMBINED_RECOGNIZER, configuration.getScannerRecognizer());
        assertEquals(configuration.getZoomLevel(), 0f);
        assertEquals(configuration.getVideoResolution(), VideoResolution.DEFAULT);
        assertEquals(configuration.getAspectMode(), AspectMode.FILL);
        assertEquals(configuration.getCameraType(), CameraType.DEFAULT);
        assertFalse(configuration.isPinchToZoomAllowed());
        assertFalse(configuration.shouldFocusOnShakingStop());
        assertFalse(configuration.shouldOptimizeCameraForNearScan());
        assertTrue(configuration.shouldAllowTapToFocus());
    }

}