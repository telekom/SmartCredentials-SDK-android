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
 * Created by Lucian Iacob on 6/29/18 2:07 PM.
 * lucian.iacob@endava.com
 * Deutsche Telekom - All Rights Reserved - ©
 */

package de.telekom.smartcredentials.documentscanner.model.mykad;

import android.support.annotation.NonNull;

import com.microblink.entities.recognizers.Recognizer;
import com.microblink.results.date.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;


public class MyKadBackSideResult extends DocumentScannerResult {

    private Date   dateOfBirth;
    private byte[] encodedFullDocumentImage;
    private String extendedNric;
    private String nric;
    private String mOldNric;

    public MyKadBackSideResult(Recognizer.Result.State scannerResultState) {
        super(ModelConverter.convertResultState(scannerResultState));
    }

    @NonNull
    @Override
    public String toString() {
        return "MyKadBackSideResult{\n" +
                "dateOfBirth=" + dateOfBirth +
                ", extendedNric='" + extendedNric + '\'' +
                ", nric='" + nric + '\'' +
                '}';
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public MyKadBackSideResult setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public byte[] getEncodedFullDocumentImage() {
        return encodedFullDocumentImage;
    }

    public MyKadBackSideResult setEncodedFullDocumentImage(byte[] encodedFullDocumentImage) {
        this.encodedFullDocumentImage = encodedFullDocumentImage;
        return this;
    }

    public MyKadBackSideResult setExtendedNric(String extendedNric) {
        this.extendedNric = extendedNric;
        return this;
    }

    public String getNric() {
        return nric;
    }

    public MyKadBackSideResult setNric(String nric) {
        this.nric = nric;
        return this;
    }

    public MyKadBackSideResult setOldNric(String oldNric) {
        mOldNric = oldNric;
        return this;
    }

    public String getOldNric() {
        return mOldNric;
    }

    @Deprecated
    public String getExtendedNric() {
        return extendedNric;
    }
}
