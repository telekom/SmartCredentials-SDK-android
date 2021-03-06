/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

package de.telekom.smartcredentials.eid.messages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex.Graur@endava.com at 2/10/2020
 */
public class Validity {

    @SerializedName("effectiveDate")
    @Expose
    private String mEffectiveDate;
    @SerializedName("expirationDate")
    @Expose
    private String mExpirationDate;

    public String getEffectiveDate() {
        return mEffectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.mEffectiveDate = effectiveDate;
    }

    public String getExpirationDate() {
        return mExpirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.mExpirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return "Validity{" +
                "mEffectiveDate='" + mEffectiveDate + '\'' +
                ", mExpirationDate='" + mExpirationDate + '\'' +
                '}';
    }
}
