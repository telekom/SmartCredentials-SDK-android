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

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class PolandCombinedResult extends CombinedResult {

    private String mParentsGivenNames;
    private String mSurname;
    private String mPersonalIdentificationNumber;
    private String mDocumentNumber;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mTitle;
    private String mIssuer;
    private String mNationality;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private boolean mMrzVerified;

    public PolandCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "PolandCombinedResult{\n" +
                "mParentsGivenNames='" + mParentsGivenNames + '\'' +
                ",\nmSurname='" + mSurname + '\'' +
                ",\nmPersonalIdentificationNumber='" + mPersonalIdentificationNumber + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmMrzVerified=" + mMrzVerified +
                "}" + super.toString();
    }

    public String getParentsGivenNames() {
        return mParentsGivenNames;
    }

    public PolandCombinedResult setParentsGivenNames(String parentsGivenNames) {
        mParentsGivenNames = parentsGivenNames;
        return this;
    }

    public String getSurname() {
        return mSurname;
    }

    public PolandCombinedResult setSurname(String surname) {
        mSurname = surname;
        return this;
    }

    public String getPersonalIdentificationNumber() {
        return mPersonalIdentificationNumber;
    }

    public PolandCombinedResult setPersonalIdentificationNumber(String personalIdentificationNumber) {
        mPersonalIdentificationNumber = personalIdentificationNumber;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public PolandCombinedResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public PolandCombinedResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public PolandCombinedResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public PolandCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public PolandCombinedResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public PolandCombinedResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public PolandCombinedResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public PolandCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public PolandCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public PolandCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
