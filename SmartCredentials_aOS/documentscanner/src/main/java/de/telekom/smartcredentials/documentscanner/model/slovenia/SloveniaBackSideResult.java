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

package de.telekom.smartcredentials.documentscanner.model.slovenia;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

public class SloveniaBackSideResult extends DocumentScannerResult {

    private String mDocumentNumber;
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
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private Date mDateOfIssue;
    private boolean mMrzVerified;

    public SloveniaBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SloveniaBackSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
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
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public SloveniaBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SloveniaBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public SloveniaBackSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public SloveniaBackSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public SloveniaBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public SloveniaBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public SloveniaBackSideResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public SloveniaBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public SloveniaBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public SloveniaBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public SloveniaBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public SloveniaBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public SloveniaBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SloveniaBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SloveniaBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SloveniaBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public SloveniaBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
