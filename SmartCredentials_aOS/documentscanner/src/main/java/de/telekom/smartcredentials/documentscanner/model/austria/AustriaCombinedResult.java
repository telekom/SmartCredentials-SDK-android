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

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;

@SuppressWarnings("ALL")
public class AustriaCombinedResult extends CombinedResult {

    private String  mFirstName;
    private String  mLastName;
    private String  mSex;
    private String  mIssuingAuthority;
    private String  mNationality;
    private String  mPlaceOfBirth;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private Date    mDateOfIssue;
    private boolean mMrzVerified;
    private String  mEyeColour;
    private String  mHeight;
    private String  mDocumentNumber;
    private String  mPrincipalResidence;

    public AustriaCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "AustriaCombinedResult{\n" +
                "mFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmIssuingAuthority='" + mIssuingAuthority + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                ",\nmEyeColour='" + mEyeColour + '\'' +
                ",\nmHeight='" + mHeight + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmPrincipalResidence='" + mPrincipalResidence + '\'' +
                "} " + super.toString();
    }

    public AustriaCombinedResult setEyeColor(String eyeColour) {
        mEyeColour = eyeColour;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public AustriaCombinedResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public AustriaCombinedResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public AustriaCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public AustriaCombinedResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public AustriaCombinedResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public AustriaCombinedResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public AustriaCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public AustriaCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public AustriaCombinedResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public AustriaCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    public String getHeight() {
        return mHeight;
    }

    public AustriaCombinedResult setHeight(String height) {
        mHeight = height;
        return this;
    }

    public AustriaCombinedResult setResidence(String principalResidence) {
        mPrincipalResidence = principalResidence;
        return this;
    }

    public String getEyeColour() {
        return mEyeColour;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public AustriaCombinedResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public String getPrincipalResidence() {
        return mPrincipalResidence;
    }
}
