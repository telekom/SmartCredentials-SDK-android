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

package de.telekom.smartcredentials.documentscanner.model.brunei;

import android.graphics.Bitmap;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on February 12, 2019.
 */
public class BruneiFrontSideResult extends DocumentScannerResult {
    private Date   mDateOfBirth;

    private String mDocumentNumber;
    private Bitmap mFaceImage;
    private String mFullName;
    private String mPlaceOfBirth;
    private String mSex;

    public BruneiFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "BruneiFrontSideResult{" +
                "mDateOfBirth=" + mDateOfBirth +
                ", mDocumentNumber='" + mDocumentNumber + '\'' +
                ", mFullName='" + mFullName + '\'' +
                ", mPlaceOfBirth='" + mPlaceOfBirth + '\'' +
                ", mSex='" + mSex + '\'' +
                '}';
    }

    public BruneiFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public BruneiFrontSideResult setDocumentNumber(String documentNumber) {
        mDocumentNumber = documentNumber;
        return this;
    }

    public BruneiFrontSideResult setFaceImage(Bitmap faceImage) {
        mFaceImage = faceImage;
        return this;
    }

    public BruneiFrontSideResult setFullName(String fullName) {
        mFullName = fullName;
        return this;
    }

    public BruneiFrontSideResult setPlaceOfBirth(String placeOfBirth) {
        mPlaceOfBirth = placeOfBirth;
        return this;
    }

    public BruneiFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public Bitmap getFaceImage() {
        return mFaceImage;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public String getSex() {
        return mSex;
    }
}
