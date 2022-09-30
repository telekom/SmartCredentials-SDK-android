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

package de.telekom.smartcredentials.core.api;

import android.content.Context;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.camera.CameraScannerLayout;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.otp.HOTPHandler;
import de.telekom.smartcredentials.core.otp.HOTPHandlerCallback;
import de.telekom.smartcredentials.core.otp.OTPImporterCallback;
import de.telekom.smartcredentials.core.otp.TOTPHandler;
import de.telekom.smartcredentials.core.otp.TOTPHandlerCallback;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public interface OtpApi {

    /**
     * Method used to get a HOTP handler that will generate OTPs based on the item id received
     *
     * @param hotpItemId HOTP item id; the item type is considered to be
     *                   OTP items should be stored encrypted, in sensitive repository, with the item type
     *                   {@link OTPType#HOTP}
     *                   so, when retrieving the item, these specs will be taken in consideration.
     * @param callback   {@link HOTPHandlerCallback} for notifying when the {@link HOTPHandler} is ready.
     * @return {@link SmartCredentialsApiResponse} containing a {@link HOTPHandler} if response was successful,
     * or {@link RootedThrowable} if device is rooted
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Void> createHOTPGenerator(@NonNull String hotpItemId, @NonNull HOTPHandlerCallback callback);

    /**
     * Method used to get a TOTP handler that will generate OTPs based on the item id received
     *
     * @param totpItemId HOTP item id; the item type is considered to be
     *                   OTP items should be stored encrypted, in sensitive repository, with the item type
     *                   {@link OTPType#TOTP}
     * @param callback   {@link TOTPHandlerCallback} for notifying when the {@link TOTPHandler} is ready.
     * @return {@link SmartCredentialsApiResponse} containing a {@link TOTPHandler} if response was successful,
     * or {@link RootedThrowable} if device is rooted
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Void> createTOTPGenerator(@NonNull String totpItemId, @NonNull TOTPHandlerCallback callback);

    /**
     * Method used to scan a QR code for OTP, parse the response to extract the OTP item and save it encrypted in the SENSITIVE repository
     *
     * @param itemId   Id that will be set for the newly created item
     * @param callback {@link OTPImporterCallback} for retrieving success or failure events
     * @return {@link SmartCredentialsApiResponse} containing a {@link CameraScannerLayout} if response was successful,
     * or {@link RootedThrowable} if device is rooted
     */
    @SuppressWarnings("unused")
    <V> SmartCredentialsApiResponse<CameraScannerLayout<V>> importOTPItemViaQRForId(@NonNull Context context, @NonNull String itemId, OTPImporterCallback callback);
}
