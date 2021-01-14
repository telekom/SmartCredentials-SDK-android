package de.telekom.scauthorizationdemo;

import android.app.Application;

import de.telekom.smartcredentials.authorization.factory.SmartCredentialsAuthorizationFactory;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 9/3/2020
 */
public class DemoApplication extends Application {

    public static final String TAG = "authorization_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        initSmartCredentialsModules();
    }

    private void initSmartCredentialsModules() {
        SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(getApplicationContext(), getString(R.string.current_user_id))
                .setLogger(new DemoLogger())
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .setAppAlias(getString(R.string.app_alias))
                .build();
        SmartCredentialsCoreFactory.initialize(configuration);
        CoreApi coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
        SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);
        SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi,
                SmartCredentialsSecurityFactory.getSecurityApi());
        SmartCredentialsAuthorizationFactory.initSmartCredentialsAuthorizationModule(this,
                coreApi, SmartCredentialsSecurityFactory.getSecurityApi(),
                SmartCredentialsStorageFactory.getStorageApi());
    }
}
