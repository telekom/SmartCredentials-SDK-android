package de.telekom.authenticationdemo;

import android.app.Application;

import de.telekom.smartcredentials.authentication.factory.SmartCredentialsAuthenticationFactory;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;

/**
 * Created by Alex.Graur@endava.com at 8/31/2020
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(getApplicationContext(), getString(R.string.current_user_id))
                .setLogger(new DemoLogger())
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .setAppAlias(getString(R.string.app_alias))
                .build();
        SmartCredentialsCoreFactory.initialize(configuration);
        CoreApi coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
        SecurityApi securityApi = SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);
        StorageApi storageApi = SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, securityApi);
        SmartCredentialsAuthenticationFactory.initSmartCredentialsAuthenticationModule(coreApi, storageApi);
    }
}
