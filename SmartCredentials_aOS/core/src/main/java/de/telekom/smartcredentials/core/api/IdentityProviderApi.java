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

import de.telekom.smartcredentials.core.identityprovider.IdentityProviderCallback;

public interface IdentityProviderApi {

    /**
     * Provides an operator token from the Entitlement Server via a Carrier Privileged application.
     * <p>
     * Using this method without a partner agreement is forbidden.
     * <p>
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     * <p>
     * Should be used when the designated back-end supports standard appToken retrieval endpoints.
     * <p>
     * A result is dispatched to the IdentityProviderCallback containing the operator token, if successful.
     * This is encrypted for the clientId. So the clients
     * public key needs to be known to the Entitlement Server.
     * The content of the operator token is specific for the client.
     * In the Beta phase the following fields are defined
     * - IMSI
     * - pairwise subscription identifier
     * - MSISDN
     *
     * @param context     - context instance
     * @param baseUrl     - base url towards of registered partner back-end
     * @param credentials - partner back-end credentials
     * @param clientId    - clientId for which the calling application was registered
     * @param scope       - scope for which the calling application was registered
     * @param callback    - callback to retrieve the SmartCredentialsApiResponse containing the result
     */
    void getOperatorToken(@NonNull Context context, @NonNull String baseUrl,
                          @NonNull String credentials, @NonNull String clientId,
                          @NonNull String scope, @NonNull IdentityProviderCallback callback);

    /**
     * Provides an operator token from the Entitlement Server via a Carrier Privileged application.
     * <p>
     * Using this method without a partner agreement is forbidden.
     * <p>
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     * <p>
     * Should only be used for custom implementations of appToken retrieval.
     * <p>
     * A result is dispatched to the IdentityProviderCallback containing the operator token, if successful.
     * This is encrypted for the clientId. So the clients
     * public key needs to be known to the Entitlement Server.
     * The content of the operator token is specific for the client.
     * In the Beta phase the following fields are defined
     * - IMSI
     * - pairwise subscription identifier
     * - MSISDN
     *
     * @param context  - context instance
     * @param appToken - valid appToken
     * @param clientId - clientId for which the calling application was registered
     * @param scope    - scope for which the calling application was registered
     * @param callback - callback to retrieve the SmartCredentialsApiResponse containing the result
     */
    void getOperatorToken(@NonNull Context context, @NonNull String appToken,
                          @NonNull String clientId, @NonNull String scope,
                          @NonNull IdentityProviderCallback callback);
}
