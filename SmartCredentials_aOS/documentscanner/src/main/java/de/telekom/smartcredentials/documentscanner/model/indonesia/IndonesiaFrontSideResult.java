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

package de.telekom.smartcredentials.documentscanner.model.indonesia;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class IndonesiaFrontSideResult extends DocumentScannerResult {

    private String  mCity;
    private String  mDistrict;
    private String  mKelDesa;
    private String  mMaritalStatus;
    private String  mOccupation;
    private String  mProvince;
    private String  mReligion;
    private String  mRt;
    private String  mRw;
    private boolean mValidUntilPermanent;
    private String  mBloodGroup;
    private String  mDocumentNumber;
    private String  mFirstName;
    private String  mSex;
    private String  mCitizenship;
    private String  mAddress;
    private String  mPlaceOfBirth;
    private Date    mDateOfBirth;
    private Date    mDateOfExpiry;
    private byte[]  mEncodedFaceImage;

    public IndonesiaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "IndonesiaFrontSideResult{\n" +
                "mCity='" + mCity + '\'' +
                ",\nmDistrict='" + mDistrict + '\'' +
                ",\nmKelDesa='" + mKelDesa + '\'' +
                ",\nmMaritalStatus='" + mMaritalStatus + '\'' +
                ",\nmOccupation='" + mOccupation + '\'' +
                ",\nmProvince='" + mProvince + '\'' +
                ",\nmReligion='" + mReligion + '\'' +
                ",\nmRt='" + mRt + '\'' +
                ",\nmRw='" + mRw + '\'' +
                ",\nmValidUntilPermanent=" + mValidUntilPermanent +
                ",\nmBloodGroup='" + mBloodGroup + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmCitizenship='" + mCitizenship + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                '}';
    }

    public IndonesiaFrontSideResult setBloodType(String bloodGroup) {
        mBloodGroup = bloodGroup;
        return this;
    }

    public IndonesiaFrontSideResult setName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getCity() {
        return mCity;
    }

    public IndonesiaFrontSideResult setCity(String city) {
        mCity = city;
        return this;
    }

    public IndonesiaFrontSideResult setDistrict(String district) {
        mDistrict = district;
        return this;
    }

    public IndonesiaFrontSideResult setKelDesa(String kelDesa) {
        mKelDesa = kelDesa;
        return this;
    }

    public String getMaritalStatus() {
        return mMaritalStatus;
    }

    public IndonesiaFrontSideResult setMaritalStatus(String maritalStatus) {
        mMaritalStatus = maritalStatus;
        return this;
    }

    public String getOccupation() {
        return mOccupation;
    }

    public IndonesiaFrontSideResult setOccupation(String occupation) {
        mOccupation = occupation;
        return this;
    }

    public String getProvince() {
        return mProvince;
    }

    public IndonesiaFrontSideResult setProvince(String province) {
        mProvince = province;
        return this;
    }

    public String getReligion() {
        return mReligion;
    }

    public IndonesiaFrontSideResult setReligion(String religion) {
        mReligion = religion;
        return this;
    }

    public IndonesiaFrontSideResult setRt(String rt) {
        mRt = rt;
        return this;
    }

    public IndonesiaFrontSideResult setRw(String rw) {
        mRw = rw;
        return this;
    }

    public boolean isValidUntilPermanent() {
        return mValidUntilPermanent;
    }

    public IndonesiaFrontSideResult setValidUntilPermanent(boolean validUntilPermanent) {
        mValidUntilPermanent = validUntilPermanent;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public IndonesiaFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getSex() {
        return mSex;
    }

    public IndonesiaFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getCitizenship() {
        return mCitizenship;
    }

    public IndonesiaFrontSideResult setCitizenship(String citizenship) {
        mCitizenship = citizenship;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public IndonesiaFrontSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public IndonesiaFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public IndonesiaFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public IndonesiaFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public IndonesiaFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    @Deprecated
    public String getBloodGroup() {
        return mBloodGroup;
    }

    @Deprecated
    public String getDistrict() {
        return mDistrict;
    }

    @Deprecated
    public String getKelDesa() {
        return mKelDesa;
    }

    @Deprecated
    public String getRw() {
        return mRw;
    }

    @Deprecated
    public String getRt() {
        return mRt;
    }
}
