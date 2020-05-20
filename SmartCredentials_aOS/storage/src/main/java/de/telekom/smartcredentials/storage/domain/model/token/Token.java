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

package de.telekom.smartcredentials.storage.domain.model.token;


import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import de.telekom.smartcredentials.core.model.otp.OTPTokenKey;

public class Token {

    static final int DEFAULT_DIGITS = 6;
    static final int DEFAULT_COUNTER = 0;
    static final int DEFAULT_VALIDITY_PERIOD = 30;
    static final int DEFAULT_TRUNCATION_OFFSET = 16;
    static final long MILLIS_PER_SECOND = 1000;

    @SerializedName(OTPTokenKey.KEY)
    String mKey;
    @SerializedName(OTPTokenKey.COUNTER)
    long mCounter;
    @SerializedName(OTPTokenKey.DIGITS_COUNT)
    int mOtpValueDigitsCount;
    @SerializedName(OTPTokenKey.ALGORITHM)
    String mAlgorithm;
    @SerializedName(OTPTokenKey.HAS_CHECK_SUM)
    boolean mAddChecksum;
    @SerializedName(OTPTokenKey.TRUNCATION_OFFSET)
    int mTruncationOffset;
    @SerializedName(OTPTokenKey.VALIDITY_PERIOD)
    long mValidityPeriod;

    public String getKey() {
        return mKey != null ? mKey : "";
    }

    long getCounter() {
        return mCounter > 0 ? mCounter : DEFAULT_COUNTER;
    }

    public void setCounter(long counter) {
        mCounter = counter;
    }

    int getOtpValueDigitsCount() {
        int digits = mOtpValueDigitsCount > 0 ? mOtpValueDigitsCount : DEFAULT_DIGITS;
        return mAddChecksum ? (digits + 1) : digits;
    }

    String getAlgorithm(String defaultAlgorithm) {
        return !TextUtils.isEmpty(mAlgorithm) ? mAlgorithm : defaultAlgorithm;
    }

    boolean addChecksum() {
        return mAddChecksum;
    }

    int getTruncationOffset() {
        return mTruncationOffset > 0 ? mTruncationOffset : DEFAULT_TRUNCATION_OFFSET;
    }

    long getValidityPeriodMillis() {
        return (mValidityPeriod > 0 ? mValidityPeriod : DEFAULT_VALIDITY_PERIOD) * MILLIS_PER_SECOND;
    }
}
