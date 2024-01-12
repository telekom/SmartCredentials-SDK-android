/*
 * Copyright (c) 2023 Telekom Deutschland AG
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

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Gabriel.Blaj@endava.com at 5/26/2021
 */
@SuppressWarnings("unused")
public interface SimApi {

    /**
     * Returns a device token from the Entitlement Server.
     *
     * Using this method without a partner agreement is forbidden.
     *
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     *
     * @return      the device token. This is opaque.
     */
    SmartCredentialsApiResponse<String> getDeviceToken();

    /**
     * Returns a device token from the Entitlement Server.
     *
     * Using this method without a partner agreement is forbidden.
     *
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     *
     * @return      the device token. This is opaque.
     */
    SmartCredentialsApiResponse<String> getDeviceToken(int subscriptionId);

    /**
     * Returns a temporary token as specified in GSMA TS.43 from the Entitlement Server.
     *
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     *
     * @return      the temporary token. This is opaque.
     */
    SmartCredentialsApiResponse<String> getTemporaryToken(String deviceToken);

    /**
     * Returns a temporary token as specified in GSMA TS.43 from the Entitlement Server.
     *
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     *
     * @return      the temporary token. This is opaque.
     */
    SmartCredentialsApiResponse<String> getTemporaryToken(String deviceToken, int subscriptionId);

    /**
     * Returns an operator token from the Entitlement Server.
     *
     * Using this method without a partner agreement is forbidden.
     *
     * This is a privileged operation and in Beta.
     * Install the privileged application SmartAgent.
     * Also, 3rd party applications using this API need to be registered.
     *
     * @return      the operator token. This is encrypted for the clientId. So the clients
     *              public key needs to be known to the Entitlement Server.
     *              The content of the operator token is specific for the client.
     *              In the Beta phase the following fields are defined
     *              - IMSI
     *              - pairwise subscription identifier
     *              - MSISDN
     */
    SmartCredentialsApiResponse<String> getOperatorToken(String clientId, String temporaryToken, String scope);
}