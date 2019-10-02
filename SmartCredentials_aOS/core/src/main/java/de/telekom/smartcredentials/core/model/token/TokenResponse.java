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

package de.telekom.smartcredentials.core.model.token;


public class TokenResponse {

    private final String mValue;
    private long mValidFrom;
    private final long mValidUntil;
    private long mValidityPeriod;

    public TokenResponse(String value, long validUntil) {
        mValue = value;
        mValidUntil = validUntil;
    }

    public TokenResponse(String value, long validFrom, long validUntil, long validityPeriod) {
        mValue = value;
        mValidFrom = validFrom;
        mValidUntil = validUntil;
        mValidityPeriod = validityPeriod;
    }

    public String getValue() {
        return mValue;
    }

    public long getValidUntil() {
        return mValidUntil;
    }

    public long getValidityPeriod() {
        return mValidityPeriod;
    }

    public long getValidFrom() {
        return mValidFrom;
    }
}
