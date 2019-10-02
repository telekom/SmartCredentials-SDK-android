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

package de.telekom.smartcredentials.documentscanner.model.mytentera;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class MalaysiaMyTenteraFrontSideResult extends DocumentScannerResult {

    private String mArmyNumber;
    private String mNricNumber;
    private String mAddressCity;
    private String mOwnerAddressState;
    private String mOwnerAddressStreet;
    private String mOwnerAddressZipCode;
    private String mReligion;
    private String mFullName;
    private String mSex;
    private String mAddress;
    private Date   mDateOfBirth;
    private byte[] mEncodedFaceImage;

    public MalaysiaMyTenteraFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "MalaysiaMyTenteraFrontSideResult{\n" +
                "mArmyNumber='" + mArmyNumber + '\'' +
                ",\nmNricNumber='" + mNricNumber + '\'' +
                ",\nmAddressCity='" + mAddressCity + '\'' +
                ",\nmOwnerAddressState='" + mOwnerAddressState + '\'' +
                ",\nmOwnerAddressStreet='" + mOwnerAddressStreet + '\'' +
                ",\nmOwnerAddressZipCode='" + mOwnerAddressZipCode + '\'' +
                ",\nmReligion='" + mReligion + '\'' +
                ",\nmFullName='" + mFullName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                '}';
    }

    public MalaysiaMyTenteraFrontSideResult setAddressState(String ownerAddressState) {
        this.mOwnerAddressState = ownerAddressState;
        return this;
    }

    public MalaysiaMyTenteraFrontSideResult setAddressStreet(String ownerAddressStreet) {
        this.mOwnerAddressStreet = ownerAddressStreet;
        return this;
    }

    public MalaysiaMyTenteraFrontSideResult setAddressZipCode(String ownerAddressZipCode) {
        this.mOwnerAddressZipCode = ownerAddressZipCode;
        return this;
    }

    public String getArmyNumber() {
        return mArmyNumber;
    }

    public MalaysiaMyTenteraFrontSideResult setArmyNumber(String armyNumber) {
        this.mArmyNumber = armyNumber;
        return this;
    }

    public String getNricNumber() {
        return mNricNumber;
    }

    public MalaysiaMyTenteraFrontSideResult setNricNumber(String nricNumber) {
        this.mNricNumber = nricNumber;
        return this;
    }

    public String getAddressCity() {
        return mAddressCity;
    }

    public MalaysiaMyTenteraFrontSideResult setAddressCity(String ownerAddressCity) {
        this.mAddressCity = ownerAddressCity;
        return this;
    }

    public String getOwnerAddressState() {
        return mOwnerAddressState;
    }

    public String getOwnerAddressStreet() {
        return mOwnerAddressStreet;
    }

    public String getOwnerAddressZipCode() {
        return mOwnerAddressZipCode;
    }

    public String getReligion() {
        return mReligion;
    }

    public MalaysiaMyTenteraFrontSideResult setReligion(String religion) {
        this.mReligion = religion;
        return this;
    }

    public String getFullName() {
        return mFullName;
    }

    public MalaysiaMyTenteraFrontSideResult setFullName(String fullName) {
        this.mFullName = fullName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public MalaysiaMyTenteraFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public MalaysiaMyTenteraFrontSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public MalaysiaMyTenteraFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public MalaysiaMyTenteraFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
