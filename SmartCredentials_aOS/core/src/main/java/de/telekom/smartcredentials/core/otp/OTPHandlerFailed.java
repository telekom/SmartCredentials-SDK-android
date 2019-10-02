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

package de.telekom.smartcredentials.core.otp;


public enum OTPHandlerFailed {

    OTP_ITEM_DECRYPTION_FAILED("OTP item could not be decrypted."),
    OTP_HANDLER_NOT_DEFINED("Could not get OTP handler"),
    OTP_ITEM_NOT_FOUND("OTP item not found.");

    private final String mDesc;

    OTPHandlerFailed(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }
}
