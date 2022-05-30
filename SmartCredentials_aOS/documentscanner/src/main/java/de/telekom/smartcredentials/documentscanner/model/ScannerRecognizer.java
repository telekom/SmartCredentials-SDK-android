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

package de.telekom.smartcredentials.documentscanner.model;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdCombinedRecognizer;
import com.microblink.entities.recognizers.blinkid.generic.BlinkIdRecognizer;
import com.microblink.entities.recognizers.blinkid.idbarcode.IdBarcodeRecognizer;
import com.microblink.entities.recognizers.blinkid.usdl.UsdlCombinedRecognizer;

import de.telekom.smartcredentials.documentscanner.utils.ScannerUtils;

public enum ScannerRecognizer {

    ID_COMBINED_RECOGNIZER() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new BlinkIdCombinedRecognizer();
        }
    },
    ID_SIMPLE_RECOGNIZER() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new BlinkIdRecognizer();
        }
    },
    @SuppressWarnings("unused")
    ID_BARCODE_RECOGNIZER() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new IdBarcodeRecognizer();
        }
    },
    @SuppressWarnings("unused")
    USDL_COMBINED_RECOGNIZER() {
        @NonNull
        @Override
        public Recognizer createRecognizer() {
            return new UsdlCombinedRecognizer();
        }
    };

    public abstract Recognizer createRecognizer();

    public Recognizer getRecognizer() {
        Recognizer recognizer = createRecognizer();
        ScannerUtils.enableImages(recognizer);
        return recognizer;
    }
}
