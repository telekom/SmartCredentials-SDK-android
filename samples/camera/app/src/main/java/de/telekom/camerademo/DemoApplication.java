package de.telekom.camerademo;

import android.app.Application;

import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import timber.log.Timber;

/**
 * Created by Alex.Graur@endava.com at 9/1/2020
 */
public class DemoApplication extends Application {

    public static final String TAG = "camera_tag";

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        SmartCredentialsConfiguration configuration = new SmartCredentialsConfiguration.Builder(getApplicationContext(), getString(R.string.user_id))
                .setRootCheckerEnabled(RootDetectionOption.ALL)
                .setAppAlias(getString(R.string.app_alias))
                .build();
        SmartCredentialsCoreFactory.initialize(configuration);
        CoreApi coreApi = SmartCredentialsCoreFactory.getSmartCredentialsCoreApi();
        SmartCredentialsCameraFactory.initSmartCredentialsCameraModule(coreApi);
    }
}
