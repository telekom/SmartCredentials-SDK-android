package de.telekom.scpushnotificationsdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import de.telekom.smartcredentials.core.api.CoreApi;
import de.telekom.smartcredentials.core.configurations.SmartCredentialsConfiguration;
import de.telekom.smartcredentials.core.factory.SmartCredentialsCoreFactory;
import de.telekom.smartcredentials.core.pushnotifications.configuration.PushNotificationsConfiguration;
import de.telekom.smartcredentials.core.pushnotifications.enums.ServiceType;
import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;
import de.telekom.smartcredentials.pushnotifications.factory.SmartCredentialsPushNotificationsFactory;
import de.telekom.smartcredentials.security.factory.SmartCredentialsSecurityFactory;
import de.telekom.smartcredentials.storage.factory.SmartCredentialsStorageFactory;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 8/26/2020
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
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
        SmartCredentialsStorageFactory.initSmartCredentialsStorageModule(this, coreApi, SmartCredentialsSecurityFactory.getSecurityApi());

        PushNotificationsConfiguration firebaseConfig = new PushNotificationsConfiguration.ConfigurationBuilder(
                this,
                BuildConfig.FCM_API_KEY,
                BuildConfig.FCM_PROJECT_ID,
                BuildConfig.FCM_DATABASE_URL,
                BuildConfig.FCM_APPLICATION_ID,
                BuildConfig.FCM_GCM_SENDER_ID,
                BuildConfig.FCM_STORAGE_BUCKET,
                ServiceType.FCM)
                .setAutoSubscribeState(false)
                .build();

        SmartCredentialsPushNotificationsFactory.initSmartCredentialsPushNotificationsModule(
                coreApi,
                SmartCredentialsStorageFactory.getStorageApi(),
                firebaseConfig);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.notification_channel_name);
            String description = getString(R.string.notification_channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(name), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}