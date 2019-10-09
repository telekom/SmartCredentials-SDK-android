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
 * Created by Lucian Iacob on 6/29/18 2:08 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.poland;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class PolandFrontSideResult extends DocumentScannerResult {

    private String parentsGivenNames;
    private String mGivenNames;
    private String mFirstName;
    private String mLastName;
    private String mSex;
    private String mTitle;
    private Date mDateOfBirth;
    private byte[] mEncodedFaceImage;

    public PolandFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "PolandFrontSideResult{\n" +
                "parentsGivenNames='" + parentsGivenNames + '\'' +
                ",\nmGivenNames='" + mGivenNames + '\'' +
                ",\nmFirstName='" + mFirstName + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                '}';
    }

    public PolandFrontSideResult setParentsGivenName(String parentsGivenNames) {
        this.parentsGivenNames = parentsGivenNames;
        return this;
    }

    public PolandFrontSideResult setGivenName(String givenNames) {
        mGivenNames = givenNames;
        return this;
    }

    public String getGivenNames() {
        return mGivenNames;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public PolandFrontSideResult setFirstName(String firstName) {
        mFirstName = firstName;
        return this;
    }

    public PolandFrontSideResult setLastName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public PolandFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public PolandFrontSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public PolandFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public PolandFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    @Deprecated
    public String getParentsGivenNames() {
        return parentsGivenNames;
    }

    @Deprecated
    public String getLastName() {
        return mLastName;
    }
}
