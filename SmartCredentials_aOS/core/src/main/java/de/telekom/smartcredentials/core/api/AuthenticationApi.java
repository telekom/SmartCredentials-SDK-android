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
import android.content.Intent;

import androidx.annotation.Nullable;

import de.telekom.smartcredentials.core.authentication.AuthenticationServiceInitListener;
import de.telekom.smartcredentials.core.authentication.AuthenticationTokenResponse;
import de.telekom.smartcredentials.core.authentication.OnFreshTokensRetrievedListener;
import de.telekom.smartcredentials.core.authentication.TokenRefreshListener;
import de.telekom.smartcredentials.core.authentication.configuration.AuthenticationConfiguration;
import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Lucian Iacob on February 27, 2019.
 */
public interface AuthenticationApi {

    /**
     * Initializes all necessary dependencies for specified provider.
     * This will fetch an OpenID Connect discovery document from the issuer in the configuration
     * to configure this instance for use or will use the endpoints specified in the configuration
     * document.
     *
     * @param authenticationConfiguration       The application configuration file which contains the
     *                                          parameters used for the authentication flow.
     * @param authenticationServiceInitListener An {@link AuthenticationServiceInitListener} that will be called
     *                                          once the initialization is complete or fail.
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> initialize(AuthenticationConfiguration authenticationConfiguration,
                                                    AuthenticationServiceInitListener authenticationServiceInitListener);

    /**
     * Logs in a user and acquires authorization tokens for that user. Uses the endpoints from
     * configuration document.
     *
     * @param context          The application context
     * @param completionIntent The intent that will be sent once the login is performed successfully
     * @param cancelIntent     The intent that will be sent if something fails in login process
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> login(Context context, Intent completionIntent, Intent cancelIntent);

    /**
     * Determines whether the current state represents a successful authorization,
     * from which at least either an access token or an ID token have been retrieved.
     *
     * @return {@code true} if a user is logged in and the configuration hasn't changed;
     * {@code false} otherwise
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> isUserLoggedIn();

    /**
     * Refreshes the access token if a refresh token is available to do so. This method will
     * do nothing if there is no refresh token.
     *
     * @param listener An {@link TokenRefreshListener} that will be called once the refresh is complete
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> refreshAccessToken(TokenRefreshListener<AuthenticationTokenResponse> listener);

    /**
     * Performs an authorized action with a fresh access token.
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> performActionWithFreshTokens(OnFreshTokensRetrievedListener listener);

    /**
     * Attempt to retrieve SDK's known user info endpoint
     *
     * @return {@link String} User info endpoint or null if no endpoint is known
     */
    @SuppressWarnings("unused")
    @Nullable
    SmartCredentialsApiResponse<String> getUserInfoEndpointUri();

    /**
     * Logs out the authenticated user
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> logOut();

    /**
     * Disposes state that will not normally be handled by garbage collection. This should be
     * called when this service is no longer required, including when any owning activity is
     * paused or destroyed.
     */
    @SuppressWarnings("unused")
    SmartCredentialsApiResponse<Boolean> destroy();

}
