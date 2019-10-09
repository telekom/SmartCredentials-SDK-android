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

package de.telekom.smartcredentials.documentscanner.model.serbia;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class SerbiaFrontSideResult extends DocumentScannerResult {

    private String mDocumentNumber;
    private String mTitle;
    private Date mDateOfExpiry;
    private Date mDateOfIssue;
    private byte[] mEncodedFaceImage;

    public SerbiaFrontSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SerbiaFrontSideResult{\n" +
                "mDocumentNumber='" + mDocumentNumber + '\'' +
                ",\nmTitle='" + mTitle + '\'' +
                ",\nmDateOfExpiry=" + mDateOfExpiry +
                ",\nmDateOfIssue=" + mDateOfIssue +
                '}';
    }

    public String getDocumentNumber() {
        return mDocumentNumber;
    }

    public SerbiaFrontSideResult setDocumentNumber(String documentNumber) {
        this.mDocumentNumber = documentNumber;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public SerbiaFrontSideResult setTitle(String title) {
        mTitle = title;
        return this;
    }

    public Date getDateOfExpiry() {
        return mDateOfExpiry;
    }

    public SerbiaFrontSideResult setDateOfExpiry(Date dateOfExpiry) {
        mDateOfExpiry = dateOfExpiry;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SerbiaFrontSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public byte[] getEncodedFaceImage() {
        return mEncodedFaceImage;
    }

    public SerbiaFrontSideResult setEncodedFaceImage(byte[] faceImage) {
        mEncodedFaceImage = faceImage;
        return this;
    }
}
