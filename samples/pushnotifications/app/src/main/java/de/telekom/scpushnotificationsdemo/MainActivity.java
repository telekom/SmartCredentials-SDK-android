package de.telekom.scpushnotificationsdemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

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

    private PushNotificationsApi pushNotificationsApi;

    private ImageView subscriptionStateIcon;
    private TextView subscriptionMessage;
    private TextView tokenValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pushNotificationsApi = SmartCredentialsPushNotificationsFactory.getPushNotificationsApi();

        subscriptionStateIcon = findViewById(R.id.subscription_state_icon);
        subscriptionMessage = findViewById(R.id.subscription_message);
        tokenValue = findViewById(R.id.token_value);

        pushNotificationsApi.setMessageReceivedCallback(remoteMessage -> {
            String notificationTitle = remoteMessage.getNotification().getTitle();
            String notificationBody = remoteMessage.getNotification().getBody();
            logMessage(String.format("%s%s\n%s%s", getResources().getString(R.string.notification_title),
                    notificationTitle, getResources().getString(R.string.notification_body), notificationBody));
            displayNotification(notificationTitle, notificationBody);
        });

        Button subscribeButton = findViewById(R.id.subscribe_button);
        subscribeButton.setOnClickListener(v ->
                SmartTask.with(this).assign(() -> pushNotificationsApi.subscribeAllNotifications(new PushNotificationsCallback() {
                    @Override
                    public void onSuccess(String message) {
                            setSubscribedState();
                            logMessage(message);
                    }

                    @Override
                    public void onFailure(String message, List<PushNotificationsError> errors) {
                            setSubscribedState();
                            logMessage(message);
                    }
                })).finish(result -> {}).execute());

        Button unsubscribeButton = findViewById(R.id.unsubscribe_button);
        unsubscribeButton.setOnClickListener(v ->
                SmartTask.with(this).assign(() -> pushNotificationsApi.unsubscribeAllNotifications(new PushNotificationsCallback() {
                    @Override
                    public void onSuccess(String message) {
                        setUnsubscribedState();
                        logMessage(message);
                    }

                    @Override
                    public void onFailure(String message, List<PushNotificationsError> errors) {
                        setUnsubscribedState();
                        logMessage(message);
                    }
                })).finish(result -> {}).execute());

        Button logTokenButton = findViewById(R.id.log_token_button);
        logTokenButton.setOnClickListener(v ->
        {
            String token = pushNotificationsApi.retrieveToken().getData();
            tokenValue.setText(token);
            logMessage(String.format("%s%s",getResources().getString(R.string.registration_token_log_text),token));
        });
    }

    private void setSubscribedState(){
        subscriptionStateIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_subscribed));
        subscriptionMessage.setText(getString(R.string.subscribed));
    }

    private void setUnsubscribedState(){
        subscriptionStateIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_unsubscribed));
        subscriptionMessage.setText(getString(R.string.unsubscribed));
    }

    private void logMessage(String message) {
        Timber.tag(DemoApplication.TAG).d(message);
    }

    private void displayNotification(String title, String body) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),
                getString(R.string.notification_channel_name))
                .setSmallIcon(R.drawable.ic_notification)
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
        int intentFlag = 0;
        if (Build.VERSION.SDK_INT >= 31) {
            intentFlag = PendingIntent.FLAG_MUTABLE;
        }
        return PendingIntent.getActivity(this, 0, intent, intentFlag);
    }
}
