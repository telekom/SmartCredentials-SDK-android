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

package de.telekom.smartcredentials.otp.task;

import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.exceptions.EncryptionException;
import de.telekom.smartcredentials.core.model.item.ItemDomainModel;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.otp.OTPHandlerCallback;
import de.telekom.smartcredentials.core.otp.OTPHandlerFailed;
import de.telekom.smartcredentials.otp.otp.OTPFactory;
import de.telekom.smartcredentials.otp.otp.OTPHandler;
import de.telekom.smartcredentials.otp.otp.OTPUpdateCallback;
import de.telekom.smartcredentials.core.storage.TokenRequest;

public class OTPHandlerResolver {

    private final OTPUpdateCallback mOtpUpdateCallback;

    OTPHandlerResolver(OTPUpdateCallback otpUpdateCallback) {
        mOtpUpdateCallback = otpUpdateCallback;
    }

    @SuppressWarnings("unchecked")
    public void resolveHandler(SecurityApi securityApi, StorageApi storageApi,
                               OTPHandlerCallback otpHandlerCallback, ItemDomainModel itemDomainModel) {
        TokenRequest tokenRequest;
        try {
            tokenRequest = storageApi.retrieveTokenRequest(itemDomainModel).getData();
        } catch (EncryptionException e) {
            otpHandlerCallback.onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_ITEM_DECRYPTION_FAILED);
            return;
        }
        if (tokenRequest.getEncryptedModel() != null) {
            try {
                OTPHandler otpHandler = (OTPHandler) getOTPHandler(tokenRequest);
                if (otpHandler != null) {
                    otpHandler.init(securityApi, tokenRequest, mOtpUpdateCallback);
                    otpHandlerCallback.onOTPHandlerReady(otpHandler);
                } else {
                    otpHandlerCallback.onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_HANDLER_NOT_DEFINED);
                }
            } catch (EncryptionException e) {
                otpHandlerCallback.onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_ITEM_DECRYPTION_FAILED);
            }
        } else {
            otpHandlerCallback.onOTPHandlerInitializationFailed(OTPHandlerFailed.OTP_ITEM_NOT_FOUND);
        }
    }

    private Object getOTPHandler(TokenRequest tokenRequest) {
        OTPType otpType = tokenRequest.getOtpType();
        return new OTPFactory().getOTPHandler(otpType);
    }
}
