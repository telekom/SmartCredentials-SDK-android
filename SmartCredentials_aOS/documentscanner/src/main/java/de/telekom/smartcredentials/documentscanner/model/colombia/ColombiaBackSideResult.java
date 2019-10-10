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
 * Created by Lucian Iacob on 6/29/18 2:03 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.colombia;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class ColombiaBackSideResult extends DocumentScannerResult {

    private String mBloodGroup;
    private String mDocumentNumber;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private Date   mDateOfBirth;
    private byte[] mFingerprint;

    public ColombiaBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "ColombiaBackSideResult{\n" +
                "mBloodGroup='" + mBloodGroup + '\'' +
                ",\nmDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                '}';
    }

    public ColombiaBackSideResult setBloodGroup(String bloodGroup) {
        mBloodGroup = bloodGroup;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public ColombiaBackSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public ColombiaBackSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public ColombiaBackSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public ColombiaBackSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public ColombiaBackSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getFingerprint() {
        return mFingerprint;
    }

    public ColombiaBackSideResult setFingerprint(byte[] fingerprint) {
        mFingerprint = fingerprint;
        return this;
    }

    @Deprecated
    public String getBloodGroup() {
        return mBloodGroup;
    }
}
