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
 * Created by Lucian Iacob on 6/29/18 2:07 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.mrz;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class MRZCombinedResult extends CombinedResult {

    private String  mImmigrantCaseNumber;
    private String  mApplicationReceiptNumber;
    private String  mAlienNumber;
    private String  mDocumentNumber;
    private String  mDocumentCode;
    private String  mIssuer;
    private String  mMrzText;
    private String  mNationality;
    private String  mOpt1;
    private String  mOpt2;
    private String  mPrimaryId;
    private String  mSecondaryId;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private boolean mMrzVerified;
    private String  mGender;

    public MRZCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "MRZCombinedResult{\n" +
                "mImmigrantCaseNumber='" + mImmigrantCaseNumber + '\'' +
                ",\nmApplicationReceiptNumber='" + mApplicationReceiptNumber + '\'' +
                ",\nmAlienNumber='" + mAlienNumber + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmDocumentCode='" + mDocumentCode + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmMrzText='" + mMrzText + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmOpt1='" + mOpt1 + '\'' +
                ",\nmOpt2='" + mOpt2 + '\'' +
                ",\nmPrimaryId='" + mPrimaryId + '\'' +
                ",\nmSecondaryId='" + mSecondaryId + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmMrzVerified=" + mMrzVerified +
                "}" + super.toString();
    }

    public MRZCombinedResult setImmigrationCaseNumber(String immigrantCaseNumber) {
        mImmigrantCaseNumber = immigrantCaseNumber;
        return this;
    }

    public String getImmigrantCaseNumber() {
        return mImmigrantCaseNumber;
    }

    public String getApplicationReceiptNumber() {
        return mApplicationReceiptNumber;
    }

    public MRZCombinedResult setApplicationReceiptNumber(String applicationReceiptNumber) {
        mApplicationReceiptNumber = applicationReceiptNumber;
        return this;
    }

    public String getAlienNumber() {
        return mAlienNumber;
    }

    public MRZCombinedResult setAlienNumber(String alienNumber) {
        mAlienNumber = alienNumber;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public MRZCombinedResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public MRZCombinedResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public MRZCombinedResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public MRZCombinedResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public MRZCombinedResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public MRZCombinedResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public MRZCombinedResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public MRZCombinedResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public MRZCombinedResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public MRZCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public MRZCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public MRZCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    public MRZCombinedResult setGender(String gender) {
        mGender = gender;
        return this;
    }

    public String getGender() {
        return mGender;
    }
}
