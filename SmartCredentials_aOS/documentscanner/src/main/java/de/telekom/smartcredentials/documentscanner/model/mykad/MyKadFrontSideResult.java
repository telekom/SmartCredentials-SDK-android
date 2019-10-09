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

package de.telekom.smartcredentials.documentscanner.model.mykad;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class MyKadFrontSideResult extends DocumentScannerResult {

    private String mOwnerAddressState;
    private String mOwnerAddressStreet;
    private String mNricNumber;
    private String mOwnerAddressCity;
    private String mOwnerAddressZipCode;
    private String mOwnerReligion;
    private String mFirstName;
    private String mSex;
    private String mAddress;
    private Date   mDateOfBirth;
    private byte[] mEncodedFaceImage;

    public MyKadFrontSideResult(Recognizer.Result.State scannerResultState) {
        super(ModelConverter.convertResultState(scannerResultState));
    }

    @Override
    public String toString() {
        return "MyKadFrontSideResult{\n" +
                "mOwnerAddressState='" + mOwnerAddressState + '\'' +
                ",\nmOwnerAddressStreet='" + mOwnerAddressStreet + '\'' +
                ",\nmNricNumber='" + mNricNumber + '\'' +
                ",\nmOwnerAddressCity='" + mOwnerAddressCity + '\'' +
                ",\nmOwnerAddressZipCode='" + mOwnerAddressZipCode + '\'' +
                ",\nmOwnerReligion='" + mOwnerReligion + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                '}';
    }

    public MyKadFrontSideResult setFullName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public MyKadFrontSideResult setAddressState(String ownerAddressState) {
        mOwnerAddressState = ownerAddressState;
        return this;
    }

    public MyKadFrontSideResult setAddressStreet(String ownerAddressStreet) {
        mOwnerAddressStreet = ownerAddressStreet;
        return this;
    }

    public MyKadFrontSideResult setAddressCity(String ownerAddressCity) {
        mOwnerAddressCity = ownerAddressCity;
        return this;
    }

    public MyKadFrontSideResult setAddressZipCode(String ownerAddressZipCode) {
        mOwnerAddressZipCode = ownerAddressZipCode;
        return this;
    }

    public MyKadFrontSideResult setReligion(String ownerReligion) {
        mOwnerReligion = ownerReligion;
        return this;
    }

    public String getOwnerAddressState() {
        return mOwnerAddressState;
    }

    public String getOwnerAddressStreet() {
        return mOwnerAddressStreet;
    }

    public String getNricNumber() {
        return mNricNumber;
    }

    public MyKadFrontSideResult setNricNumber(String nricNumber) {
        mNricNumber = nricNumber;
        return this;
    }

    public String getOwnerAddressCity() {
        return mOwnerAddressCity;
    }

    public String getOwnerAddressZipCode() {
        return mOwnerAddressZipCode;
    }

    public String getOwnerReligion() {
        return mOwnerReligion;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getSex() {
        return mSex;
    }

    public MyKadFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public MyKadFrontSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public MyKadFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public MyKadFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
