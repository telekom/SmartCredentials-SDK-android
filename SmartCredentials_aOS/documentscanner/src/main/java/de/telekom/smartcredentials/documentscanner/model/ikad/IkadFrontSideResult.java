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

package de.telekom.smartcredentials.documentscanner.model.ikad;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class IkadFrontSideResult extends DocumentScannerResult {

    private String mEmployer;
    private String mFacultyAddress;
    private String mPassportNumber;
    private String mSector;
    private String mFirstName;
    private String mSex;
    private String mAddress;
    private String mNationality;
    private Date   mDateOfBirth;
    private Date   mDateOfExpiry;
    private byte[] mEncodedFaceImage;

    public IkadFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "IkadFrontSideResult{\n" +
                "mEmployer='" + mEmployer + '\'' +
                ",\nmFacultyAddress='" + mFacultyAddress + '\'' +
                ",\nmPassportNumber='" + mPassportNumber + '\'' +
                ",\nmSector='" + mSector + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                '}';
    }

    public IkadFrontSideResult setName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getEmployer() {
        return mEmployer;
    }

    public IkadFrontSideResult setEmployer(String employer) {
        mEmployer = employer;
        return this;
    }

    public String getFacultyAddress() {
        return mFacultyAddress;
    }

    public IkadFrontSideResult setFacultyAddress(String facultyAddress) {
        mFacultyAddress = facultyAddress;
        return this;
    }

    public IkadFrontSideResult setPassportNumber(String passportNumber) {
        mPassportNumber = passportNumber;
        return this;
    }

    public String getSector() {
        return mSector;
    }

    public IkadFrontSideResult setSector(String sector) {
        mSector = sector;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getSex() {
        return mSex;
    }

    public IkadFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public IkadFrontSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public IkadFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public IkadFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public IkadFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public IkadFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    @Deprecated
    public String getPassportNumber() {
        return mPassportNumber;
    }
}
