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

package de.telekom.smartcredentials.documentscanner.model.singapore;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class SingaporeFrontSideResult extends DocumentScannerResult {
    private String mName;

    private String mRace;
    private String mSex;
    private String mPlaceOfBirth;
    private Date   mDateOfBirth;
    private String mIdentityCardNumber;
    private byte[] mEncodedFaceImage;

    public SingaporeFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "SingaporeFrontSideResult{\n" +
                "mName='" + mName + '\'' +
                ",\nmRace='" + mRace + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmIdentityCardNumber='" + mIdentityCardNumber + '\'' +
                '}';
    }

    public SingaporeFrontSideResult setCardNumber(String identityCardNumber) {
        mIdentityCardNumber = identityCardNumber;
        return this;
    }

    public String getName() {
        return mName;
    }

    public SingaporeFrontSideResult setName(String name) {
        this.mName = name;
        return this;
    }

    public String getRace() {
        return mRace;
    }

    public SingaporeFrontSideResult setRace(String race) {
        this.mRace = race;
        return this;
    }

    public String getSex() {
        return mSex;
    }

    public SingaporeFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public SingaporeFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SingaporeFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public String getIdentityCardNumber() {
        return mIdentityCardNumber;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public SingaporeFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
