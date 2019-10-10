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


public enum OTPImportFailed {

    OTP_ITEM_NOT_FOUND("Could not extract OTP item from detected QR codes."),
    OTP_ITEM_NOT_ENCRYPTED("Imported OTP item could not be encrypted, so saving failed."),
    OTP_ITEM_NOT_SAVED("Imported OTP item could not be saved.");

    private final String mDesc;

    OTPImportFailed(String desc) {
        mDesc = desc;
    }

    public String getDesc() {
        return mDesc;
    }
}
