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
public class AustriaBackSideResult extends DocumentScannerResult {

    private String mIssuingAuthority;
    private String mPlaceOfBirth;
    private Date mDateOfIssue;
    private String mEyeColour;
    private String mHeight;
    private String mDocumentNumber;
    private String mPrincipalResidence;
    private byte[] mEncodedFullDocumentImage;
    private String mMrzResult;

    public AustriaBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "AustriaBackSideResult{\n" +
                "mIssuingAuthority='" + mIssuingAuthority + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmEyeColour='" + mEyeColour + '\'' +
                ",\nmHeight='" + mHeight + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmPrincipalResidence='" + mPrincipalResidence + '\'' +
                ",\nmMrzResult='" + mMrzResult + '\'' +
                '}';
    }

    public AustriaBackSideResult setEyeColor(String eyeColour) {
        mEyeColour = eyeColour;
        return this;
    }

    public AustriaBackSideResult setResidence(String principalResidence) {
        mPrincipalResidence = principalResidence;
        return this;
    }

    public AustriaBackSideResult setMrzText(String mrzResult) {
        mMrzResult = mrzResult;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public AustriaBackSideResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public AustriaBackSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public AustriaBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public AustriaBackSideResult setHeight(String height) {
        mHeight = height;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public AustriaBackSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public byte[] getEncodedFullDocumentImage() {
        return mEncodedFullDocumentImage;
    }

    public AustriaBackSideResult setEncodedFullDocumentImage(byte[] encodedFullDocumentImage) {
        mEncodedFullDocumentImage = encodedFullDocumentImage;
        return this;
    }

    public String getMrzResult() {
        return mMrzResult;
    }

    @Deprecated
    public String getEyeColour() {
        return mEyeColour;
    }

    @Deprecated
    public String getHeight() {
        return mHeight;
    }

    @Deprecated
    public String getPrincipalResidence() {
        return mPrincipalResidence;
    }
}
