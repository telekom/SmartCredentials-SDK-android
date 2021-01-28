package de.telekom.scotpdemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

import java.util.Collections;

import de.telekom.smartcredentials.camera.factory.SmartCredentialsCameraFactory;
import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.otp.factory.SmartCredentialsOtpFactory;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 8/31/2020
 */
public class DemoApplication extends Application {

    public static final String TAG = "otp_tag";
    public static final String SHA_1_ALGORITHM = "SHA1";

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
        SmartCredentialsSecurityFactory.initSmartCredentialsSecurityModule(this, coreApi);
        SmartCredentialsCameraFactory.initSmartCredentialsCameraModule(coreApi);
        SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, SmartCredentialsSecurityFactory.getSecurityApi());
        SmartCredentialsOtpFactory.initSmartCredentialsOtpModule(coreApi,
                SmartCredentialsSecurityFactory.getSecurityApi(),
                SmartCredentialsStorageFactory.getStorageApi(),
                SmartCredentialsCameraFactory.getCameraApi());
        SmartCredentialsOtpFactory.addAcceptedAlgorithms(Collections.singletonList(SHA_1_ALGORITHM));
    }
}
