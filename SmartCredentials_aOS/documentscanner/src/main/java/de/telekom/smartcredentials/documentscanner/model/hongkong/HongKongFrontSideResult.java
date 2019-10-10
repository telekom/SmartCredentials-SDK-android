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
 * Created by Lucian Iacob on 6/29/18 2:05 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.hongkong;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class HongKongFrontSideResult extends DocumentScannerResult {

    private String mDocumentNumber;
    private String mFullName;
    private String mSex;
    private Date   mDateOfBirth;
    private Date   mDateOfIssue;
    private String mCommercialCode;
    private byte[] mEncodedFaceImage;
    private String mResidentialStatus;

    public HongKongFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "HongKongFrontSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmFullName='" + mFullName + '\'' +
                ",\nmSex='" + mSex + '\'' +
                ",\nmDateOfBirth=" + mDateOfBirth +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmCommercialCode='" + mCommercialCode + '\'' +
                '}';
    }

    public HongKongFrontSideResult setFullName(String fullName) {
        mFullName = fullName;
        return this;
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public HongKongFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getFullName() {
        return mFullName;
    }

    public String getSex() {
        return mSex;
    }

    public HongKongFrontSideResult setSex(String sex) {
        mSex = sex;
        return this;
    }

    public Date getDateOfBirth() {
        return mDateOfBirth;
    }

    public HongKongFrontSideResult setDateOfBirth(Date dateOfBirth) {
        mDateOfBirth = dateOfBirth;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public HongKongFrontSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public HongKongFrontSideResult setCommercialCode(String commercialCode) {
        mCommercialCode = commercialCode;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public HongKongFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }

    public HongKongFrontSideResult setResidentialStatus(String residentialStatus) {
        mResidentialStatus = residentialStatus;
        return this;
    }

    public String getResidentialStatus() {
        return mResidentialStatus;
    }

    @Deprecated
    public String getCommercialCode() {
        return mCommercialCode;
    }
}
