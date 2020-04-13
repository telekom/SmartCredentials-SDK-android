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

package de.telekom.smartcredentials.otp.otp;

import android.os.Handler;
import android.os.Looper;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.core.otp.OTPCallback;
import de.telekom.smartcredentials.core.otp.OTPPluginError;
import de.telekom.smartcredentials.core.security.Base32String;
import de.telekom.smartcredentials.core.storage.TokenRequest;
import de.telekom.smartcredentials.otp.otp.utils.OTPGenerator;
import de.telekom.smartcredentials.otp.otp.utils.OTPTask;

public abstract class OTPHandler {

    public OTPGenerator mOTPGenerator;
    public OTPUpdateCallback mOtpUpdateCallback;
    protected long mValidPeriodMillis;
    private Handler mHandler;
    private OTPRunnable mOTPRunnable;
    private OTPTask mOTPTask;
    private OTPCallback mOTPCallback;

    public void init(SecurityApi securityApi, TokenRequest tokenRequest,
                     OTPUpdateCallback otpUpdateCallback) throws EncryptionException {
        mOtpUpdateCallback = otpUpdateCallback;
        mHandler = new Handler(Looper.getMainLooper());
        mOTPGenerator = new OTPGenerator(securityApi, tokenRequest);
        mValidPeriodMillis = tokenRequest.getValidityPeriodMillis();
    }

    public void stop() {
        if (mOTPTask != null) {
            mOTPTask.releaseRef();
            mOTPTask.cancel(true);
        }
        mHandler.removeCallbacks(mOTPRunnable);
        mOTPRunnable = null;
        ApiLoggerResolver.logInfo("OTP generation stopped");
    }

    public String getOTP(String defaultAlgorithm) {
        try {
            return mOTPGenerator.getOTP(defaultAlgorithm);
        } catch (Base32String.DecodingException e) {
            onFailed(OTPPluginError.DECODING_ERROR, e);
        } catch (EncryptionException e) {
            onFailed(OTPPluginError.ALGORITHM_ERROR, e);
        }
        return null;
    }

    public void onOTPValueGenerated(String otpValue) {
        ApiLoggerResolver.logInfo("OTP value generated: " + otpValue);
        notifyOTPGenerated(otpValue);
        performNextStep();
    }

    protected void startGeneratingOTP(OTPCallback otpCallback, String defaultAlgorithm) {
        mOTPCallback = otpCallback;
        runOTPRunnable(0, defaultAlgorithm);
        ApiLoggerResolver.logInfo("OTP generation requested");
    }

    protected void runOTPRunnable(long delay, String defaultAlgorithm) {
        mOTPRunnable = new OTPRunnable(defaultAlgorithm);
        mHandler.postDelayed(mOTPRunnable, delay);
    }

    protected abstract void notifyOTPGenerated(String otpValue);

    protected abstract void performNextStep();

    protected abstract String getTag();

    private void onFailed(OTPPluginError otpPluginError, Exception e) {
        if (mOTPCallback != null) {
            mOTPCallback.onFailed(otpPluginError);
        }
        ApiLoggerResolver.logError(getTag(), e.getMessage());
    }

    private class OTPRunnable implements Runnable {

        private String defaultAlgorithm;

        public OTPRunnable(String defaultAlgorithm) {
            this.defaultAlgorithm = defaultAlgorithm;
        }

        @Override
        public void run() {
            mOTPTask = new OTPTask(OTPHandler.this, defaultAlgorithm);
            mOTPTask.execute();
        }
    }
}
