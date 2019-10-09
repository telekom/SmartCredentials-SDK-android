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
 * Created by Lucian Iacob on 6/29/18 2:09 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.utils;

import android.content.Context;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.blinkid.imageoptions.FaceImageOptions;
import com.microblink.entities.recognizers.blinkid.imageoptions.encode.EncodeFaceImageOptions;
import com.microblink.entities.recognizers.blinkid.imageoptions.encode.EncodeFullDocumentImagesOptions;
import com.microblink.entities.recognizers.blinkid.imageoptions.encode.EncodeSignatureImageOptions;
import com.microblink.util.RecognizerCompatibility;

import de.telekom.smartcredentials.core.documentscanner.CameraType;


public class ScannerUtils {

    private ScannerUtils() {
        // required empty constructor
    }

    public static boolean checkIfRecognizerIsSupported(Recognizer recognizer,
                                                       Context context,
                                                       CameraType cameraType) {
        com.microblink.hardware.camera.CameraType cameraTypeRaw = ModelConverter.convertCameraTypeEnum(cameraType);
        return !recognizer.requiresAutofocus() ||
                RecognizerCompatibility.cameraHasAutofocus(cameraTypeRaw, context);
    }

    public static void enableImages(Object recognizer) {
        if (recognizer instanceof EncodeFullDocumentImagesOptions) {
            ((EncodeFullDocumentImagesOptions) recognizer).setEncodeFullDocumentImage(true);
        }
        if (recognizer instanceof EncodeSignatureImageOptions) {
            ((EncodeSignatureImageOptions) recognizer).setEncodeSignatureImage(true);
        }
        if (recognizer instanceof FaceImageOptions) {
            ((FaceImageOptions) recognizer).setReturnFaceImage(true);
        }
        if (recognizer instanceof EncodeFaceImageOptions) {
            ((EncodeFaceImageOptions) recognizer).setEncodeFaceImage(true);
        }
    }
}
