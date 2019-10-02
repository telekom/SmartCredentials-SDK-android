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

package de.telekom.smartcredentials.authentication.converter;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.TokenResponse;

import de.telekom.smartcredentials.authentication.model.SmartCredentialsAuthenticationTokenResponse;

/**
 * Created by Lucian Iacob on March 05, 2019.
 */
public class Converter {

    public static SmartCredentialsAuthenticationTokenResponse convert(TokenResponse response) {
        return response == null ? null : new SmartCredentialsAuthenticationTokenResponse(
                response.accessToken,
                response.accessTokenExpirationTime,
                response.additionalParameters,
                response.getScopeSet(),
                response.idToken,
                response.refreshToken,
                response.scope,
                response.tokenType);
    }

    public static de.telekom.smartcredentials.core.authentication.AuthorizationException convert(AuthorizationException exception) {
        return exception == null ? null : new de.telekom.smartcredentials.core.authentication.AuthorizationException(
                exception.type,
                exception.code,
                exception.error,
                exception.errorDescription,
                exception.errorUri
        );
    }
}
