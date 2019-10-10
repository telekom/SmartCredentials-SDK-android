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


public class SlovakiaBackSideResult extends DocumentScannerResult {

    private String  mSpecialRemarks;
    private String  mSurnameAtBirth;
    private String  mDocumentNumber;
    private String  mSex;
    private String  mAddress;
    private String  mDocumentCode;
    private String  mIssuer;
    private String  mMrzText;
    private String  mNationality;
    private String  mOpt1;
    private String  mOpt2;
    private String  mPrimaryId;
    private String  mSecondaryId;
    private String  mPlaceOfBirth;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private boolean mMrzVerified;

    public SlovakiaBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SlovakiaBackSideResult{\n" +
                "mSpecialRemarks='" + mSpecialRemarks + '\'' +
                ",\nmSurnameAtBirth='" + mSurnameAtBirth + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmDocumentCode='" + mDocumentCode + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmMrzText='" + mMrzText + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmOpt1='" + mOpt1 + '\'' +
                ",\nmOpt2='" + mOpt2 + '\'' +
                ",\nmPrimaryId='" + mPrimaryId + '\'' +
                ",\nmSecondaryId='" + mSecondaryId + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmMrzVerified=" + mMrzVerified +
                '}';
    }

    public SlovakiaBackSideResult setSpecialRemarks(String specialRemarks) {
        this.mSpecialRemarks = specialRemarks;
        return this;
    }

    public SlovakiaBackSideResult setSurnameAtBirth(String surnameAtBirth) {
        mSurnameAtBirth = surnameAtBirth;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public SlovakiaBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SlovakiaBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public SlovakiaBackSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public SlovakiaBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public SlovakiaBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public SlovakiaBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public SlovakiaBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public SlovakiaBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public SlovakiaBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public SlovakiaBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public SlovakiaBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public SlovakiaBackSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SlovakiaBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SlovakiaBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public SlovakiaBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    @Deprecated
    public String getSpecialRemarks() {
        return mSpecialRemarks;
    }

    @Deprecated
    public String getSurnameAtBirth() {
        return mSurnameAtBirth;
    }
}
