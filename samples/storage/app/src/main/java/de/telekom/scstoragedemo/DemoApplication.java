package de.telekom.scstoragedemo;

import android.app.Application;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;

/**
 * Created by Alex.Graur@endava.com at 8/26/2020
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
        SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);
        SecurityApi securityApi = SmartCredentialsSecurityFactory.getSecurityApi();
        SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, securityApi);
    }
}
