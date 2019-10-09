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

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.model.CombinedResult;

/**
 * Created by Lucian Iacob on January 23, 2019.
 */
public class PaymentCardCombinedResult extends CombinedResult {

    private String mCardNumber;
    private String mCvv;
    private String mInventoryNumber;
    private String mOwner;
    private Date   mValidThru;

    public PaymentCardCombinedResult(Recognizer.Result.State resultState) {
        super(resultState);
    }

    @Override
    public String toString() {
        return "PaymentCardCombinedResult{" +
                "mCardNumber='" + mCardNumber + '\'' +
                ", mCvv='" + mCvv + '\'' +
                ", mInventoryNumber='" + mInventoryNumber + '\'' +
                ", mOwner='" + mOwner + '\'' +
                ", mValidThru=" + mValidThru +
                '}';
    }

    public PaymentCardCombinedResult setCardNumber(String cardNumber) {
        mCardNumber = cardNumber;
        return this;
    }

    public PaymentCardCombinedResult setCvv(String cvv) {
        mCvv = cvv;
        return this;
    }

    public PaymentCardCombinedResult setInventoryNumber(String inventoryNumber) {
        mInventoryNumber = inventoryNumber;
        return this;
    }

    public PaymentCardCombinedResult setOwner(String owner) {
        mOwner = owner;
        return this;
    }

    public PaymentCardCombinedResult setValidThru(Date validThru) {
        mValidThru = validThru;
        return this;
    }

    public String getCardNumber() {
        return mCardNumber;
    }

    public String getCvv() {
        return mCvv;
    }

    public String getInventoryNumber() {
        return mInventoryNumber;
    }

    public String getOwner() {
        return mOwner;
    }

    public Date getValidThru() {
        return mValidThru;
    }
}
