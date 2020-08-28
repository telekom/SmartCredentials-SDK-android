package de.telekom.scpushnotificationsdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.List;

import de.telekom.smartcredentials.core.api.PushNotificationsApi;
import de.telekom.smartcredentials.core.pushnotifications.callbacks.PushNotificationsCallback;
import de.telekom.smartcredentials.core.pushnotifications.models.PushNotificationsError;
import de.telekom.smartcredentials.pushnotifications.factory.SmartCredentialsPushNotificationsFactory;
import timber.log.Timber;

/**
 * Created by gabriel.blaj@endava.com at 8/26/2020
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "pushnotifications_tag";

    private PushNotificationsApi pushNotificationsApi;

    private ImageView subscriptionStateIcon;
    private TextView tokenValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pushNotificationsApi = SmartCredentialsPushNotificationsFactory.getPushNotificationsApi();

        subscriptionStateIcon = findViewById(R.id.subscription_state_icon);
        tokenValue = findViewById(R.id.token_value);

        pushNotificationsApi.setMessageReceivedCallback(remoteMessage -> {
            String notificationTitle = remoteMessage.getNotification().getTitle();
            String notificationBody = remoteMessage.getNotification().getBody();
            logMessage(String.format("%s%s\n%s%s", getResources().getString(R.string.notification_title),
                    notificationTitle, getResources().getString(R.string.notification_body), notificationBody));
            displayNotification(notificationTitle, notificationBody);
        });

        Button subscribeButton = findViewById(R.id.subscribe_button);
        subscribeButton.setOnClickListener(v -> pushNotificationsApi.subscribeAllNotifications(new PushNotificationsCallback() {
            @Override
            public void onSuccess(String message) {
                subscriptionStateIcon.setImageDrawable(getDrawable(R.drawable.ic_subscribed));
                logMessage(message);
            }

            @Override
            public void onFailure(String message, List<PushNotificationsError> errors) {
                logMessage(message);
            }
        }));
        Button unsubscribeButton = findViewById(R.id.unsubscribe_button);
        unsubscribeButton.setOnClickListener(v -> pushNotificationsApi.unsubscribeAllNotifications(new PushNotificationsCallback() {
            @Override
            public void onSuccess(String message) {
                subscriptionStateIcon.setImageDrawable(getDrawable(R.drawable.ic_unsubscribed));
                logMessage(message);
            }

            @Override
            public void onFailure(String message, List<PushNotificationsError> errors) {
                logMessage(message);
            }
        }));
        Button logTokenButton = findViewById(R.id.log_token_button);
        logTokenButton.setOnClickListener(v ->
        {
            String token = pushNotificationsApi.retrieveToken().getData();
            tokenValue.setText(token);
            logMessage(String.format("%s%s",getResources().getString(R.string.registration_token_log_text),token));
        });
    }

    private void logMessage(String message) {
        Timber.tag(TAG).d(message);
    }

    private void displayNotification(String title, String body) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),
                getString(R.string.notification_channel_name))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                .setContentIntent(getNotificationTapIntent())
                .setChannelId(getString(R.string.notification_channel_name))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification);
    }

    private PendingIntent getNotificationTapIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return PendingIntent.getActivity(this, 0, intent, 0);
    }
}
