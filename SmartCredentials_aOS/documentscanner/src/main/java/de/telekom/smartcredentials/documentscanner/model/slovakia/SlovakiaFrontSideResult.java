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
 * Created by Lucian Iacob on 6/29/18 2:10 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.slovakia;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class SlovakiaFrontSideResult extends DocumentScannerResult {

    private String mPersonalIdentificationNumber;
    private String mDocumentNumber;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mIssuer;
    private String mNationality;
    private Date   mDateOfBirth;
    private Date   mDateOfExpiry;
    private Date   mDateOfIssue;
    private byte[] mEncodedFaceImage;

    public SlovakiaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SlovakiaFrontSideResult{\n" +
                "mPersonalIdentificationNumber='" + mPersonalIdentificationNumber + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                '}';
    }

    public String getPersonalIdentificationNumber() {
        return mPersonalIdentificationNumber;
    }

    public SlovakiaFrontSideResult setPersonalIdentificationNumber(String personalIdentificationNumber) {
        mPersonalIdentificationNumber = personalIdentificationNumber;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public SlovakiaFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public SlovakiaFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public SlovakiaFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SlovakiaFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public SlovakiaFrontSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public SlovakiaFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SlovakiaFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SlovakiaFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SlovakiaFrontSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public SlovakiaFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
