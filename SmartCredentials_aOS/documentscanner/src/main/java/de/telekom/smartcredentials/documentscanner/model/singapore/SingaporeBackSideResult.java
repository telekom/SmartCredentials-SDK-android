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

import com.microblink.entities.recognizers.Recognizer;

import java.util.Date;

import de.telekom.smartcredentials.documentscanner.utils.ModelConverter;
import de.telekom.smartcredentials.core.model.DocumentScannerResult;

public class SingaporeBackSideResult extends DocumentScannerResult {
    private String mBloodType;

    private String mAddress;
    private Date   mDateOfIssue;
    private String mCanNumber;

    public SingaporeBackSideResult(Recognizer.Result.State resultState) {
        super(ModelConverter.convertResultState(resultState));
    }

    @Override
    public String toString() {
        return "SingaporeBackSideResult{\n" +
                "mBloodType='" + mBloodType + '\'' +
                ",\nmAddress='" + mAddress + '\'' +
                ",\nmDateOfIssue=" + mDateOfIssue +
                ",\nmCanNumber='" + mCanNumber + '\'' +
                '}';
    }

    public SingaporeBackSideResult setCardNumber(String canNumber) {
        mCanNumber = canNumber;
        return this;
    }

    public SingaporeBackSideResult setBloodType(String bloodType) {
        mBloodType = bloodType;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public SingaporeBackSideResult setAddress(String address) {
        mAddress = address;
        return this;
    }

    public Date getDateOfIssue() {
        return mDateOfIssue;
    }

    public SingaporeBackSideResult setDateOfIssue(Date dateOfIssue) {
        mDateOfIssue = dateOfIssue;
        return this;
    }

    public String getCanNumber() {
        return mCanNumber;
    }

    @Deprecated
    public String getBloodType() {
        return mBloodType;
    }
}
