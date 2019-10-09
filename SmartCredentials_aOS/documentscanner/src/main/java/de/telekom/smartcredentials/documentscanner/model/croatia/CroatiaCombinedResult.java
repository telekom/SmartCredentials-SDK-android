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

package de.telekom.smartcredentials.documentscanner.model.croatia;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class CroatiaCombinedResult extends CombinedResult {

    private String  mFirstName;
    private String  mLastName;
    private String  mSex;
    private String  mCitizenship;
    private String  mDocumentNumber;
    private String  mIssuedBy;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private Date    mDateOfIssue;
    private byte[]  mEncodedSignatureImage;
    private boolean mDateOfExpiryPermanent;
    private boolean mDocumentBilingual;
    private boolean mDocumentForNonResident;
    private boolean mMrzVerified;
    private String  mOib;
    private String  mResidence;

    public CroatiaCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "CroatiaCombinedResult{\n" +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmCitizenship='" + mCitizenship + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmIssuedBy='" + mIssuedBy + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmDateOfExpiryPermanent=" + mDateOfExpiryPermanent +
                ",\nmDocumentBilingual=" + mDocumentBilingual +
                ",\nmDocumentForNonResident=" + mDocumentForNonResident +
                ",\nmMrzVerified=" + mMrzVerified +
                "}" + super.toString();
    }

    public String getFirstName() {
        return mFirstName;
    }

    public CroatiaCombinedResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public CroatiaCombinedResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public CroatiaCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getCitizenship() {
        return mCitizenship;
    }

    public CroatiaCombinedResult setCitizenship(String citizenship) {
        mCitizenship = citizenship;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public CroatiaCombinedResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public String getIssuedBy() {
        return mIssuedBy;
    }

    public CroatiaCombinedResult setIssuedBy(String issuedBy) {
        mIssuedBy = issuedBy;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public CroatiaCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public CroatiaCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public CroatiaCombinedResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedSignatureImage() {
        return mEncodedSignatureImage;
    }

    public CroatiaCombinedResult setEncodedSignatureImage(byte[] encodedSignatureImage) {
        mEncodedSignatureImage = encodedSignatureImage;
        return this;
    }

    public boolean isDateOfExpiryPermanent() {
        return mDateOfExpiryPermanent;
    }

    public CroatiaCombinedResult setDateOfExpiryPermanent(boolean dateOfExpiryPermanent) {
        mDateOfExpiryPermanent = dateOfExpiryPermanent;
        return this;
    }

    public boolean isDocumentBilingual() {
        return mDocumentBilingual;
    }

    public CroatiaCombinedResult setDocumentBilingual(boolean documentBilingual) {
        mDocumentBilingual = documentBilingual;
        return this;
    }

    public boolean isDocumentForNonResident() {
        return mDocumentForNonResident;
    }

    public CroatiaCombinedResult setDocumentForNonResident(boolean documentForNonResident) {
        mDocumentForNonResident = documentForNonResident;
        return this;
    }

    public CroatiaCombinedResult setOIB(String oib) {
        mOib = oib;
        return this;
    }

    public CroatiaCombinedResult setResidence(String residence) {
        mResidence = residence;
        return this;
    }

    public String getResidence() {
        return mResidence;
    }

    public String getOib() {
        return mOib;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public CroatiaCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
