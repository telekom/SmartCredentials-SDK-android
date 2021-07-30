package de.telekom.smartcredentials.core.api;

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Gabriel.Blaj@endava.com at 5/26/2021
 */
@SuppressWarnings("unused")
public interface EntitlementsApi {

    SmartCredentialsApiResponse<String> getAccessToken(String secret);

    SmartCredentialsApiResponse<String> getBearerToken(String accessToken, String packageName);

    SmartCredentialsApiResponse<String> getTransactionToken(String bearerToken, String packageName);
}
