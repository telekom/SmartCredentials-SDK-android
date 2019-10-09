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

package de.telekom.smartcredentials.documentscanner.model.poland;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class PolandBackSideResult extends DocumentScannerResult {

    private String mDocumentNumber;
    private String mSex;
    private String mTitle;
    private String mDocumentCode;
    private String mIssuer;
    private String mMrzText;
    private String mNationality;
    private String mOpt1;
    private String mOpt2;
    private String mPrimaryId;
    private String mSecondaryId;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private boolean mMrzVerified;

    public PolandBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "PolandBackSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
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
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public PolandBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public PolandBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public PolandBackSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getDocumentCode() {
        return mDocumentCode;
    }

    public PolandBackSideResult setDocumentCode(String documentCode) {
        mDocumentCode = documentCode;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public PolandBackSideResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getMrzText() {
        return mMrzText;
    }

    public PolandBackSideResult setMrzText(String mrzText) {
        mMrzText = mrzText;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public PolandBackSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getOpt1() {
        return mOpt1;
    }

    public PolandBackSideResult setOpt1(String opt1) {
        mOpt1 = opt1;
        return this;
    }

    public String getOpt2() {
        return mOpt2;
    }

    public PolandBackSideResult setOpt2(String opt2) {
        mOpt2 = opt2;
        return this;
    }

    public String getPrimaryId() {
        return mPrimaryId;
    }

    public PolandBackSideResult setPrimaryId(String primaryId) {
        mPrimaryId = primaryId;
        return this;
    }

    public String getSecondaryId() {
        return mSecondaryId;
    }

    public PolandBackSideResult setSecondaryId(String secondaryId) {
        mSecondaryId = secondaryId;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public PolandBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public PolandBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public PolandBackSideResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
