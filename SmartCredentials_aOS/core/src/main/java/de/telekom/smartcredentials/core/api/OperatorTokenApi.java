package de.telekom.smartcredentials.core.api;

import android.content.Context;

import androidx.annotation.NonNull;

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

public interface OperatorTokenApi {

    /**
     * Returns an operator token from the Entitlement Server.
     * <p>
     * Using this method without a partner agreement is forbidden.
     * <p>
     * This is a privileged operation and in Beta.
     *
     * This request is performed automatically on the carrier network provided via the
     * OperatorTokenConfig, if available and allowed to by the OS (Samsung devices are known to not
     * comply with the specification).
     *
     * @return the operator token. This is encrypted for the clientId. So the clients
     * public key needs to be known to the Entitlement Server.
     * The content of the operator token is specific for the client.
     * In the Beta phase the following fields are defined
     * - IMSI
     * - pairwise subscription identifier
     * - MSISDN
     */
    @NonNull
    SmartCredentialsApiResponse<String> getOperatorToken(@NonNull Context context,
                                                         @NonNull String clientId,
                                                         @NonNull String scope);

    /**
     * Returns an operator token from the Entitlement Server.
     * <p>
     * Using this method without a partner agreement is forbidden.
     * <p>
     * This is a privileged operation and in Beta.
     *
     * This request is performed on the specified subscriptionId. If the provided subscriptionId does
     * not correspond to the carrier network configured through the OperatorTokenConfig, it then
     * automatically switches to the provided carrier network , if available and allowed to by the
     * OS (Samsung devices are known to notcomply with the specification).
     *
     * @return the operator token. This is encrypted for the clientId. So the clients
     * public key needs to be known to the Entitlement Server.
     * The content of the operator token is specific for the client.
     * In the Beta phase the following fields are defined
     * - IMSI
     * - pairwise subscription identifier
     * - MSISDN
     */
    @NonNull
    SmartCredentialsApiResponse<String> getOperatorToken(@NonNull Context context,
                                                         @NonNull String clientId,
                                                         @NonNull String scope,
                                                         int subscriptionId);

}
