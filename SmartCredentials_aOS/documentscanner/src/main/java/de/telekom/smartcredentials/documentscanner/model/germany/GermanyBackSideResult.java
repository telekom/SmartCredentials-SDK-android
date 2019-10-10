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

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class GermanyBackSideResult extends DocumentScannerResult {

    private String  mAddressCity;
    private String  mAddressStreet;
    private String  mAddressHouseNumber;
    private String  mAddressZipCode;
    private String  mDocumentNumber;
    private String  mSex;
    private String  mAddress;
    private String  mDocumentCode;
    private String  mIssuer;
    private String  mIssuingAuthority;
    private String  mMrzText;
    private String  mNationality;
    private String  mOpt1;
    private String  mOpt2;
    private String  mPrimaryId;
    private String  mSecondaryId;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private Date    mDateOfIssue;
    private boolean mMrzVerified;
    private String  mEyeColour;
    private String  mHeight;

    public GermanyBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "GermanyBackSideResult{\n" +
                "mAddressCity='" + mAddressCity + '\'' +
                ",\nmAddressStreet='" + mAddressStreet + '\'' +
                ",\nmAddressHouseNumber='" + mAddressHouseNumber + '\'' +
                ",\nmAddressZipCode='" + mAddressZipCode + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmDocumentCode='" + mDocumentCode + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmIssuingAuthority='" + mIssuingAuthority + '\'' +
                ",\nmMrzText='" + mMrzText + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmOpt1='" + mOpt1 + '\'' +
                ",\nmOpt2='" + mOpt2 + '\'' +
                ",\nmPrimaryId='" + mPrimaryId + '\'' +
                ",\nmSecondaryId='" + mSecondaryId + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                ",\nmEyeColour='" + mEyeColour + '\'' +
                ",\nmHeight='" + mHeight + '\'' +
                '}';
    }

    public GermanyBackSideResult setEyeColour(String eyeColour) {
        mEyeColour = eyeColour;
        return this;
    }

    public String getAddressCity() {
        return mAddressCity;
    }

    public GermanyBackSideResult setAddressCity(String addressCity) {
        mAddressCity = addressCity;
        return this;
    }

    public String getEyeColour() {
        return mEyeColour;
    }

    public String getAddressStreet() {
        return mAddressStreet;
    }

    public GermanyBackSideResult setAddressStreet(String addressStreet) {
        mAddressStreet = addressStreet;
        return this;
    }

    public String getAddressHouseNumber() {
        return mAddressHouseNumber;
    }

    public GermanyBackSideResult setAddressHouseNumber(String addressHouseNumber) {
        mAddressHouseNumber = addressHouseNumber;
        return this;
    }

    public String getAddressZipCode() {
        return mAddressZipCode;
    }

    public GermanyBackSideResult setAddressZipCode(String addressZipCode) {
        mAddressZipCode = addressZipCode;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public GermanyBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public GermanyBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public GermanyBackSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public GermanyBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public GermanyBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public GermanyBackSideResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public GermanyBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public GermanyBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public GermanyBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public GermanyBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public GermanyBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public GermanyBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public GermanyBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public GermanyBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public GermanyBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public GermanyBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    @Deprecated
    public String getHeight() {
        return mHeight;
    }

    public GermanyBackSideResult setHeight(String height) {
        mHeight = height;
        return this;
    }
}
