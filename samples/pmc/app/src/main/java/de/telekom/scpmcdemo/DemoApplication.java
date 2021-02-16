package de.telekom.scpmcdemo;

import android.app.Application;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.api.StorageApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.pmc.factory.SmartCredentialsPmcFactory;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

public class DemoApplication extends Application {

    public static final String TAG = "pmc_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(getApplicationContext(),
                getString(R.string.current_user_id))
                .setLogger(new DemoLogger())
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .setAppAlias(getString(R.string.app_alias))
                .build();
        CoreApi coreApi = SmartCredentialsCoreFactory.initialize(configuration);
        SecurityApi securityApi = SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);
        StorageApi storageApi = SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, securityApi);
        SmartCredentialsPmcFactory.initSmartCredentialsPmcModule(storageApi);
    }
}
