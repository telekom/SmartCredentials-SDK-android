package de.telekom.scdocumentscannerdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.api.SecurityApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.documentscanner.factory.SmartCredentialsDocumentScannerFactory;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 9/14/2020
 */
public class DemoApplication extends Application {

    public static final String TAG = "documentscanner_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Stetho.initializeWithDefaults(this);
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
        SecurityApi securityApi = SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);
        SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, securityApi);
        SmartCredentialsDocumentScannerFactory.initSmartCredentialsDocumentScannerModule(coreApi);
        SmartCredentialsDocumentScannerFactory.setLicense(BuildConfig.MICROBLINK_LICENCE_KEY, this);
    }

}
