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
 * Created by Lucian Iacob on 6/29/18 2:03 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.colombia;

import com.microblink.entities.recognizers.Recognizer;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

public class ColombiaFrontSideResult extends DocumentScannerResult {

    private String mDocumentNumber;
    private String mFirstName;
    private String mLastName;
    private byte[] mEncodedFaceImage;
    private byte[] mEncodedFullDocumentImage;
    private byte[] mEncodedSignatureImage;
    public ColombiaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "ColombiaFrontSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public ColombiaFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public ColombiaFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public ColombiaFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public ColombiaFrontSideResult setEncodedFaceImage(byte[] encodedFaceImage) {
        mEncodedFaceImage = encodedFaceImage;
        return this;
    }

    public byte[] getEncodedFullDocumentImage() {
        return mEncodedFullDocumentImage;
    }

    public ColombiaFrontSideResult setEncodedFullDocumentImage(byte[] encodedFullDocumentImage) {
        mEncodedFullDocumentImage = encodedFullDocumentImage;
        return this;
    }

    public byte[] getEncodedSignatureImage() {
        return mEncodedSignatureImage;
    }

    public ColombiaFrontSideResult setEncodedSignatureImage(byte[] encodedSignatureImage) {
        mEncodedSignatureImage = encodedSignatureImage;
        return this;
    }
}
