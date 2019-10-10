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

import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.plugins.authentication.OTPPlugin;
import de.telekom.smartcredentials.core.logger.ApiLoggerResolver;
import de.telekom.smartcredentials.otp.otp.hotp.HOTPHandlerImpl;
import de.telekom.smartcredentials.otp.otp.totp.TOTPHandlerImpl;

public class OTPFactory implements OTPPlugin<OTPHandler> {

    private static final String TAG = "OTPFactory";
    private static final String HANDLER_NOT_MAPPED = "Handler not mapped for %s";
    private static final String NULL_OTP_TYPE = "Could not get handler. OTP type is null";

    @Override
    public OTPHandler getOTPHandler(OTPType otpType) {
        if (otpType != null) {
            switch (otpType) {
                case HOTP:
                    return new HOTPHandlerImpl();
                case TOTP:
                    return new TOTPHandlerImpl();
            }
        }
        ApiLoggerResolver.logError(TAG, NULL_OTP_TYPE);
        return null;
    }
}
