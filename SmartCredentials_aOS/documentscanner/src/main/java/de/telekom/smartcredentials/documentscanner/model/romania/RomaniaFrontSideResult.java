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
 * Created by Lucian Iacob on 6/29/18 2:08 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.romania;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class RomaniaFrontSideResult extends DocumentScannerResult {

    private String mParentNames;
    private String mIdSeries;
    private String mPersonalIdentificationNumber;
    private String mDocumentNumber;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mTitle;
    private String mAddress;
    private String mDocumentCode;
    private String mIssuer;
    private String mIssuingAuthority;
    private String mMrzText;
    private String mNationality;
    private String mOpt1;
    private String mOpt2;
    private String mPrimaryId;
    private String mSecondaryId;
    private String mPlaceOfBirth;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private Date mDateOfIssue;
    private boolean mMrzVerified;
    private String mCardNumber;
    private byte[] mEncodedFaceImage;

    public RomaniaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "RomaniaFrontSideResult{\n" +
                "mParentNames='" + mParentNames + '\'' +
                ",\nmIdSeries='" + mIdSeries + '\'' +
                ",\nmPersonalIdentificationNumber='" + mPersonalIdentificationNumber + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
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
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                ",\nmCardNumber='" + mCardNumber + '\'' +
                '}';
    }

    public RomaniaFrontSideResult setSeries(String idSeries) {
        mIdSeries = idSeries;
        return this;
    }

    public RomaniaFrontSideResult setParentsName(String parentNames) {
        this.mParentNames = parentNames;
        return this;
    }

    public String getIdSeries() {
        return mIdSeries;
    }

    public String getPersonalIdentificationNumber() {
        return mPersonalIdentificationNumber;
    }

    public RomaniaFrontSideResult setPersonalIdentificationNumber(String personalIdentificationNumber) {
        mPersonalIdentificationNumber = personalIdentificationNumber;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public RomaniaFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public RomaniaFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public RomaniaFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public RomaniaFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public RomaniaFrontSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public RomaniaFrontSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public RomaniaFrontSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public RomaniaFrontSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public RomaniaFrontSideResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public RomaniaFrontSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public RomaniaFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public RomaniaFrontSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public RomaniaFrontSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public RomaniaFrontSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public RomaniaFrontSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public RomaniaFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public RomaniaFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public RomaniaFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public RomaniaFrontSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public RomaniaFrontSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public RomaniaFrontSideResult setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public RomaniaFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    @Deprecated
    public String getParentNames() {
        return mParentNames;
    }
}
