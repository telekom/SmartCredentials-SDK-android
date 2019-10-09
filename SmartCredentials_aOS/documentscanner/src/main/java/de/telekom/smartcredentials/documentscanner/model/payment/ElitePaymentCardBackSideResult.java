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

package de.telekom.smartcredentials.documentscanner.model.payment;

import android.graphics.Bitmap;

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

/**
 * Created by Lucian Iacob on January 25, 2019.
 */
public class ElitePaymentCardBackSideResult extends DocumentScannerResult {

    private String mCardNumber;
    private String mCvv;
    private Bitmap mDocumentImage;
    private String mInventoryNumber;
    private Date   mValidThru;

    public ElitePaymentCardBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "ElitePaymentCardBackSideResult{" +
                "mCardNumber='" + mCardNumber + '\'' +
                ", mCvv='" + mCvv + '\'' +
                ", mInventoryNumber='" + mInventoryNumber + '\'' +
                ", mValidThru=" + mValidThru +
                '}';
    }

    public ElitePaymentCardBackSideResult setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
        return this;
    }

    public ElitePaymentCardBackSideResult setCvv(String cvv) {
        mCvv = cvv;
        return this;
    }

    public ElitePaymentCardBackSideResult setDocumentImage(Bitmap documentImage) {
        mDocumentImage = documentImage;
        return this;
    }

    public ElitePaymentCardBackSideResult setInventoryNumber(String inventoryNumber) {
        mInventoryNumber = inventoryNumber;
        return this;
    }

    public ElitePaymentCardBackSideResult setValidThru(Date validThru) {
        mValidThru = validThru;
        return this;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public String getCvv() {
        return mCvv;
    }

    public Bitmap getDocumentImage() {
        return mDocumentImage;
    }

    public String getInventoryNumber() {
        return mInventoryNumber;
    }

    public Date getValidThru() {
        return mValidThru;
    }
}
