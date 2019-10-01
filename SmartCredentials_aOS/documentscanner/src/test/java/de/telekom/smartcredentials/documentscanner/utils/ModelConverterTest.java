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

import android.graphics.Bitmap;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.hardware.camera.VideoResolutionPreset;
import com.microblink.image.Image;
import com.microblink.results.date.DateResult;
import com.microblink.view.CameraAspectMode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Calendar;

import de.telekom.smartcredentials.core.documentscanner.AspectMode;
import de.telekom.smartcredentials.core.documentscanner.CameraType;
import de.telekom.smartcredentials.core.documentscanner.VideoResolution;
import de.telekom.smartcredentials.core.model.DocumentScannerResultState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
public class ModelConverterTest {

    @Test
    public void convertCameraTypeEnumConvertsFromInternalModelToMicroblinkModel() {
        com.microblink.hardware.camera.CameraType frontCamera =
                ModelConverter.convertCameraTypeEnum(CameraType.FRONT_CAMERA);
        assertSame(com.microblink.hardware.camera.CameraType.CAMERA_FRONTFACE, frontCamera);

        com.microblink.hardware.camera.CameraType backCamera =
                ModelConverter.convertCameraTypeEnum(CameraType.BACK_CAMERA);
        assertSame(com.microblink.hardware.camera.CameraType.CAMERA_BACKFACE, backCamera);

        com.microblink.hardware.camera.CameraType defaultCamera =
                ModelConverter.convertCameraTypeEnum(CameraType.DEFAULT);
        assertSame(com.microblink.hardware.camera.CameraType.CAMERA_DEFAULT, defaultCamera);
    }

    @Test
    public void convertResultStateConvertsFromMicroblinkModelToInternalModel() {
        DocumentScannerResultState emptyState =
                ModelConverter.convertResultState(Recognizer.Result.State.Empty);
        assertSame(DocumentScannerResultState.EMPTY, emptyState);

        DocumentScannerResultState uncertainState =
                ModelConverter.convertResultState(Recognizer.Result.State.Uncertain);
        assertSame(DocumentScannerResultState.UNCERTAIN, uncertainState);

        DocumentScannerResultState validState =
                ModelConverter.convertResultState(Recognizer.Result.State.Valid);
        assertSame(DocumentScannerResultState.VALID, validState);
    }

    @Test
    public void convertDateConvertsFromMicroblinkModelToInternalModel() {
        java.util.Date date1 = ModelConverter.convertDate(DateResult.createFromDMY(20, 4, 1994, ""));
        java.util.Date date2 = ModelConverter.convertDate(DateResult.createFromDMY(1, 1, 2000, ""));
        java.util.Date date3 = ModelConverter.convertDate(DateResult.createFromDMY(31, 12, 2017, ""));
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date1);
        assertEquals(20, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(3, calendar.get(Calendar.MONTH));
        assertEquals(1994, calendar.get(Calendar.YEAR));

        calendar.setTime(date2);
        assertEquals(1, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(0, calendar.get(Calendar.MONTH));
        assertEquals(2000, calendar.get(Calendar.YEAR));

        calendar.setTime(date3);
        assertEquals(31, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(11, calendar.get(Calendar.MONTH));
        assertEquals(2017, calendar.get(Calendar.YEAR));
    }

    @Test
    public void convertAspectModeConvertsFromInternalModelToMicroblinkModel() {
        CameraAspectMode aspectFit = ModelConverter.convertAspectMode(AspectMode.FIT);
        CameraAspectMode aspectFill = ModelConverter.convertAspectMode(AspectMode.FILL);

        assertSame(CameraAspectMode.ASPECT_FIT, aspectFit);
        assertSame(CameraAspectMode.ASPECT_FILL, aspectFill);
    }

    @Test
    public void convertVideoPresetConvertsFromInternalModelToMicroblinkModel() {
        VideoResolutionPreset video480p =
                ModelConverter.convertVideoPreset(VideoResolution.RESOLUTION_480P);
        VideoResolutionPreset video720p =
                ModelConverter.convertVideoPreset(VideoResolution.RESOLUTION_720P);
        VideoResolutionPreset video1080p =
                ModelConverter.convertVideoPreset(VideoResolution.RESOLUTION_1080P);
        VideoResolutionPreset video2160p =
                ModelConverter.convertVideoPreset(VideoResolution.RESOLUTION_2160P);
        VideoResolutionPreset videoMax =
                ModelConverter.convertVideoPreset(VideoResolution.RESOLUTION_MAX_AVAILABLE);
        VideoResolutionPreset videoDefault =
                ModelConverter.convertVideoPreset(VideoResolution.DEFAULT);

        assertSame(VideoResolutionPreset.VIDEO_RESOLUTION_480p, video480p);
        assertSame(VideoResolutionPreset.VIDEO_RESOLUTION_720p, video720p);
        assertSame(VideoResolutionPreset.VIDEO_RESOLUTION_1080p, video1080p);
        assertSame(VideoResolutionPreset.VIDEO_RESOLUTION_2160p, video2160p);
        assertSame(VideoResolutionPreset.VIDEO_RESOLUTION_MAX_AVAILABLE, videoMax);
        assertSame(VideoResolutionPreset.VIDEO_RESOLUTION_DEFAULT, videoDefault);
    }

    @Test
    public void convertImageReturnsNullWhenArgumentIsNull() {
        assertNull(ModelConverter.encodeImage(null));
    }

    @Test
    public void convertImageReturnsNullWhenBitmapIsNull() {
        Image faceImage = Mockito.mock(Image.class);
        when(faceImage.convertToBitmap()).thenReturn(null);

        byte[] convertedImage = ModelConverter.encodeImage(faceImage);

        assertNull(convertedImage);
    }

    @PrepareForTest(Bitmap.class)
    @Test
    public void convertImage() {
        Image faceImage = Mockito.mock(Image.class);
        PowerMockito.mockStatic(Bitmap.class);
        PowerMockito.when(faceImage.convertToBitmap()).thenReturn(PowerMockito.mock(Bitmap.class));

        byte[] convertedImage = ModelConverter.encodeImage(faceImage);

        assertNotNull(convertedImage);
        assertFalse(convertedImage.length != 0);
    }
}