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

package de.telekom.smartcredentials.documentscanner.model.swiss;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class SwissBackSideResult extends DocumentScannerResult {

    private String  mDocumentNumber;
    private String  mSex;
    private String  mDocumentCode;
    private String  mIssuer;
    private String  mIssuingAuthority;
    private String  mMrzText;
    private String  mNationality;
    private String  mOpt1;
    private String  mOpt2;
    private String  mPrimaryId;
    private String  mSecondaryId;
    private String  mPlaceOfBirth;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private Date    mDateOfIssue;
    private boolean mMrzVerified;
    private String  mHeight;

    public SwissBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SwissBackSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmSex='" + mSex + '\'' +
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
                ",\nmHeight='" + mHeight + '\'' +
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public SwissBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SwissBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public SwissBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public SwissBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public SwissBackSideResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public SwissBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public SwissBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public SwissBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public SwissBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public SwissBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public SwissBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public SwissBackSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SwissBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SwissBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SwissBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public SwissBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    public SwissBackSideResult setHeight(String height) {
        mHeight = height;
        return this;
    }

    @Deprecated
    public String getHeight() {
        return mHeight;
    }
}
