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
public interface TokenRefreshListener<T extends AuthenticationTokenResponse> {

    /**
     * Invoked when the request completes successfully or fails.
     * <p>
     * Exactly one of `response` or `exception` will be non-null. If `response` is `null`, a failure
     * occurred during the request. This can happen if a bad URI was provided, no connection
     * to the server could be established, or the response JSON was incomplete or incorrectly
     * formatted.
     *
     * @param response  the retrieved token response, if successful; `null` otherwise.
     * @param exception a description of the failure, if one occurred: `null` otherwise.
     */
    void onTokenRequestCompleted(@Nullable T response,
                                 @Nullable AuthorizationException exception);

    /**
     * Invoked when a step from this operation failed.
     *
     * @param errorDescription contains a description about the error that happened.
     */
    void onFailed(AuthenticationError errorDescription);
}
