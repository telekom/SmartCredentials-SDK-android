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
 * Created by Lucian Iacob on 6/29/18 2:04 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.czech;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class CzechFrontSideResult extends DocumentScannerResult {

    private byte[] mEncodedFaceImage;
    private String mGivenNames;
    private String mSurname;
    private String mSex;
    private String mPlaceOfBirth;
    private Date   mDateOfBirth;
    private Date   mDateOfExpiry;
    private Date   mDateOfIssue;
    private String mDocumentNumber;

    public CzechFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "CzechFrontSideResult{\n" +
                "mGivenNames='" + mGivenNames + '\'' +
                ",\nmSurname='" + mSurname + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                '}';
    }

    public String getGivenNames() {
        return mGivenNames;
    }

    public CzechFrontSideResult setGivenNames(String givenNames) {
        mGivenNames = givenNames;
        return this;
    }

    public String getSurname() {
        return mSurname;
    }

    public CzechFrontSideResult setSurname(String surname) {
        mSurname = surname;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public CzechFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public CzechFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public CzechFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public CzechFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public CzechFrontSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public CzechFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    public CzechFrontSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }
}
