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

public interface TOTPHandler {

    /**
     * Generates multiple TOTP values; the period between the generation of two consecutive OTP values
     * is dictated by the VALIDITY_PERIOD specified in the stored otp item.
     *
     * @param otpCallback {@link TOTPCallback} implementation whose methods will be used to
     *                    notify and return the generated HOTP value
     * @param defaultMacAlgorithm {String value of mac algorithm that will be used in case there is no
     *                            algorithm retrieved from the Otp QR code. Ex. "SHA256"}
     */
    void startGeneratingTOTP(TOTPCallback otpCallback, String defaultMacAlgorithm);

    /**
     * Stops the generation of TOTP items.
     */
    void stop();
}
