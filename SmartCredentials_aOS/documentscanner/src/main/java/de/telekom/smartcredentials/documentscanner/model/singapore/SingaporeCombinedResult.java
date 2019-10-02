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
 * Created by Lucian Iacob on 6/29/18 2:09 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.singapore;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;

public class SingaporeCombinedResult extends CombinedResult {

    private String race;
    private String mBloodType;
    private String mLastName;
    private String mSex;
    private String mAddress;
    private String mPlaceOfBirth;
    private Date   mDateOfBirth;
    private Date   mDateOfIssue;
    private String mCanNumber;

    public SingaporeCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @NonNull
    @Override
    public String toString() {
        return "SingaporeCombinedResult{\n" +
                "race='" + race + '\'' +
                ",\nmBloodType='" + mBloodType + '\'' +
                ",\nmLastName='" + mLastName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmCanNumber='" + mCanNumber + '\'' +
                "}" + super.toString();
    }

    public SingaporeCombinedResult setName(String lastName) {
        mLastName = lastName;
        return this;
    }

    public SingaporeCombinedResult setCardNumber(String canNumber) {
        mCanNumber = canNumber;
        return this;
    }

    public String getRace() {
        return race;
    }

    public SingaporeCombinedResult setRace(String race) {
        this.race = race;
        return this;
    }

    public String getBloodType() {
        return mBloodType;
    }

    public SingaporeCombinedResult setBloodType(String bloodType) {
        mBloodType = bloodType;
        return this;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getSex() {
        return mSex;
    }

    public SingaporeCombinedResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public SingaporeCombinedResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public SingaporeCombinedResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public SingaporeCombinedResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SingaporeCombinedResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public String getCanNumber() {
        return mCanNumber;
    }
}
