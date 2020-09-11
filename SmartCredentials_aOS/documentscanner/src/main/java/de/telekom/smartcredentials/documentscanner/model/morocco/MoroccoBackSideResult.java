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

import androidx.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on January 03, 2019.
 */
public class MoroccoBackSideResult extends DocumentScannerResult {

    private String mAddress;
    private String mCivilStatusNumber;
    private Date   mDateOfExpiry;
    private String mDocumentNumber;
    private String mFathersName;
    private String mMothersName;
    private String mSex;

    public MoroccoBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "MoroccoBackSideResult{" +
                "mAddress='" + mAddress + '\'' +
                ", mCivilStatusNumber='" + mCivilStatusNumber + '\'' +
                ", mDateOfExpiry=" + mDateOfExpiry +
                ", mDocumentNumber='" + mDocumentNumber + '\'' +
                ", mFathersName='" + mFathersName + '\'' +
                ", mMothersName='" + mMothersName + '\'' +
                ", mSex='" + mSex + '\'' +
                '}';
    }

    public MoroccoBackSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public MoroccoBackSideResult setCivilStatusNumber(String civilStatusNumber) {
        mCivilStatusNumber = civilStatusNumber;
        return this;
    }

    public MoroccoBackSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public MoroccoBackSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public MoroccoBackSideResult setFathersName(String fathersName) {
        mFathersName = fathersName;
        return this;
    }

    public MoroccoBackSideResult setMothersName(String mothersName) {
        mMothersName = mothersName;
        return this;
    }

    public MoroccoBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getCivilStatusNumber() {
        return mCivilStatusNumber;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public String getSex() {
        return mSex;
    }

    @Deprecated
    public String getFathersName() {
        return mFathersName;
    }

    @Deprecated
    public String getMothersName() {
        return mMothersName;
    }
}
