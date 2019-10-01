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
 * Created by Lucian Iacob on 6/29/18 2:05 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.germany;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;


public class GermanyCombinedResult extends CombinedResult {

    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mTitle;
    private String mIdentityCardNumber;
    private String mAddress;
    private String mIssuingAuthority;
    private String mNationality;
    private String mPlaceOfBirth;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private Date mDateOfIssue;
    private byte[] mEncodedSignatureImage;
    private boolean mMrzVerified;
    private String mCanNumber;
    private String mEyeColour;
    private String mHeight;

    public GermanyCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "GermanyCombinedResult{\n" +
                "mFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmIdentityCardNumber='" + mIdentityCardNumber + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmIssuingAuthority='" + mIssuingAuthority + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmMrzVerified=" + mMrzVerified +
                ",\nmCanNumber='" + mCanNumber + '\'' +
                ",\nmEyeColour='" + mEyeColour + '\'' +
                ",\nmHeight='" + mHeight + '\'' +
                "}" + super.toString();
    }

    public GermanyCombinedResult setEyeColor(String eyeColour) {
        mEyeColour = eyeColour;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public GermanyCombinedResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public GermanyCombinedResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public GermanyCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public GermanyCombinedResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getIdentityCardNumber() {
        return mIdentityCardNumber;
    }

    public GermanyCombinedResult setIdentityCardNumber(String identityCardNumber) {
        mIdentityCardNumber = identityCardNumber;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public GermanyCombinedResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getIssuingAuthority() {
        return mIssuingAuthority;
    }

    public GermanyCombinedResult setIssuingAuthority(String issuingAuthority) {
        mIssuingAuthority = issuingAuthority;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public GermanyCombinedResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public GermanyCombinedResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public GermanyCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public GermanyCombinedResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public GermanyCombinedResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedSignatureImage() {
        return mEncodedSignatureImage;
    }

    public GermanyCombinedResult setEncodedSignatureImage(byte[] encodedSignatureImage) {
        mEncodedSignatureImage = encodedSignatureImage;
        return this;
    }

    public boolean isMrzVerified() {
        return mMrzVerified;
    }

    public GermanyCombinedResult setMrzVerified(boolean mrzVerified) {
        mMrzVerified = mrzVerified;
        return this;
    }

    public String getCanNumber() {
        return mCanNumber;
    }

    public GermanyCombinedResult setCanNumber(String canNumber) {
        mCanNumber = canNumber;
        return this;
    }

    public String getEyeColour() {
        return mEyeColour;
    }

    public String getHeight() {
        return mHeight;
    }

    public GermanyCombinedResult setHeight(String height) {
        mHeight = height;
        return this;
    }
}
