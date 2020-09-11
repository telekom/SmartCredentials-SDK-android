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

package de.telekom.smartcredentials.documentscanner.model.croatia;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class CroatiaFrontSideResult extends DocumentScannerResult {

    private byte[]  mEncodedFaceImage;
    private String  mFirstName;
    private String  mLastName;
    private String  mSex;
    private String  mCitizenship;
    private String  mDocumentNumber;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private boolean mDateOfExpiryPermanent;
    private boolean mDocumentBilingual;

    public CroatiaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "CroatiaFrontSideResult{\n" +
                "mFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmCitizenship='" + mCitizenship + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfExpiryPermanent=" + mDateOfExpiryPermanent +
                ",\nmDocumentBilingual=" + mDocumentBilingual +
                '}';
    }

    public String getFirstName() {
        return mFirstName;
    }

    public CroatiaFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public CroatiaFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public CroatiaFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getCitizenship() {
        return mCitizenship;
    }

    public CroatiaFrontSideResult setCitizenship(String citizenship) {
        mCitizenship = citizenship;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public CroatiaFrontSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public CroatiaFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public CroatiaFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public boolean isDateOfExpiryPermanent() {
        return mDateOfExpiryPermanent;
    }

    public CroatiaFrontSideResult setDateOfExpiryPermanent(boolean dateOfExpiryPermanent) {
        mDateOfExpiryPermanent = dateOfExpiryPermanent;
        return this;
    }

    public boolean isDocumentBilingual() {
        return mDocumentBilingual;
    }

    public CroatiaFrontSideResult setDocumentBilingual(boolean documentBilingual) {
        mDocumentBilingual = documentBilingual;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public CroatiaFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
