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

import android.os.AsyncTask;

import de.telekom.smartcredentials.otp.otp.OTPHandler;


public class OTPTask extends AsyncTask<Object, String, String> {

    private OTPHandler mOTPHandler;
    private String mDefaultAlgorithm;

    public OTPTask(OTPHandler otpHandler, String defaultAlgorithm) {
        mDefaultAlgorithm = defaultAlgorithm;
        mOTPHandler = otpHandler;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        return mOTPHandler.getOTP(mDefaultAlgorithm);
    }

    @Override
    protected void onPostExecute(String o) {
        mOTPHandler.onOTPValueGenerated(o);
    }

    public void releaseRef() {
        mOTPHandler = null;
    }
}
