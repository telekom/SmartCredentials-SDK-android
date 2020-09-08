package de.telekom.sccoredemo;

import android.app.Application;

import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

/**
 * Created by gabriel.blaj@endava.com at 9/8/2020
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initSmartCredentialsModule();
    }

    private void initSmartCredentialsModule() {
        SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(getApplicationContext(), getString(R.string.current_user_id))
                .setLogger(new DemoLogger())
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .setAppAlias(getString(R.string.app_alias))
                .build();
        SmartCredentialsCoreFactory.initialize(configuration);
    }
}
