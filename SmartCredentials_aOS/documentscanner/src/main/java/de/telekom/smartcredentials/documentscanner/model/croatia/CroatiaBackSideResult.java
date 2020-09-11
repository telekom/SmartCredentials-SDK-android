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

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class CroatiaBackSideResult extends DocumentScannerResult {

    private String  mDocumentNumber;
    private String  mResidence;
    private String  mDocumentCode;
    private String  mIssuer;
    private String  mIssuedBy;
    private String  mMrzText;
    private String  mNationality;
    private String  mOpt1;
    private String  mOpt2;
    private String  mPrimaryId;
    private String  mSecondaryId;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private Date    mDateOfIssue;
    private boolean mDateOfExpiryPermanent;
    private boolean mDocumentForNonResident;
    private boolean mMrzVerified;

    public CroatiaBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "CroatiaBackSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmResidence='" + mResidence + '\'' +
                ",\nmDocumentCode='" + mDocumentCode + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmIssuedBy='" + mIssuedBy + '\'' +
                ",\nmMrzText='" + mMrzText + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmOpt1='" + mOpt1 + '\'' +
                ",\nmOpt2='" + mOpt2 + '\'' +
                ",\nmPrimaryId='" + mPrimaryId + '\'' +
                ",\nmSecondaryId='" + mSecondaryId + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmDateOfExpiryPermanent=" + mDateOfExpiryPermanent +
                ",\nmDocumentForNonResident=" + mDocumentForNonResident +
                ",\nmMrzVerified=" + mMrzVerified +
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public CroatiaBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getResidence() {
        return mResidence;
    }

    public CroatiaBackSideResult setResidence(String residence) {
        mResidence = residence;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public CroatiaBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public CroatiaBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getIssuedBy() {
        return mIssuedBy;
    }

    public CroatiaBackSideResult setIssuedBy(String issuedBy) {
        mIssuedBy = issuedBy;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public CroatiaBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public CroatiaBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public CroatiaBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public CroatiaBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public CroatiaBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public CroatiaBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public CroatiaBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public CroatiaBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public CroatiaBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public boolean isDateOfExpiryPermanent() {
        return mDateOfExpiryPermanent;
    }

    public CroatiaBackSideResult setDateOfExpiryPermanent(boolean dateOfExpiryPermanent) {
        mDateOfExpiryPermanent = dateOfExpiryPermanent;
        return this;
    }

    public boolean isDocumentForNonResident() {
        return mDocumentForNonResident;
    }

    public CroatiaBackSideResult setDocumentForNonResident(boolean documentForNonResident) {
        mDocumentForNonResident = documentForNonResident;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public CroatiaBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
