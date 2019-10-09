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

package de.telekom.smartcredentials.documentscanner.model.austria;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

@SuppressWarnings("ALL")
public class AustriaFrontSideResult extends DocumentScannerResult {

    private String mFirstName;
    private String mLastName;
    private String mSex;
    private Date mDateOfBirth;
    private String mDocumentNumber;
    private byte[] mEncodedFullDocumentImage;
    private byte[] mEncodedFaceImage;
    private byte[] mEncodedSignatureImage;

    public AustriaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "AustriaFrontSideResult{\n" +
                "mFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                '}';
    }

    public String getFirstName() {
        return mFirstName;
    }

    public AustriaFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public AustriaFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public AustriaFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public AustriaFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public AustriaFrontSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public byte[] getEncodedFullDocumentImage() {
        return mEncodedFullDocumentImage;
    }

    public AustriaFrontSideResult setEncodedFullDocumentImage(byte[] encodedFullDocumentImage) {
        mEncodedFullDocumentImage = encodedFullDocumentImage;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public AustriaFrontSideResult setEncodedFaceImage(byte[] encodedFaceImage) {
        mEncodedFaceImage = encodedFaceImage;
        return this;
    }

    public byte[] getEncodedSignatureImage() {
        return mEncodedSignatureImage;
    }

    public AustriaFrontSideResult setEncodedSignatureImage(byte[] encodedSignatureImage) {
        mEncodedSignatureImage = encodedSignatureImage;
        return this;
    }
}
