package de.telekom.smartcredentials.core.api;

import de.telekom.smartcredentials.core.responses.SmartCredentialsApiResponse;

/**
 * Created by Gabriel.Blaj@endava.com at 5/26/2021
 */
@SuppressWarnings("unused")
public interface SimApi {

    SmartCredentialsApiResponse<String> getDeviceToken();

    SmartCredentialsApiResponse<String> getTemporaryToken(String deviceToken);

    SmartCredentialsApiResponse<String> getOperatorToken(String clientId, String temporaryToken);
}
