/*
 * Copyright (c) 2020 Telekom Deutschland AG
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

import de.telekom.smartcredentials.core.mobileconnect.callbacks.AuthenticationCallback;
import de.telekom.smartcredentials.core.mobileconnect.callbacks.AuthenticationTokenCallback;
import de.telekom.smartcredentials.core.mobileconnect.callbacks.DiscoveryCallback;
import de.telekom.smartcredentials.core.mobileconnect.callbacks.MsisdnCallback;

/**
 * Created by Alex.Graur@endava.com at 9/11/2020
 */
@SuppressWarnings("unused")
public interface MobileConnectApi {

    @SuppressWarnings("unused")
    void retrieveMsisdn(Context context, MsisdnCallback callback);

    @SuppressWarnings("unused")
    void discover(String msisdn, String ignoreCookies, String redirectUrl, String correlationId,
                  String client, String secret, DiscoveryCallback callback);

    @SuppressWarnings("unused")
    void authenticate(String clientId, String redirectUri, String responseType, String scope,
                      String version, String state, String nonce, String msisdn, String acrValues,
                      String correlationId, AuthenticationCallback callback);

    @SuppressWarnings("unused")
    void authenticationToken(String grantType, String code, String redirectUri,
                             String client, String secret, String correlationId, AuthenticationTokenCallback callback);
}
