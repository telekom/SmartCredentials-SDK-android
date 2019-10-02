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

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelopeFactory;
import de.telekom.smartcredentials.core.qrlogin.AuthenticationCallback;
import de.telekom.smartcredentials.core.responses.RootedThrowable;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on November 12, 2018.
 */
public interface QrLoginApi {

    /**
     * Method used to start listening for fingerprint authorization and perform login once the authorization succeeded.
     *
     * @param callback     {@link AuthenticationCallback} for retrieving the success or failure events
     * @param itemEnvelope {@link ItemEnvelope} that will be used when making the login request and the put of the new refresh token
     *                     - the summary should contain the parameters used in the request to log in
     *                     - the details should contain the refresh token model,
     *                     with the {@link ItemEnvelopeFactory} PLACEHOLDER
     *                     instead of the actual token (will pe replaced when new refresh token is saved)
     * @return {@link SmartCredentialsApiResponse} containing a {@link Fragment} (for api below 23)
     * or a {@link DialogFragment} (for api starting with 23)
     * if response is successful, or {@link RootedThrowable} if device is rooted.
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Fragment> getAuthorizeUserLogInFragment(@NonNull AuthenticationCallback callback, ItemEnvelope itemEnvelope);

}
