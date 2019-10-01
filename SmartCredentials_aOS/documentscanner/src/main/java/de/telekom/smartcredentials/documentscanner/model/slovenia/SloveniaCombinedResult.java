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

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class SloveniaCombinedResult extends CombinedResult {

    private String mPersonalIdentificationNumber;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mTitle;
    private String mCitizenship;
    private String mIdentityCardNumber;
    private String mAddress;
    private String mIssuingAuthority;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private Date mDateOfIssue;
    private byte[] mEncodedSignatureImage;
    private boolean mMrzVerified;

    public SloveniaCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "SloveniaCombinedResult{\n" +
                "mPersonalIdentificationNumber='" + mPersonalIdentificationNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmCitizenship='" + mCitizenship + '\'' +
                ",\nmIdentityCardNumber='" + mIdentityCardNumber + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmIssuingAuthority='" + mIssuingAuthority + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                "}" + super.toString();
    }

    public String getPersonalIdentificationNumber() {
        return mPersonalIdentificationNumber;
    }

    public SloveniaCombinedResult setPersonalIdentificationNumber(String personalIdentificationNumber) {
        mPersonalIdentificationNumber = personalIdentificationNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public SloveniaCombinedResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public SloveniaCombinedResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SloveniaCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public SloveniaCombinedResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getCitizenship() {
        return mCitizenship;
    }

    public SloveniaCombinedResult setCitizenship(String citizenship) {
        mCitizenship = citizenship;
        return this;
    }

    public String getIdentityCardNumber() {
        return mIdentityCardNumber;
    }

    public SloveniaCombinedResult setIdentityCardNumber(String identityCardNumber) {
        mIdentityCardNumber = identityCardNumber;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public SloveniaCombinedResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public SloveniaCombinedResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SloveniaCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SloveniaCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SloveniaCombinedResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedSignatureImage() {
        return mEncodedSignatureImage;
    }

    public SloveniaCombinedResult setEncodedSignatureImage(byte[] encodedSignatureImage) {
        mEncodedSignatureImage = encodedSignatureImage;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public SloveniaCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
