// IOperatorTokenService.aidl
package de.telekom.smartcredentials.core;

import de.telekom.smartcredentials.core.IOperatorTokenCallback;

interface IOperatorTokenService {

    void getOperatorToken(String bearerToken, String clientId, String scope,
        IOperatorTokenCallback callback);
}