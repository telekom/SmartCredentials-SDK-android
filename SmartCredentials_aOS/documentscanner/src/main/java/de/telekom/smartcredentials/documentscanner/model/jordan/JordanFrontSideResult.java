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

package de.telekom.smartcredentials.documentscanner.model.jordan;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class JordanFrontSideResult extends DocumentScannerResult {

    private String mFirstName;
    private String mSex;
    private String mTitle;
    private String mNationalNumber;
    private Date mDateOfBirth;
    private byte[] mEncodedFaceImage;

    public JordanFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "JordanFrontSideResult{\n" +
                "mFirstName='" + mFirstName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmNationalNumber='" + mNationalNumber + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                '}';
    }

    public JordanFrontSideResult setName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getSex() {
        return mSex;
    }

    public JordanFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public JordanFrontSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public String getNationalNumber() {
        return mNationalNumber;
    }

    public JordanFrontSideResult setNationalNumber(String nationalNumber) {
        mNationalNumber = nationalNumber;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public JordanFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public JordanFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
