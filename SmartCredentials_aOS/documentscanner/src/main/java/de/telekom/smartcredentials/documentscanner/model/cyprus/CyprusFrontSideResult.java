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

package de.telekom.smartcredentials.documentscanner.model.cyprus;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on January 03, 2019.
 */
public class CyprusFrontSideResult extends DocumentScannerResult {

    private byte[] mEncodedFaceImage;
    private String mIdNumber;

    public CyprusFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "CyprusFrontSideResult{" +
                ", mIdNumber='" + mIdNumber + '\'' +
                '}';
    }

    public CyprusFrontSideResult setEncodedFaceImage(byte[] encodedFaceImage) {
        mEncodedFaceImage = encodedFaceImage;
        return this;
    }

    public CyprusFrontSideResult setIdNumber(String idNumber) {
        mIdNumber = idNumber;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public String getIdNumber() {
        return mIdNumber;
    }
}
