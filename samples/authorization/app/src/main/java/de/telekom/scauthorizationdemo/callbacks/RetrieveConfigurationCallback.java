package de.telekom.scauthorizationdemo.callbacks;

import de.telekom.smartcredentials.core.authorization.AuthorizationConfiguration;

public interface RetrieveConfigurationCallback {

    void onConfigurationRetrieved(AuthorizationConfiguration configuration);

    void onFailedToRetrieveConfiguration();
}
