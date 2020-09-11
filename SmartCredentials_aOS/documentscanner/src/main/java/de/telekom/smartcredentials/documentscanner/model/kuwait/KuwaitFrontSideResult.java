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

package de.telekom.smartcredentials.documentscanner.model.kuwait;

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on January 03, 2019.
 */
public class KuwaitFrontSideResult extends DocumentScannerResult {

    private Date   mDateOfBirth;
    private String mCivilIdNumber;
    private byte[] mEncodedFaceImage;
    private Date   mDateOfExpiry;
    private String mName;
    private String mNationality;
    private String mSex;

    public KuwaitFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "KuwaitFrontSideResult{" +
                "mDateOfBirth=" + mDateOfBirth +
                ", mCivilIdNumber='" + mCivilIdNumber + '\'' +
                ", mDateOfExpiry=" + mDateOfExpiry +
                ", mName='" + mName + '\'' +
                ", mNationality='" + mNationality + '\'' +
                ", mSex='" + mSex + '\'' +
                '}';
    }

    public KuwaitFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public KuwaitFrontSideResult setCivilIdNumber(String civilIdNumber) {
        mCivilIdNumber = civilIdNumber;
        return this;
    }

    public KuwaitFrontSideResult setEncodedFaceImage(byte[] encodedFaceImage) {
        mEncodedFaceImage = encodedFaceImage;
        return this;
    }

    public KuwaitFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public KuwaitFrontSideResult setName(String name) {
        mName = name;
        return this;
    }

    public KuwaitFrontSideResult setNationality(String nationality) {
        mNationality = nationality;
        return this;
    }

    public KuwaitFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public String getCivilIdNumber() {
        return mCivilIdNumber;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public String getName() {
        return mName;
    }

    public String getNationality() {
        return mNationality;
    }

    public String getSex() {
        return mSex;
    }
}
