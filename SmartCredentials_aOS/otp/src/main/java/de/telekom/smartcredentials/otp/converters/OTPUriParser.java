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

package de.telekom.smartcredentials.otp.converters;


import android.net.Uri;

import java.util.Locale;

import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;

public class OTPUriParser {

    private static final String TAG = OTPUriParser.class.getSimpleName();

    static final String ISSUER_QUERY_PARAM_KEY = "issuer";
    static final String ALGORITHM_QUERY_PARAM_KEY = "algorithm";
    static final String DEFAULT_ALGORITHM = "SHA256";
    static final String DIGITS_QUERY_PARAM_KEY = "digits";
    static final String PERIOD_QUERY_PARAM_KEY = "period";
    static final int DEFAULT_PERIOD = 30;
    static final String DEFAULT_PERIOD_STRING = String.valueOf(DEFAULT_PERIOD);
    static final String COUNTER_QUERY_PARAM_KEY = "counter";
    static final int DEFAULT_DIGITS = 6;
    static final String DEFAULT_DIGITS_STRING = String.valueOf(DEFAULT_DIGITS);
    static final String SECRET_QUERY_PARAM_KEY = "secret";
    static final String IMAGE_QUERY_PARAM_KEY = "image";
    static final String OTP_SCHEME = "otpauth";
    static final int DEFAULT_COUNTER = 0;
    static final String DEFAULT_COUNTER_STRING = String.valueOf(DEFAULT_COUNTER);

    OTPType mOTPType;
    String mIssuer;
    String mUserLabel;
    String mAlgorithm;
    int mDigits;
    int mPeriod;
    long mCounter;
    String mSecret;
    String mImage;

    public OTPUriParser() {
        mCounter = -1;
    }

    boolean isTokenUriValid(Uri uri) {
        if (uri == null) {
            ApiLoggerResolver.logError(TAG, "Uri is null.");
            return false;
        }

        if (uri.getScheme() == null) {
            ApiLoggerResolver.logError(TAG, "Uri scheme is null.");
            return false;
        }

        if (!uri.getScheme().equals(OTP_SCHEME)) {
            ApiLoggerResolver.logError(TAG, String.format("Uri scheme is not %s.", OTP_SCHEME));
            return false;
        }

        if (uri.getAuthority() == null) {
            ApiLoggerResolver.logError(TAG, "Uri authority is null.");
            return false;
        }

        if (!OTPType.contains(uri.getAuthority())) {
            String otpErrorMessage = String.format("Uri authority type is neither %s or %s.", OTPType.TOTP.getDesc(), OTPType.HOTP.getDesc());
            ApiLoggerResolver.logError(TAG, otpErrorMessage);
            return false;
        }

        String path = uri.getPath();
        if (path == null) {
            ApiLoggerResolver.logError(TAG, "Uri path is null.");
            return false;
        }
        path = path.replaceFirst("/", "");

        if (path.length() == 0) {
            ApiLoggerResolver.logError(TAG, "Uri path is invalid.");
            return false;
        }
        return true;
    }

    void extractOTPItemProperties(Uri uri) {
        extractOTPType(uri);
        extractUserLabel(uri);
        extractIssuer(uri);
        extractAlgorithm(uri);
        extractDigits(uri);
        extractPeriod(uri);
        extractCounter(uri);
        extractSecret(uri);
        extractImage(uri);
    }

    void extractOTPType(Uri uri) {
        mOTPType = OTPType.getOTP(uri.getAuthority());
    }

    void extractUserLabel(Uri uri) {
        String path = uri.getPath();
        path = path.replaceFirst("/", "");
        int i = path.indexOf(':');
        mUserLabel = path.substring(i >= 0 ? i + 1 : 0);
    }

    void extractAlgorithm(Uri uri) {
        mAlgorithm = uri.getQueryParameter(ALGORITHM_QUERY_PARAM_KEY);
        if (mAlgorithm == null)
            mAlgorithm = DEFAULT_ALGORITHM;
        mAlgorithm = mAlgorithm.toUpperCase(Locale.US);
    }

    void extractDigits(Uri uri) {
        try {
            String digitsString = uri.getQueryParameter(DIGITS_QUERY_PARAM_KEY);
            if (digitsString == null)
                digitsString = DEFAULT_DIGITS_STRING;
            mDigits = Integer.parseInt(digitsString);
        } catch (NumberFormatException e) {
            mDigits = DEFAULT_DIGITS;
        }
    }

    void extractCounter(Uri uri) {
        if (mOTPType == OTPType.HOTP) {
            try {
                String c = uri.getQueryParameter(COUNTER_QUERY_PARAM_KEY);
                if (c == null)
                    c = DEFAULT_COUNTER_STRING;
                mCounter = Long.parseLong(c);
            } catch (NumberFormatException e) {
                mCounter = DEFAULT_COUNTER;
            }
        }
    }

    void extractPeriod(Uri uri) {
        try {
            String p = uri.getQueryParameter(PERIOD_QUERY_PARAM_KEY);
            if (p == null)
                p = DEFAULT_PERIOD_STRING;
            mPeriod = Integer.parseInt(p);
            mPeriod = (mPeriod > 0) ? mPeriod : DEFAULT_PERIOD;
        } catch (NumberFormatException e) {
            mPeriod = DEFAULT_PERIOD;
        }
    }

    void extractSecret(Uri uri) {
        mSecret = uri.getQueryParameter(SECRET_QUERY_PARAM_KEY);
    }

    void extractIssuer(Uri uri) {
        mIssuer = uri.getQueryParameter(ISSUER_QUERY_PARAM_KEY);
    }

    void extractImage(Uri uri) {
        mImage = uri.getQueryParameter(IMAGE_QUERY_PARAM_KEY);
    }
}
