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
 * Created by Lucian Iacob on 6/29/18 2:11 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.uae;

import com.microblink.entities.recognizers.Recognizer;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class UAEFrontSideResult extends DocumentScannerResult {

    private String mLastName;
    private String mIdentityCardNumber;
    private String mNationality;
    private byte[] mEncodedFaceImage;
    private byte[] mEncodedFullDocumentImage;

    public UAEFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "UAEFrontSideResult{\n" +
                "mLastName='" + mLastName + '\'' +
                ",\nmIdentityCardNumber='" + mIdentityCardNumber + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                '}';
    }

    public UAEFrontSideResult setName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getIdentityCardNumber() {
        return mIdentityCardNumber;
    }

    public UAEFrontSideResult setIdentityCardNumber(String identityCardNumber) {
        mIdentityCardNumber = identityCardNumber;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public UAEFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public UAEFrontSideResult setEncodedFaceImage(byte[] encodedFaceImage) {
        mEncodedFaceImage = encodedFaceImage;
        return this;
    }

    public byte[] getEncodedFullDocumentImage() {
        return mEncodedFullDocumentImage;
    }

    public UAEFrontSideResult setEncodedFullDocumentImage(byte[] encodedFullDocumentImage) {
        mEncodedFullDocumentImage = encodedFullDocumentImage;
        return this;
    }
}
