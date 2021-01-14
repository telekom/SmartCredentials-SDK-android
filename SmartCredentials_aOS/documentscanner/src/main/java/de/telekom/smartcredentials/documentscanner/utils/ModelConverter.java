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

package de.telekom.smartcredentials.documentscanner.utils;

import android.graphics.Bitmap;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.hardware.camera.VideoResolutionPreset;
import com.microblink.image.Image;
import com.microblink.results.date.Date;
import com.microblink.results.date.DateResult;
import com.microblink.view.CameraAspectMode;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import de.telekom.smartcredentials.core.documentscanner.AspectMode;
import de.telekom.smartcredentials.core.documentscanner.CameraType;
import de.telekom.smartcredentials.core.documentscanner.VideoResolution;
import de.telekom.smartcredentials.core.model.DocumentScannerResultState;


public class ModelConverter {

    private static final Bitmap.CompressFormat BITMAP_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
    private static final int BITMAP_COMPRESS_QUALITY = 100;

    private ModelConverter() {
        // required empty constructor
    }

    @NonNull
    public static com.microblink.hardware.camera.CameraType convertCameraTypeEnum(CameraType cameraType) {
        switch (cameraType) {
            case BACK_CAMERA:
                return com.microblink.hardware.camera.CameraType.CAMERA_BACKFACE;
            case FRONT_CAMERA:
                return com.microblink.hardware.camera.CameraType.CAMERA_FRONTFACE;
            case DEFAULT:
            default:
                return com.microblink.hardware.camera.CameraType.CAMERA_DEFAULT;
        }
    }

    public static DocumentScannerResultState convertResultState(Recognizer.Result.State resultState) {
        switch (resultState) {
            case Empty:
                return DocumentScannerResultState.EMPTY;
            case Valid:
                return DocumentScannerResultState.VALID;
            case Uncertain:
            default:
                return DocumentScannerResultState.UNCERTAIN;
        }
    }

    public static CameraAspectMode convertAspectMode(AspectMode aspectMode) {
        switch (aspectMode) {
            case FIT:
                return CameraAspectMode.ASPECT_FIT;
            case FILL:
            default:
                return CameraAspectMode.ASPECT_FILL;
        }
    }

    public static VideoResolutionPreset convertVideoPreset(VideoResolution videoResolution) {
        switch (videoResolution) {
            case RESOLUTION_480P:
                return VideoResolutionPreset.VIDEO_RESOLUTION_480p;
            case RESOLUTION_720P:
                return VideoResolutionPreset.VIDEO_RESOLUTION_720p;
            case RESOLUTION_1080P:
                return VideoResolutionPreset.VIDEO_RESOLUTION_1080p;
            case RESOLUTION_2160P:
                return VideoResolutionPreset.VIDEO_RESOLUTION_2160p;
            case RESOLUTION_MAX_AVAILABLE:
                return VideoResolutionPreset.VIDEO_RESOLUTION_MAX_AVAILABLE;
            case DEFAULT:
            default:
                return VideoResolutionPreset.VIDEO_RESOLUTION_DEFAULT;
        }
    }

    @Nullable
    public static byte[] encodeImage(@Nullable Image faceImage) {
        if (faceImage != null) {
            Bitmap bitmap = faceImage.convertToBitmap();
            if (bitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(BITMAP_COMPRESS_FORMAT, BITMAP_COMPRESS_QUALITY, stream);
                return stream.toByteArray();
            }
        }
        return null;
    }

    public static Bitmap convertImageToBitmap(Image documentImage) {
        if (documentImage != null) {
            return documentImage.convertToBitmap();
        }
        return null;
    }

    public static java.util.Date convertDate(@Nullable DateResult dateResult) {
        if (dateResult == null) {
            return null;
        }
        return convertDate(dateResult.getDate());
    }

    public static java.util.Date convertDate(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, date.getYear());
        calendar.set(Calendar.MONTH, date.getMonth() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, date.getDay());
        return calendar.getTime();
    }
}
