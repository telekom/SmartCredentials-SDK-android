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

package de.telekom.smartcredentials.documentscanner.model.kuwait;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on January 03, 2019.
 */
public class KuwaitBackSideResult extends DocumentScannerResult {

    private String  mSerialNo;
    private String  mAlienNumber;
    private String  mImmigrantCaseNumber;
    private String  mApplicationReceiptNumber;
    private String  mDocumentType;
    private String  mDocumentNumber;
    private String  mSex;
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
    private byte[]  mEncodedFullDocumentImage;
    private boolean mMrzVerified;

    public KuwaitBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "KuwaitBackSideResult{" +
                "mSerialNo='" + mSerialNo + '\'' +
                ", mAlienNumber='" + mAlienNumber + '\'' +
                ", mImmigrantCaseNumber='" + mImmigrantCaseNumber + '\'' +
                ", mApplicationReceiptNumber='" + mApplicationReceiptNumber + '\'' +
                ", mDocumentType='" + mDocumentType + '\'' +
                ", mDocumentNumber='" + mDocumentNumber + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mDocumentCode='" + mDocumentCode + '\'' +
                ", mIssuer='" + mIssuer + '\'' +
                ", mMrzText='" + mMrzText + '\'' +
                ", mNationality='" + mNationality + '\'' +
                ", mOpt1='" + mOpt1 + '\'' +
                ", mOpt2='" + mOpt2 + '\'' +
                ", mPrimaryId='" + mPrimaryId + '\'' +
                ", mSecondaryId='" + mSecondaryId + '\'' +
                ", mDateOfBirth=" + mDateOfBirth +
                ", mDateOfExpiry=" + mDateOfExpiry +
                ", mMrzVerified=" + mMrzVerified +
                '}';
    }

    public KuwaitBackSideResult setSerialNumber(String serialNo) {
        mSerialNo = serialNo;
        return this;
    }

    public KuwaitBackSideResult setAlienNr(String alienNumber) {
        mAlienNumber = alienNumber;
        return this;
    }

    public String getAlienNumber() {
        return mAlienNumber;
    }

    public String getImmigrantCaseNumber() {
        return mImmigrantCaseNumber;
    }

    public KuwaitBackSideResult setImmigrantCaseNumber(String immigrantCaseNumber) {
        mImmigrantCaseNumber = immigrantCaseNumber;
        return this;
    }

    public String getApplicationReceiptNumber() {
        return mApplicationReceiptNumber;
    }

    public KuwaitBackSideResult setApplicationReceiptNumber(String applicationReceiptNumber) {
        mApplicationReceiptNumber = applicationReceiptNumber;
        return this;
    }

    public String getDocumentType() {
        return mDocumentType;
    }

    public KuwaitBackSideResult setDocumentType(String documentType) {
        mDocumentType = documentType;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public KuwaitBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public KuwaitBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public KuwaitBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public KuwaitBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public KuwaitBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public KuwaitBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public KuwaitBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public KuwaitBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public KuwaitBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public KuwaitBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public KuwaitBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public KuwaitBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public byte[] getEncodedFullDocumentImage() {
        return mEncodedFullDocumentImage;
    }

    public KuwaitBackSideResult setEncodedFullDocumentImage(byte[] encodedFullDocumentImage) {
        mEncodedFullDocumentImage = encodedFullDocumentImage;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public KuwaitBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
