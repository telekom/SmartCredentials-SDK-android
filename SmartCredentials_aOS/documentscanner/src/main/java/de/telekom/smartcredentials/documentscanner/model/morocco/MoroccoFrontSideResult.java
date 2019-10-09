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

package de.telekom.smartcredentials.documentscanner.model.morocco;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on January 03, 2019.
 */
public class MoroccoFrontSideResult extends DocumentScannerResult {

    private Date   mDateOfBirth;
    private Date   mDateOfExpiry;
    private String mDocumentNumber;
    private byte[] mEncodedFaceImage;
    private String mName;
    private String mPlaceOfBirth;
    private String mSex;
    private String mSurname;

    public MoroccoFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "MoroccoFrontSideResult{" +
                "mDateOfBirth=" + mDateOfBirth +
                ", mDateOfExpiry=" + mDateOfExpiry +
                ", mDocumentNumber='" + mDocumentNumber + '\'' +
                ", mName='" + mName + '\'' +
                ", mPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ", mSex='" + mSex + '\'' +
                ", mSurname='" + mSurname + '\'' +
                '}';
    }

    public MoroccoFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public MoroccoFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public MoroccoFrontSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public MoroccoFrontSideResult setEncodedFaceImage(byte[] encodedFaceImage) {
        mEncodedFaceImage = encodedFaceImage;
        return this;
    }

    public MoroccoFrontSideResult setName(String name) {
        mName = name;
        return this;
    }

    public MoroccoFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public MoroccoFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public MoroccoFrontSideResult setSurname(String surname) {
        mSurname = surname;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public String getName() {
        return mName;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public String getSex() {
        return mSex;
    }

    public String getSurname() {
        return mSurname;
    }
}
