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

package de.telekom.smartcredentials.otp.otp.utils;


import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.security.Base32String;
import de.telekom.smartcredentials.core.security.MacAlgorithm;
import de.telekom.smartcredentials.core.storage.TokenRequest;

public class OTPGenerator {

    private static final int[] DIGITS_POWER = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};
    private static final List<String> ACCEPTED_MAC_ALGORITHMS = Collections.unmodifiableList(
            Arrays.asList(MacAlgorithm.SHA1, MacAlgorithm.SHA256, MacAlgorithm.SHA384, MacAlgorithm.SHA512));

    private final SecurityApi mSecurityApi;
    private final TokenRequest mTokenRequest;
    private long mCounter;

    public OTPGenerator(SecurityApi securityApi, TokenRequest tokenRequest) throws EncryptionException {
        mSecurityApi = securityApi;
        mTokenRequest = tokenRequest;
        mCounter = tokenRequest.getCounter();
    }

    public long getCounter() {
        return mCounter;
    }

    public String getOTP() throws Base32String.DecodingException, EncryptionException {
        long counter = mCounter;
        ByteBuffer text = ByteBuffer.allocate(8).putLong(0, counter);

        String result = Integer.toString(computeOtp(text));
        while (result.length() < mTokenRequest.getOtpValueDigitsCount()) {
            result = "0".concat(result);
        }

        mCounter++;
        mTokenRequest.setCounter(mCounter);

        return result;
    }

    public TokenRequest getTokenRequest() {
        return mTokenRequest;
    }

    int computeOtp(ByteBuffer text) throws Base32String.DecodingException, EncryptionException {
        int otp = getBinary(text) % DIGITS_POWER[mTokenRequest.getOtpValueDigitsCount()];
        if (mTokenRequest.addChecksum()) {
            otp = (otp * 10) + mSecurityApi.calculateChecksum(otp, mTokenRequest.getOtpValueDigitsCount());
        }
        return otp;
    }

    int getBinary(ByteBuffer text) throws Base32String.DecodingException, EncryptionException {
        String algorithm = mTokenRequest.getAlgorithm();

        if (ACCEPTED_MAC_ALGORITHMS.contains(algorithm)) {
            byte[] hash = mSecurityApi.hmac_sha("Hmac" + algorithm, Base32String.decode(mTokenRequest.getKey()), text.array());
            int offset = getOffset(hash);

            return ((hash[offset] & 0x7f) << 0x18)
                    | ((hash[offset + 1] & 0xff) << 0x10)
                    | ((hash[offset + 2] & 0xff) << 0x08)
                    | (hash[offset + 3] & 0xff);
        } else {
            throw new EncryptionException(algorithm + " is not a supported MAC algorithm for OTP generation.");
        }
    }

    int getOffset(byte[] hash) throws EncryptionException {
        if (hash != null && hash.length > 0) {
            int offset = hash[hash.length - 1] & 0xf;
            if ((0 <= mTokenRequest.getTruncationOffset()) && (mTokenRequest.getTruncationOffset() < (hash.length - 4))) {
                offset = mTokenRequest.getTruncationOffset();
            }
            return offset;
        }
        return 0;
    }
}
