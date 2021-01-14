/*
 * Copyright (c) 2020 Telekom Deutschland AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.telekom.smartcredentials.pushnotifications.models;

import android.net.Uri;

import com.google.firebase.messaging.RemoteMessage;

import de.telekom.smartcredentials.core.pushnotifications.models.SmartCredentialsNotification;

/**
 * Created by gabriel.blaj@endava.com at 5/15/2020
 */
public class FirebaseNotification extends SmartCredentialsNotification {

    private RemoteMessage.Notification mNotification;

    FirebaseNotification(RemoteMessage.Notification notification) {
        mNotification = notification;
    }

    @Override
    public String getTitle() {
        return mNotification.getTitle();
    }

    @Override
    public String getBody() {
        return mNotification.getBody();
    }

    @Override
    public String[] getBodyLocalizationArgs() {
        return mNotification.getBodyLocalizationArgs();
    }

    @Override
    public String getBodyLocalizationKey() {
        return mNotification.getBodyLocalizationKey();
    }

    @Override
    public String getChannelId() {
        return mNotification.getChannelId();
    }

    @Override
    public String getClickAction() {
        return mNotification.getClickAction();
    }

    @Override
    public String getColor() {
        return mNotification.getColor();
    }

    @Override
    public String getIcon() {
        return mNotification.getIcon();
    }

    @Override
    public Uri getImageUrl() {
        return mNotification.getImageUrl();
    }

    @Override
    public Uri getLink() {
        return mNotification.getLink();
    }

    @Override
    public String getSound() {
        return mNotification.getSound();
    }

    @Override
    public String getTag() {
        return mNotification.getTag();
    }

    @Override
    public String[] getTitleLocalizationArgs() {
        return mNotification.getTitleLocalizationArgs();
    }

    @Override
    public String getTitleLocalizationKey() {
        return mNotification.getTitleLocalizationKey();
    }
}
