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

import de.telekom.smartcredentials.core.plugins.callbacks.BasePluginCallback;

public abstract class OTPCallback<T> extends BasePluginCallback {

    /**
     * Called when a new OTP value is generated; the value is wrapped into a
     * {@link T} generic type parameter
     *
     * @param otpResponse the response wrapper, containing the OTP value and expiration time
     */
    public abstract void onOTPGenerated(T otpResponse);

    /**
     * Called when something went wrong
     *
     * @param otpPluginError the wrapper over the error message
     */
    public abstract void onFailed(OTPPluginError otpPluginError);
}
