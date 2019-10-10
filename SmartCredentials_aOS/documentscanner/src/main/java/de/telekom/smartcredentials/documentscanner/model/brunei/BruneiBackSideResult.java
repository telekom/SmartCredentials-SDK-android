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

package de.telekom.smartcredentials.documentscanner.model.brunei;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on February 12, 2019.
 */
public class BruneiBackSideResult extends DocumentScannerResult {

    private String  mAddress;
    private Date    mDateOfIssue;
    private String  mRace;
    private String  mAlienNumber;
    private String  mApplicationReceiptNumber;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private String  mDocumentCode;
    private String  mDocumentNumber;
    private String  mSex;
    private String  mImmigrantCaseNumber;
    private String  mIssuer;
    private String  mMrzText;
    private String  mNationality;
    private String  mOpt1;
    private String  mOpt2;
    private String  mPrimaryId;
    private String  mSecondaryId;
    private boolean mMrzVerified;

    public BruneiBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "BruneiBackSideResult{" +
                "mAddress='" + mAddress + '\'' +
                ", mDateOfIssue=" + mDateOfIssue +
                ", mRace='" + mRace + '\'' +
                ", mAlienNumber='" + mAlienNumber + '\'' +
                ", mApplicationReceiptNumber='" + mApplicationReceiptNumber + '\'' +
                ", mDateOfBirth=" + mDateOfBirth +
                ", mDateOfExpiry=" + mDateOfExpiry +
                ", mDocumentCode='" + mDocumentCode + '\'' +
                ", mDocumentNumber='" + mDocumentNumber + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mImmigrantCaseNumber='" + mImmigrantCaseNumber + '\'' +
                ", mIssuer='" + mIssuer + '\'' +
                ", mMrzText='" + mMrzText + '\'' +
                ", mNationality='" + mNationality + '\'' +
                ", mOpt1='" + mOpt1 + '\'' +
                ", mOpt2='" + mOpt2 + '\'' +
                ", mPrimaryId='" + mPrimaryId + '\'' +
                ", mSecondaryId='" + mSecondaryId + '\'' +
                ", mMrzVerified=" + mMrzVerified +
                '}';
    }

    public BruneiBackSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public BruneiBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public BruneiBackSideResult setRace(String race) {
        mRace = race;
        return this;
    }

    public BruneiBackSideResult setAlienNr(String alienNumber) {
        mAlienNumber = alienNumber;
        return this;
    }

    public BruneiBackSideResult setApplicationReceiptNumber(String applicationReceiptNumber) {
        mApplicationReceiptNumber = applicationReceiptNumber;
        return this;
    }

    public BruneiBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public BruneiBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public BruneiBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public BruneiBackSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public BruneiBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public BruneiBackSideResult setImmigrantCaseNumber(String immigrantCaseNumber) {
        mImmigrantCaseNumber = immigrantCaseNumber;
        return this;
    }

    public BruneiBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public BruneiBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public BruneiBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public BruneiBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public BruneiBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public BruneiBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public BruneiBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public BruneiBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public String getRace() {
        return mRace;
    }

    public String getAlienNumber() {
        return mAlienNumber;
    }

    public String getApplicationReceiptNumber() {
        return mApplicationReceiptNumber;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public String getSex() {
        return mSex;
    }

    public String getImmigrantCaseNumber() {
        return mImmigrantCaseNumber;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public String getNationality() {
        return mNationality;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }
}
