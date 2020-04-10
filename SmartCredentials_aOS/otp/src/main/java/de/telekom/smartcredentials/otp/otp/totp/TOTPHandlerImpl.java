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

package de.telekom.smartcredentials.otp.otp.totp;

import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.model.token.TokenResponse;
import de.telekom.smartcredentials.core.otp.TOTPCallback;
import de.telekom.smartcredentials.core.otp.TOTPHandler;
import de.telekom.smartcredentials.otp.otp.OTPHandler;

public class TOTPHandlerImpl extends OTPHandler implements TOTPHandler {

    private static final String TAG = "TOTPHandler";

    private boolean mGenerateNextOTP;
    private TOTPCallback mOTPCallback;

    @Override
    public void startGeneratingTOTP(TOTPCallback otpCallback) {
        mOTPCallback = otpCallback;
        mGenerateNextOTP = true;
        startGeneratingOTP(otpCallback);
    }

    @Override
    public void stop() {
        super.stop();
        mGenerateNextOTP = false;
    }

    @Override
    protected void runOTPRunnable(long delay) {
        super.runOTPRunnable(delay);
    }

    @Override
    protected String getTag() {
        return TAG;
    }

    @Override
    protected void notifyOTPGenerated(String otpValue) {
        if (mOTPCallback != null) {
            mOTPCallback.onOTPGenerated(new TokenResponse(otpValue, getExpirationTime() - mValidPeriodMillis, getExpirationTime(), mValidPeriodMillis));
        }
    }

    @Override
    protected void performNextStep() {
        if (generateNextOTP()) {
            long remainingValidPeriod = Math.max(getExpirationTime() - System.currentTimeMillis(), 0);
            runOTPRunnable(remainingValidPeriod);
            ApiLoggerResolver.logInfo("Scheduled OTPGenerator to run in " + remainingValidPeriod + " milliseconds.");
        } else {
            stop();
        }
    }

    private boolean generateNextOTP() {
        return mGenerateNextOTP && mValidPeriodMillis > 0;
    }

    private long getExpirationTime() {
        return mOTPGenerator.getCounter() * mValidPeriodMillis;
    }
}
