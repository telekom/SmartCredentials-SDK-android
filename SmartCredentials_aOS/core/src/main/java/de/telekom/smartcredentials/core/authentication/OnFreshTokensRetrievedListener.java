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

package de.telekom.smartcredentials.core.authentication;

import androidx.annotation.Nullable;

/**
 * Created by Lucian Iacob on March 05, 2019.
 */
public interface OnFreshTokensRetrievedListener {

    /**
     * Invoked when refresh token operation has completed. Executed in the context of fresh
     * (non-expired) tokens. If new tokens were required to execute the action and could not be
     * acquired, an authorization exception is provided instead. One or both of the access token
     * and ID token will be provided, dependent upon the token types previously negotiated.
     *
     * @param accessToken {@link String} representing access token
     * @param idToken     {@link String} representing ID token
     * @param exception   {@link AuthorizationException} encapsulating the error messages occurred
     */
    void onRefreshComplete(@Nullable String accessToken,
                           @Nullable String idToken,
                           @Nullable AuthorizationException exception);

    /**
     * Invoked when a step from this operation failed.
     *
     * @param errorDescription contains a description about the error that happened.
     */
    void onFailed(AuthenticationError errorDescription);
}
