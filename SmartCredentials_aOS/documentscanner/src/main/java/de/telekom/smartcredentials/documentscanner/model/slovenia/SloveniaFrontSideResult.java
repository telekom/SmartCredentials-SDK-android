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

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class SloveniaFrontSideResult extends DocumentScannerResult {
    private String mFirstName;

    private String mLastName;
    private String mSex;
    private String mTitle;
    private String mNationality;
    private Date mDateOfBirth;
    private Date mDateOfExpiry;
    private byte[] mEncodedFaceImage;

    public SloveniaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SloveniaFrontSideResult{\n" +
                "mFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmNationality='" + mNationality + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                '}';
    }

    public String getFirstName() {
        return mFirstName;
    }

    public SloveniaFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public SloveniaFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SloveniaFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public SloveniaFrontSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getNationality() {
        return mNationality;
    }

    public SloveniaFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SloveniaFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SloveniaFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public SloveniaFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
