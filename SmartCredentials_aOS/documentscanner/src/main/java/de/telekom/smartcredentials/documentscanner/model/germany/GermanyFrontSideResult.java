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
 * Created by Lucian Iacob on 6/29/18 2:05 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.germany;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class GermanyFrontSideResult extends DocumentScannerResult {

    private String mDocumentNumber;
    private String mGivenNames;
    private String mSurname;
    private String mNationality;
    private String mPlaceOfBirth;
    private Date   mDateOfBirth;
    private Date   mDateOfExpiry;
    private String mCanNumber;
    private byte[] mEncodedFaceImage;

    public GermanyFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "GermanyFrontSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmGivenNames='" + mGivenNames + '\'' +
                ",\nmSurname='" + mSurname + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmCanNumber='" + mCanNumber + '\'' +
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public GermanyFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getGivenNames() {
        return mGivenNames;
    }

    public GermanyFrontSideResult setGivenNames(String givenNames) {
        mGivenNames = givenNames;
        return this;
    }

    public String getSurname() {
        return mSurname;
    }

    public GermanyFrontSideResult setSurname(String surname) {
        mSurname = surname;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public GermanyFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public GermanyFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public GermanyFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public GermanyFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public String getCanNumber() {
        return mCanNumber;
    }

    public GermanyFrontSideResult setCanNumber(String canNumber) {
        mCanNumber = canNumber;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public GermanyFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
