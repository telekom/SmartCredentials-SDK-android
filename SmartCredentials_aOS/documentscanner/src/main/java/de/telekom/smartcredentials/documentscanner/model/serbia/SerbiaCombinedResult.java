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
 * Created by Lucian Iacob on 6/29/18 2:09 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.serbia;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class SerbiaCombinedResult extends CombinedResult {

    private String mJmbg;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mTitle;
    private String mIdentityCardNumber;
    private String mIssuer;
    private String mNationality;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private Date mDateOfIssue;
    private byte[] mEncodedSignatureImage;
    private boolean mMrzVerified;

    public SerbiaCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "SerbiaCombinedResult{\n" +
                "mJmbg='" + mJmbg + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmIdentityCardNumber='" + mIdentityCardNumber + '\'' +
                ",\nmIssuer='" + mIssuer + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                "}" + super.toString();
    }

    public String getJmbg() {
        return mJmbg;
    }

    public SerbiaCombinedResult setJmbg(String jmbg) {
        this.mJmbg = jmbg;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public SerbiaCombinedResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public SerbiaCombinedResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SerbiaCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public SerbiaCombinedResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getIdentityCardNumber() {
        return mIdentityCardNumber;
    }

    public SerbiaCombinedResult setIdentityCardNumber(String identityCardNumber) {
        mIdentityCardNumber = identityCardNumber;
        return this;
    }

    public String getIssuer() {
        return mIssuer;
    }

    public SerbiaCombinedResult setIssuer(String issuer) {
        mIssuer = issuer;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public SerbiaCombinedResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SerbiaCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SerbiaCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SerbiaCombinedResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedSignatureImage() {
        return mEncodedSignatureImage;
    }

    public SerbiaCombinedResult setEncodedSignatureImage(byte[] encodedSignatureImage) {
        mEncodedSignatureImage = encodedSignatureImage;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public SerbiaCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }
}
