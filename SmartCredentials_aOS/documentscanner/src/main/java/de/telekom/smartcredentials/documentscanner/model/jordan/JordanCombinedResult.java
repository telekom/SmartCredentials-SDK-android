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
 * Created by Lucian Iacob on 6/29/18 2:06 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.jordan;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class JordanCombinedResult extends CombinedResult {

    private String mDocumentNumber;
    private String mFirstName;
    private String mSex;
    private String mTitle;
    private String mIssuer;
    private String mNationality;
    private String mNationalNumber;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private boolean mMrzVerified;

    public JordanCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "JordanCombinedResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmNationalNumber='" + mNationalNumber + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmMrzVerified=" + mMrzVerified +
                "}" + super.toString();
    }

    public JordanCombinedResult setName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public JordanCombinedResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getSex() {
        return mSex;
    }

    public JordanCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public JordanCombinedResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public JordanCombinedResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public JordanCombinedResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getNationalNumber() {
        return mNationalNumber;
    }

    public JordanCombinedResult setNationalNumber(String nationalNumber) {
        mNationalNumber = nationalNumber;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public JordanCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public JordanCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public JordanCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
